package Piezas;

import daw.Utiles;

public class Reina extends Pieza {

    @Override
    public String getNombre() {
        return "Reina";
    }      

public static void main(String[] args) throws Exception {
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos una reina blanca en el centro
        tablero[4][4] = new Reina(true, 4, 4);
        
        // Piezas que puede comer (negras en línea recta y diagonal)
        tablero[4][7] = new Peon(false, 4, 7); // horizontal
        tablero[7][4] = new Peon(false, 7, 4); // vertical
        tablero[6][6] = new Peon(false, 6, 6); // diagonal
        tablero[2][2] = new Peon(false, 2, 2); // diagonal
        
        // Piezas que no puede comer (blancas)
        tablero[4][6] = new Peon(true, 4, 6);
        tablero[5][5] = new Peon(true, 5, 5);
        
        // Piezas que bloquean el camino
        tablero[3][3] = new Peon(false, 3, 3); // bloquea diagonal
        tablero[4][5] = new Peon(true, 4, 5);  // bloquea horizontal
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        // Movimientos tipo torre
        Posicion pos1 = new Posicion(4, 7); // Movimiento bloqueado horizontal
        Posicion pos2 = new Posicion(7, 4); // Movimiento vertical a pieza negra (válido)
        Posicion pos3 = new Posicion(4, 3); // Movimiento horizontal libre
        
        // Movimientos tipo alfil
        Posicion pos4 = new Posicion(6, 6); // Diagonal bloqueada
        Posicion pos5 = new Posicion(3, 5); // Diagonal libre
        Posicion pos6 = new Posicion(2, 2); // Diagonal bloqueada
        
        // Otros movimientos
        Posicion pos7 = new Posicion(5, 3); // Movimiento inválido (ni recto ni diagonal)
        Posicion pos8 = new Posicion(8, 8); // Fuera del tablero
        
        System.out.println("\nPruebas de movimientos tipo torre:");
        System.out.println("Movimiento a (4,7): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser false - bloqueado en horizontal)");
        System.out.println("Movimiento a (7,4): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser true - vertical libre hasta pieza negra)");
        System.out.println("Movimiento a (4,3): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser true - horizontal libre)");
        
        System.out.println("\nPruebas de movimientos tipo alfil:");
        System.out.println("Movimiento a (6,6): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser false - diagonal bloqueada)");
        System.out.println("Movimiento a (3,5): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser true - diagonal libre)");
        System.out.println("Movimiento a (2,2): " + tablero[4][4].esMovimientoValido(tablero, pos6) + " (debería ser false - diagonal bloqueada)");
        
        System.out.println("\nOtras pruebas:");
        System.out.println("Movimiento a (5,3): " + tablero[4][4].esMovimientoValido(tablero, pos7) + " (debería ser false - movimiento ni recto ni diagonal)");
        System.out.println("Movimiento a (8,8): " + tablero[4][4].esMovimientoValido(tablero, pos8) + " (debería ser false - fuera del tablero)");
    }
    public Reina(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (this.COLOR) {
                return "\u2655 ";
            } else {
                return "\u265B ";
            }
        } else {
            if (this.COLOR) {
                return "Qw";
            } else {
                return "Qb";
            }
        }

    }

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {

        if (!movimientoFactible(tablero, nuevaPosicion)) {
            return false;
        }

        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();

        int columnaAComprobar;
        int filaAComprobar;

        int diferenciaFil = nuevaPosicion.getFila() - this.getFila();
        int diferenciaCol = nuevaPosicion.getColumna() - this.getColumna();

        int difColAbsoluta = Math.abs(diferenciaCol);
        int difFilAbsoluta = Math.abs(diferenciaFil);
        // Torre

        // Si llega aqui el movimiento es factible, solo queda saber si es legal
        // Necesitamos saber si esta en linea recta

        if (difColAbsoluta == 0 || difFilAbsoluta == 0) {
            if (difColAbsoluta == 0) {
                // Se mueve en el eje Y
                int signoMovimiento = (int) Math.signum(diferenciaFil);

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
                } else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(this.COLOR))) {
                    // Tambien si hay una ficha del color contrario
                    return true;
                } else {
                    return false;
                }

            } else if (difFilAbsoluta == 0) {
                // Se mueve en el eje X
                int signoMovimiento = (int) Math.signum(diferenciaCol);

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
                } else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(this.COLOR))) {
                    // Tambien si hay una ficha del color contrario
                    return true;
                } 
            }

        } else if (difColAbsoluta == difFilAbsoluta) {
            // Alfil

            // Sigue la misma logica que el alfil
            int signoCol = (int) Math.signum(diferenciaCol);
            int signoFil = (int) Math.signum(diferenciaFil);

            for (int i = 1; i < Math.abs(diferenciaCol); i++) {

                // con esto calculamos la posicion que debemos comprobar
                columnaAComprobar = this.getColumna() + (i * signoCol);
                filaAComprobar = this.getFila() + (i * signoFil);

                if (tablero[filaAComprobar][columnaAComprobar] != null) {
                    return false;
                }
            }

            // Comprobamos la casilla final
            if (tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()] == null) {
                return true;
            } else if (!tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()].equalsColor(this.COLOR)) {
                return true;
            }
        } 
            return false;
        
    }

    
}
