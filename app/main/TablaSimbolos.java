package main;

/**
 * Interfaz TablaSimbolos que define las operaciones básicas para 
 * gestionar una tabla de símbolos en un analizador léxico.
 */
public interface TablaSimbolos {
    
    /**
     * Agrega un nuevo símbolo a la tabla.
     * 
     * @param lexema El lexema del símbolo.
     * @param linea  La línea donde se encuentra el símbolo en el código.
     */
    void agregar(String lexema, int linea);
    
    /**
     * Obtiene la información del símbolo asociado a un lexema.
     * 
     * @param lexema El lexema del símbolo a buscar.
     * @return La información del símbolo, o null si no se encuentra.
     */
    DefaultSymbolInfo obtener(String lexema);
    
    /**
     * Verifica si un lexema existe en la tabla de símbolos.
     * 
     * @param lexema El lexema a verificar.
     * @return true si el lexema existe, false en caso contrario.
     */
    boolean contiene(String lexema);
    
    /**
     * Muestra todos los símbolos y sus respectivas líneas en la tabla.
     */
    void mostrar();
    
    /**
     * Elimina un símbolo de la tabla.
     * 
     * @param lexema El lexema del símbolo a eliminar.
     */
    void eliminar(String lexema);
}