package main;

/**
 * Interfaz DFA que define la estructura básica de un autómata finito determinista.
 * Contiene métodos para agregar transiciones y para determinar si una cadena es aceptada por el autómata.
 */
public interface DFA {
    /**
     * Agrega una transición al autómata.
     * 
     * @param desdeEstado Estado desde el cual se realiza la transición.
     * @param inputChar   Carácter de entrada que provoca la transición.
     * @param alEstado    Estado al que se transiciona.
     */
    void agregarTransicion(int desdeEstado, char inputChar, int alEstado);

    /**
     * Determina si una cadena de entrada es aceptada por el autómata.
     * 
     * @param input Cadena de entrada a evaluar.
     * @return true si la cadena es aceptada, false en caso contrario.
     */
    boolean acepta(String input);
}
