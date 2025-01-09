package daw;


public enum EstadoJuego {

    // estado de juego
    // Turno
    // Mensaje del turno
    // Si hay jaque mate o rey ahogado (fin de juego) 

    TURNO_BLANCAS(true,false,false,"Turno de las blancas"),
    TURNO_NEGRAS(false,false,false,"Turno de las negras"),

    JAQUE_BLANCAS(true,true,false,"Turno de las blancas, hay jaque"),
    JAQUE_NEGRAS(false,true,false,"Turno de las negras, hay jaque"),

    JAQUE_MATE_BLANCAS(true,true,true,"Jaque mate, las negras han ganado"),
    JAQUE_MATE_NEGRAS(false,true,true,"Jaque mate, las blancas han ganado"),

    REY_AHOGADO(null,true,true,"Rey ahogado, hay empate");

    private final Boolean turno;
    private final boolean jaque;
    private final boolean fin;
    private final String mensaje;

    private EstadoJuego(Boolean turno,boolean jaque, boolean fin, String mensaje) {
        this.turno = turno;
        this.jaque = jaque;
        this.fin = fin;
        this.mensaje = mensaje;
    }
    
    public boolean puedeJugar(boolean colorPieza) {
        return this.turno == colorPieza;
    }

    public EstadoJuego siguienteTurno(boolean jaque, boolean fin, boolean reyAhogado) {
        if (reyAhogado) {
            return REY_AHOGADO;
        }
        if (fin) {
            return this.turno ? JAQUE_MATE_NEGRAS : JAQUE_MATE_BLANCAS;
        }
        if (jaque) {
            return this.turno ? JAQUE_NEGRAS : JAQUE_BLANCAS;
        }
        return this.turno ? TURNO_NEGRAS : TURNO_BLANCAS;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public Boolean colorTurno() {
        return this.turno;
    }

    public boolean esFinDelJuego() {
        return this.fin;
    }

    public boolean esJaque() {
        return this.jaque;
    }   

   

}