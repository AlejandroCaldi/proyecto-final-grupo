package es.santander.ascender.proyecto20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Juego {

    private final int MINIMO = 0;
    private final int MAXIMO = 100;
    private int numTarget = (int) (Math.random() * 100)  + 1;
    private int intentos;
    private int sesion = 1;
    public String usuario = "";


    private static final String ARCHIVO_DE_REGISTRO = "log.txt";

    public Juego(String usuarioJugador) throws Exception {

        this.usuario = usuarioJugador;
    }

    /**
     * @param usuarioJugador
     * @return
     */
    public int[] inicializarJuego() {

        int retorno[] = new int[2];
        this.intentos = 0;
        retorno[0] = this.intentos;
        this.sesion = 1;
        retorno[1] = this.sesion;
        return retorno;

    }


    /**
     * @param intento el nmumero que arriesga el jugador. 
     * @param sesion la sesión para devolver por api rest, no tiene uso en consola: pasar cualquier valor. 
     * @return un int[] array con el primer valor para saber si acertó, el id
     * @throws Exception si el número está fuera de rango.
     */
     public int[] jugarIntento(String usuario, int intento, int sesion) throws MiExcepcion {

        boolean chequeoNumero = chequearLimite(intento);
        int[] retorno = new int [3];   

        if (chequeoNumero) {

            intentos++;
            
            if (intento == numTarget) {

                String mensaje = "";

                mensaje = usuario + "," + sesion + "," + intento;
                escribirRegistro(mensaje);

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

            throw new MiExcepcion("El número está fuera de rango");
        }

    }

        /**
         * @return String mostrando que terminó el juegoy cuál era el valor a adivinar.
         */
        public String cancelarPartida(String Usuario, int sesion) {
        
            return "Partida terminada: El número a adivinar era: " + numTarget;
            
        }

    /**
     * @param numero el valor pasado por el jugador.
     * @return True si el numero está dentro de limite. False si no. 
     */
    private boolean chequearLimite(int numero) {

        return (numero <= MAXIMO && numero >= MINIMO);
    }

    public static void escribirRegistro(String mensaje) {
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

    public static void leerRegistro(String filter) {
        try (Stream<String> lines = Files.lines(Paths.get(ARCHIVO_DE_REGISTRO))) {
            lines.filter(line -> line.contains(filter))
                 .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public int getNumTarget() {
        return numTarget;
    }
}
