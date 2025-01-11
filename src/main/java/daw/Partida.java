package daw;

import java.util.ArrayList;

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
        boolean jaqueMate =  (jaque) ? hayJaqueMate(reyContrario) : false;
        boolean reyAhogado = (jaque) ? false : comprobarReyAhogado();

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

    
    private boolean hayJaqueMate(Pieza reyContrario) {

        Posicion posicionReyContrario = reyContrario.getPosicion();
        Posicion posicionAdyacente = null;

        // Comprobamos si el rey del otro color puede moverse a ninguna posicion adyacente
        // Si puede moverse a alguna, no hay jaque mate
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                posicionAdyacente = new Posicion(posicionReyContrario.getFila() + i, posicionReyContrario.getColumna() + j);

                if (reyContrario.esMovimientoValido(tablero, posicionAdyacente)) {
                    return false;
                }
            }
        }
        

        ArrayList<Pieza> piezasPuedenComerRey = new ArrayList<>();
        ArrayList<Posicion> PosicionesQuePuedenBloquearJaque = new ArrayList<>();

        // Comprobamos si hay una pieza que pueda comer al rey del otro color
        // Si hay una pieza que pueda comer al rey tenemos que comprobar que no haya una pieza que pueda bloquear el jaque
        // Si hay 2 piezas que puedan comer al rey, entonces ese jaque no se puede bloquear
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                // Comprobamos que no sea null y que no sea de nuestro color y que no sea un rey (ya que no puede comer al rey)
                if (pieza != null && pieza.equalsColor(estadoActual.colorTurno()) && !(pieza instanceof Rey)) {
                    // Comprobamos que la pieza pueda comer al rey
                    if (pieza.esMovimientoValido(tablero, posicionReyContrario)) {
                        piezasPuedenComerRey.add(pieza);
                    }  
                }
            }
        }

        // Si hay 2 piezas que puedan comer al rey, entonces ese jaque no se puede bloquear, por lo que hay jaque mate
        if (piezasPuedenComerRey.size() == 2) {
            return true;
        }
// Hay que mirar esto otra vez
        // Añadimos las posiciones que pueden bloquear el jaque, dependiendo del tipo de pieza
        PosicionesQuePuedenBloquearJaque = posicionesQuePuedenBloquearJaque(piezasPuedenComerRey, posicionReyContrario);

        // Comprobamos si hay una pieza que pueda bloquear el jaque
        // Si hay una pieza que pueda bloquear el jaque, no hay jaque mate
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.equalsColor(estadoActual.colorTurno()) && !(pieza instanceof Rey)) {
                    for (Posicion posicion : PosicionesQuePuedenBloquearJaque) {
                        if (this.esMovimientoValido(pieza.getPosicion(), posicion)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    } 

    private ArrayList<Posicion> posicionesQuePuedenBloquearJaque(ArrayList<Pieza> piezasPuedenComerRey, Posicion posicionReyContrario) {

        ArrayList<Posicion> PosicionesQuePuedenBloquearJaque = new ArrayList<>();

        // Si es un caballo o un peon, añadimos su posicion solo, ya que no se pueden bloquear
        // Para el resto debemos añadir todas las posiciones entre esa pieza y el rey
        for (Pieza pieza : piezasPuedenComerRey) {
            if (pieza instanceof Caballo) {
                PosicionesQuePuedenBloquearJaque.add(pieza.getPosicion());
            } else if (pieza instanceof Peon) {
                PosicionesQuePuedenBloquearJaque.add(pieza.getPosicion());

            } else if (pieza instanceof Reina) {
                PosicionesQuePuedenBloquearJaque.add(pieza.getPosicion());

                int diferenciaFil = posicionReyContrario.getFila() - pieza.getFila();
                int diferenciaCol = posicionReyContrario.getColumna() - pieza.getColumna();

                int signoFil = (int) Math.signum(diferenciaFil);
                int signoCol = (int) Math.signum(diferenciaCol);

                // Movimiento diagonal (como alfil)
                if (Math.abs(diferenciaFil) == Math.abs(diferenciaCol)) {
                    for (int i = 1; i < Math.abs(diferenciaCol); i++) {
                        PosicionesQuePuedenBloquearJaque.add(new Posicion(
                            pieza.getFila() + (i * signoFil),
                            pieza.getColumna() + (i * signoCol)));
                    }
                }
                // Movimiento horizontal/vertical (como torre)
                else {
                    boolean movimientoVertical = diferenciaCol == 0;
                    if (movimientoVertical) {
                        for (int i = 1; i < Math.abs(diferenciaFil); i++) {
                            PosicionesQuePuedenBloquearJaque.add(new Posicion(
                                pieza.getFila() + (i * signoFil),
                                pieza.getColumna()));
                        }
                    } else {
                        for (int i = 1; i < Math.abs(diferenciaCol); i++) {
                            PosicionesQuePuedenBloquearJaque.add(new Posicion(
                                pieza.getFila(),
                                pieza.getColumna() + (i * signoCol)));
                        }
                    }
                }
            } else if (pieza instanceof Alfil) {
                PosicionesQuePuedenBloquearJaque.add(pieza.getPosicion());

                int diferenciaFil = posicionReyContrario.getFila() - pieza.getFila();
                int diferenciaCol = posicionReyContrario.getColumna() - pieza.getColumna();

                // necesitamos el signo de la diferencia ya que
                // con ello sabemos en que diagonal va a moverse

                // filas : positivo = va para abajo, negativo = arriba
                // columnas : positivo = va para la derecha, negativo = izquierda
                int signoCol = (int) Math.signum(diferenciaCol);
                int signoFil = (int) Math.signum(diferenciaFil);

                for (int i = 1; i < Math.abs(diferenciaCol); i++) { 
                    PosicionesQuePuedenBloquearJaque.add(new Posicion(
                        pieza.getFila() + (i * signoFil), 
                        pieza.getColumna() + (i * signoCol)));
                }

            } else if (pieza instanceof Torre) {
                PosicionesQuePuedenBloquearJaque.add(pieza.getPosicion());

                int diferenciaFil = posicionReyContrario.getFila() - pieza.getFila();
                int diferenciaCol = posicionReyContrario.getColumna() - pieza.getColumna();

                int signoFil = (int) Math.signum(diferenciaFil);
                int signoCol = (int) Math.signum(diferenciaCol);

                boolean movimientoVertical = diferenciaCol == 0;

                // Dependiendo de si es vertical o horizontal, añadimos las posiciones que puede bloquear
                // la posicion original mas el signo de la diferencia
                if (movimientoVertical) {
                    for (int i = 1; i < Math.abs(diferenciaFil); i++) { 
                        PosicionesQuePuedenBloquearJaque.add(new Posicion(
                            pieza.getFila() + (i * signoFil), 
                            pieza.getColumna()));
                    }
                } else {
                    for (int i = 1; i < Math.abs(diferenciaCol); i++) { 
                        PosicionesQuePuedenBloquearJaque.add(new Posicion(
                            pieza.getFila(), 
                            pieza.getColumna() + (i * signoCol)));
                    }
                }
            }
        }

        return PosicionesQuePuedenBloquearJaque;

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
