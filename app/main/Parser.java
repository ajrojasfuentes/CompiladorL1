package main;


import java.util.Stack;

import main.Token;

import java.util.List;


public class Parser {

    private List<Token> tokens;  // Lista de tokens generada por el lexer
    private int index;  // Índice para rastrear el token actual
    private Stack<String> pila;  // Pila de símbolos de la gramática

    // Constructor
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        this.pila = new Stack<>();
    }

}
