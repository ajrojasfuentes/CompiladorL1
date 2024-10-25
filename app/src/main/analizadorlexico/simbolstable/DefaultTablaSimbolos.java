package CompiladorL1.src.main.analizadorlexico.simbolstable;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase DefaultTablaSimbolos que gestiona una tabla de símbolos.
 * Almacena información sobre los símbolos en un mapa, permitiendo 
 * agregar, obtener, verificar la existencia, mostrar y eliminar símbolos.
 */
public class DefaultTablaSimbolos implements TablaSimbolos {
    private final Map<String, DefaultSymbolInfo> tabla;

    /**
     * Constructor que inicializa la tabla de símbolos.
     */
    public DefaultTablaSimbolos() {
        this.tabla = new HashMap<>();
    }

    public void agregar(String lexema, int linea) {
        tabla.put(lexema, new DefaultSymbolInfo(lexema, linea));
    }

    public DefaultSymbolInfo obtener(String lexema) {
        return tabla.get(lexema);
    }

    public boolean contiene(String lexema) {
        return tabla.containsKey(lexema);
    }

    public void mostrar() {
        tabla.forEach((llave, valor) -> 
            System.out.println("Lexema: " + llave + ", Línea: " + valor.obtenerLinea()));
    }

    public void eliminar(String lexema) {
        tabla.remove(lexema);
    }
}
