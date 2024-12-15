package es.santander.ascender.proyecto20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
    @Test //comprueba que el juego acepte una entrada valida de un numero dentro del rango permitido
    void testEntradaValida() throws Exception {
        Juego jueguito = new Juego ("Jugador1");
        jueguito.inicializarJuego();
        Map<String, Number> resultado = jueguito.jugarIntento(50,1);

        int respuesta = resultado.get("respuesta").intValue();
        assertTrue(respuesta == 0 || respuesta == 1 || respuesta == -1);
    }
    /* 
    @Test
    void testIntentosMaximos() {
        
    }
    */
    @Test //comprobar que la accion de cancelar partida introduciendo una letra funciona bien 
    void testCancelacionDePartida(){
        try {
            Juego jueguito = new Juego("Jugador1");
            jueguito.inicializarJuego();

            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();

            int numero =jueguito.cancelarPartida(13456);

            String mensaje = "Partida terminada. El numero a adivinar era: " + numero;
            assertEquals("Partida terminada. El numero a adivinar era: " + jueguito.getNumTarget(),mensaje);

        } catch (InputMismatchException e) {
            System.out.println("Error en la entrada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
