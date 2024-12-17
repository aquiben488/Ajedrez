
package Piezas;


public class Alfil extends Pieza{

    public Alfil(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    
    // Para comprobar si el movimiento de un alfil es valido:
    // - Esta dento del tablero
    // - La distancia de filas y de columnas es la misma (esta en diagonal) 
    // - No hay piezas en medio
    
    @Override
    public boolean movimientoValido(Pieza[][] tablero,Posicion nuevaPosicion) {
        
        int diferenciaFil = nuevaPosicion.getFila() - this.getFila();
        int diferenciaCol = nuevaPosicion.getColumna() - this.getColumna();
        
        if (estaClavada) { 
        //Si esta clavada no se puede mover 
            return false;
        }else if (!(nuevaPosicion.esValida())) { 
        // Esta fuera del tablero
            return false;
        }else if (Math.abs(diferenciaCol) != Math.abs(diferenciaFil)) { 
        // No esta en diagonal
            return false;
        }else if (this.posicion.equals(nuevaPosicion)) { 
        // Es la misma poscion
            return false;
        }else if (tablero[nuevaPosicion.getFila()][nuevaPosicion.getFila()].equalsColor(color)) {
        // La posicon a la que quiere moverse esta ocupada por otra pieza del mismo color
            return false;
        }
        
        // Si llega aqui, en princio el movimiento es valido a no ser que haya 
        // alguna pieza en medio, para comprobarlo tenemos que comprobar todas
        // las casillas de en medio
        
        
        // necesitamos el signo de la diferencia ya que 
        // con ello sabemos en que diagonal va a moverse
        
        // filas : positivo = va para abajo, negativo = arriba
        // columnas : positivo = va para la derecha, negativo = izquierda
        int signoCol = (int) Math.signum(diferenciaCol);
        int signoFil = (int) Math.signum(diferenciaFil);
        
        int columnaAComprobar;
        int filaAComprobar;
        
        for (int i = 1; i < diferenciaCol; i++) {
            
            // con esto calculamos la posicion que debemos comprobar
            columnaAComprobar = this.getColumna()+(i*signoCol);
            filaAComprobar = this.getFila()+(i*signoFil);
            
            if (tablero[columnaAComprobar][filaAComprobar] != null) {
                return false;
            }
        }
        // Si llega aqui el movimiento es valido
        return true;
        
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
