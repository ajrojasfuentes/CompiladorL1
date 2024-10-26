package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase representa una tabla de parseo para una gramática libre de contexto.
 * Se utiliza para determinar el próximo conjunto de reglas de producción a aplicar
 * basado en el símbolo no terminal actual y el siguiente símbolo de entrada.
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

    /**
     * Esta clase representa una tabla de parseo para una gramática libre de contexto.
     * Se utiliza para determinar el próximo conjunto de reglas de producción a aplicar
     * basado en el símbolo no terminal actual y el siguiente símbolo de entrada.
     */
    public TablaParseo() {
        this.tabla = crearTablaParseo();
    }

    /**
     * Crea y retorna la tabla de parseo.
     * La tabla es un mapa que asocia símbolos no terminales a mapas, los cuales a su vez
     * asocian símbolos de entrada con sus correspondientes producciones.
     *
     * @return La tabla de parseo.
     */
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

    /**
     * Agrega las entradas correspondientes a un no terminal a la tabla de parseo.
     *
     * @param tabla   La tabla de parseo donde se agregarán las entradas.
     * @param noTerminal El símbolo no terminal para el cual se agregarán las entradas.
     * @param entradas Un arreglo de entradas, donde cada entrada es un arreglo de dos elementos:
     *                 el símbolo de entrada y la producción correspondiente.
     */
    private static void agregarEntradasNoTerminal(Map<String, Map<String, String>> tabla, String noTerminal, String[][] entradas) {
        tabla.put(noTerminal, new HashMap<>());
        for (String[] entrada : entradas) {
            tabla.get(noTerminal).put(entrada[0], entrada[1]);
        }
    }

    /**
     * Obtiene la producción a aplicar para un par dado de símbolos.
     *
     * @param simboloPila    El símbolo no terminal en la cima de la pila de parseo.
     * @param simboloEntrada El siguiente símbolo de entrada.
     * @return La producción correspondiente, o null si no hay ninguna producción definida para el par dado.
     */
    public String obtenerProduccion(String simboloPila, String simboloEntrada) {
        return tabla.getOrDefault(simboloPila, new HashMap<>()).get(simboloEntrada);
    }

    /**
     * Retorna los símbolos de entrada esperados para un no terminal dado.
     *
     * @param noTerminal El símbolo no terminal para el cual se desean conocer los símbolos de entrada esperados.
     * @return Una cadena de texto con los símbolos de entrada esperados, separados por comas.
     */
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


