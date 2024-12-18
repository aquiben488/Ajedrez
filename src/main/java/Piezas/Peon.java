package Piezas;

import daw.Utiles;

public class Peon extends Pieza {
    
    public static void main(String[] args) throws Exception {
        
        Pieza[][] tablero = new Pieza[8][8];
        
        tablero[2][2] = new Peon(true,2,2);
        tablero[3][2] = new Peon(true,3,2);
        tablero[3][3] = new Peon(false,3,3);
        
         Utiles.toString(tablero);
         
         Posicion pos = new Posicion(5,2);
         
         System.out.println(tablero[2][2].esMovimientoValido(tablero, pos));
        
    }

    public Peon(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    // Para comprobar si el movimiento de un Peon es valido:
    // - Esta dento del tablero
    // - Se mueve uno o dos para alante (depende del color) 
    // - No hay piezas en medio
    // Ademas el peon come en diagonal hacia delante
    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {

        if (!movimientoFactible(nuevaPosicion)) {
            return false;
        }
        
        // si llegamos aqui, en principio el movimiento es factible
        // no esta clavada, la posicion esta dentro del tablero y no es la misma 
        // en la que ya esta la propia pieza
        // ahora debemos comprobar que el movimiento es posible por la propia pieza
        // Si es blanca suma en filas, si es negra resta
        int signoMovimiento = (this.color) ? (1) : (-1);
        int diferenciaFilas = nuevaPosicion.getFila() - this.getFila();
        int diferenciaColumnas = nuevaPosicion.getColumna() - this.getColumna();
        int filaAComprobar;

        if (((int) Math.signum(diferenciaFilas)) != signoMovimiento) {
            // se esta moviendo para atras
            return false;
        }else if (diferenciaColumnas == 0) {
            
            // Si entra significa que el peon no va a comer piezas (esta en su misma columna)
            if (Math.abs(diferenciaFilas) > 2) {
                // se esta moviendo mas de 2 casillas
                return false;
            } else {
                // Se esta moviendo 1 o 2 casillas para alante, ahora 
                // debemos comprobar que estan vacias
                for (int i = 1; i < Math.abs(diferenciaFilas) + 1; i++) {
                    filaAComprobar = this.getFila() + i * signoMovimiento;
                    if (tablero[filaAComprobar][this.getColumna()] != null) {
                        return false;
                    }
                }
                if (diferenciaFilas == 1) {
                    // Siempre puede moverse 2 casillas
                    return true;
                } else if (noSeHaMovido && diferenciaFilas == 2) {
                    // Puede moverse 2 casillas en el primer movimiento
                    return true;
                } else {
                    // Cualquier otra cosa es falso
                    return false;
                }

            }
            
        // Si entra aqui significa que el peon se mueve uno en diagonal hacia delante
        } else if (Math.abs(diferenciaColumnas) == 1 && Math.abs(diferenciaFilas) == 1) {
            
            if (tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()] == null) {
                // La casilla esta vacia
                return false;
            }else if (!(tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()].equalsColor(color))) {
                // La casilla esta ocupada por una pieza de disatinto color
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2659 ";
            } else {
                return "\u265F ";
            }
        } else {
            if (color) {
                return "\u2659";
            } else {
                return "\u265F";
            }
        }

    }

}
