package daw;

import Piezas.*;


public class Ajedrez {

    public static void main(String[] args) throws Exception {

        Pieza[][] tablero = new Pieza[8][8];
        
        tablero[0][0] = new Torre(true,0,0);
        tablero[0][1] = new Caballo(true,0,1);
        tablero[0][2] = new Alfil(true,0,2);
        tablero[0][3] = new Reina(true,0,3);
        tablero[0][4] = new Rey(true,0,4);
        tablero[0][5] = new Alfil(true,0,5);
        tablero[0][6] = new Caballo(true,0,6);
        tablero[0][7] = new Torre(true,0,7);
        
        for (int i = 0; i < 8; i++) {
            tablero[1][i] = new Peon(true,0,i);
            tablero[6][i] = new Peon(false,6,i);
        }
        
        tablero[7][0] = new Torre(false,7,0);
        tablero[7][1] = new Caballo(false,7,1);
        tablero[7][2] = new Alfil(false,7,2);
        tablero[7][3] = new Reina(false,7,3);
        tablero[7][4] = new Rey(false,7,4);
        tablero[7][5] = new Alfil(false,7,5);
        tablero[7][6] = new Caballo(false,7,6);
        tablero[7][7] = new Torre(false,7,7);
        
        Utiles.toString(tablero);
        
        System.out.println(tablero[0][0].getPosicion());
        
    
    
   
        
    }
}
