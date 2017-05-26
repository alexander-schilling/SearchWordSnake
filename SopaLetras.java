/* -*- coding: cp1252 -*-
 Esta clase se encarga de almacenar los datos de cada sopa de letra y buscar
 cada palabra en la sopa, además guarda cada coordenada recorrida. En ella se
 incluye una clase interna para cada palabra a encontrar y así facilitar el
 trabajo con estados.
 Los print están escritos en inglés por comodidad y orden, ya que me disgusta
 que las palabras estén sin sus signos de puntuación correspondientes.
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 1.0 | Fecha: 26/05/2017 */
package BestPackage;

// BLOQUE DE IMPORTACIÓN
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.List;

// BLOQUE PRINCIPAL
public class SopaLetras {

    // Variables nativas
    private final int id;
    private List<String> soup = new ArrayList();
    private List<String> words = new ArrayList();
    private final List<List<Integer>> estados = new ArrayList();
    private final List<SopaLetras.Palabra> data = new ArrayList();

    // Constructores
    public SopaLetras() {
        id = 0;
        soup = Arrays.asList("rruanw", "aeojot", "caeeri", "aspere", "eioiuq");
        words = Arrays.asList("casa", "hipopotamo", "carruaje", "perrito");
    }

    public SopaLetras(int id, List<String> soup, List<String> words) {
        this.id = id;
        this.soup = soup;
        this.words = words;
    }

    // Clase interna que se crea para cada palabra a encontrar.
    public class Palabra {

        private final String word;
        private boolean inSoup;
        private final Stack<List<Integer>> open = new Stack();
        private final List<List<Integer>> closed = new ArrayList();

        private Palabra(String word) {
            this.word = word;
        }

        // Métodos Privados
        // Función que verifica que la siguiente letra superior no esté
        // fuera de la matriz, que no esté en los estados cerrados, y
        // que la letra superior coincida con la letra correspondiente.
        // Entrada: Enteros de la posición y la iteración de la letra.
        // Salida: Booleano dependiendo de lo mencionado.
        private boolean superior(int x, int y, int i) {
            return x - 1 >= 0
                    && closed.contains(Arrays.asList(x - 1, y)) == false
                    && soup.get(x - 1).charAt(y) == word.charAt(i);
        }

        // Función que verifica que la siguiente letra derecha no esté
        // fuera de la matriz, que no esté en los estados cerrados, y
        // que la letra superior coincida con la letra correspondiente.
        // Entrada: Enteros de la posición y la iteración de la letra.
        // Salida: Booleano dependiendo de lo mencionado.
        private boolean right(int x, int y, int i) {
            return y + 1 < soup.get(0).length()
                    && closed.contains(Arrays.asList(x, y + 1)) == false
                    && soup.get(x).charAt(y + 1) == word.charAt(i);
        }

        // Función que verifica que la siguiente letra inferior no esté
        // fuera de la matriz, que no esté en los estados cerrados, y
        // que la letra superior coincida con la letra correspondiente.
        // Entrada: Enteros de la posición y la iteración de la letra.
        // Salida: Booleano dependiendo de lo mencionado.
        private boolean inferior(int x, int y, int i) {
            return x + 1 < soup.size()
                    && closed.contains(Arrays.asList(x + 1, y)) == false
                    && soup.get(x + 1).charAt(y) == word.charAt(i);
        }

        // Función que verifica que la siguiente letra izquierda no esté
        // fuera de la matriz, que no esté en los estados cerrados, y
        // que la letra superior coincida con la letra correspondiente.
        // Entrada: Enteros de la posición y la iteración de la letra.
        // Salida: Booleano dependiendo de lo mencionado.
        private boolean left(int x, int y, int i) {
            return y - 1 >= 0
                    && closed.contains(Arrays.asList(x, y - 1)) == false
                    && soup.get(x).charAt(y - 1) == word.charAt(i);
        }

