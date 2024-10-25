package main;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;


/**
 * Clase faseLexica que implementa un analizador léxico para el lenguaje L1.
 * Esta clase se encarga de leer el código fuente, identificar y tokenizar
 * los elementos léxicos, así como registrar errores y gestionar una tabla de símbolos.
 */
public class Lexer {
    private final Map<TipoToken, DFA> dfaMap;
    private final List<Token> tokens;
    private final List<String> errores;
    private final DefaultTablaSimbolos tablaSimbolos;

    /**
     * Constructor de la clase Lexer.
     * Inicializa la lista de tokens, la lista de errores y la tabla de símbolos.
     * También configura los DFAs para cada tipo de token.
     */
    public Lexer() {
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
        this.tablaSimbolos = new DefaultTablaSimbolos();

        Map<TipoToken, DFA> mydfaMap = new EnumMap<>(TipoToken.class);
        mydfaMap.put(TipoToken.IDENTIFICADOR, inicializarIdentificadorDFA());
        mydfaMap.put(TipoToken.NUMERO, inicializarDigitoDFA());
        mydfaMap.put(TipoToken.ASIGNACION, inicializarAsignacionDFA());
        mydfaMap.put(TipoToken.SUMA, inicializarSumaDFA());
        mydfaMap.put(TipoToken.RESTA, inicializarRestaDFA());
        mydfaMap.put(TipoToken.MULTIPLICACION, inicializarMultiplicacionDFA());
        mydfaMap.put(TipoToken.DIVISION, inicializarDivisionDFA());
        mydfaMap.put(TipoToken.PARENTESIS_IZQ, inicializarParentesisIzqDFA());
        mydfaMap.put(TipoToken.PARENTESIS_DER, inicializarParentesisDerDFA());
        mydfaMap.put(TipoToken.PUNTO_COMA, inicializarPuntoYComaDFA());

        this.dfaMap = mydfaMap;
    }

