package main;

/**
 * Clase DefaultToken que implementa la interfaz Token.
 * Esta clase representa un token en el analizador léxico,
 * incluyendo su valor (lexema) y su tipo.
 */
public class DefaultToken implements Token {
    private final String llave;
    private final TipoToken tipo;

    /**
     * Constructor de la clase DefaultToken.
     * 
     * @param llave  Valor del token (lexema).
     * @param tipo Tipo del token (TokenType).
     */
    public DefaultToken(String llave, TipoToken tipo) {
        this.llave = llave;
        this.tipo = tipo;
    }

    /**
     * Obtiene el valor del token, que es una representación
     * en forma de cadena del token.
     *
     * @return El valor del token.
     */
    @Override
    public String obtenerValor() {
        return llave;
    }

    /**
     * Obtiene el tipo de atributo del token, que indica la
     * categoría del token (identificador, número, operador, etc.).
     *
     * @return El tipo de token.
     */
    @Override
    public TipoToken obtenerAtributo() {
        return tipo;
    }

    /**
     * Returns the string representation of the DefaultToken object.
     * The string contains the value and the type of the token.
     *
     * @return String representation of the DefaultToken object.
     */
    @Override
    public String toString() {
        return "Valor: " + llave + ", Tipo: " + tipo;
    }
}