        // Función que se encarga de buscar y formar los estados de la palabra,
        // en esta se realizan las verificaciones de las funciones anteriores.
        // Entrada: -
        // Salida: Genera los estados del stack abierto, genera los estados
        // cerrados y retorna un booleano si se encuentra la palabra o no.
        private boolean form() {
            while (open.empty() == false) {
                if (open.peek().get(2) == word.length() - 1) {
                    return true;
                } else {
                    int x = open.peek().get(0);
                    int y = open.peek().get(1);
                    int i = open.peek().get(2);
                    if (superior(x, y, i + 1)) {
                        open.push(Arrays.asList(x - 1, y, i + 1));
                        if (closed.contains(Arrays.asList(x, y)) == false) {
                            closed.add(Arrays.asList(x, y));
                        }
                    } else if (right(x, y, i + 1)) {
                        open.push(Arrays.asList(x, y + 1, i + 1));
                        if (closed.contains(Arrays.asList(x, y)) == false) {
                            closed.add(Arrays.asList(x, y));
                        }
                    } else if (inferior(x, y, i + 1)) {
                        open.push(Arrays.asList(x + 1, y, i + 1));
                        if (closed.contains(Arrays.asList(x, y)) == false) {
                            closed.add(Arrays.asList(x, y));
                        }
                    } else if (left(x, y, i + 1)) {
                        open.push(Arrays.asList(x, y - 1, i + 1));
                        if (closed.contains(Arrays.asList(x, y)) == false) {
                            closed.add(Arrays.asList(x, y));
                        }
                    } else {
                        if (closed.contains(Arrays.asList(x, y)) == false) {
                            closed.add(Arrays.asList(x, y));
                        }
                        open.pop();
                    }
                }
            }
            return false;
        }

        // Función que inicia la búsqueda de la palabra en la sopa, busca la
        // primera letra en esta, luego la forma con los estados siguientes.
        // Entrada: -
        // Salida: Booleano si encuentra la palabra o no.
        private boolean search() {
            boolean tempBool = false;
            eliminarIncorrectos();
            for (int i = 0; i < soup.size(); i++) {
                for (int j = 0; j < soup.get(0).length(); j++) {
                    if (soup.get(i).charAt(j) == word.charAt(0)) {
                        open.push(Arrays.asList(i, j, 0));
                        tempBool = form();
                        if (tempBool) {
                            return true;
                        }
                    }
                }
            }
            return tempBool;
        }

        // Función que ingresa los estados de las letras en la sopa que no
        // están en la palabra a buscar.
        // Entrada: -
        // Salida: Algunos estados cerrados de la palabra.
        private void eliminarIncorrectos() {
            int i = 0;
            while (i < estados.size()) {
                int x = estados.get(i).get(0);
                int y = estados.get(i).get(1);
                if (word.indexOf(soup.get(x).charAt(y)) >= 0) {
                    i += 1;
                } else {
                    closed.add(Arrays.asList(x, y));
                    i += 1;
                }
            }
        }

        // Función que obtiene un String con la solución de la palabra.
        // Entrada: -
        // Salida: Solución de la palabra en un String.
        private String getCoords() {
            List<List<Integer>> list = new ArrayList<>(open);
            String str = "  " + word + ": ";
            if (list.isEmpty()) {
                str += "Not in the puzzle.";
            } else {
                str += "In the puzzle with coords: <";
            }
            for (int i = 0; i < list.size(); i++) {
                String x = String.valueOf(list.get(i).get(0) + 1);
                String y = String.valueOf(list.get(i).get(1) + 1);
                str += "[" + x + ", " + y + "] ";
                if (i == list.size() - 1) {
                    str += ">";
                }
            }
            return str;
        }

        // Función que ejecuta la búsqueda de la solución.
        private void executeSolution() {
            inSoup = search();
        }
    }

    // Métodos Públicos
    // Función que crea un arreglo para cada palabra a encontrar, con la clase
    // "Palabra".
    // Entrada: -
    // Salida: Llenado de la lista data (lista de clase "Palabra").
    public void crearPalabras() {
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            Palabra tempWord = new Palabra(w);
            data.add(tempWord);
        }
    }

    // Función que obtiene un arreglo de Strings, ya sea de las filas de
    // la sopa de letras o las filas de las palabras a encontrar en esta.
    // Entrada: String con el tipo de archivo que se pedirá.
    // Salida: Lista de Strings con las filas del archivo.
    public void crearEstados() {
        for (int i = 0; i < soup.size(); i++) {
            for (int j = 0; j < soup.get(0).length(); j++) {
                estados.add(Arrays.asList(i, j));
            }
        }
    }

    // Función que crea una lista con las filas de la solución de la sopa
    // de letras, donde incluye su identificador y soluciones.
    // Entrada: -
    // Salida: Lista de Strings con las filas del archivo.
    public List<String> solution() {
        String idS = String.valueOf(id + 1);
        List<String> list = new ArrayList<>();
        list.add("Solution file " + idS + ":");
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getCoords());
        }
        return (list);
    }

    // Función que inicializa la función para la búsqueda de soluciones de
    // todas las sopas de letras ingresadas (o la sopa ingresada).
    // Entrada: -
    // Salida: Ejecución de métodos.
    public void init() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).executeSolution();
        }
    }
}
