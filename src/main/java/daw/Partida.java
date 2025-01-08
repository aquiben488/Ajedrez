package daw;

import Piezas.*;

public class Partida {
    
    //Atributos
    private Pieza[][] tablero;
    private EstadoJuego estadoActual;
    private Posicion PosicionCapturaAlPaso = null;
    private boolean haHabidoCapturaAlPaso = false;

    //Constructor
    public Partida() {
        tablero = new Pieza[8][8];
        inicializarTablero();

        //Estado de la partida
        this.estadoActual = EstadoJuego.TURNO_BLANCAS;
    }

    // Coloca las piezas en el tablero
    private void inicializarTablero() {
         
        this.tablero[0][0] = new Torre(true,0,0);
        this.tablero[0][1] = new Caballo(true,0,1);
        this.tablero[0][2] = new Alfil(true,0,2);
        this.tablero[0][3] = new Reina(true,0,3);
        this.tablero[0][4] = new Rey(true,0,4);
        this.tablero[0][5] = new Alfil(true,0,5);
        this.tablero[0][6] = new Caballo(true,0,6);
        this.tablero[0][7] = new Torre(true,0,7);
        
        for (int i = 0; i < 8; i++) {
            this.tablero[1][i] = new Peon(true,0,i);
            this.tablero[6][i] = new Peon(false,6,i);
        }
        
        this.tablero[7][0] = new Torre(false,7,0);
        this.tablero[7][1] = new Caballo(false,7,1);
        this.tablero[7][2] = new Alfil(false,7,2);
        this.tablero[7][3] = new Reina(false,7,3);
        this.tablero[7][4] = new Rey(false,7,4);
        this.tablero[7][5] = new Alfil(false,7,5);
        this.tablero[7][6] = new Caballo(false,7,6);
        this.tablero[7][7] = new Torre(false,7,7);   
    }

    public boolean esMovimientoValido(Posicion origen, Posicion destino) {

        Pieza pieza = tablero[origen.getFila()][origen.getColumna()];

        if (pieza == null) {
            return false;
        }
        if (this.estadoActual.puedeJugar(pieza.getColor())) {

        if (pieza instanceof Peon) {
            if (((Peon)pieza).esMovimientoValidoCapturaAlPaso(tablero, destino, PosicionCapturaAlPaso)) {
                haHabidoCapturaAlPaso = true;
                return true;
            }
        }
    }
        return pieza.esMovimientoValido(tablero, destino);
    
    }
    
    public void realizarMovimiento(Posicion origen, Posicion destino) {

        // Si ha habido captura al paso, se elimina la pieza capturada
        if (haHabidoCapturaAlPaso) {
            tablero[PosicionCapturaAlPaso.getFila()][PosicionCapturaAlPaso.getColumna()] = null;
            haHabidoCapturaAlPaso = false;
        }

        // Actualizamos la posicion de la captura al paso (si no ha habido es null)
        actualizarPosicionCapturaAlPaso(origen, destino);

        // Movemos la pieza
        tablero[origen.getFila()][origen.getColumna()].mover(tablero, destino);

    }

    public Pieza[][] getTablero() {
        return tablero;
    }

    public EstadoJuego getEstadoActual() {
        return estadoActual;
    }

    public Pieza getPieza(Posicion posicion) {
        return tablero[posicion.getFila()][posicion.getColumna()];
    }

    public boolean hayJaque() {
        return estadoActual.esJaque();
    }

    public boolean hayfinDelJuego() {
        return estadoActual.esFinDelJuego();
    }

    // Metodo que comprueba si hay captura al paso, si hay, actualiza la posicion de la captura al paso
    public void actualizarPosicionCapturaAlPaso(Posicion origen, Posicion destino) {

        int distancia = Math.abs(origen.getFila() - destino.getFila());
        
        if (tablero[origen.getFila()][origen.getColumna()] instanceof Peon && distancia == 2) {
            PosicionCapturaAlPaso = destino;
        } else {
            PosicionCapturaAlPaso = null;
        }
    }


}
