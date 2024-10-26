package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class miCompilador {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java miCompilador [ARCHIVO DE ENTRADA]");
            return;
        }

        String filename = args[0];
        DefaultTablaSimbolos tablaSimbolos = new DefaultTablaSimbolos();
        Lexer lexer = new Lexer(tablaSimbolos);
        Parser parser = new Parser(tablaSimbolos);
        List<String> erroresLexer;
        List<String> erroresParser;
        List<Token> tokens;


        tokens = lexer.analizarLexicamente(filename);
        erroresLexer = lexer.getErrores();

        if (erroresLexer.isEmpty()) {
            System.out.println("--------------------------------------");
            System.out.println("Analizis léxico completado sin errores");
            System.out.println("--------------------------------------");
            boolean sintax = parser.analizarSintacticamente(tokens);
            if (sintax) {
                System.out.println("------------------------------------------");
                System.out.println("Analizis sintáctico completado sin errores");
                System.out.println("------------------------------------------");
            }
        }

        erroresParser = parser.getErrores();

        tablaSimbolos.guardarEnArchivo();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Compilador_out.txt"))) {
            if(erroresLexer.isEmpty()) {
                for (Token token : tokens) {
                    writer.write(String.valueOf(token));
                    writer.newLine();
                }
            }
            for (String error : erroresLexer) {
                writer.write(String.valueOf(error));
                writer.newLine();
            }
            for (String error : erroresParser) {
                writer.write(String.valueOf(error));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de salida del Lexer: " + e.getMessage());
        }
    }
}
