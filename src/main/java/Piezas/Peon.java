package Piezas;

import daw.Utiles;

public class Peon extends Pieza {

    @Override
    public String getNombre() {
        return "Peon";
    }      

    public static void main(String[] args) throws Exception {
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un peón negro en el centro
        tablero[4][4] = new Peon(false, 4, 4);
        
        // Piezas que puede comer (blancas en diagonal)
        tablero[3][5] = new Peon(true, 3, 5);
        tablero[3][3] = new Peon(true, 3, 3);
        
        // Piezas que bloquean movimiento
        tablero[3][4] = new Peon(true, 3, 4); // Bloquea avance
        tablero[2][4] = new Peon(false, 2, 4); // Pieza propia
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        // Movimientos válidos
        Posicion pos1 = new Posicion(3, 5); // Comer en diagonal derecha
        Posicion pos2 = new Posicion(3, 3); // Comer en diagonal izquierda
        
        // Movimientos inválidos
        Posicion pos3 = new Posicion(3, 4); // Avanzar con pieza delante
        Posicion pos4 = new Posicion(2, 4); // Mover a casilla con pieza propia
        Posicion pos5 = new Posicion(1, 4); // Avanzar más de 2 casillas
        Posicion pos6 = new Posicion(5, 4); // Retroceder
        
        System.out.println("\nPruebas de movimientos válidos:");
        System.out.println("Comer en diagonal derecha: " + tablero[4][4].esMovimientoValido(tablero, pos1));
        System.out.println("Comer en diagonal izquierda: " + tablero[4][4].esMovimientoValido(tablero, pos2));
        
        System.out.println("\nPruebas de movimientos inválidos:");
        System.out.println("Avanzar con pieza delante: " + tablero[4][4].esMovimientoValido(tablero, pos3));
        System.out.println("Mover a casilla con pieza propia: " + tablero[4][4].esMovimientoValido(tablero, pos4));
        System.out.println("Avanzar más de 2 casillas: " + tablero[4][4].esMovimientoValido(tablero, pos5));
        System.out.println("Retroceder: " + tablero[4][4].esMovimientoValido(tablero, pos6));
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

        if (!movimientoFactible(tablero, nuevaPosicion)) {
            return false;
        }
        
        // si llegamos aqui, en principio el movimiento es factible
        // no esta clavada, la posicion esta dentro del tablero y no es la misma 
        // en la que ya esta la propia pieza
        // ahora debemos comprobar que el movimiento es posible por la propia pieza
        // Si es blanca suma en filas, si es negra resta
        int signoMovimiento = (this.COLOR) ? (1) : (-1);
        int diferenciaFilas = nuevaPosicion.getFila() - this.getFila();
        int diferenciaColumnas = nuevaPosicion.getColumna() - this.getColumna();
        int filaAComprobar;

        if (((int) Math.signum(diferenciaFilas)) != signoMovimiento) {
            // se esta moviendo para atras
            return false;
        }
        if (diferenciaColumnas == 0) {
            
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
            }else if (!(tablero[nuevaPosicion.getFila()][nuevaPosicion.getColumna()].equalsColor(this.COLOR))) {
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
            if (this.COLOR) {
                return "\u2659 ";
            } else {
                return "\u265F ";
            }
        } else {
            if (this.COLOR) {
                return "Pw";
            } else {
                return "Pb";
            }
        }

    }

}
