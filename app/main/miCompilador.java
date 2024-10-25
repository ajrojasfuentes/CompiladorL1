package main;
import java.util.List;

public class miCompilador {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java miCompilador [ARCHIVO DE ENTRADA]");
            return;
        }

        String filename = args[0];
        Lexer analyzer = new Lexer();
        List<Token> tokens = analyzer.analizarLexicamente(filename);
        if (tokens != null) {
            System.out.println("Analizis léxico completado sin errores");
            Parser parser = new Parser();
            boolean sintax = parser.analizarSintacticamente(tokens);
            if (sintax) {
                System.out.println("Analizis sintáctico completado sin errores");
            }
        }


    }
}
