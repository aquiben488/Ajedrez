package Piezas;

import daw.Utiles;

public class Caballo extends Pieza {

    @Override
    public String getNombre() {
        return "Caballo";
    }   

    public static void main(String[] args) throws Exception {
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un caballo blanco en el centro
        tablero[4][4] = new Caballo(true, 4, 4);
        
        // Piezas que puede comer (negras)
        tablero[2][3] = new Peon(false, 2, 3);
        tablero[6][5] = new Peon(false, 6, 5);
        tablero[3][6] = new Peon(false, 3, 6);
        
        // Piezas que no puede comer (blancas)
        tablero[2][5] = new Peon(true, 2, 5);
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        Posicion pos1 = new Posicion(2, 3); // Movimiento válido - puede comer pieza negra
        Posicion pos2 = new Posicion(2, 5); // Movimiento inválido - hay pieza blanca
        Posicion pos3 = new Posicion(3, 2); // Movimiento válido en L a casilla vacía
        Posicion pos4 = new Posicion(4, 6); // Movimiento inválido - no es en L
        Posicion pos5 = new Posicion(8, 8); // Fuera del tablero
        Posicion pos6 = new Posicion(6, 5); // Movimiento válido - puede comer pieza negra
        Posicion pos7 = new Posicion(5, 6); // Movimiento válido en L a casilla vacía
        
        System.out.println("Movimiento a (2,3): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser true - puede comer pieza negra)");
        System.out.println("Movimiento a (2,5): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser false - hay una pieza blanca)"); 
        System.out.println("Movimiento a (3,2): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser true - movimiento en L válido)");
        System.out.println("Movimiento a (4,6): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser false - no es movimiento en L)");
        System.out.println("Movimiento a (8,8): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser false - fuera del tablero)");
        System.out.println("Movimiento a (6,5): " + tablero[4][4].esMovimientoValido(tablero, pos6) + " (debería ser true - puede comer pieza negra)");
        System.out.println("Movimiento a (5,6): " + tablero[4][4].esMovimientoValido(tablero, pos7) + " (debería ser true - movimiento en L válido)");
    }

    public Caballo(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (this.COLOR) {
                return "\u2658 ";
            } else {
                return "\u265E ";
            }
        } else {
            if (this.COLOR) {
                return "Nw";
            } else {
                return "Nb";
            }
        }

    }
    
    // Para comprobar el movimiento de un caballo :
    // - Tiene que estar en L
    // - La casilla tiene que estar libre u ocupada por una pieza del otro color 

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {
        
        if (!movimientoFactible(tablero, nuevaPosicion)) {
            return false;
        }
        
        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();

        // Si llega aqui el movimiento es factible, solo queda saber si es legal
        // Necesitamos saber si esta en L
        
        int diferenciaFilas = nuevaPosicion.getFila() - this.getFila();
        int diferenciaColumnas = nuevaPosicion.getColumna() - this.getColumna();
        int difColAbsoluta = Math.abs(diferenciaColumnas);
        int difFilAbsoluta = Math.abs(diferenciaFilas);
        
        if (difColAbsoluta + difFilAbsoluta != 3) {
            // Si la suma de las diferencias es distinto de 3 
            // es imposible que haga una L
            return false;
        } 
        if (difColAbsoluta == 3 || difFilAbsoluta == 3) {
            // Si alguno de estos es igual a 3 significa que no hace una L
            // hace una linea recta
            return false;  
        }
        if (tablero[nuevaFila][nuevaColumna] == null || !(tablero[nuevaFila][nuevaColumna].equalsColor(this.COLOR))) {
            return true;
        }
        
        return false;
             
    }
}
