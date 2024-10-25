package main;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a parsing table for a context-free grammar.
 * It is used to determine the next set of production rules to apply
 * based on the current non-terminal symbol and the next input symbol.
 */
class TablaParseo {
    private static final String P = "P";
    private static final String E = "E";
    private static final String C = "C";
    private static final String X = "X";
    private static final String A = "A";
    private static final String T = "T";
    private static final String Y = "Y";
    private static final String B = "B";
    private static final String F = "F";
    private static final String VACIO = ""; // Para ε (vacío)

    private final Map<String, Map<String, String>> tabla;

    public TablaParseo() {
        this.tabla = crearTablaParseo();
    }

    public static Map<String, Map<String, String>> crearTablaParseo() {
        Map<String, Map<String, String>> tabla = new HashMap<>();
        agregarEntradasNoTerminal(tabla, P, new String[][]{
                {"PARENTESIS_IZQ", "E PUNTO_COMA P"},
                {"IDENTIFICADOR", "E PUNTO_COMA P"},
                {"NUMERO", "E PUNTO_COMA P"},
                {"$", VACIO}
        });
        agregarEntradasNoTerminal(tabla, E, new String[][]{
                {"PARENTESIS_IZQ", "PARENTESIS_IZQ E PARENTESIS_DER Y X"},
                {"IDENTIFICADOR", "IDENTIFICADOR C"},
                {"NUMERO", "NUMERO Y X"}
        });
        agregarEntradasNoTerminal(tabla, C, new String[][]{
                {"ASIGNACION", "ASIGNACION E"},
                {"PUNTO_COMA", "Y X"},
                {"SUMA", "Y X"},
                {"RESTA", "Y X"},
                {"MULTIPLICACION", "Y X"},
                {"DIVISION", "Y X"},
                {"PARENTESIS_DER", "Y X"}
        });
        agregarEntradasNoTerminal(tabla, X, new String[][]{
                {"SUMA", "A T X"},
                {"RESTA", "A T X"},
                {"PUNTO_COMA", VACIO},
                {"PARENTESIS_DER", VACIO}
        });
        agregarEntradasNoTerminal(tabla, A, new String[][]{
                {"SUMA", "SUMA"},
                {"RESTA", "RESTA"}
        });
        agregarEntradasNoTerminal(tabla, T, new String[][]{
                {"IDENTIFICADOR", "F Y"},
                {"NUMERO", "F Y"},
                {"PARENTESIS_IZQ", "F Y"}
        });
        agregarEntradasNoTerminal(tabla, Y, new String[][]{
                {"MULTIPLICACION", "B F Y"},
                {"DIVISION", "B F Y"},
                {"SUMA", VACIO},
                {"RESTA", VACIO},
                {"PUNTO_COMA", VACIO},
                {"PARENTESIS_DER", VACIO}
        });
        agregarEntradasNoTerminal(tabla, B, new String[][]{
                {"MULTIPLICACION", "MULTIPLICACION"},
                {"DIVISION", "DIVISION"}
        });
        agregarEntradasNoTerminal(tabla, F, new String[][]{
                {"IDENTIFICADOR", "IDENTIFICADOR"},
                {"NUMERO", "NUMERO"},
                {"PARENTESIS_IZQ", "PARENTESIS_IZQ E PARENTESIS_DER"}
        });
        return tabla;
    }

    private static void agregarEntradasNoTerminal(Map<String, Map<String, String>> tabla, String noTerminal, String[][] entradas) {
        tabla.put(noTerminal, new HashMap<>());
        for (String[] entrada : entradas) {
            tabla.get(noTerminal).put(entrada[0], entrada[1]);
        }
    }

    public String obtenerProduccion(String simboloPila, String simboloEntrada) {
        return tabla.getOrDefault(simboloPila, new HashMap<>()).get(simboloEntrada);
    }

    public String SimbolosEsperados(String noTerminal) {
        if (tabla.containsKey(noTerminal)) {
            Map<String, String> producciones = tabla.get(noTerminal);

            // Concatenar los símbolos de entrada con el separador "|"
            String resultado = String.join(" | ", producciones.keySet());
            return resultado;
        } else {
            System.out.println("Error: Simbolo '" + noTerminal + "' no encontrado en la tabla de parseo.");
            return null;
        }
    }
}


