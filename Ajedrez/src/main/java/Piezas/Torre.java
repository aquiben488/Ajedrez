package Piezas;

public class Torre extends Pieza {

    public Torre(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2656 ";
            } else {
                return "\u265C ";
            }
        } else {
            if (color) {
                return "Rw";
            } else {
                return "Rb";
            }
        }

    }

}
