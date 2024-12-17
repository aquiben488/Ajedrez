/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Piezas;

/**
 *
 * @author ale
 */
public class Caballo extends Pieza {

    public Caballo(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2658 ";
            } else {
                return "\u265E ";
            }
        } else {
            if (color) {
                return "Nw";
            } else {
                return "Nb";
            }
        }

    }
}
