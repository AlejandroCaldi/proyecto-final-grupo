package es.santander.ascender.proyecto20;

public class Juego {

    private final int MINIMO = 0;
    private final int MAXIMO = 100;
    private int numTarget = (int) Math.random()*100;

    public Juego(String usuario, int intento) throws Exception {

        boolean chequeoNumero = chequearLimite(intento);

        if(!chequeoNumero) {

            throw new Exception("Número fuera de rango");
        }

    }
    
    /**
     * @param intento
     * @return
     */
    private int evalIntento(int intento) {

        if (intento==numTarget) {
            return 0;
        } 
        if (intento < numTarget) {
            return -1;
        }
        return 1;

    }
 
    private boolean chequearLimite(int numero) {

        if (numero > MAXIMO || numero < MINIMO) {
            return false;
        }
        return true;
    }
}
