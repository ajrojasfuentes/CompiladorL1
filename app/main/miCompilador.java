package main;
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


        List<Token> tokens = lexer.analizarLexicamente(filename);
        if (lexer.getErrores().isEmpty()) {
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

        tablaSimbolos.guardarEnArchivo();
    }
}
