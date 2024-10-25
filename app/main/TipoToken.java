package main;

/**
 * Enumeración TipoToken que define los distintos tipos de tokens
 * que pueden ser reconocidos por el analizador léxico.
 */
public enum TipoToken {
    IDENTIFICADOR,
    NUMERO,
    SUMA,
    RESTA,
    MULTIPLICACION,
    DIVISION,
    ASIGNACION,
    PUNTO_COMA,
    PARENTESIS_IZQ,
    PARENTESIS_DER
}
