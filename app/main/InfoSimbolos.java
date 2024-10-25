package main;

/**
 * Interfaz InfoSimbolos que define la estructura para la información de un símbolo.
 * Incluye métodos para obtener el lexema y la línea del código donde se encuentra el símbolo.
 */
public interface InfoSimbolos {
     /**
     * Obtiene el lexema asociado a este símbolo.
     * 
     * @return Lexema del símbolo.
     */
    String obtenerLexema();

    /**
     * Obtiene el número de línea donde se encuentra este símbolo en el código.
     * 
     * @return Número de línea del símbolo.
     */
    int obtenerLinea();
}
