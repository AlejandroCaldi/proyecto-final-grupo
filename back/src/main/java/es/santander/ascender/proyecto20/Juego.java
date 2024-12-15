package es.santander.ascender.proyecto20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Juego {

    private final int MINIMO = 0;
    private final int MAXIMO = 100;
    private final int INTENTOS_MAXIMO = 10;
    private int numTarget = (int) (Math.random() * 100) + 1;
    private int intentos;
    private long sesion;
    public String usuario = "";

    private static final String ARCHIVO_DE_REGISTRO = System.getProperty("user.dir") + "/log.txt";

    public Juego(String usuarioJugador) throws Exception {

        this.usuario = usuarioJugador;
    }

    /**
     * @param usuarioJugador
     * @return
     */
    public long inicializarJuego() {

        this.intentos = 0;
        this.sesion = (long) (Math.random() * 999999999) + 1;

        System.out.println("Número Sesión: " + this.sesion);
        return this.sesion;

    }

    /**
     * @param intento el nmumero que arriesga el jugador.
     * @param sesion  la sesión para devolver por api rest, no tiene uso en consola:
     *                pasar cualquier valor.
     * @return un int[] array con el primer valor para saber si acertó, el id
     * @throws Exception si el número está fuera de rango.
     */
    public Map<String, Number> jugarIntento(int numeroCliente, long sesion) throws MiExcepcion {

        boolean chequeoNumero = chequearLimite(numeroCliente);     

        if (chequeoNumero) {

            if (this.intentos < INTENTOS_MAXIMO) {

                this. intentos++;

                if (numeroCliente == numTarget) {

                    return armarMapyLog(usuario, sesion, numeroCliente, numTarget, 0, intentos);
                    
                }

                if (numeroCliente > numTarget) {

                    return armarMapyLog(usuario, sesion, numeroCliente, numTarget, -1, intentos);

                }

                return armarMapyLog(usuario, sesion, numeroCliente, numTarget, 1, intentos);

            } else {

                throw new MiExcepcion("No quedan mas intentos.");
            }

        } else {

            throw new MiExcepcion("El número está fuera de rango");
        }

    }

    /**
     * @return String mostrando que terminó el juegoy cuál era el valor a adivinar.
     */
    public int cancelarPartida(long sesion) {

            return numTarget;

    }

    /**
     * La funciòn graba en archivo la actividad y devuelve un map con los datos de si acertò en màs o en menos, y la cantidad de intentos. 
     * @param usuario el usuario pasado para grabar en log
     * @param sesion el id de sesion, para guardar en archivo. 
     * @param numeroCliente el número int de 0 a 100 que arriesgó el jugador. 
     * @param numTarget el número que debe adivinar
     * @param acerto el valor de acierto a devolver al cliente, -1 es en menos, 0 acertó y 1 en largo. 
     * @param intentos la cantidad de intentos efectuada por el jugador para la sesión 
     * @return un hash para responder los datos de acierto o no, y la cantidad de intentos incurridos. 
     */
    private Map<String, Number> armarMapyLog(String usuario, long sesion, long numeroCliente, int numTarget, int acerto, int intentos) {

        String mensaje = usuario + "," + sesion + "," + numeroCliente + "," + numTarget;
        Map<String, Number> retorno = new HashMap<>();
        
        escribirRegistro(mensaje);

        retorno.put("respuesta",acerto);
        retorno.put("intentos",intentos);

        return retorno;
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