    /**
     * Inicializa el DFA para identificar identificadores (letras minúsculas).
     *
     * @return El DFA configurado para identificadores.
     */
    private DFA inicializarIdentificadorDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        for (char ch = 'a'; ch <= 'z'; ch++) {
            dfa.agregarTransicion(0, ch, 1);
            dfa.agregarTransicion(1, ch, 1);
        }
        return dfa;
    }

    /**
     * Inicializa el DFA para identificar números (cadenas de dígitos).
     *
     * @return El DFA configurado para números.
     */
    private DFA inicializarDigitoDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        for (char ch = '0'; ch <= '9'; ch++) {
            dfa.agregarTransicion(0, ch, 1);
            dfa.agregarTransicion(1, ch, 1);
        }
        return dfa;
    }

    /**
     * Inicializa el DFA para el operador de asignación '='.
     *
     * @return El DFA configurado para la asignación.
     */
    private DFA inicializarAsignacionDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, '=', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el operador de suma '+'.
     *
     * @return El DFA configurado para la suma.
     */
    private DFA inicializarSumaDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, '+', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el operador de resta '-'.
     *
     * @return El DFA configurado para la resta.
     */
    private DFA inicializarRestaDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, '-', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el operador de multiplicación '*'.
     *
     * @return El DFA configurado para la multiplicación.
     */
    private DFA inicializarMultiplicacionDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, '*', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el operador de división '/'.
     *
     * @return El DFA configurado para la división.
     */
    private DFA inicializarDivisionDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, '/', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el paréntesis izquierdo '('.
     *
     * @return El DFA configurado para el paréntesis izquierdo.
     */
    private DFA inicializarParentesisIzqDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, '(', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el paréntesis derecho ')'.
     *
     * @return El DFA configurado para el paréntesis derecho.
     */
    private DFA inicializarParentesisDerDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, ')', 1);
        return dfa;
    }

    /**
     * Inicializa el DFA para el punto y coma ';'.
     *
     * @return El DFA configurado para el punto y coma.
     */
    private DFA inicializarPuntoYComaDFA() {
        DFA dfa = new DefaultDFA(0, 1);
        dfa.agregarTransicion(0, ';', 1);
        return dfa;
    }

    /**
     * Lee el contenido de un archivo de código fuente y lo devuelve como una cadena.
     *
     * @param filePath Ruta del archivo a leer.
     * @return El contenido del archivo como una cadena.
     */
    public String leerPrograma(String filePath) {
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String codeLine;

            // Leer cada línea y agregarla al StringBuilder
            while ((codeLine = reader.readLine()) != null) {
                contenido.append(codeLine).append(System.lineSeparator());  // Agrega un espacio entre líneas para mantener separación
            }

        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.info(e.getMessage());
        }

        return contenido.toString().trim().replaceAll("[ \t]+", "");
    }

    /**
     * Envía un mensaje de error si se encuentra un token no reconocido.
     *
     * @param token El token que generó el error.
     * @param numeroLinea El número de línea donde se encontró el error.
     */
    public void enviarError(String token, int numeroLinea) {
        String errorMessage = String.format("Error [Fase Lexica]: La linea %d contiene un error, lexema no reconocido: '%s'", numeroLinea, token);
        errores.add(errorMessage);
        tablaSimbolos.agregar(token, numeroLinea);
    }

    /**
     * Tokeniza un lexema dado, determinando su tipo utilizando los DFAs.
     *
     * @param token El lexema a tokenizar.
     * @param numeroLinea El número de línea del token.
     */
    public void tokenizar(String token, int numeroLinea) {
        Optional<TipoToken> tipoToken = Arrays.stream(TipoToken.values())
                .filter(type -> {
                    DFA dfa = dfaMap.get(type);
                    if (dfa != null) {
                        return dfa.acepta(token);
                    }
                    return false;
                })
                .findFirst();

        if (tipoToken.isPresent()) {
            tokens.add(new DefaultToken(token, tipoToken.get()));
        } else {
            enviarError(token, numeroLinea);
        }

    }

    /**
     * Realiza el análisis léxico de una expresión dada, identificando
     * los tokens y verificando la validez de los paréntesis.
     *
     * @param filePath La ruta del archivo con la entrada a analizar.
     */
    public void lexear(String filePath) {

        String programa = leerPrograma(filePath);

        String[] lineas = programa.split(System.lineSeparator());

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i]; // Obtener la línea actual
            int numeroLinea = i + 1;  // El número de línea, comenzando desde 1

            // Inicializar variables para el lexema y el recorrido de caracteres
            StringBuilder lexema = new StringBuilder();

            // Segundo ciclo que recorre la línea carácter por carácter
            for (int j = 0; j < linea.length(); j++) {
                char caracter = linea.charAt(j);

                // Verificar si es una letra minúscula
                if (Character.isLowerCase(caracter)) {
                    lexema.append(caracter); // Concatenar al lexema
                    // Continuar mientras el siguiente caracter sea también una letra minúscula
                    while (j + 1 < linea.length() && Character.isLowerCase(linea.charAt(j + 1))) {
                        j++; // Avanzar al siguiente carácter
                        lexema.append(linea.charAt(j));
                    }
                    if (lexema.length() > 12){
                        enviarError (lexema.toString(), numeroLinea);
                        break;
                    }
                    tokenizar(lexema.toString(), numeroLinea);
                    lexema.setLength(0); // Reiniciar el lexema después de guardarlo
                }
                // Verificar si es un dígito
                else if (Character.isDigit(caracter)) {
                    lexema.append(caracter); // Concatenar al lexema
                    // Continuar mientras el siguiente carácter sea también un dígito
                    while (j + 1 < linea.length() && Character.isDigit(linea.charAt(j + 1))) {
                        j++; // Avanzar al siguiente carácter
                        lexema.append(linea.charAt(j));
                    }
                    tokenizar(lexema.toString(), numeroLinea);
                    lexema.setLength(0); // Reiniciar el lexema después de guardarlo
                }
                // Si el carácter es otro, lo tratamos como un lexema independiente
                else {
                    lexema.append(caracter);
                    tokenizar(lexema.toString(), numeroLinea);
                    lexema.setLength(0);
                }
            }
        }

        imprimirErrores();
        imprimirTokens();
        showtablaSimbolos();
    }

    /**
     * Imprime los tokens generados en el análisis léxico.
     * Solo imprime si no se han encontrado errores.
     */
    public void imprimirTokens() {
        if(errores.isEmpty()) {
            for (Token token : tokens) {
                System.out.println(token);
            }
        }
    }

    /**
     * Imprime los errores encontrados durante el análisis léxico.
     */
    public void imprimirErrores() {
        for (String error : errores) {
            System.err.println(error);
        }
    }

    /**
     * Muestra la tabla de símbolos.
     */
    public void showtablaSimbolos() {
        tablaSimbolos.mostrar();
    }
}
