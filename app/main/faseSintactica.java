package main;

import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

/**
 * The Parser class handles syntactic analysis based on a predefined parsing table.
 * It leverages a stack-based approach to compare input tokens against expected symbols
 * according to production rules.
 */
public class faseSintactica {
    private final TablaParseo tabla;
    private final Stack<String> pila;
    private final TablaSimbolos tablaSimbolos;
    private final List<String> errores;
    private static final String SIMBOLO_CIERRE = "$";
    int indiceTablaSimbolos = 0;

    public faseSintactica(TablaSimbolos tablaSimbolos) {
        this.tabla = new TablaParseo();
        this.pila = new Stack<>();
        this.tablaSimbolos = tablaSimbolos;
        this.errores = new ArrayList<>();
    }

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

    public List<String> getErrores() {
        return errores;
    }

    private void reportarError(String mensaje) {
        System.err.println(mensaje);
        errores.add(mensaje);
    }


}