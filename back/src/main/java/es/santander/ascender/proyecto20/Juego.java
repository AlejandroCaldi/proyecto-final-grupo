package es.santander.ascender.proyecto20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Juego {

    private final int MINIMO = 0;
    private final int MAXIMO = 100;
    private int numTarget = (int) Math.random() * 100;
    private int intentos;
    private int sesion = 1;
    private String usuario = "";

    private static final String ARCHIVO_DE_REGISTRO = "log.txt";

    public Juego(String usuarioJugador) throws Exception {

    }

    /**
     * @param usuarioJugador
     * @return
     */
    public int[] inicializarJuego(String usuarioJugador) {

        int retorno[] = new int[2];
        this.intentos = 0;
        retorno[0] = this.intentos;
        this.sesion = 1;
        retorno[1] = this.sesion;
        this.usuario = usuarioJugador;
        return retorno;

    }


    /**
     * @param intento el nmumero que arriesga el jugador. 
     * @param sesion la sesión para devolver por api rest, no tiene uso en consola: pasar cualquier valor. 
     * @return un int[] array con el primer valor para saber si acertó, el id
     * @throws Exception si el número está fuera de rango.
     */
     public int[] jugarIntento(String usuario, int intento, int sesion) throws Exception {

        boolean chequeoNumero = chequearLimite(intento);
        int[] retorno = new int [3];   

        if (!chequeoNumero) {

            intentos++;
            
            if (intento == numTarget) {

                String mensaje = "";

                mensaje = usuario + "," + sesion + "," + intento;
                erscribirRegistro(mensaje);

                retorno[0]= 0;
                retorno[1]= intentos;
                retorno[2]= sesion; 
                return retorno;
            }
            if (intento > numTarget) {
                retorno[0]= -1;
                retorno[1]= intentos;
                retorno[2]= sesion; 
                return retorno;
            } 
            retorno[0]= 1;
            retorno[1]= intentos; 
            retorno[2]= sesion;
            return retorno;
        } else  {

            throw new Exception("El número está fuera de rango");
        }

    }

        public String cancelarPartida() {
        
            return "Partida terminada: El númeroa a adivinar era: " + numTarget;
            
        }

    /**
     * @param numero el valor pasado por el jugador.
     * @return True si el numero está dentro de limite. False si no. 
     */
    private boolean chequearLimite(int numero) {

        return (numero <= MAXIMO && numero >= MINIMO);
    }

    public static void erscribirRegistro(String mensaje) {
        try {
            Files.write(pathArchivo(), (mensaje + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path pathArchivo() {
        return Paths.get(ARCHIVO_DE_REGISTRO);
    }
}
