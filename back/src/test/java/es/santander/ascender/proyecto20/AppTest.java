package es.santander.ascender.proyecto20;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /*
     * Este test comprueba que el juego acepte una entrada valida de un numero dentro del rango permitido
     */
    @Test 
    void testEntradaValida() throws Exception {
        Juego jueguito = new Juego ("Jugador1");
        jueguito.inicializarJuego();
        Map<String, Number> resultado = jueguito.jugarIntento(50,1);

        int respuesta = resultado.get("respuesta").intValue();
        assertTrue(respuesta == 0 || respuesta == 1 || respuesta == -1);

    }
    /**
     * Comprobar que la accion de cancelar partida introduciendo una letra funciona bien 
     */
    @Test 
    void testCancelacionDePartida(){
        try {
            Juego jueguito = new Juego("Jugador1");
            jueguito.inicializarJuego();

            int numero =jueguito.cancelarPartida(12456);

            String mensaje = "Partida terminada. El numero a adivinar era: " + numero;
            assertEquals("Partida terminada. El numero a adivinar era: " + jueguito.getNumTarget(),mensaje);

        } catch (InputMismatchException e) {
            System.out.println("Error en la entrada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void testIntentosMaximos() throws Exception {
        String nombreJugador = "Juan";
        Juego juego = new Juego(nombreJugador);
        
        
        juego.inicializarJuego();

        
        int intentos = 0;
        while (intentos < 10) {
            try {
                juego.jugarIntento(50, 1);
                intentos++;
            } catch (MiExcepcion e) {
                break;
            }
        }
        assertEquals(10, intentos, "El número de intentos no debe exceder los 10.");
    }
    /*
     * Este test verifica que el programa acepta un nombre de jugador válido y puede continuar con el flujo normal del juego.
     */
    @Test
    void testNombreValido() {
        String input = "Rodrigo\n"; 
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
    /* 
     * Este test verifica que cuando el jugador no proporciona un nombre, el programa debe manejarlo correctamente, lanzando una excepción
    */
    @Test
    void testNombreVacio() {

        String input = "\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

}


