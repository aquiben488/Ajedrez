package Piezas;

import daw.Utiles;

public class Torre extends Pieza {

    public static void main(String[] args) throws Exception {

        Pieza[][] tablero = new Pieza[8][8];

        tablero[2][2] = new Torre(true, 2, 2);
        // tablero[3][2] = new Peon(true,3,2);
        tablero[2][1] = new Peon(false, 2, 1);
        tablero[7][2] = new Peon(false, 7, 2);

        tablero[5][2] = new Torre(true, 5, 2);
        tablero[1][2] = new Peon(false, 1, 2);
        tablero[2][4] = new Peon(false, 2, 4);

        Utiles.toString(tablero);

        Posicion pos = new Posicion(5, 2);

        System.out.println(tablero[2][2].esMovimientoValido(tablero, pos));
    }

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

    // Para comprobar el movimiento de una Torre:
    // - Tiene que estar en la misma Fila o columna
    // - Tiene que tener el camino libre
    // - La casilla tiene que estar libre u ocupada por una pieza del otro color

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {

        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();

        if (estaClavada) {
            // Si esta clavada no se puede mover
            return false;
        } else if (!(nuevaPosicion.esValida())) {
            // Esta fuera del tablero
            return false;
        } else if (this.posicion.equals(nuevaPosicion)) {
            // La posicion es la misma
            return false;
        }

        // Si llega aqui el movimiento es factible, solo queda saber si es legal
        // Necesitamos saber si esta en linea recta

        int diferenciaFilas = nuevaPosicion.getFila() - this.getFila();
        int diferenciaColumnas = nuevaPosicion.getColumna() - this.getColumna();
        int difColAbsoluta = Math.abs(diferenciaColumnas);
        int difFilAbsoluta = Math.abs(diferenciaFilas);
        int columnaAComprobar;
        int filaAComprobar;

        if (difColAbsoluta == 0) {
            // Se mueve en el eje Y
            int signoMovimiento = (int) Math.signum(diferenciaFilas);

            for (int i = 1; i < difFilAbsoluta; i++) {

                filaAComprobar = this.getFila() + i * signoMovimiento;

                if (tablero[filaAComprobar][this.getColumna()] != null) {
                    // Comprobamos todas las casillas hasta la penultima
                    // y si no estan vacias no puede moverse
                    return false;
                }
            }
            if (tablero[nuevaFila][nuevaColumna] == null) {
                // Si el camino esta vacio y la casilla tambien puede moverse
                return true;
            } else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(color))) {
                // Tambien si hay una ficha del color contrario
                return true;
            } else {
                return false;
            }

        } else if (difFilAbsoluta == 0) {
            // Se mueve en el eje X
            int signoMovimiento = (int) Math.signum(diferenciaColumnas);

            for (int i = 1; i < difColAbsoluta; i++) {

                columnaAComprobar = this.getColumna() + i * signoMovimiento;

                if (tablero[this.getFila()][columnaAComprobar] != null) {
                    // Comprobamos todas las casillas hasta la penultima
                    // y si no estan vacias no puede moverse
                    return false;
                }
            }
            if (tablero[nuevaFila][nuevaColumna] == null) {
                // Si el camino esta vacio y la casilla tambien puede moverse
                return true;
            } else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(color))) {
                // Tambien si hay una ficha del color contrario
                return true;
            } else {
                return false;
            }
        } else {
            // Cualquier otra cosa es ilegal
            return false;
        }

    }

}
