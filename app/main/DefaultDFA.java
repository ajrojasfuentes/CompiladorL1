package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase DefaultDFA que implementa la interfaz DFA.
 * Esta clase representa un autómata finito determinista (DFA) que puede
 * manejar transiciones entre estados basadas en caracteres de entrada.
 */
public class DefaultDFA implements DFA {
    private final Map<Integer, Map<Character, Integer>> transiciones;
    private final int estadoInicial;
    private final int estadoAceptacion;

    /**
     * Constructor de la clase DefaultDFA.
     * Inicializa el estado inicial y el estado de aceptación, así como el mapa de transiciones.
     *
     * @param estadoInicial  Estado inicial del DFA.
     * @param estadoAceptacion Estado de aceptación del DFA.
     */
    public DefaultDFA(int estadoInicial, int estadoAceptacion) {
        this.estadoInicial = estadoInicial;
        this.estadoAceptacion = estadoAceptacion;
        this.transiciones = new HashMap<>();
    }

    /**
     * Método para agregar una transición entre estados.
     * Si el estado inicial no existe en el mapa de transiciones, se crea una nueva entrada.
     *
     * @param desdeEstado Estado desde el cual comienza la transición.
     * @param inputChar Caracter de entrada que provoca la transición.
     * @param alEstado Estado al cual se realiza la transición.
     */
    public void agregarTransicion(int desdeEstado, char inputChar, int alEstado) {
        transiciones.putIfAbsent(desdeEstado, new HashMap<>());
        transiciones.get(desdeEstado).put(inputChar, alEstado);
    }

    /**
     * Determina si una cadena de entrada es aceptada por el DFA (Autómata Finito Determinista).
     *
     * @param input Cadena de entrada a evaluar.
     * @return true si la cadena es aceptada, false en caso contrario.
     */
    public boolean acepta(String input) {
        int estadoActual = estadoInicial;
        for (char ch : input.toCharArray()) {
            if (transiciones.containsKey(estadoActual) && transiciones.get(estadoActual).containsKey(ch)) {
                estadoActual = transiciones.get(estadoActual).get(ch);
            } else {
                return false;
            }
        }
        return estadoActual == estadoAceptacion;
    }
}
