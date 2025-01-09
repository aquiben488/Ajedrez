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

        Pieza pieza = tablero[origen.getFila()][origen.getColumna()];
        // Si ha habido captura al paso, se elimina la pieza capturada
        if (haHabidoCapturaAlPaso) {
            tablero[PosicionCapturaAlPaso.getFila()][PosicionCapturaAlPaso.getColumna()] = null;
            haHabidoCapturaAlPaso = false;
        }

        // Actualizamos la posicion de la captura al paso (si no ha habido es null)
        actualizarPosicionCapturaAlPaso(origen, destino);

        // Comprobamos si hay enroque
        if (pieza instanceof Rey && (Math.abs(origen.getColumna() - destino.getColumna()) == 2)) {
            realizarMovimientoEnroque(origen, destino);
        }

        // Movemos la pieza
        pieza.mover(tablero, destino);

    }

    private void realizarMovimientoEnroque(Posicion origen, Posicion destino) {

        // El enroque es igual independientemente del color
        // Ocurre en la misma fila del rey, por lo que solo debemos saber si es corto o largo
        boolean enroqueCorto = destino.getColumna() > origen.getColumna();
        int fila = origen.getFila();

        // Posiciones de la torre
        Posicion origenTorre = new Posicion(fila, enroqueCorto ? 7 : 0);
        Posicion destinoTorre = new Posicion(fila, enroqueCorto ? 5 : 3);

        // Mover la torre
        tablero[origenTorre.getFila()][origenTorre.getColumna()].mover(tablero, destinoTorre);
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

    public void actualizarEstadoJuego() {

        Pieza reyContrario = null;

        // Buscamos el rey del otro color, ya que necesitamos su posicion para comprobar si hay jaque, jaque mate y rey ahogado
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                if (pieza instanceof Rey && !(pieza.equalsColor(estadoActual.colorTurno()))) {
                    reyContrario = pieza;
                    break;
                }
            }
        }

        // Esto no funciona ya que para comprobar si hay jaque mate, necesitamos saber si el rey del otro color puede moverse
        // y ademas si no hay ninguna pieza que pueda bloquear el jaque
        // tengo que comprobar que pieza puede comer al rey del otro color y que pieza puede bloquear el jaque
        // tengo que meter esas piezas en una lista y comprobar con todas mis piezas las casillas intermedias (menos si es un caballo)

        boolean jaque = hayNuevoJaque(reyContrario.getPosicion());

        boolean elReyContrarioPuedeMoverse = elReyContrarioPuedeMoverse(reyContrario);
        boolean jaqueMate =  (jaque) ? elReyContrarioPuedeMoverse : false;
        boolean reyAhogado = (elReyContrarioPuedeMoverse) ? hayReyAhogado(reyContrario.getPosicion()) : false;

        estadoActual = estadoActual.siguienteTurno(jaque, jaqueMate, reyAhogado);
    }

    // Comprueba si un nuevo jaque se ha producido
    private boolean hayNuevoJaque(Posicion posicionReyContrario) {

        

        // Comprobamos si hay una pieza que pueda comer al rey del otro color
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                // Comprobamos que no sea null y que no sea de nuestro color y que no sea un rey
                if (pieza != null && pieza.equalsColor(estadoActual.colorTurno()) && !(pieza instanceof Rey)) {
                    // Comprobamos que la pieza pueda comer al rey
                    if (pieza.esMovimientoValido(tablero, posicionReyContrario)) {
                        return true;
                    }  
                }
            }
        }
        return false;
    }

    // Comprueba si el jaque mate se ha producido
    private boolean elReyContrarioPuedeMoverse(Pieza reyContrario) {

        // Comprobamos si el rey del otro color puede moverse a ninguna posicion adyacente

        Posicion posicionReyContrario = reyContrario.getPosicion();
        Posicion posicionAdyacente = null;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                posicionAdyacente = new Posicion(posicionReyContrario.getFila() + i, posicionReyContrario.getColumna() + j);

                if (reyContrario.esMovimientoValido(tablero, posicionAdyacente)) {
                    return false;
                }
            }
        }
        return true;

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

}
