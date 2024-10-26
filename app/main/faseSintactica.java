package main;

import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

/**
 * La clase faseSintactica se encarga del análisis sintáctico basado en una tabla de parseo predefinida.
 * Utiliza un enfoque basado en pila para comparar tokens de entrada contra símbolos esperados
 * de acuerdo con las reglas de producción.
 */
public class faseSintactica {
    private final TablaParseo tabla;
    private final Stack<String> pila;
    private final TablaSimbolos tablaSimbolos;
    private final List<String> errores;
    private static final String SIMBOLO_CIERRE = "$";
    int indiceTablaSimbolos = 0;

    /**
     * Constructor para la clase faseSintactica.
     *
     * @param tablaSimbolos La tabla de símbolos usada durante el análisis sintáctico.
     */
    public faseSintactica(TablaSimbolos tablaSimbolos) {
        this.tabla = new TablaParseo();
        this.pila = new Stack<>();
        this.tablaSimbolos = tablaSimbolos;
        this.errores = new ArrayList<>();
    }

    /**
     * Realiza el análisis sintáctico de una lista de tokens proporcionada.
     *
     * @param tokens La lista de tokens a analizar.
     * @return true si el análisis sintáctico se completa sin errores, false en caso contrario.
     */
    public boolean analizarSintacticamente(List<Token> tokens) {
        pila.push("P");
        while (!tokens.isEmpty()) {
            String simboloActual = pila.pop();
            Token primerToken = tokens.getFirst();
            String simboloEntrada = primerToken.obtenerAtributo().toString();
            if (!compararSimbolos(simboloActual, simboloEntrada, tokens)) {
                reportarError("Se han eliminado las coincidencias de error detectadas de la tabla de símbolos en la linea: " + tablaSimbolos.obtenerPorIndice(indiceTablaSimbolos-1).getLinea());
                tablaSimbolos.eliminar(tablaSimbolos.obtenerPorIndice(indiceTablaSimbolos-1).getLinea());
                return false;
            }
        }
        return compararSimbolos(pila.pop(), SIMBOLO_CIERRE, tokens) && pila.isEmpty() && tokens.isEmpty();
    }

    /**
     * Compares and processes the current symbol with the input symbol during syntactic analysis.
     *
     * @param simboloActual   The current symbol being analyzed.
     * @param simboloEntrada  The next input symbol from the token list.
     * @param tokens          The list of tokens to be processed.
     * @return true if the symbols match or if a valid production is applied; false otherwise.
     */
    private boolean compararSimbolos(String simboloActual, String simboloEntrada, List<Token> tokens) {
        //imprimirSimbolos(simboloActual, simboloEntrada);
        if (esTerminal(simboloActual)) {
            if (simboloActual.equals(simboloEntrada)) {
                tokens.removeFirst();
                indiceTablaSimbolos++;
            } else {
                reportarError("Error [Fase Sintáctica]: La línea " + tablaSimbolos.obtenerPorIndice(indiceTablaSimbolos-1).getLinea() + " contiene un error en su gramática, falta posible token " + simboloActual);
                return false;
            }
        } else {
            String produccion = tabla.obtenerProduccion(simboloActual, simboloEntrada);
            if (produccion == null) {
                if (simboloEntrada.equals(SIMBOLO_CIERRE)) {
                    reportarError("Error [Fase Sintáctica]: La línea " + tablaSimbolos.obtenerPorIndice(indiceTablaSimbolos-1).getLinea() + " contiene un error en su gramática, falta posible token " + tabla.SimbolosEsperados(simboloActual));
                    return false;
                }
                reportarError("Error [Fase Sintáctica]: La línea " + tablaSimbolos.obtenerPorIndice(indiceTablaSimbolos-1).getLinea() + " contiene un error en su gramática, falta posible token " + tabla.SimbolosEsperados(simboloActual));
                return false;
            } else if (!produccion.isEmpty()) {
                String[] simbolos = produccion.split(" ");
                for (int i = simbolos.length - 1; i >= 0; i--) {
                    pila.push(simbolos[i]);
                }
            }
        }
        return true;
    }

    /**
     * Imprime los símbolos actuales de la pila y de entrada.
     *
     * @param simboloActual  El símbolo actual de la pila.
     * @param simboloEntrada El símbolo actual de entrada.
     */
    private void imprimirSimbolos(String simboloActual, String simboloEntrada) {
        System.out.println("|--> simboloPila: " + simboloActual);
        System.out.println("|--> simboloEntrada: " + simboloEntrada);
    }

    /**
     * Verifica si el símbolo actual es un terminal en la gramática.
     *
     * @param simboloActual El símbolo que se va a verificar.
     * @return true si el símbolo actual es un terminal, false en caso contrario.
     */
    private boolean esTerminal(String simboloActual) {
        try {
            TipoToken.valueOf(simboloActual);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Obtiene la lista de errores ocurridos durante el análisis sintáctico.
     *
     * @return Una lista de cadenas que describen los errores encontrados.
     */
    public List<String> getErrores() {
        return errores;
    }

    /**
     * Registra un mensaje de error y lo añade a la lista de errores.
     *
     * @param mensaje El mensaje de error que debe ser registrado.
     */
    private void reportarError(String mensaje) {
        System.err.println(mensaje);
        errores.add(mensaje);
    }

}