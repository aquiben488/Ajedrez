package daw;


public enum EstadoJuego {

    // estado de juego
    // Turno
    // Mensaje del turno
    // Si hay jaque mate o rey ahogado (fin de juego) 

    TURNO_BLANCAS(true,false,"Turno de las blancas"),
    TURNO_NEGRAS(false,false,"Turno de las negras"),
    TURNO_BLANCAS_FIN(true,true,"Jaque mate, las blancas han ganado"),
    TURNO_NEGRAS_FIN(false,true,"Jaque mate, las negras han ganado"),
    TURNO_FIN_REY_AHOGADO(null,true,"Rey ahogado, hay empate");

    private final Boolean turno;
    private final boolean fin;
    private final String mensaje;

    private EstadoJuego(Boolean turno, boolean fin, String mensaje) {
        this.turno = turno;
        this.fin = fin;
        this.mensaje = mensaje;
    }
    
    








    public String getMensaje() {
        return this.mensaje;
    }

    public Boolean esTurnoBlancas() {
        return this.turno;
    }

    public boolean esFinDelJuego() {
        return this.fin;
    }

   
}