/* -*- coding: cp1252 -*-
 El programa tiene la capacidad de leer arreglos (entregados manualmente o por archivo) con un formato específico y calcular el área
 de triángulos blancos, estos están dentro de un triángulo más grande que contiene triángulos blancos (-) y negros (#).
 Autor: Alexander Carlos Andrés Schilling Miranda.
 Versión: 0.5 | Fecha: 26/04/2017 */
package BestPackage;

// BLOQUE DE IMPORTACIÓN
import java.util.Scanner;

// BLOQUE PRINCIPAL
public class Programa {

    public static void main(String args[]) {
        Lector lector = new Lector();
        SopaLetras sopa1 = new SopaLetras();
        sopa1.crearPalabras();
        sopa1.printSolutions();
        sopa1.printSolutionsCoords();
    }
}
