package Piezas;

public class Peon extends Pieza {

    public Peon(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    // Para comprobar si el movimiento de un Peon es valido:
    // - Esta dento del tablero
    // - Se mueve uno o dos para alante (depende del color) 
    // - No hay piezas en medio
    // Ademas el peon come en diagonal hacia delante
    @Override
    public boolean movimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {

        if (estaClavada) {
            //Si esta clavada no se puede mover 
            return false;
        } else if (!(nuevaPosicion.esValida())) {
            // Esta fuera del tablero
            return false;
        } else if (this.posicion.equals(nuevaPosicion)) {
            return false;
        }

        // si llegamos aqui, en principio el movimiento es factible
        // no esta clavada, la posicion esta dentro del tablero y no es la misma 
        // en la que ya esta la propia pieza
        // ahora debemos comprobar que el movimiento es posible por la propia pieza
        
        
        // Si es blanca suma en filas, si es negra resta
        int signoMovimiento = (this.color) ? (1) : (-1);
        
        
        if (!(nuevaPosicion.getColumna() == this.getColumna() || 
                nuevaPosicion.getColumna() == this.getColumna() + 1||
                nuevaPosicion.getColumna() == this.getColumna() - 1||
                nuevaPosicion.getFila()*signoMovimiento)){
            return false;
            
            "".equals(tablero)
        }
        
        
        
        
        // El movimiento es normal (No come pieza)
        if (nuevaPosicion.getColumna() == this.getColumna()) {

            
            int numCasillas = (this.noSeHaMovido) ? 2 : 1;
            int filaAComprobar;

            for (int i = 0; i < numCasillas; i++) {

                // con esto calculamos la posicion que debemos comprobar
                // misma columna y una (o dos) filas abajo o arrba
                filaAComprobar = this.getFila() + (i * signoMovimiento);

                if (tablero[this.getColumna()][filaAComprobar] != null) {
                    return false;
                }
            }
        }else if () {
            
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
                return "Pw";
            } else {
                return "Pb";
            }
        }

    }

}
