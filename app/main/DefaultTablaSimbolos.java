package main;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La clase DefaultTablaSimbolos implementa la interfaz TablaSimbolos y proporciona
 * una implementación básica de una tabla de símbolos.
 */
public class DefaultTablaSimbolos implements TablaSimbolos {
    private List<TablaSimbolosEntrada> tabla;

    /**
     * Constructor que inicializa una nueva tabla de símbolos vacía.
     */
    public DefaultTablaSimbolos() {
        this.tabla = new ArrayList<>();
    }

    /**
     * Agrega una nueva entrada a la tabla de símbolos.
     *
     * @param lexema El lexema que identifica la entrada.
     * @param linea La línea de código donde aparece el lexema.
     */
    @Override
    public void agregar(String lexema, int linea) {
            tabla.add(new TablaSimbolosEntrada(lexema, linea));
    }

    /**
     * Obtiene la información del símbolo asociado a un lexema dado.
     *
     * @param lexema El lexema del símbolo a buscar.
     * @return Información del símbolo si se encuentra, de lo contrario devuelve null.
     */
    @Override
    public DefaultSymbolInfo obtenerPorLexema(String lexema) {
        for (TablaSimbolosEntrada entrada : tabla) {
            if (entrada.getLexema().equals(lexema)) {
                return new DefaultSymbolInfo(entrada.getLexema(), entrada.getLinea());
            }
        }
        return null;
    }

    /**
     * Obtiene la entrada de la tabla de símbolos por su índice.
     *
     * @param indice El índice de la entrada a obtener.
     * @return La entrada de la tabla de símbolos en el índice especificado.
     */
    @Override
    public TablaSimbolosEntrada obtenerPorIndice(int indice) {
      return tabla.get(indice);
    }

    /**
     * Verifica si un determinado lexema está presente en la tabla de símbolos.
     *
     * @param lexema El lexema a verificar.
     * @return true si la tabla de símbolos contiene el lexema, false en caso contrario.
     */
    @Override
    public boolean contiene(String lexema) {
        for (TablaSimbolosEntrada entrada : tabla) {
            if (entrada.getLexema().equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina una entrada de la tabla de símbolos basado en su lexema.
     *
     * @param lexema El lexema del símbolo a eliminar.
     */
    @Override
    public void eliminar(String lexema) {
        tabla.removeIf(entrada -> entrada.getLexema().equals(lexema));
    }

    /**
     * Elimina una entrada de la tabla de símbolos basado en la línea de código.
     *
     * @param numeroLinea La línea de código del símbolo a eliminar.
     */
    @Override
    public void eliminar(int numeroLinea) {
        tabla.removeIf(entrada -> entrada.getLinea() == numeroLinea);
    }

    /**
     * Guarda el contenido de la tabla de símbolos en un archivo de texto.
     */
    @Override
    public void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tablaDeSimbolos.txt"))) {
            for (TablaSimbolosEntrada entrada : tabla) {
                writer.write("Lexema: " + entrada.getLexema() + ", Línea: " + entrada.getLinea());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar la tabla en el archivo: " + e.getMessage());
        }
    }
}
