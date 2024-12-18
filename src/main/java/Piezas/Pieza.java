 
package Piezas;

// Pieza es una clase abtracta que sirve como plantilla para las demas piezas
public abstract class Pieza {
    
    
    protected final boolean estamosEnLinux = true;
    
    // Protected ya que van a ser usadas por las otras piezas
    protected final boolean color;
    protected Posicion posicion;
    
    // Este boolean es necesario ya que hay movimientos como 
    // el enrroque o el primer movimiento del peon que solo pueden hacerse 
    // si las piezas no se han movido
    // se volvera false tras el primer movimiento
    protected boolean noSeHaMovido = true;
    
    // Este boolean es necesario ya que se una pieza esta clavada
    // No puede moverse
    protected boolean estaClavada = false;

    
    public Pieza(boolean color, int fila, int columna){
        this.color = color;
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
    
    // Devuelve el getFila de Posicion
    public int getFila(){
        return this.posicion.getFila();
    }
    
    // Devuelve el getColumna de Posicion
    public int getColumna(){
        return this.posicion.getColumna();
    }
    
    public boolean equalsColor(boolean color) {
        return color == this.color;
    }

    public boolean getEstaClavada() {
        return estaClavada;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
    
    public void setPosicion(int fila, int columna) {
        this.posicion.setFila(fila);
        this.posicion.setColumna(columna);
    }
    
    public void setEstaClavada(boolean estaClavada) {
        this.estaClavada = estaClavada;
    }
    
    @Override
    public abstract String toString();  
    
    protected boolean movimientoFactible(Posicion nuevaPosicion) {
        if (!(nuevaPosicion.esValida())) {
            // Esta fuera del tablero
            return false;
        } else if (this.posicion.equals(nuevaPosicion)) {
            // La posicion es la misma
            return false;
        }
        return true;
    }
    
}


