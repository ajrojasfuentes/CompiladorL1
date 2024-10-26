package main;

/**
 * Clase DefaultSymbolInfo que implementa la interfaz SymbolInfo.
 * Esta clase representa la información de un símbolo en el analizador léxico,
 * incluyendo su lexema y el número de línea donde se encuentra.
 */
public class DefaultSymbolInfo implements InfoSimbolos {
    private final String lexema;
    private final int lineaCodigo;

     /**
     * Constructor de la clase DefaultSymbolInfo.
     * 
     * @param lexemaActual Lexema del símbolo actual.
     * @param lineaActual   Número de línea donde se encuentra el símbolo.
     */
    public DefaultSymbolInfo(String lexemaActual, int lineaActual) {
        this.lexema = lexemaActual;
        this.lineaCodigo = lineaActual;
    }

    /**
     * Obtiene el lexema asociado a este símbolo.
     *
     * @return el lexema del símbolo.
     */
    @Override
    public String obtenerLexema() {
        return lexema;
    }

    /**
     * Obtiene el número de línea donde se encuentra este símbolo en el código.
     *
     * @return Número de línea del símbolo.
     */
    @Override
    public int obtenerLinea() {
        return lineaCodigo;
    }

    /**
     * Devuelve una representación en cadena del objeto DefaultSymbolInfo.
     *
     * @return Cadena que contiene el lexema y el número de línea del símbolo.
     */
    @Override
    public String toString() {
        return "Lexema: " + lexema + ", Línea: " + lineaCodigo;
    }
}
