
package Piezas;

import daw.Utiles;

public class Alfil extends Pieza{

    public static void main(String[] args) throws Exception {
        // Creamos un tablero de prueba
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un alfil blanco en la posición (4,4)
        Pieza alfilBlanco = new Alfil(true, 4, 4);
        tablero[4][4] = alfilBlanco;
        
        // Colocamos algunas piezas para probar:
        // Una pieza negra que puede comer en diagonal
        Pieza alfilNegro = new Alfil(false, 6, 6);
        tablero[6][6] = alfilNegro;
        
        // Una pieza blanca que bloquea el paso (mismo color)
        Pieza alfilBlanco2 = new Alfil(true, 2, 2);
        tablero[2][2] = alfilBlanco2;
        
        // Una pieza en medio del camino
        Pieza alfilObstaculo = new Alfil(false, 5, 5);
        tablero[5][5] = alfilObstaculo;
        
        // Probamos algunos movimientos
        Posicion pos1 = new Posicion(6, 6); // Movimiento válido pero hay obstáculo
        Posicion pos2 = new Posicion(2, 2); // Movimiento válido pero hay pieza del mismo color
        Posicion pos3 = new Posicion(3, 5); // Movimiento no diagonal
        Posicion pos4 = new Posicion(3, 3); // Movimiento válido y sin obstáculos

        Utiles.toString(tablero);
        
        System.out.println("Movimiento a (6,6): " + alfilBlanco.esMovimientoValido(tablero, pos1));
        System.out.println("Movimiento a (2,2): " + alfilBlanco.esMovimientoValido(tablero, pos2));
        System.out.println("Movimiento a (3,5): " + alfilBlanco.esMovimientoValido(tablero, pos3));
        System.out.println("Movimiento a (3,3): " + alfilBlanco.esMovimientoValido(tablero, pos4));
    }

    public Alfil(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    
    // Para comprobar si el movimiento de un alfil es valido:
    // - Esta dento del tablero
    // - La distancia de filas y de columnas es la misma (esta en diagonal) 
    // - No hay piezas en medio
    
    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {
        // Calculamos la diferencia entre la posición actual y la nueva
        int diferenciaFil = nuevaPosicion.getFila() - this.getFila();
        int diferenciaCol = nuevaPosicion.getColumna() - this.getColumna();
        
        // Comprobaciones iniciales
        if (estaClavada) {
            return false; // La pieza está clavada y no se puede mover
        }
        
        if (!nuevaPosicion.esValida()) {
            return false; // La nueva posición está fuera del tablero
        }
        
        boolean movimientoDiagonal = Math.abs(diferenciaCol) == Math.abs(diferenciaFil);
        if (!movimientoDiagonal) {
            return false; // El movimiento no es diagonal
        }
        
        if (this.posicion.equals(nuevaPosicion)) {
            return false; // La posición destino es la misma que la actual
        }
        
        // Comprobamos que no haya piezas en el camino
        // El signo nos indica la dirección del movimiento:
        // Filas: positivo = abajo, negativo = arriba
        // Columnas: positivo = derecha, negativo = izquierda
        int signoCol = (int) Math.signum(diferenciaCol);
        int signoFil = (int) Math.signum(diferenciaFil);
        
        // Recorremos todas las casillas intermedias
        for (int i = 1; i < Math.abs(diferenciaCol); i++) {
            int columnaAComprobar = this.getColumna() + (i * signoCol);
            int filaAComprobar = this.getFila() + (i * signoFil);
            
            if (tablero[filaAComprobar][columnaAComprobar] != null) {
                return false; // Hay una pieza bloqueando el camino
            }
        }
        
        // Verificación final: la posición destino debe estar vacía o contener una pieza enemiga
        // Si la casilla está vacía, el movimiento es válido
        if (tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()] == null) {
            return true;
        }
        // Si hay una pieza enemiga, el movimiento es válido
        if (!tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()].equalsColor(color)) {
            return true;
        }
        return false;
    }
    
        @Override
    public String toString() {
        
            if (estamosEnLinux) {
                if (color) {
                    return "\u2657 ";
                } else {
                    return "\u265D ";
                }
            } else {
                if (color) {
                    return "Bw";
                } else {
                    return "Bb";
                }
            }
    }
    
}
