/* -*- coding: cp1252 -*-
 El programa tiene la capacidad de leer arreglos (entregados manualmente o por archivo) con un formato específico y calcular el área
 de triángulos blancos, estos están dentro de un triángulo más grande que contiene triángulos blancos (-) y negros (#).
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 0.5 | Fecha: 26/04/2017 */
package BestPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SopaLetrasOLD {

    // Variables nativas
    private List<String> soup = new ArrayList<>();
    private List<String> words = new ArrayList<>();
    private List<SopaLetrasOLD.Palabra> data = new ArrayList<>();

    // Constructores
    public SopaLetrasOLD() {
        soup = Arrays.asList("rruanw", "aeojot", "caeeri", "aspere", "eioiuq");
        words = Arrays.asList("casa", "hipopotamo", "carruaje", "perrito");
    }

    public SopaLetrasOLD(List<String> soup, List<String> words) {
        this.soup = soup;
        this.words = words;
    }

    public class Palabra {

        private final String word;
        private boolean inSoup;
        private List<List<Integer>> coords = new ArrayList<>();

        public Palabra(String word) {
            this.word = word;
        }

        // Métodos Privados
        private boolean superior(int x, int y, int i) {
            return x - 1 >= 0
                    && coords.contains(Arrays.asList(x - 1, y)) == false
                    && soup.get(x - 1).charAt(y) == word.charAt(i);
        }

        private boolean right(int x, int y, int i) {
            return y + 1 < soup.get(0).length()
                    && coords.contains(Arrays.asList(x, y + 1)) == false
                    && soup.get(x).charAt(y + 1) == word.charAt(i);
        }

        private boolean inferior(int x, int y, int i) {
            return x + 1 < soup.size()
                    && coords.contains(Arrays.asList(x + 1, y)) == false
                    && soup.get(x + 1).charAt(y) == word.charAt(i);
        }

        private boolean left(int x, int y, int i) {
            return y - 1 >= 0
                    && coords.contains(Arrays.asList(x, y - 1)) == false
                    && soup.get(x).charAt(y - 1) == word.charAt(i);
        }

        private boolean noSolution(int x, int y, int i) {
            return (!superior(x, y, i) && !right(x, y, i)
                    && !inferior(x, y, i) && !left(x, y, i));
        }

        private boolean forms(int x, int y, int i) {
            if (i < word.length() && noSolution(x, y, i)) {
                coords.remove(coords.size() - 1);
                return formr(x + 1, y, i - 1);
            } else if (i < word.length()) {
                coords.add(Arrays.asList(x, y));
                if (superior(x, y, i)) {
                    return forms(x - 1, y, i + 1);
                } else {
                    coords.remove(coords.size() - 1);
                    return formr(x, y, i);
                }
            } else {
                coords.add(Arrays.asList(x, y));
                return true;
            }
        }

        private boolean formr(int x, int y, int i) {
            if (i < word.length() && noSolution(x, y, i)) {
                coords.remove(coords.size() - 1);
                return formi(x, y - 1, i - 1);
            } else if (i < word.length()) {
                coords.add(Arrays.asList(x, y));
                if (right(x, y, i)) {
                    return forms(x, y + 1, i + 1);
                } else {
                    coords.remove(coords.size() - 1);
                    return formi(x, y, i);
                }
            } else {
                coords.add(Arrays.asList(x, y));
                return true;
            }
        }

        private boolean formi(int x, int y, int i) {
            if (i < word.length() && noSolution(x, y, i)) {
                coords.remove(coords.size() - 1);
                return forml(x - 1, y, i - 1);
            } else if (i < word.length()) {
                coords.add(Arrays.asList(x, y));
                if (inferior(x, y, i)) {
                    return forms(x + 1, y, i + 1);
                } else {
                    coords.remove(coords.size() - 1);
                    return forml(x, y, i);
                }
            } else {
                coords.add(Arrays.asList(x, y));
                return true;
            }
        }

        private boolean forml(int x, int y, int i) {
            if (i < word.length() && noSolution(x, y, i)) {
                coords.remove(coords.size() - 1); // not really necessary
                return false;
            } else if (i < word.length()) {
                coords.add(Arrays.asList(x, y));
                if (left(x, y, i)) {
                    return forms(x, y - 1, i + 1);
                } else {
                    coords.remove(coords.size() - 1);
                    return forms(x, y, i);
                }
            } else {
                coords.add(Arrays.asList(x, y));
                return true;
            }
        }

        private boolean search() {
            boolean tempBool = false;
            for (int i = 0; i < soup.size(); i++) {
                for (int j = 0; j < soup.get(0).length(); j++) {
                    if (soup.get(i).charAt(j) == word.charAt(0)) {
                        coords = new ArrayList<>();
                        tempBool = forms(i, j, 1);
                        if (tempBool) {
                            return true;
                        }
                    }
                }
            }
            return tempBool;
        }

        public void isInSoup() {
            boolean isIt = search();
            if (isIt) {
                System.out.println("La palabra " + word
                        + " está en la sopa.");
            } else {
                System.out.println("La palabra " + word
                        + " no está en la sopa.");
            }
        }

        public void printCoords() {
            System.out.print(word + ": ");
            if (coords.isEmpty()) {
                System.out.print("No está en la sopa.");
            }
            for (int i = 0; i < coords.size(); i++) {
                System.out.print(Arrays.toString(coords.get(i).toArray()));
                System.out.print(" ");
            }
            System.out.println();
        }

        public void printWord() {
            System.out.println(word);
        }

        public void editNamesCoords(List<List<Integer>> coords) {
            this.coords = coords;
        }

        public void editInSoup(boolean inSoup) {
            this.inSoup = inSoup;
        }

        public void editCoordsList(List<List<Integer>> temp) {
            this.coords = temp;
        }

        public List<List<Integer>> getCoords() {
            return coords;
        }

        public boolean getInSoup() {
            return inSoup;
        }

        public List<List<Integer>> getCoordsList() {
            return coords;
        }
    }

    // Métodos Privados
    public void crearPalabras() {
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            Palabra tempWord = new Palabra(w);
            data.add(tempWord);
        }
    }

    // Métodos Públicos
    public void printSolutionsCoords() {
        for (int i = 0; i < words.size(); i++) {
            data.get(i).printCoords();
        }
    }

    public void printSolutions() {
        for (int i = 0; i < words.size(); i++) {
            data.get(i).isInSoup();
        }
    }

    public void editSoup(List<String> soup) {
        this.soup = soup;
    }

    public void editWords(List<String> words) {
        this.words = words;
    }

    public void editData(List<Palabra> data) {
        this.data = data;
    }

    public List<String> getSoup() {
        return soup;
    }

    public List<String> getWords() {
        return words;
    }

    public List<Palabra> getData() {
        return data;
    }
}
