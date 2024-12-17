package Piezas;

public class Reina extends Pieza {

    public Reina(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2655 ";
            } else {
                return "\u265B ";
            }
        } else {
            if (color) {
                return "Qw";
            } else {
                return "Qb";
            }
        }

    }

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {
            return color;
        



    }
}
