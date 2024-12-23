package Piezas;

import daw.Utiles;

public class Rey extends Pieza {

    @Override
    public String getNombre() {
        return "Rey";
    }   

    public static void main(String[] args) throws Exception {
        /*
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos un rey blanco en el centro
        tablero[4][4] = new Rey(true, 4, 4);
        
        // Piezas que puede comer (negras sin protección)
        tablero[4][5] = new Peon(false, 4, 5); // Horizontal
        
        // Pieza negra protegida por peón
        tablero[3][3] = new Alfil(false, 3, 3);
        tablero[2][2] = new Peon(false, 2, 2); // Protege al alfil
        
        // Piezas del mismo color (blancas)
        tablero[5][4] = new Peon(true, 5, 4);
        
        Utiles.toString(tablero);
        
        // Probamos movimientos
        // Movimientos válidos
        Posicion pos1 = new Posicion(4, 5); // Comer pieza negra sin protección
        Posicion pos2 = new Posicion(3, 4); // Movimiento a casilla vacía
        
        // Movimientos inválidos
        Posicion pos3 = new Posicion(3, 3); // Mover a casilla con pieza protegida
        Posicion pos4 = new Posicion(5, 4); // Mover a casilla con pieza propia
        Posicion pos5 = new Posicion(6, 6); // Fuera del alcance del rey
        Posicion pos6 = new Posicion(8, 8); // Fuera del tablero
        
        System.out.println("\nPruebas de movimientos válidos:");
        System.out.println("Movimiento a (4,5): " + tablero[4][4].esMovimientoValido(tablero, pos1) + " (debería ser true - comer pieza sin protección)");
        System.out.println("Movimiento a (3,4): " + tablero[4][4].esMovimientoValido(tablero, pos2) + " (debería ser true - casilla vacía)");
        
        System.out.println("\nPruebas de movimientos inválidos:");
        System.out.println("Movimiento a (3,3): " + tablero[4][4].esMovimientoValido(tablero, pos3) + " (debería ser false - pieza protegida)");
        System.out.println("Movimiento a (5,4): " + tablero[4][4].esMovimientoValido(tablero, pos4) + " (debería ser false - pieza propia)");
        System.out.println("Movimiento a (6,6): " + tablero[4][4].esMovimientoValido(tablero, pos5) + " (debería ser false - fuera de alcance)");
        System.out.println("Movimiento a (8,8): " + tablero[4][4].esMovimientoValido(tablero, pos6) + " (debería ser false - fuera del tablero)");
        */
        
        // Prueba de interacción entre reyes
        /* Pruebas de reyes enfrentados
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos los reyes en posiciones cercanas
        Rey reyBlanco = new Rey(true, 4, 4);
        Rey reyNegro = new Rey(false, 4, 6);
        
        tablero[4][4] = reyBlanco;
        tablero[4][6] = reyNegro;
        
        Utiles.toString(tablero);
        
        System.out.println("\nPruebas de movimientos con reyes enfrentados:");
        
        // Intentamos mover el rey blanco hacia el rey negro
        Posicion pos1 = new Posicion(4, 5); // A una casilla del rey negro
        System.out.println("Movimiento del rey blanco a (4,5): " + reyBlanco.esMovimientoValido(tablero, pos1) + 
                         " (debería ser false - demasiado cerca del rey negro)");
        
        // Movimiento válido del rey blanco
        Posicion pos2 = new Posicion(3, 4);
        System.out.println("Movimiento del rey blanco a (3,4): " + reyBlanco.esMovimientoValido(tablero, pos2) + 
                         " (debería ser true - se aleja del rey negro)");
        
        // Intentamos mover el rey negro hacia el rey blanco
        Posicion pos3 = new Posicion(4, 5);
        System.out.println("Movimiento del rey negro a (4,5): " + reyNegro.esMovimientoValido(tablero, pos3) + 
                         " (debería ser false - demasiado cerca del rey blanco)");
        */
        
        // Pruebas de enroque
        Pieza[][] tablero = new Pieza[8][8];
        
        // Colocamos rey y torres blancas en posición inicial
        Rey reyBlanco = new Rey(true, 0, 4);
        Torre torreBlancaReina = new Torre(true, 0, 0);
        Torre torreBlancaRey = new Torre(true, 0, 7);
        
        tablero[0][4] = reyBlanco;
        tablero[0][0] = torreBlancaReina;
        tablero[0][7] = torreBlancaRey;
        
        Utiles.toString(tablero);
        
        System.out.println("\nPruebas de enroque:");
        
        // Enroque corto (lado del rey)
        Posicion posEnroqueCorto = new Posicion(0, 6);
        System.out.println("Enroque corto: " + reyBlanco.esMovimientoValido(tablero, posEnroqueCorto) + 
                         " (debería ser true - camino libre)");
        
        // Enroque largo (lado de la reina)
        Posicion posEnroqueLargo = new Posicion(0, 2);
        System.out.println("Enroque largo: " + reyBlanco.esMovimientoValido(tablero, posEnroqueLargo) + 
                         " (debería ser true - camino libre)");
        
        
        // Colocamos una pieza enemiga atacando el camino del enroque largo
        //tablero[1][2] = new Torre(false, 1, 2);
        //System.out.println("Enroque largo con casilla atacada: " + reyBlanco.esMovimientoValido(tablero, posEnroqueLargo) + 
        //                 " (debería ser false - casilla atacada)");
        
        // Colocamos una pieza enemiga atacando al rey
        tablero[1][4] = new Torre(false, 1, 4);
        System.out.println("Enroque corto con rey atacado: " + reyBlanco.esMovimientoValido(tablero, posEnroqueCorto) + 
                         " (debería ser false - rey en jaque)");
        
        // Colocamos una pieza enemiga atacando la casilla final del enroque
        tablero[1][6] = new Torre(false, 1, 6);
        System.out.println("Enroque corto con casilla final atacada: " + reyBlanco.esMovimientoValido(tablero, posEnroqueCorto) + 
                         " (debería ser false - casilla final atacada)");

        // Colocamos una pieza enemiga atacando la última casilla del enroque largo
        tablero[1][1] = new Torre(false, 1, 1);
        System.out.println("Enroque largo con última casilla atacada: " + reyBlanco.esMovimientoValido(tablero, posEnroqueLargo) + 
                         " (debería ser true - última casilla atacada (como el rey no pasa por ahi solo importa que no este ocupada))");

        Utiles.toString(tablero);
    }

