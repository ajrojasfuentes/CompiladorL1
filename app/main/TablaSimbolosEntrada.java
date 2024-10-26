package main;

/**
 * Clase que representa una entrada en la tabla de símbolos.
 */
public class TablaSimbolosEntrada {
    private String lexema;
    private int linea;

    public TablaSimbolosEntrada(String lexema, int linea) {
        this.lexema = lexema;
        this.linea = linea;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLinea() {
        return linea;
    }
}
