package Piezas;

import daw.Utiles;

public class Torre extends Pieza {  

    @Override
    public String getNombre() {
        return "Torre";
    }   
    
    public static void main(String[] args) throws Exception {
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos una torre blanca en el centro
        tablero[4][4] = new Torre(true, 4, 4);
        
        // Piezas que puede comer (negras en línea recta)
        tablero[4][7] = new Peon(false, 4, 7);
        tablero[7][4] = new Peon(false, 7, 4);
        tablero[4][1] = new Peon(false, 4, 1);
        
        // Piezas que no puede comer (blancas)
        tablero[4][6] = new Peon(true, 4, 6);
        
        // Piezas que bloquean el camino
        tablero[4][5] = new Peon(false, 4, 5);
        tablero[5][4] = new Peon(true, 5, 4);
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        Posicion pos1 = new Posicion(4, 7); // Movimiento bloqueado por pieza en 4,5
        Posicion pos2 = new Posicion(7, 4); // Movimiento bloqueado por pieza en 5,4
        Posicion pos3 = new Posicion(4, 3); // Movimiento válido horizontal
        Posicion pos4 = new Posicion(3, 3); // Movimiento inválido (diagonal)
        Posicion pos5 = new Posicion(8, 8); // Fuera del tablero
        Posicion pos6 = new Posicion(4, 1); // Movimiento válido - puede comer pieza negra
        Posicion pos7 = new Posicion(3, 4); // Movimiento válido vertical a casilla vacía
        
        System.out.println("Movimiento a (4,7): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser false - hay una pieza bloqueando en (4,5))");
        System.out.println("Movimiento a (7,4): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser false - hay una pieza bloqueando en (5,4))"); 
        System.out.println("Movimiento a (4,3): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser true - horizontal libre)");
        System.out.println("Movimiento a (3,3): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser false - no es movimiento en línea recta)");
        System.out.println("Movimiento a (8,8): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser false - fuera del tablero)");
        System.out.println("Movimiento a (4,1): " + tablero[4][4].esMovimientoValido(tablero, pos6) + " (debería ser true - puede comer pieza negra)");
        System.out.println("Movimiento a (3,4): " + tablero[4][4].esMovimientoValido(tablero, pos7) + " (debería ser true - vertical libre a casilla vacía)");
    }

    public Torre(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2656 ";
            } else {
                return "\u265C ";
            }
        } else {
            if (color) {
                return "Rw";
            } else {
                return "Rb";
            }
        }

    }
    
    // Para comprobar el movimiento de una Torre:
    // - Tiene que estar en la misma Fila o columna
    // - Tiene que tener el camino libre
    // - La casilla tiene que estar libre u ocupada por una pieza del otro color 

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {
        
        if (!movimientoFactible(tablero, nuevaPosicion)) {
            return false;
        }
        
        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();
        
        // Si llega aqui el movimiento es factible, solo queda saber si es legal
        // Necesitamos saber si esta en linea recta
        
        int diferenciaFilas = nuevaPosicion.getFila() - this.getFila();
        int diferenciaColumnas = nuevaPosicion.getColumna() - this.getColumna();
        int difColAbsoluta = Math.abs(diferenciaColumnas);
        int difFilAbsoluta = Math.abs(diferenciaFilas);
        int columnaAComprobar;
        int filaAComprobar;
        
   
        if (difColAbsoluta == 0) {
            // Se mueve en el eje Y
            int signoMovimiento = (int) Math.signum(diferenciaFilas);
            
            for (int i = 1; i < difFilAbsoluta; i++) {
                
                filaAComprobar = this.getFila() + i * signoMovimiento;
                
                if (tablero[filaAComprobar][this.getColumna()] != null) {
                    // Comprobamos todas las casillas hasta la penultima
                    // y si no estan vacias no puede moverse 
                    return false;
                }   
            }if (tablero[nuevaFila][nuevaColumna] == null) {
                // Si el camino esta vacio y la casilla tambien puede moverse
                return true;
            }else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(color))) {
                // Tambien si hay una ficha del color contrario                 
                return true;
            }else{
                return false;
            }
            
            
        }else if (difFilAbsoluta == 0) {
            // Se mueve en el eje X
            int signoMovimiento = (int) Math.signum(diferenciaColumnas);
            
            for (int i = 1; i < difColAbsoluta; i++) {
                
                columnaAComprobar = this.getColumna()+ i * signoMovimiento;
                
                if (tablero[this.getFila()][columnaAComprobar] != null) {
                    // Comprobamos todas las casillas hasta la penultima
                    // y si no estan vacias no puede moverse 
                    return false;
                }
            }if (tablero[nuevaFila][nuevaColumna] == null) {
                // Si el camino esta vacio y la casilla tambien puede moverse
                return true;
            }else if (!(tablero[nuevaFila][nuevaColumna].equalsColor(color))) {
                // Tambien si hay una ficha del color contrario 
                return true;
            }else{
                return false;
            }
        }else{
            // Cualquier otra cosa es ilegal
            return false;
        }
        
        
        
    }

}
