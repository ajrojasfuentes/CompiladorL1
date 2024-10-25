package main;

public class miCompilador {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java miCompilador [ARCHIVO DE ENTRADA]");
            return;
        }

        String filename = args[0];
        Lexer analyzer = new Lexer();
        analyzer.analizarLexicamente(filename);
    }
}
