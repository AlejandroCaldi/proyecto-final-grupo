package es.santander.ascender.proyecto20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JuegoTest {

    private Juego jueguito;

    @BeforeEach
    void setUp() throws Exception {
        jueguito = new Juego("Alejandro"); 
        jueguito.inicializarJuego(); 
    }
   
    @Test
    void testCancelarPartida() {
        int sesion = 4;
        String respuesta = jueguito.cancelarPartida(sesion);

        assertEquals(respuesta,"Partida terminada: El número a adivinar era: " + jueguito.getNumTarget());

    }

    // @Test
    // void testEscribirRegistro() {

    // String mensaje= "Alejandro,15,6";
    //     jueguito.escribirRegistro(mensaje);

    // }

    @Test
    void testInicializarJuego() {
        long sesion = jueguito.inicializarJuego();
        assertEquals(1, sesion);
    }

    @Test
    void testJugarIntento() throws Exception {
        int numeroAdivinar = jueguito.getNumTarget();
        System.out.println(numeroAdivinar);

        int[] respuesta = jueguito.jugarIntento(numeroAdivinar, 1);

        assertTrue(respuesta[0]==0);
        assertTrue(respuesta[1]==1);


    }

    @Test //test para comprobar que el numero objetivo esta en el rango de 0 a 100
    void testNumeroObjetivoRango(){

        int Numobjetivo = jueguito.getNumTarget();
        assertTrue(Numobjetivo >= 1 && Numobjetivo <=100, "el numero objetivo debe estar entre 0 y 100");

    }
    @Test //test para comprobar que si el jugador sobrepasa el numero de intentos le lanze una excepcion
    void testIntentosMaximos(){
        try{
            for (int i = 0; i < 10; i++) {

                jueguito.jugarIntento(50, 1);

            }
        } catch(MiExcepcion e) {
            assertEquals("No quedan mas intentos", e.getMessage());
        }
    }

    // @Test
    // void testLeerRegistro() {

    // }
}
