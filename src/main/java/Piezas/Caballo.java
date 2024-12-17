

package Piezas;

public class Caballo extends Pieza {

    public Caballo(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2658 ";
            } else {
                return "\u265E ";
            }
        } else {
            if (color) {
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
        
        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();
        
        if (estaClavada) {
            //Si esta clavada no se puede mover 
            return false;
        } else if (!(nuevaPosicion.esValida())) {
            // Esta fuera del tablero
            return false;
        } else if (this.posicion.equals(nuevaPosicion)) {
            // La posicion es la misma
            return false;
        }
        
        // Si llega aqui el movimiento es factible, solo queda saber si es legal
        // Necesitamos saber si esta en L
        
        int diferenciaFilas = nuevaPosicion.getFila() - this.getFila();
        int diferenciaColumnas = nuevaPosicion.getColumna() - this.getColumna();
        int difColAbsoluta = Math.abs(diferenciaColumnas);
        int difFilAbsoluta = Math.abs(diferenciaFilas);
        int columnaAComprobar;
        int filaAComprobar;
        
        if (difColAbsoluta + difFilAbsoluta != 3) {
            // Si la suma de las diferencias es distinto de 3 
            // es imposible que haga una L
            return false;
        }else if (difColAbsoluta == 3 || difFilAbsoluta == 3) {
            // Si alguno de estos es igual a 3 significa que no hace una L
            // hace una linea recta
            return false;  
        }else if (tablero[nuevaFila][nuevaColumna] == null) {
            return true;
        }else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(color))) {
            return true;
        }else{
            return false;
        }     
    }
}