    public Rey(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public String toString() {

        if (estamosEnLinux) {
            if (color) {
                return "\u2654 ";
            } else {
                return "\u265A ";
            }
        } else {
            if (color) {
                return "Kw";
            } else {
                return "Kb";
            }
        }

    }

    @Override
    public boolean esMovimientoValido(Pieza[][] tablero, Posicion nuevaPosicion) {

        // el rey se puede mover una casilla en cualquier direccion
        // por lo que solo hay que comprobar que la nueva posicion este a una casilla de distancia
        // y que no este ocupada por una pieza del mismo color
        // y que no haya ninguna pieza del otro color que pueda comer al rey

        if (!movimientoFactible(tablero, nuevaPosicion)) {
            return false;
        }

        // Comprobamos que el rey del otro color no este a una casilla de distancia
        if (estaCercaDelOtroRey(tablero, nuevaPosicion)) {
            return false;
        }

        // Si hay enroque, devolvemos true  (comprobamos todo lo necesario en el metodo)
        if (hayEnroque(tablero, nuevaPosicion)) {
            return true;
        }

        // Si no hay enroque, comprobamos que la nueva posicion este a una casilla de distancia
        // y que no este ocupada por una pieza del mismo color
        // y que no haya ninguna pieza del otro color que pueda comer al rey
        int diferenciaFil = nuevaPosicion.getFila() - this.getFila();
        int diferenciaCol = nuevaPosicion.getColumna() - this.getColumna();

        int difColAbsoluta = Math.abs(diferenciaCol);
        int difFilAbsoluta = Math.abs(diferenciaFil);

        int nuevaFila = nuevaPosicion.getFila();
        int nuevaColumna = nuevaPosicion.getColumna();
        
        // Comprobamos que la nueva posicion este a una casilla de distancia
        if (!(difColAbsoluta <= 1 && difFilAbsoluta <= 1)) {
            return false;
        }

        // Comprobamos que la nueva posicion no este ocupada por una pieza del mismo color
        if (tablero[nuevaFila][nuevaColumna] != null) {
            if (tablero[nuevaFila][nuevaColumna].equalsColor(color)) {
                return false;
            }
        }

        Pieza[][] tableroTrasMovimiento = new Pieza[8][8];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tableroTrasMovimiento[i][j] = tablero[i][j];
            }
        }
        tableroTrasMovimiento[nuevaFila][nuevaColumna] = new Rey(this.color, nuevaFila, nuevaColumna);

