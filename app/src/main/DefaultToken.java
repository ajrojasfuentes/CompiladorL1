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

    @Override
    public String obtenerValor() {
        return llave;
    }

    @Override
    public TipoToken obtenerAtributo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Valor: " + llave + ", Tipo: " + tipo;
    }
}
