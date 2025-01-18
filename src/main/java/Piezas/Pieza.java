package Piezas;

import daw.Utiles;
import java.util.ArrayList;

// Pieza es una clase abtracta que sirve como plantilla para las demas piezas
public abstract class Pieza {

    public static void main(String[] args) throws Exception {
        /*
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un rey blanco
        tablero[4][4] = new Rey(true, 4, 4);
        
        // Colocamos una torre blanca que protege al rey
        tablero[4][3] = new Torre(true, 4, 3);
        
        // Colocamos una torre negra que amenaza al rey si se mueve la torre blanca
        tablero[4][0] = new Torre(false, 4, 0);
        
        // Probamos movimientos de la torre clavada
        Posicion pos1 = new Posicion(3, 3); // Intento de comer pieza - debería ser inválido por estar clavada
        Posicion pos2 = new Posicion(5, 3); // Intento de mover verticalmente - debería ser inválido por estar clavada
        Posicion pos3 = new Posicion(4, 2); // Intento de mover horizontalmente en línea con el rey - debería ser válido
        Posicion pos4 = new Posicion(4, 1); // Intento de mover horizontalmente en línea con el rey - debería ser válido
        
        System.out.println("\nPruebas de movimientos de torre clavada:");
        System.out.println("Mover a (3,3): " + tablero[4][3].esMovimientoValido(tablero, pos1) + " (debería ser false - torre clavada)");
        System.out.println("Mover a (5,3): " + tablero[4][3].esMovimientoValido(tablero, pos2) + " (debería ser false - torre clavada)");
        System.out.println("Mover a (4,2): " + tablero[4][3].esMovimientoValido(tablero, pos3) + " (debería ser true - movimiento en línea con el rey)");
        System.out.println("Mover a (4,1): " + tablero[4][3].esMovimientoValido(tablero, pos4) + " (debería ser true - movimiento en línea con el rey)");
        */
        
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un rey blanco y una torre negra que lo pone en jaque

        tablero[4][4] = new Rey(true, 4, 4);
        tablero[4][7] = new Torre(false, 4, 7);
        
        // Colocamos una torre blanca que puede interponerse
        tablero[2][5] = new Torre(true, 2, 5);
        
        Utiles.toString(tablero);
        
        // Probamos movimientos del rey en jaque
        Posicion pos1 = new Posicion(3, 4); // Movimiento válido - el rey escapa del jaque
        Posicion pos2 = new Posicion(4, 5); // Movimiento inválido - el rey sigue en jaque
        Posicion pos3 = new Posicion(4, 3); // Movimiento inválido - el rey sigue en jaque
        Posicion pos4 = new Posicion(5, 5); // Movimiento válido - el rey escapa del jaque
        Posicion pos5 = new Posicion(5, 4); // Movimiento válido - el rey escapa del jaque
        
        System.out.println("\nPruebas de movimientos del rey en jaque:");
        System.out.println("Rey a (3,4): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser true - escapa del jaque)");
        System.out.println("Rey a (4,5): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser false - sigue en jaque)");
        System.out.println("Rey a (4,3): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser false - sigue en jaque)");
        System.out.println("Rey a (5,5): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser true - escapa del jaque)");
        System.out.println("Rey a (5,4): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser true - escapa del jaque)");
        
        // Probamos movimientos de otras piezas con el rey en jaque
        Posicion pos6 = new Posicion(4, 5); // Movimiento válido - la torre se interpone
        Posicion pos7 = new Posicion(2, 5); // Movimiento inválido - no ayuda al rey
        
        System.out.println("\nPruebas de movimientos de otras piezas con rey en jaque:");
        System.out.println("Torre a (4,5): " + tablero[2][5].esMovimientoValido(tablero, pos6) + " (debería ser true - se interpone)");
        System.out.println("Torre a (2,5): " + tablero[2][5].esMovimientoValido(tablero, pos7) + " (debería ser false - no ayuda al rey)");
    }
    
    
    protected final boolean estamosEnLinux = false;
    
    // Protected ya que van a ser usadas por las otras piezas
    protected final boolean COLOR;
    protected Posicion posicion;
    
    // Este boolean es necesario ya que hay movimientos como 
    // el enrroque o el primer movimiento del peon que solo pueden hacerse 
    // si las piezas no se han movido
    // se volvera false tras el primer movimiento
    protected boolean noSeHaMovido = true;
    
    // Flag para evitar la recursión infinita en la comprobación de piezas clavadas (deja al rey en jaque)
    private boolean verificandoJaque = false;
    
    private ArrayList<Posicion> movimientosPosibles;

    public ArrayList<Posicion> getMovimientosPosibles() {
        return movimientosPosibles;
    }

