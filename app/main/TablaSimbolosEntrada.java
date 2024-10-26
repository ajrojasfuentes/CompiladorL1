package main;

/**
 * Clase que representa una entrada en la tabla de símbolos.
 * Esta clase se utiliza para almacenar información relevante
 * acerca de los símbolos identificados durante el análisis léxico.
 */
public class TablaSimbolosEntrada {
    private String lexema;
    private int linea;

    /**
     * Constructor de la clase TablaSimbolosEntrada.
     *
     * @param lexema El lexema que representa el símbolo.
     * @param linea La línea del código fuente donde se encontró el símbolo.
     */
    public TablaSimbolosEntrada(String lexema, int linea) {
        this.lexema = lexema;
        this.linea = linea;
    }

    /**
     * Obtiene el lexema de esta entrada de la tabla de símbolos.
     *
     * @return El lexema asociado a esta entrada.
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * Obtiene la línea donde se encontró el símbolo en el código fuente.
     *
     * @return La línea del código fuente correspondiente a este símbolo.
     */
    public int getLinea() {
        return linea;
    }
}
