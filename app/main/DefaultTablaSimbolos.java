package main;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultTablaSimbolos implements TablaSimbolos {
    private List<TablaSimbolosEntrada> tabla;

    public DefaultTablaSimbolos() {
        this.tabla = new ArrayList<>();
    }

    @Override
    public void agregar(String lexema, int linea) {
            tabla.add(new TablaSimbolosEntrada(lexema, linea));
    }

    @Override
    public DefaultSymbolInfo obtenerPorLexema(String lexema) {
        for (TablaSimbolosEntrada entrada : tabla) {
            if (entrada.getLexema().equals(lexema)) {
                return new DefaultSymbolInfo(entrada.getLexema(), entrada.getLinea());
            }
        }
        return null;
    }

    @Override
    public TablaSimbolosEntrada obtenerPorIndice(int indice) {
      return tabla.get(indice);
    }

    @Override
    public boolean contiene(String lexema) {
        for (TablaSimbolosEntrada entrada : tabla) {
            if (entrada.getLexema().equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void eliminar(String lexema) {
        tabla.removeIf(entrada -> entrada.getLexema().equals(lexema));
    }

    @Override
    public void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tabla_de_simbolos.txt"))) {
            for (TablaSimbolosEntrada entrada : tabla) {
                writer.write("Lexema: " + entrada.getLexema() + ", Línea: " + entrada.getLinea());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar la tabla en el archivo: " + e.getMessage());
        }
    }
}
