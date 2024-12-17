package Piezas;

public class Rey extends Pieza {

    public Rey(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2654 ";
            } else {
                return "\u265A ";
            }
        } else {
            if (color) {
                return "Kw";
            } else {
                return "Kb";
            }
        }

    }

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
