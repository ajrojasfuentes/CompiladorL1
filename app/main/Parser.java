package main;

import java.util.Stack;
import java.util.List;

/**
 * The Parser class handles syntactic analysis based on a predefined parsing table.
 * It leverages a stack-based approach to compare input tokens against expected symbols
 * according to production rules.
 */
public class Parser {
    private final TablaParseo tabla;
    private final Stack<String> pila;
    private static final String SIMBOLO_CIERRE = "$";

    public Parser() {
        this.tabla = new TablaParseo();
        this.pila = new Stack<>();
    }

    public boolean analizarSintacticamente(List<Token> tokens) {
        pila.push("P");
        while (!tokens.isEmpty()) {
            String simboloActual = pila.pop();
            Token primerToken = tokens.getFirst();
            String simboloEntrada = primerToken.obtenerAtributo().toString();
            if (!compararSimbolos(simboloActual, simboloEntrada, tokens)) return false;
        }
        return compararSimbolos(pila.pop(), SIMBOLO_CIERRE, tokens) && pila.isEmpty() && tokens.isEmpty();
    }

    private boolean compararSimbolos(String simboloActual, String simboloEntrada, List<Token> tokens) {
        imprimirSimbolos(simboloActual, simboloEntrada);
        if (esTerminal(simboloActual)) {
            if (simboloActual.equals(simboloEntrada)) {
                tokens.removeFirst();
            } else {
                reportarError("Error: Se esperaba " + simboloActual + " pero se encontró " + simboloEntrada);
                return false;
            }
        } else {
            String produccion = tabla.obtenerProduccion(simboloActual, simboloEntrada);
            if (produccion == null) {
                if (simboloEntrada.equals(SIMBOLO_CIERRE)) {
                    reportarError("Error: Se esperaba PUNTO_COMA pero se encontró " + SIMBOLO_CIERRE);
                    return false;
                }
                reportarError("Error: Se esperaba " + tabla.SimbolosEsperados(simboloActual) + " pero se encontró " + simboloEntrada);
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

    private void imprimirSimbolos(String simboloActual, String simboloEntrada) {
        System.out.println("|--> simboloPila: " + simboloActual);
        System.out.println("|--> simboloEntrada: " + simboloEntrada);
    }

    private boolean esTerminal(String simboloActual) {
        try {
            TipoToken.valueOf(simboloActual);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void reportarError(String mensaje) {
        System.err.println(mensaje);
    }
}