/* -*- coding: cp1252 -*-
 El programa tiene la capacidad de leer arreglos (entregados manualmente o por archivo) con un formato específico y calcular el área
 de triángulos blancos, estos están dentro de un triángulo más grande que contiene triángulos blancos (-) y negros (#).
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 0.5 | Fecha: 26/04/2017 */
package BestPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.List;

public class SopaLetras {

    // Variables nativas
    private List<String> soup = new ArrayList();
    private List<String> words = new ArrayList();
    private final List<List<Integer>> estados = new ArrayList();
    private final List<SopaLetras.Palabra> data = new ArrayList();

    // Constructores
    public SopaLetras() {
        soup = Arrays.asList("rruanw", "aeojot", "caeeri", "aspere", "eioiuq");
        words = Arrays.asList("casa", "hipopotamo", "carruaje", "perrito");
    }

    public SopaLetras(List<String> soup, List<String> words) {
        this.soup = soup;
        this.words = words;
    }

    public class Palabra {

        private final String word;
        private boolean inSoup;
        private final Stack<List<Integer>> open = new Stack();
        private final List<List<Integer>> closed = new ArrayList();

        private Palabra(String word) {
            this.word = word;
        }

        // Métodos Privados
        private boolean superior(int x, int y, int i) {
            return x - 1 >= 0
                    && closed.contains(Arrays.asList(x - 1, y)) == false
                    && soup.get(x - 1).charAt(y) == word.charAt(i);
        }

        private boolean right(int x, int y, int i) {
            return y + 1 < soup.get(0).length()
                    && closed.contains(Arrays.asList(x, y + 1)) == false
                    && soup.get(x).charAt(y + 1) == word.charAt(i);
        }

        private boolean inferior(int x, int y, int i) {
            return x + 1 < soup.size()
                    && closed.contains(Arrays.asList(x + 1, y)) == false
                    && soup.get(x + 1).charAt(y) == word.charAt(i);
        }

        private boolean left(int x, int y, int i) {
            return y - 1 >= 0
                    && closed.contains(Arrays.asList(x, y - 1)) == false
                    && soup.get(x).charAt(y - 1) == word.charAt(i);
        }

        private boolean form() {
            while (open.empty() == false) {
                // remover el primer estado X de la lista open
                if (open.peek().get(2) == word.length() - 1) {
                    return true;
                } else {
                    // generar el conjunto de sucesores del estado X
                    // agregar el estado X al conjunto closed
                    // eliminar sucesores que ya están en open o en closed
                    // agregar el resto de los sucesores al principio de open
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

        private void isInSoup() {
            if (inSoup) {
                System.out.println("La palabra " + word
                        + " está en la sopa.");
            } else {
                System.out.println("La palabra " + word
                        + " no está en la sopa.");
            }
        }

        private void printCoords() {
            Stack<List<Integer>> copy = new Stack<>();
            copy.addAll(open);
            System.out.print(word + ": ");
            if (copy.empty() == true) {
                System.out.print("No está en la sopa.");
            }
            while (copy.empty() == false) {
                System.out.print(Arrays.toString(copy.peek().toArray()));
                copy.pop();
                System.out.print(" ");
            }
            System.out.println();
        }

        private void executeSolution() {
            inSoup = search();
        }
    }

    // Métodos Privados
    // Métodos Públicos
    public void crearPalabras() {
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            Palabra tempWord = new Palabra(w);
            data.add(tempWord);
        }
    }

    public void crearEstados() {
        for (int i = 0; i < soup.size(); i++) {
            for (int j = 0; j < soup.get(0).length(); j++) {
                estados.add(Arrays.asList(i, j));
            }
        }
    }

    public void printEstados() {
        for (int i = 0; i < estados.size(); i++) {
            System.out.print(Arrays.toString(estados.get(i).toArray()));
            System.out.print(" ");
        }
        System.out.println();
    }

    public void printSolutionsCoords() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).printCoords();
        }
    }

    public void printSolutions() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).isInSoup();
        }
    }

    public void init() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).executeSolution();
        }
    }
}
