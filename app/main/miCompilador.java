package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * miCompilador es una clase que simula el funcionamiento de un compilador simple.
 * Realiza análisis léxico y sintáctico de un archivo de entrada y genera un archivo de salida con los resultados.
 */
public class miCompilador {

    /**
     * Punto de entrada del programa.
     * Se esperan dos argumentos: la ruta del archivo de entrada y la ruta del archivo de salida.
     *
     * @param args Argumentos de línea de comandos: [RUTA DE ARCHIVO DE ENTRADA] [RUTA DE ARCHIVO DE SALIDA]
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java miCompilador [RUTA DE ARCHIVO DE ENTRADA] [RUTA DE ARCHIVO DE SALIDA]");
            return;
        }

        String archivoEntrada = args[0];
        String archivoSalida = args[1]; // Ruta del archivo de salida
        DefaultTablaSimbolos tablaSimbolos = new DefaultTablaSimbolos();
        faseLexica faseLexica = new faseLexica(tablaSimbolos);
        faseSintactica faseSintactica = new faseSintactica(tablaSimbolos);
        List<String> erroresLexer;
        List<String> erroresParser;
        List<Token> tokens;

        tokens = faseLexica.analizarLexicamente(archivoEntrada);
        erroresLexer = faseLexica.getErrores();

        List<Token> tokensCopia = new ArrayList<>(tokens);

        if (erroresLexer.isEmpty()) {
            System.out.println("--------------------------------------");
            System.out.println("Análisis léxico completado sin errores");
            System.out.println("--------------------------------------");
            boolean sintax = faseSintactica.analizarSintacticamente(tokens);
            if (sintax) {
                System.out.println("------------------------------------------");
                System.out.println("Análisis sintáctico completado sin errores");
                System.out.println("------------------------------------------");
            }
        }

        erroresParser = faseSintactica.getErrores();
        tablaSimbolos.guardarEnArchivo();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida))) {
            if (erroresLexer.isEmpty()) {
                for (Token token : tokensCopia) {
                    writer.write(String.valueOf(token));
                    writer.newLine();
                }
            }
            for (String error : erroresLexer) {
                writer.write(error);
                writer.newLine();
            }
            for (String error : erroresParser) {
                writer.write(error);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de salida: " + e.getMessage());
        }
    }
}
