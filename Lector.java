/* -*- coding: cp1252 -*-
 El programa tiene la capacidad de leer arreglos (entregados manualmente o por archivo) con un formato específico y calcular el área
 de triángulos blancos, estos están dentro de un triángulo más grande que contiene triángulos blancos (-) y negros (#).
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 0.5 | Fecha: 26/04/2017 */
package BestPackage;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lector {

    Scanner scan = new Scanner(System.in);

    // Función que obtiene el nombre del archivo con el arreglo y verifica si
    // es que se puede abrir.
    // Entrada: -
    // Salida: Nombre de Archivo.
    public List<String> getFileData(String type) {
        while (true) {
            System.out.print("Ingrese la ruta con el nombre del archivo\n"
                    + "  de " + type + "\n  (o sólo el nombre si se"
                    + " encuentra junto al programa):");
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
                System.out.println("El archivo no existe o no se puede leer,\n"
                        + "  por favor, ingrese otro.");
            }
        }
    }

    public boolean checkSize(List<String> list) {
        boolean flag = true;
        int initSize = list.get(0).length();
        while (true) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).length() != initSize) {
                    flag = false;
                    System.out.println("El largo de las filas no coincide,\n"
                            + "  por favor, ingrese otro nombre de archivo.");
                    return flag;
                }
            }
            return flag;
        }
    }

    public List<String> setSoup() {
        while (true) {
            List<String> tempSoup = getFileData("la sopa de letras");
            if (checkSize(tempSoup)) {
                return tempSoup;
            }
        }
    }

    public List<String> setNames() {
        return getFileData("los nombres a encontrar");
    }
}
