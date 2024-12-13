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
        String user = jueguito.getUsuario();
        String respuesta = jueguito.cancelarPartida(user);

        assertEquals(respuesta,"Partida terminada: El número a adivinar era: " + jueguito.getNumTarget());

    }

    // @Test
    // void testEscribirRegistro() {

    // String mensaje= "Alejandro,15,6";
    //     jueguito.escribirRegistro(mensaje);

    // }

    @Test
    void testInicializarJuego() {
        int sesion = jueguito.inicializarJuego();
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

    // @Test
    // void testLeerRegistro() {

    // }
}
