package es.santander.ascender.proyecto20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JuegoTest {

    private Juego jueguito;

    @BeforeEach
    void setUp() throws Exception {
        jueguito = new Juego("Alejandro"); 
        jueguito.inicializarJuego(); 
    }
   

    /**
     * Testeo de respuesta del valor a adivinar. 
     */
    @Test
    void testCancelarPartida() {
        int sesion = 4;
        int numero = jueguito.cancelarPartida(sesion);

        assertEquals(numero, jueguito.getNumTarget());
    }


    /**
     * Testea la inicialicaciòn de juego. 
     */
    @Test
    void testInicializarJuego() {
        long sesion = jueguito.inicializarJuego();
        assertEquals(1, sesion);
    }

    
    /**
     * @throws Exception n caso de que el númeroa  adivinar sea negativo o mayor a 100, no relevante al test. 
     */
    @Test 
    void testJugarIntento() throws Exception {
        int numeroAdivinar = jueguito.getNumTarget();
        System.out.println(numeroAdivinar);

        Map<String, Number> respuesta = jueguito.jugarIntento(numeroAdivinar, 1);

        assertTrue(respuesta.get("respuesta").intValue()==0);
        assertTrue(respuesta.get("respuesta").intValue()==1);
    }

    /**
     * Test para comprobar que el numero objetivo esta en el rango de 0 a 100
     */
    @Test 
    void testNumeroObjetivoRango(){

        int Numobjetivo = jueguito.getNumTarget();
        assertTrue(Numobjetivo >= 1 && Numobjetivo <=100, "el numero objetivo debe estar entre 0 y 100");
    }


    /**
     * Test para comprobar que si el jugador sobrepasa el numero de intentos le lanze una excepcion
     */
    @Test 
    void testIntentosMaximos(){
        try{
            for (int i = 0; i < 10; i++) {

                jueguito.jugarIntento(50, 1);

            }
        } catch(MiExcepcion e) {
            assertEquals("No quedan mas intentos", e.getMessage());
        }
    }
}
