package Piezas;

import daw.Utiles;

public class Reina extends Pieza {
    // Atributos para delegar los movimientos
    private Torre movimientosTorre;
    private Alfil movimientosAlfil;

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
        // Inicializamos las piezas auxiliares con la misma posición y color
        this.movimientosTorre = new Torre(color, fila, columna);
        this.movimientosAlfil = new Alfil(color, fila, columna);
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

        int difColAbsoluta = Math.abs(nuevaPosicion.getColumna() - this.getColumna());
        int difFilAbsoluta = Math.abs(nuevaPosicion.getFila() - this.getFila());

        // Actualizamos la posición de las piezas auxiliares
        movimientosTorre.setPosicion(this.getFila(), this.getColumna());
        movimientosAlfil.setPosicion(this.getFila(), this.getColumna());

        // Si se mueve en línea recta (como Torre)
        if (difColAbsoluta == 0 || difFilAbsoluta == 0) {
            return movimientosTorre.esMovimientoValido(tablero, nuevaPosicion);
        }
        // Si se mueve en diagonal (como Alfil)
        else if (difColAbsoluta == difFilAbsoluta) {
            return movimientosAlfil.esMovimientoValido(tablero, nuevaPosicion);
        }
        
        return false;
    }
}
