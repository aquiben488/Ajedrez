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
        
        // La reina combina los movimientos de la torre y el alfil
        // Comprobamos si el movimiento es válido como torre (movimiento en línea recta)
        boolean movimientoTorre = Math.abs(nuevaPosicion.getFila() - this.getFila()) == 0 || 
                                 Math.abs(nuevaPosicion.getColumna() - this.getColumna()) == 0;

        // Comprobamos si el movimiento es válido como alfil (movimiento diagonal)
        boolean movimientoAlfil = Math.abs(nuevaPosicion.getFila() - this.getFila()) == 
                                 Math.abs(nuevaPosicion.getColumna() - this.getColumna());

        // Si el movimiento no es válido ni como torre ni como alfil, retornamos false
        if (!movimientoTorre && !movimientoAlfil) {
            return false;
        }

        // Verificamos que no haya piezas en el camino
        int incrementoFila = Integer.compare(nuevaPosicion.getFila() - this.getFila(), 0);
        int incrementoColumna = Integer.compare(nuevaPosicion.getColumna() - this.getColumna(), 0);
        
        int filaActual = this.getFila() + incrementoFila;
        int columnaActual = this.getColumna() + incrementoColumna;

        // Verificamos que no nos salimos del tablero
        if (nuevaPosicion.getFila() < 0 || nuevaPosicion.getFila() > 7 || 
            nuevaPosicion.getColumna() < 0 || nuevaPosicion.getColumna() > 7) {
            return false;
        }

        while (filaActual != nuevaPosicion.getFila() || columnaActual != nuevaPosicion.getColumna()) {
            if (tablero[filaActual][columnaActual] != null) {
                return false;
            }
            filaActual += incrementoFila;
            columnaActual += incrementoColumna;
        }

        // Verificamos que en la posición final no haya una pieza del mismo color
        return tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()] == null || 
               tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()].equalsColor(color);




    }
}