    public void setMovimientosPosibles(ArrayList<Posicion> movimientosPosibles) {
        this.movimientosPosibles = movimientosPosibles;
    }
    
    public abstract String getNombre();
    
    public Pieza(boolean COLOR, int fila, int columna){
        this.COLOR = COLOR;
        this.posicion = new Posicion(fila, columna);
    }
    
    // Este es el metodo que usaran todas las piezas
    // Aqui deben llegar la fila y la columna convertidos a numeros normales, 
    // no en notacion de ajedrez
    
    
    public abstract boolean esMovimientoValido(Pieza[][] tablero,Posicion nuevaPosicion);
    
    // Este sera el metodo que usaran todas las piezas

    // Esto llama a posicion.toString, que devuleve la posicon en notacion de ajedrez
    public Posicion getPosicion() {
        return posicion;
    }

    public boolean getNoSeHaMovido() {
        return noSeHaMovido;
    }
    
    // Devuelve el getFila de Posicion
    public int getFila(){
        return this.posicion.getFila();
    }
    
    // Devuelve el getColumna de Posicion
    public int getColumna(){
        return this.posicion.getColumna();
    }
    
    public boolean equalsColor(boolean color) {
        return color == this.COLOR;
    }

    public boolean getColor() {
        return this.COLOR;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
    
    public void setPosicion(int fila, int columna) {
        this.posicion.setFila(fila);
        this.posicion.setColumna(columna);
    }
    
    @Override
    public abstract String toString();  
    
    protected boolean movimientoFactible(Pieza[][] tablero, Posicion nuevaPosicion) {
        // Verificar null primero
        if (nuevaPosicion == null) {
            return false;
        }

        if (!(nuevaPosicion.esValida())) {
            // Esta fuera del tablero
            return false;
        } else if (this.posicion.equals(nuevaPosicion)) {
            // La posicion es la misma
            return false;
        }

        // Si ya estamos verificando jaque, evitamos la recursión
        if (verificandoJaque) {
            return true;
        }

        try {
            verificandoJaque = true;
            if (dejaAlReyEnJaque(tablero, nuevaPosicion)) {
                return false;
            }
        } finally {
            verificandoJaque = false;
        }
        
        return true;
    }
    
    public void mover(Pieza[][] tablero, Posicion nuevaPosicion) {

            // Eliminamos la pieza de su posicion actual    
            tablero[this.getFila()][this.getColumna()] = null;
            // Movemos la pieza a la nueva posicion
            this.posicion = nuevaPosicion;
            // Indicamos que la pieza ya no es la primera vez que se mueve
            this.noSeHaMovido = false;
            // Movemos la pieza a la nueva posicion
            tablero[this.getFila()][this.getColumna()] = this; 

    }

    protected boolean dejaAlReyEnJaque(Pieza[][] tablero, Posicion nuevaPosicion) {

        // Si el movimiento deja al rey en jaque, devuelve true
         
        // Basicamente, con esto tenemos que si hay jaque solo se puede mover una pieza que interponga o se mueva a una posicion que no deje al rey en jaque
        // y que no se puedan mover piezas clavadas

        // Para ello, creamos un tablero auxiliar con la nueva posicion de la pieza
        // (ademas buscaremos el rey ya que necesitamos su posicion)

        // En este caso estamos haciendo un alias de cada pieza, no copiamos la pieza, 
        // solo copiamos la referencia a la pieza, por lo que si modificamos el tableroTrasMovimiento,
        // modificamos el tablero original

        Posicion reyPosicion = null;
        Pieza[][] tableroTrasMovimiento = new Pieza[8][8];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tableroTrasMovimiento[i][j] = tablero[i][j];
                if (tablero[i][j] instanceof Rey && tablero[i][j].equalsColor(this.COLOR)) {
                    reyPosicion = new Posicion(i, j);
                }
            }
        }
        tableroTrasMovimiento[nuevaPosicion.getFila()][nuevaPosicion.getColumna()] = this;
        tableroTrasMovimiento[this.getFila()][this.getColumna()] = null;

        // Si la pieza que se mueve es el rey, actualizar su posición
        if (this instanceof Rey) {
            reyPosicion = nuevaPosicion;
        }

        for (Pieza[] fila : tableroTrasMovimiento) {
            for (Pieza pieza : fila) {
                // Comprobamos que no sea null y que no sea de nuestro color y que no sea un rey (lo comprobamos antes)
                if (pieza != null && !(pieza.equalsColor(this.COLOR)) && !(pieza instanceof Rey)) {
                    // Comprobamos que la pieza pueda comer al rey
                    if (pieza.esMovimientoValido(tableroTrasMovimiento, reyPosicion)) {
                        return true;
                    }  
                }
            }
        }
        return false;
    }
}

