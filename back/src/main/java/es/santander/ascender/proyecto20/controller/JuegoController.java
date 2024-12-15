package es.santander.ascender.proyecto20.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.proyecto20.Juego;
import es.santander.ascender.proyecto20.MiExcepcion;

@RestController
@RequestMapping("/juego")
public class JuegoController {

    // para almacenar el id para la sesiòn de consola. 
    private long sesion = 0;

    /**
    *Empieza el juego inicializando el objeto juego, preservando el httoSession como id de sesión. .
    * @param getUsuario El nombre de jugador que ha pasado el cliente. 
    * @param session La sesión de Http que servirá como identificador. 
    * @return id de Sesión. 
    * @throws Exception 
    */
    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = "/inicio", method = RequestMethod.POST)
    @PostMapping("/inicio")
    public ResponseEntity<Map<String, Object>> iniciarJuego(@RequestBody String getUsuario, HttpSession session) throws Exception {
    
    Juego juego = new Juego(getUsuario);
    
    this.sesion = juego.inicializarJuego();
    
    session.setAttribute("juego", juego);
    session.setAttribute("sessionID", this.sesion);

    Map<String, Object> response = new HashMap<>();
    response.put("sessionID", this.sesion);

    return ResponseEntity.ok(response);
}

    /**
     * Cancela el juego y envía el número que se debia haber adivinado. 
     * 
     * @param session The HTTP session to retrieve the game instance.
     * @return A response containing the result of cancelling the game.
     */
    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = "/cancelar", method = RequestMethod.PUT)
    @PutMapping("/cancelar")
    public ResponseEntity<Map<String, Number>> cancelarJuego(@RequestBody Map<String, Long> payload, HttpSession session) {

        Long sessionID = payload.get("sessionID");
        Juego juego = (Juego) session.getAttribute("juego");

        int numeroEra = juego.cancelarPartida(sessionID);
        Map<String, Number> respuesta = new HashMap<>();

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
    @CrossOrigin(origins = "http://localhost:1234")
    @PostMapping("/adivinar")
    public ResponseEntity<Map<String, Number>> adivinaNumero(@RequestBody Map<String, Integer> payload, HttpSession session) throws MiExcepcion {

        int numero = payload.get("numero");
        long sessionID = (long) session.getAttribute("sessionID");
        Juego juego = (Juego) session.getAttribute("juego");

        Map<String, Number> respuesta = juego.jugarIntento(numero, sessionID);

        return ResponseEntity.ok(respuesta);

    }

}
