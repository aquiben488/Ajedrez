package Piezas;

import daw.Utiles;

public class Rey extends Pieza {

    public static void main(String[] args) throws Exception {
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un rey blanco en el centro
        tablero[4][4] = new Rey(true, 4, 4);
        
        // Piezas que puede comer (negras sin protección)
        tablero[4][5] = new Peon(false, 4, 5); // Horizontal
        
        // Pieza negra protegida por peón
        tablero[3][3] = new Alfil(false, 3, 3);
        tablero[2][2] = new Peon(false, 2, 2); // Protege al alfil
        
        // Piezas del mismo color (blancas)
        tablero[5][4] = new Peon(true, 5, 4);
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        // Movimientos válidos
        Posicion pos1 = new Posicion(4, 5); // Comer pieza negra sin protección
        Posicion pos2 = new Posicion(3, 4); // Movimiento a casilla vacía
        
        // Movimientos inválidos
        Posicion pos3 = new Posicion(3, 3); // Mover a casilla con pieza protegida
        Posicion pos4 = new Posicion(5, 4); // Mover a casilla con pieza propia
        Posicion pos5 = new Posicion(6, 6); // Fuera del alcance del rey
        Posicion pos6 = new Posicion(8, 8); // Fuera del tablero
        
        System.out.println("\nPruebas de movimientos válidos:");
        System.out.println("Movimiento a (4,5): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser true - comer pieza sin protección)");
        System.out.println("Movimiento a (3,4): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser true - casilla vacía)");
        
        System.out.println("\nPruebas de movimientos inválidos:");
        System.out.println("Movimiento a (3,3): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser false - pieza protegida)");
        System.out.println("Movimiento a (5,4): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser false - pieza propia)");
        System.out.println("Movimiento a (6,6): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser false - fuera de alcance)");
        System.out.println("Movimiento a (8,8): " + tablero[4][4].esMovimientoValido(tablero, pos6) + " (debería ser false - fuera del tablero)");
    }

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

        // el rey se puede mover una casilla en cualquier direccion
        // por lo que solo hay que comprobar que la nueva posicion este a una casilla de distancia
        // y que no este ocupada por una pieza del mismo color
        // y que no haya ninguna pieza del otro color que pueda comer al rey

        if (!movimientoFactible(nuevaPosicion)) {
            return false;
        }

        int diferenciaFil = nuevaPosicion.getFila() - this.getFila();
        int diferenciaCol = nuevaPosicion.getColumna() - this.getColumna();

        int difColAbsoluta = Math.abs(diferenciaCol);
        int difFilAbsoluta = Math.abs(diferenciaFil);

        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();
        
        // Comprobamos que la nueva posicion este a una casilla de distancia
        if (!(difColAbsoluta <= 1 && difFilAbsoluta <= 1)) {
            return false;
        }

        // Comprobamos que la nueva posicion no este ocupada por una pieza del mismo color
        if (tablero[nuevaFila][nuevaColumna] != null) {
                    if (tablero[nuevaFila][nuevaColumna].equalsColor(color)) {
                        return false;
                    }
                }

        Pieza[][] piezaComida = new Pieza[8][8];

        // Comprobamos que no haya ninguna pieza del otro color que pueda comer al rey
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                // Comprobamos que no sea null  
                if (pieza != null) {
                    // Comprobamos que no sea de nuestro color
                    if (!(pieza.equalsColor(color))) {
                        // Comprobamos que la pieza pueda comer al rey
                        if (pieza.esMovimientoValido(tablero, nuevaPosicion)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
