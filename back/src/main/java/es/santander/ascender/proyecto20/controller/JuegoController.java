package es.santander.ascender.proyecto20.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.proyecto20.Guess;
import es.santander.ascender.proyecto20.Juego;
import es.santander.ascender.proyecto20.MiExcepcion;
import es.santander.ascender.proyecto20.Partida;
import es.santander.ascender.proyecto20.Usuario;

@CrossOrigin(origins = "http://localhost:1234")
@RestController
@RequestMapping("/juego")
public class JuegoController {

    // para almacenar el id para la sesiòn de consola. 
    
    private Map<Long, Juego> jugadores = new HashMap<>();

    /**
    *Empieza el juego inicializando el objeto juego, preservando el httoSession como id de sesión. .
    * @param getUsuario El nombre de jugador que ha pasado el cliente. 
    * @param session La sesión de Http que servirá como identificador. 
    * @return id de Sesión. 
    * @throws Exception 
    */
    @PostMapping("/inicio")
    public ResponseEntity<Map<String, Object>> iniciarJuego(@RequestBody Usuario getUsuario) throws Exception {
        
        String usuario = getUsuario.getGetUsuario();
        Juego juego = new Juego(usuario);
        long sesion = (long) (Math.random() * 999999999) + 1;
        jugadores.put(sesion, juego);
        juego.inicializarJuego();


        Map<String, Object> response = new HashMap<>();
        response.put("sessionID", sesion);

        return ResponseEntity.ok(response);
}

    /**
     * Cancela el juego y envía el número que se debia haber adivinado. 
     * 
     * @param session @return A response containing the result of cancelling the game.
     */
    @PostMapping("/cancelar")
    public ResponseEntity<Map<String, Number>> cancelarJuego(@RequestBody Partida partida) {

        long session = partida.getSessionID();
        Juego juego = (Juego) jugadores.get(session);

        int numeroEra = juego.cancelarPartida(session);
        Map<String, Number> respuesta = new HashMap<>();
        jugadores.remove(session, juego);

        respuesta.put("Numero", numeroEra);
        return ResponseEntity.ok(respuesta);

    }

    
    /**
     * @param numero el numero arriesgado por el jugador, es un entero entre 0 y 100. 
     * @param request datos se sesión de HTTP
     * @param session el identificador único de sesión HTTP, usado como Id de instancia de juego cuando es web. 
     * @return ResponseEntity<Map<String, Number>> con los valores enteros de respuesta e intento. En el caso de respuesta
     *         se trta de -1 si el dato es menor, 0 si acertó, o 1 s el número es mayor. 
     * @throws MiExcepcion. Para el caso de que se pase un texto o un valor menor a 0 o mayor a 100. 
     */
    @PostMapping("/adivinar")
    public ResponseEntity<Map<String, Number>> adivinaNumero(@RequestBody Guess guess) throws MiExcepcion {

        long sesion = guess.getSessionID();

        int numero = guess.getNumero();
        Juego juego = (Juego) jugadores.get(sesion);

        Map<String, Number> respuesta = juego.jugarIntento(numero, sesion);

        return ResponseEntity.ok(respuesta);

    }

}
