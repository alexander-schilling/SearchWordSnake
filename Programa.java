/* -*- coding: cp1252 -*-
 Este programa puede leer sopas de letras (con las palabras a encontrar) y
 ver si las palabras se encuentran en esta, además, también muestra las
 coordenadas de las letras en la sopa.
 Esta clase es la principal y es la que contiene todas las ejecuciones de
 comandos y los menús para ejecutarlas.
 Los print están escritos en inglés por comodidad y orden, ya que me disgusta
 que las palabras estén sin sus signos de puntuación correspondientes.
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 1.0 | Fecha: 26/05/2017 */
package BestPackage;

// BLOQUE DE IMPORTACIÓN
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// BLOQUE PRINCIPAL
public class Programa {

    // Función que ejecuta los métodos de la(s) sopa(s) de letras para
    // encontrar las soluciones.
    // Entrada: Lista de la(s) sopa(s) de letras.
    // Salida: Ejecución de comandos para encontrar soluciones.
    private void executeSolutions(List<SopaLetras> sopas) {
        for (int i = 0; i < sopas.size(); i++) {
            sopas.get(i).crearEstados();
            sopas.get(i).crearPalabras();
            sopas.get(i).init();
        }
    }

    // Función que obtiene un arreglo de Strings de las filas de las soluciones
    // de las sopas de letras, esto para después agregarlas a otra lista aparte
    // que serían las líneas del archivo a escribir.
    // Entrada: Lista con la(s) sopa(s) de letras.
    // Salida: Lista de Strings con las filas del archivo a escribir.
    private List<String> getListSolutions(List<SopaLetras> sopas) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < sopas.size(); i++) {
            for (int j = 0; j < sopas.get(i).solution().size(); j++) {
                list.add(sopas.get(i).solution().get(j));
            }
        }
        return list;
    }

    // FUNCIÓN PRINCIPAL
    // Básicamente, aquí están los menús y la redirección a los métodos de las
    // demás clases.
    public static void main(String args[]) {
        Programa p = new Programa();
        Lector l = new Lector();
        Scanner scan = new Scanner(System.in);
        System.out.println("- Search Word Puzzle Solver -\n");
        boolean programa = true, flag0 = true, flag1 = true, flag2 = true;
        List<SopaLetras> sopas = new ArrayList<>();
        do {
            while (flag0) {
                boolean flagSoup = true;
                int i = 0;
                while (flagSoup) {
                    SopaLetras tempSoup = new SopaLetras(i, l.setSoup(),
                            l.setWords());
                    sopas.add(tempSoup);
                    boolean sw = true;
                    while (sw) {
                        System.out.print("Do you want to add another puzzle to"
                                + " solve?\n 1: Yes\n 2: No\n>> ");
                        String continuar = scan.next();
                        switch (continuar) {
                            case "1":
                                i += 1;
                                sw = false;
                                break;
                            case "2":
                                sw = false;
                                flagSoup = false;
                                flag0 = false;
                                flag1 = true;
                                flag2 = true;
                                break;
                            default:
                                System.out.println("Please, insert a valid "
                                        + "option.");
                        }
                    }
                }
            }
            p.executeSolutions(sopas);
            while (flag1) {
                String dir = l.getSolutionDir();
                List<String> list = p.getListSolutions(sopas);
                try {
                    l.writeSolutions(list, dir);
                    System.out.println("File created as \"Solucion.out\"");
                    flag1 = false;
                } catch (IOException e) {
                    System.out.println("The directory is incorrect, please,"
                            + " type another.");
                }
            }
            while (flag2) {
                System.out.print("Do you want to exit?\n 1: Yes\n 2: No\n"
                        + ">> ");
                String question = scan.next();
                switch (question) {
                    case "1":
                        flag0 = false;
                        flag1 = false;
                        flag2 = false;
                        programa = false;
                        break;
                    case "2":
                        flag0 = true;
                        flag1 = false;
                        flag2 = false;
                        break;
                    default:
                        System.out.println("Please, insert a valid option.");
                }
            }
        } while (programa);
    }
}