        // Comprobamos que no haya ninguna pieza del otro color que pueda comer al peon de prueba del nuevo tablero
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                // Comprobamos que no sea null y que no sea de nuestro color y que no sea un rey (lo comprobamos antes)
                if (pieza != null && !(pieza.equalsColor(color)) && !(pieza instanceof Rey)) {
                    // Comprobamos que la pieza pueda comer al peon de prueba
                    if (pieza.esMovimientoValido(tableroTrasMovimiento, nuevaPosicion)) {
                        return false;
                    }  
                }
            }
        }
        // Esto es necesario ya que sin una pieza del color contrario en la poscion del nuevo movimiento 
        // no podemos comprobar si el rey se pone en jaque

        return true;
    }
    // Comprueba si el rey del otro color esta a una casilla de distancia
    // Es necesario ya que si no se crea un bucle infinito
    // se podria hacer en el metodo esMovimientoValido pero asi hay mejor legibilidad
    private boolean estaCercaDelOtroRey(Pieza[][] tablero, Posicion nuevaPosicion) {

        Pieza reyOtroColor = null;
        
        // Buscamos el rey del otro color
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                if (pieza instanceof Rey && pieza.equalsColor(!color)) {
                    reyOtroColor = pieza;
                    break;
                }
            }
        }
        // En una partida de ajedrez este caso no deberia darse nunca
        if (reyOtroColor == null) {
            return false;
        }

        // Comprobamos que el rey del otro color este a una casilla de distancia
        int diferenciaFil = reyOtroColor.getFila() - nuevaPosicion.getFila();
        int diferenciaCol = reyOtroColor.getColumna() - nuevaPosicion.getColumna();

        int difColAbsoluta = Math.abs(diferenciaCol);
        int difFilAbsoluta = Math.abs(diferenciaFil);

        if (!(difColAbsoluta <= 1 && difFilAbsoluta <= 1)) {
            // Si el rey del otro color no esta a una casilla de distancia, devolvemos false
            return false;
        }

        return true;
    }

    private boolean hayEnroque(Pieza[][] tablero, Posicion nuevaPosicion) {

        // Para que haya enroque, el rey no se debe haber movido ya
        // ademas la torre tampoco se debe haber movido
        // no debe haber jaque (no esta implementado)
        // ademas las casillas entre el rey y la torre deben estar libres y no estar atacadas por ninguna pieza del otro color

        int diferenciaColumna = nuevaPosicion.getColumna() - this.getColumna();
        int signoDiferenciaColumna = (int)Math.signum(diferenciaColumna);
        boolean enroqueCorto = (signoDiferenciaColumna == 1);

        if (!(this.noSeHaMovido)) {
            // Comprobamos que el rey no haya movido ya
            return false;
        }else if (this.getFila() != nuevaPosicion.getFila()) {
            // Comprobamos que la fila de la nueva posicion sea la misma que la del rey
            return false;
        }else if (diferenciaColumna != 2 && diferenciaColumna != -2) {
            // Comprobamos que la diferencia de columnas sea 2 o -2
            return false;
        }

        // Si llegamos aqui el movimiento es un enroque, 
        // ahora comprobamos que las casillas entre el rey y la torre esten libres y no esten atacadas por ninguna pieza del otro color

        int columnaTorre = (enroqueCorto) ? 7 : 0;
        int casillasIntermedias = (enroqueCorto) ? 2 : 3;

        
        // Comprobamos que la torre este en su sitio y que no se haya movido
        // si no se ha movido significa que hay torre en esa posicion, y que no se ha movido, por lo que se puede enrocar
        if (!(tablero[this.getFila()][columnaTorre].getNoSeHaMovido())) {
            return false;
        }
        
        // Comprobamos que las casillas entre el rey y la torre esten libres y no esten atacadas por ninguna pieza del otro color

        int columnaAComprobar;

        // Comprobamos que las casillas intermedias esten libres
        for (int i = 1; i < casillasIntermedias; i++) {

            columnaAComprobar = this.getColumna() + signoDiferenciaColumna * i;

            if (tablero[this.getFila()][columnaAComprobar] != null) {
                return false;
            }
        }

        // Comprobamos que las casillas por las que pasaria el rey no esten atacadas por ninguna pieza del otro color
        Posicion posicion1diferencia = new Posicion(this.getFila(), this.getColumna() + signoDiferenciaColumna);
        Posicion posicion2diferencia = new Posicion(this.getFila(), this.getColumna() + signoDiferenciaColumna * 2);

        
        for (Pieza[] fila : tablero) {
            for (Pieza pieza : fila) {
                if (pieza != null && !(pieza.equalsColor(color)) && !(pieza instanceof Rey)) {
                    if (pieza.esMovimientoValido(tablero, posicion1diferencia) || pieza.esMovimientoValido(tablero, posicion2diferencia)) {
                        return false;
                    }  
                }
            }
        }

        return true;
    }
}
