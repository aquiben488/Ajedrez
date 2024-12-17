package daw;

import Piezas.*;

public class Utiles {

    
    // Devuleve todo el tablero en String, llama a el toString de la pieza 
    // que este en la posicon, si no pinta la casilla vacia(blanca o negra)
    // tambien añade las coordenadas (1-8 y a-h)
     public static String toString(Pieza[][] tablero) {
        StringBuilder sb = new StringBuilder();
        int notLat = 8;

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {

                if (tablero[i][j] != null) {
                    sb.append(tablero[i][j]);
                } else {
                    if (celdaEsBlanca(i, j)) {
                        sb.append("X ");
                    } else {
                        sb.append("O ");
                    }

                }
            }
            sb.append("  " + notLat);
            sb.append("\n");
            notLat--;
        }
        sb.append("\na b c d e f g h");
        return sb.toString();
    }
    
    // las celdas blancas son las pares en las filas pares y las impares en las impares
    private static boolean celdaEsBlanca(int col, int fil) {

        if (col % 2 == 0) {
            return fil % 2 == 0;
        } else {
            return fil % 2 == 1;
        }
    }
    
    
}
