
package Piezas;


public class Posicion {
    
    private int fila;
    private int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    public boolean esValida(){
        if (fila < 0 || fila > 7) {
            return false;
        }else if (columna < 0 || columna > 7) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Posicion other = (Posicion) obj;
        if (this.fila != other.fila) {
            return false;
        }
        return this.columna == other.columna;
    }
    
    
    
    // toString devuleve en notacion de ajedrez
    @Override
    public String toString() {
        // Convertir fila a notación de ajedrez (8 - fila)
        int filaNotacion = 1 + fila; // 0 -> 1, 7 -> 8

        // Convertir columna a letra (columna 0 = 'a', columna 7 = 'h')
        char columnaNotacion = (char) ('a' + columna);

        // Combinar fila y columna en el formato "8a"
        return filaNotacion + "" + columnaNotacion;
    }
    

}
