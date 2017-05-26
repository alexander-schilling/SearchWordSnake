/* -*- coding: cp1252 -*-
 Esta clase se encarga de leer y escribir archivos (entregados mediante una
 ruta y nombre por el usuario). También verifica que estén correctos.
 Los print están escritos en inglés por comodidad y orden, ya que me disgusta
 que las palabras estén sin sus signos de puntuación correspondientes.
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 1.0 | Fecha: 26/05/2017 */
package BestPackage;

// BLOQUE DE IMPORTACIÓN
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// BLOQUE PRINCIPAL
public class Lector {

    Scanner scan = new Scanner(System.in);

    // Función que obtiene un arreglo de Strings, ya sea de las filas de
    // la sopa de letras o las filas de las palabras a encontrar en esta.
    // Entrada: String con el tipo de archivo que se pedirá.
    // Salida: Lista de Strings con las filas del archivo.
    public List<String> getFileData(String type) {
        while (true) {
            System.out.print("Type the directory with the name of the file"
                    + " of " + type + "\n (or just the name of the file"
                    + " if it's with the program)\n>> ");
            String fn = scan.next();
            fn += ".in";
            try (BufferedReader br = new BufferedReader(new FileReader(fn))) {
                String linea;
                List<String> temp;
                temp = new ArrayList<>();
                while ((linea = br.readLine()) != null) {
                    temp.add(linea.replaceAll("\\s+", ""));
                }
                return temp;
            } catch (IOException e) {
                System.out.println("The file doesn't exist or it's unreadable,"
                        + " please, type another name.");
            }
        }
    }

    // Función que obtiene el directorio en que se escribirá la solución.
    // Entrada: -
    // Salida: String con el nombre del directorio.
    public String getSolutionDir() {
        while (true) {
            System.out.print("Insert the directory where the solution"
                    + " will be created\n>> ");
            String dir = scan.next();
            if (dir.endsWith("\\")) {
                return (dir + "Solucion.out");
            } else {
                return (dir + "\\Solucion.out");
            }
        }
    }

    // Función que verifica que las filas de la sopa coinciden en tamaño,
    // de este modo, la sopa de letras es rectangular/cuadrada.
    // Entrada: Lista con las filas de strings de la sopa.
    // Salida: Booleano si se cumple la condición mencionada.
    public boolean checkSize(List<String> list) {
        boolean flag = true;
        int initSize = list.get(0).length();
        while (true) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).length() != initSize) {
                    flag = false;
                    System.out.println("The length of the rows don't match,"
                            + " please, type another name.");
                    return flag;
                }
            }
            return flag;
        }
    }

    // Función que obtiene las filas de la sopa de letras, utilizando la
    // función verificadora de tamaño de filas.
    // Entrada: -
    // Salida: Lista de strings de las filas de la sopa.
    public List<String> setSoup() {
        while (true) {
            List<String> tempSoup = getFileData("the search word puzzle");
            if (checkSize(tempSoup)) {
                return tempSoup;
            }
        }
    }

    // Función que obtiene las filas de las palabras.
    // Entrada: -
    // Salida: Lista de strings con las palabras.
    public List<String> setWords() {
        return getFileData("the words to find");
    }

    // Función que escribe las soluciones de la(s) sopa(s) de letras.
    // Entrada: lineas del archivo y directorio del archivo.
    // Salida: Archivo escrito.
    public void writeSolutions(List<String> lines, String dir)
            throws IOException {
        Path file = Paths.get(dir);
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
