package CompiladorL1.src.main.analizadorlexico.token;

/**
 * Interfaz Token que define los métodos que deben implementar
 * las clases de tokens en el analizador léxico.
 */
public interface Token {
    /**
     * Obtiene el valor del token, que es una representación
     * en forma de cadena del token.
     *
     * @return El valor del token.
     */
    String obtenerValor();

    /**
     * Obtiene el tipo de atributo del token, que indica la
     * categoría del token (identificador, número, operador, etc.).
     *
     * @return El tipo de token.
     */
    TipoToken obtenerAtributo();

    /**
     * Representación en forma de cadena del token, que puede
     * incluir su valor y tipo.
     *
     * @return La representación en forma de cadena del token.
     */
    @Override
    String toString();
}