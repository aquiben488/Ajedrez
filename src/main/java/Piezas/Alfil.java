package Piezas;

import daw.Utiles;

public class Alfil extends Pieza{

    @Override
    public String getNombre() {
        return "Alfil";
    }   

    public static void main(String[] args) throws Exception {
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un alfil blanco en el centro
        tablero[4][4] = new Alfil(true, 4, 4);
        
        // Piezas que puede comer (negras en diagonal)
        tablero[2][2] = new Peon(false, 2, 2);
        tablero[6][6] = new Peon(false, 6, 6);
        tablero[1][7] = new Peon(false, 1, 7);  // Nueva pieza negra que puede comer
        
        // Piezas que no puede comer (blancas)
        tablero[6][2] = new Peon(true, 6, 2);
        
        // Piezas que bloquean el camino
        tablero[3][3] = new Peon(false, 3, 3);
        tablero[5][5] = new Peon(true, 5, 5);
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        Posicion pos1 = new Posicion(2, 2); // Movimiento bloqueado
        Posicion pos2 = new Posicion(6, 6); // Movimiento bloqueado
        Posicion pos3 = new Posicion(3, 5); // Movimiento válido en diagonal
        Posicion pos4 = new Posicion(4, 5); // Movimiento inválido (no diagonal)
        Posicion pos5 = new Posicion(8, 8); // Fuera del tablero
        Posicion pos6 = new Posicion(1, 7); // Movimiento válido - puede comer pieza negra
        Posicion pos7 = new Posicion(5, 3); // Movimiento válido a casilla vacía
        
        System.out.println("Movimiento a (2,2): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser false - hay una pieza bloqueando en (3,3))");
        System.out.println("Movimiento a (6,6): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser false - hay una pieza bloqueando en (5,5))"); 
        System.out.println("Movimiento a (3,5): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser true - diagonal libre)");
        System.out.println("Movimiento a (4,5): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser false - no es movimiento diagonal)");
        System.out.println("Movimiento a (8,8): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser false - fuera del tablero)");
        System.out.println("Movimiento a (1,7): " + tablero[4][4].esMovimientoValido(tablero, pos6) + " (debería ser true - puede comer pieza negra)");
        System.out.println("Movimiento a (5,3): " + tablero[4][4].esMovimientoValido(tablero, pos7) + " (debería ser true - diagonal libre a casilla vacía)");
    }

    public Alfil(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }
    
    // Para comprobar si el movimiento de un alfil es valido:
    // - Esta dentro del tablero
    // - La distancia de filas y de columnas es la misma (esta en diagonal) 
    // - No hay piezas en medio
    
    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {
        if (!movimientoFactible(tablero, nuevaPosicion)) {
            return false;
        }
        
        int diferenciaFil = nuevaPosicion.getFila() - this.getFila();
        int diferenciaCol = nuevaPosicion.getColumna() - this.getColumna();

        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();
        
        // Comprobamos que el movimiento es diagonal
        if (Math.abs(diferenciaCol) != Math.abs(diferenciaFil)) {
            return false;
        }
        
        // necesitamos el signo de la diferencia ya que 
        // con ello sabemos en que diagonal va a moverse
        
        // filas : positivo = va para abajo, negativo = arriba
        // columnas : positivo = va para la derecha, negativo = izquierda
        int signoCol = (int) Math.signum(diferenciaCol);
        int signoFil = (int) Math.signum(diferenciaFil);
        
        int columnaAComprobar;
        int filaAComprobar;
        
        for (int i = 1; i < Math.abs(diferenciaCol); i++) {
            
            // con esto calculamos la posicion que debemos comprobar
            columnaAComprobar = this.getColumna()+(i*signoCol);
            filaAComprobar = this.getFila()+(i*signoFil);
            
            if (tablero[filaAComprobar][columnaAComprobar] != null) {
                return false;
            }
        }
        
        // Comprobamos la casilla final
        if (tablero[nuevaFila][nuevaColumna] == null) {
            // Si el camino esta vacio y la casilla tambien puede moverse
            return true;
        } else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(this.COLOR))) {
            // Tambien si hay una ficha del color contrario                 
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        
            if (estamosEnLinux) {
                if (this.COLOR) {
                    return "\u2657 ";
                } else {
                    return "\u265D ";
                }
            } else {
                if (this.COLOR) {
                    return "Bw";
                } else {
                    return "Bb";
                }
            }
    }
}
