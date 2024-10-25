package main;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Map<String, String>> tabla;

    private TablaParseo() {
        this.tabla = crearTablaParseo();
    }

    public static Map<String, Map<String, String>> crearTablaParseo() {
        Map<String, Map<String, String>> tabla = new HashMap<>();
        crearEntradasNoTerminalP(tabla);
        crearEntradasNoTerminalE(tabla);
        crearEntradasNoTerminalC(tabla);
        crearEntradasNoTerminalX(tabla);
        crearEntradasNoTerminalA(tabla);
        crearEntradasNoTerminalT(tabla);
        crearEntradasNoTerminalY(tabla);
        crearEntradasNoTerminalB(tabla);
        crearEntradasNoTerminalF(tabla);
        return tabla;
    }

    private static void crearEntradasNoTerminalP(Map<String, Map<String, String>> tabla) {
        tabla.put(P, new HashMap<>());
        tabla.get(P).put("PARENTESIS_IZQ", "E PUNTO_COMA P");
        tabla.get(P).put("IDENTIFICADOR", "E PUNTO_COMA P");
        tabla.get(P).put("NUMERO", "E PUNTO_COMA P");
        tabla.get(P).put("$", "");// Para ε (vacío)
    }

    private static void crearEntradasNoTerminalE(Map<String, Map<String, String>> tabla) {
        tabla.put(E, new HashMap<>());
        tabla.get(E).put("PARENTESIS_IZQ", "PARENTESIS_IZQ E PARENTESIS_DER Y X");
        tabla.get(E).put("IDENTIFICADOR", "IDENTIFICADOR C");
        tabla.get(E).put("NUMERO", "NUMERO Y X");
    }

    private static void crearEntradasNoTerminalC(Map<String, Map<String, String>> tabla) {
        tabla.put(C, new HashMap<>());
        tabla.get(C).put("ASIGNACION", "ASIGNACION E");
        tabla.get(C).put("PUNTO_COMA", "Y X");
        tabla.get(C).put("SUMA", "Y X");
        tabla.get(C).put("RESTA", "Y X");
        tabla.get(C).put("MULTIPLICACION", "Y X");
        tabla.get(C).put("DIVISION", "Y X");
        tabla.get(C).put("PARENTESIS_DER", "Y X");
    }

    private static void crearEntradasNoTerminalX(Map<String, Map<String, String>> tabla) {
        tabla.put(X, new HashMap<>());
        tabla.get(X).put("SUMA", "A T X");
        tabla.get(X).put("RESTA", "A T X");
        tabla.get(X).put("PUNTO_COMA", ""); // Para ε (vacío)
        tabla.get(X).put("PARENTESIS_DER", ""); // Para ε (vacío)
    }

    private static void crearEntradasNoTerminalA(Map<String, Map<String, String>> tabla) {
        tabla.put(A, new HashMap<>());
        tabla.get(A).put("SUMA", "SUMA");
        tabla.get(A).put("RESTA", "RESTA");
    }

    private static void crearEntradasNoTerminalT(Map<String, Map<String, String>> tabla) {
        tabla.put(T, new HashMap<>());
        tabla.get(T).put("IDENTIFICADOR", "F Y");
        tabla.get(T).put("NUMERO", "F Y");
        tabla.get(T).put("PARENTESIS_IZQ", "F Y");
    }

    private static void crearEntradasNoTerminalY(Map<String, Map<String, String>> tabla) {
        tabla.put(Y, new HashMap<>());
        tabla.get(Y).put("MULTIPLICACION", "B F Y");
        tabla.get(Y).put("DIVISION", "B F Y");
        tabla.get(Y).put("SUMA", ""); // Para ε (vacío)
        tabla.get(Y).put("RESTA", ""); // Para ε (vacío)
        tabla.get(Y).put("PUNTO_COMA", ""); // Para ε (vacío)
        tabla.get(Y).put("PARENTESIS_DER", ""); // Para ε (vacío)
    }

    private static void crearEntradasNoTerminalB(Map<String, Map<String, String>> tabla) {
        tabla.put(B, new HashMap<>());
        tabla.get(B).put("MULTIPLICACION", "MULTIPLICACION");
        tabla.get(B).put("DIVISION", "DIVISION");
    }

    private static void crearEntradasNoTerminalF(Map<String, Map<String, String>> tabla) {
        tabla.put(F, new HashMap<>());
        tabla.get(F).put("IDENTIFICADOR", "IDENTIFICADOR");
        tabla.get(F).put("NUMERO", "NUMERO");
        tabla.get(F).put("PARENTESIS_IZQ", "PARENTESIS_IZQ E PARENTESIS_DER");
    }

    public static String obtenerProduccion(String simboloPila, String simboloEntrada) {
        Map<String, Map<String, String>> tabla = crearTablaParseo();
        if (tabla.containsKey(simboloPila)) {
            Map<String, String> producciones = tabla.get(simboloPila);
            if (producciones.containsKey(simboloEntrada)) {
                return producciones.get(simboloEntrada);
            }
        }
        return null;
    }
}


