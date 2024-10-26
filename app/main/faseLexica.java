package main;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * La clase Lexer se encarga de realizar el análisis léxico de un código fuente.
 * Consta de métodos para inicializar autómatas finitos deterministas (DFA) para diferentes tipos de tokens.
 * También proporciona funcionalidad para leer archivos de código, identificar tokens y manejar errores léxicos.
 */
public class faseLexica {
    private final Map<TipoToken, DFA> dfaMap;
    private final List<Token> tokens;
    private final List<String> errores;
    private final TablaSimbolos tablaSimbolos;
    private static final Logger LOGGER = Logger.getLogger(faseLexica.class.getName());
    private static final String FORMATO_MENSAJE_ERROR = "Error [Fase Lexica]: La linea %d contiene un error, lexema no reconocido: '%s'";
    private static final int MAX_LONGITUD_LEXEMA = 12;

    /**
     * Constructor de la clase Lexer.
     * Inicializa la lista de tokens, la lista de errores y la tabla de símbolos.
     * También configura los DFAs para cada tipo de token.
     */
    public faseLexica(TablaSimbolos tablaSimbolos) {
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
        this.tablaSimbolos = tablaSimbolos;
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
     * Lee el contenido de un archivo de texto ubicado en la ruta especificada y lo devuelve como una cadena.
     *
     * @param filePath La ruta del archivo que se desea leer.
     * @return El contenido del archivo como una cadena. Si ocurre un error durante la lectura, se devolverá una cadena vacía.
     */
    private String leerArchivo(String filePath) {
        StringBuilder contenidoArchivo = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(filePath))) {
            String lineaCodigo;
            while ((lineaCodigo = lector.readLine()) != null) {
                contenidoArchivo.append(lineaCodigo).append(System.lineSeparator());
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return contenidoArchivo.toString();
    }

    /**
     * Lee el contenido de un archivo de texto, eliminando espacios en blanco y tabulaciones redundantes.
     *
     * @param filePath La ruta del archivo que se desea leer.
     * @return Una cadena que representa el contenido del archivo sin espacios en blanco ni tabulaciones redundantes.
     */
    public String leerPrograma(String filePath) {
        String contenidoArchivo = leerArchivo(filePath);
        return contenidoArchivo.trim().replaceAll("[ \t]+", "");
    }

    /**
     * Crea el mensaje de error con el formato especificado.
     *
     * @param token       El token que generó el error.
     * @param numeroLinea El número de línea donde se encontró el error.
     * @return El mensaje de error formateado.
     */
    private String crearMensajeError(String token, int numeroLinea) {
        return String.format(FORMATO_MENSAJE_ERROR, numeroLinea, token);
    }

    /**
     * Reporta un mensaje de error si se encuentra un token no reconocido.
     *
     * @param token       El token que generó el error.
     * @param numeroLinea El número de línea donde se encontró el error.
     */
    public void reportarError(String token, int numeroLinea) {
        String errorMessage = crearMensajeError(token, numeroLinea);
        errores.add(errorMessage);
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
            tablaSimbolos.agregar(token, numeroLinea);
        } else {
            reportarError(token, numeroLinea);
        }
    }

    /**
     * Realiza el análisis léxico de un archivo de código fuente, tokenizando
     * identificadores, dígitos y otros símbolos.
     *
     * @param filePath La ruta del archivo que contiene el código fuente a analizar.
     */
    public List<Token> analizarLexicamente(String filePath) {
        String programa = leerPrograma(filePath);
        String[] lineas = programa.split(System.lineSeparator());
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i]; // Obtener la línea actual
            int numeroLinea = i + 1;  // El número de línea, comenzando desde 1
            StringBuilder lexema = new StringBuilder();
            for (int j = 0; j < linea.length(); j++) {
                char caracter = linea.charAt(j);
                if (Character.isLowerCase(caracter)) {
                    j = procesarIdentificador(linea, lexema, numeroLinea, j);
                } else if (Character.isDigit(caracter)) {
                    j = procesarDigito(linea, lexema, numeroLinea, j);
                } else {
                    lexema.append(caracter);
                    tokenizar(lexema.toString(), numeroLinea);
                    lexema.setLength(0);
                }
            }
        }
        imprimirErrores();
        imprimirTokens();
        return tokens;
    }

    /**
     * Procesa un identificador en una línea de código fuente, acumulando todos los
     * caracteres consecutivos que sean letras minúsculas.
     *
     * @param linea La línea de código fuente que se está procesando.
     * @param lexema El StringBuilder usado para acumular el lexema.
     * @param numeroLinea El número de línea actual que se está procesando.
     * @param j El índice actual en la línea de código fuente.
     * @return El índice actualizado después de procesar el identificador.
     */
    private int procesarIdentificador(String linea, StringBuilder lexema, int numeroLinea, int j) {
        lexema.append(linea.charAt(j));
        while (j + 1 < linea.length() && Character.isLowerCase(linea.charAt(j + 1))) {
            j++;
            lexema.append(linea.charAt(j));
        }
        if (lexema.length() > MAX_LONGITUD_LEXEMA) {
            reportarError(lexema.toString(), numeroLinea);
        } else {
            tokenizar(lexema.toString(), numeroLinea);
        }
        lexema.setLength(0);
        return j;
    }

    /**
     * Procesa un carácter de dígito en una línea de código fuente,
     * acumulando todos los caracteres consecutivos que también sean dígitos.
     *
     * @param linea La línea de código fuente que se está procesando.
     * @param lexema El StringBuilder usado para acumular el lexema.
     * @param numeroLinea El número de línea actual que se está procesando.
     * @param j El índice actual en la línea de código fuente.
     * @return El índice actualizado después de procesar los dígitos.
     */
    private int procesarDigito(String linea, StringBuilder lexema, int numeroLinea, int j) {
        lexema.append(linea.charAt(j));
        while (j + 1 < linea.length() && Character.isDigit(linea.charAt(j + 1))) {
            j++;
            lexema.append(linea.charAt(j));
        }
        tokenizar(lexema.toString(), numeroLinea);
        lexema.setLength(0);
        return j;
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

    public List<String> getErrores() {
        return errores;
    }
}


