package es.santander.ascender.proyecto20.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.proyecto20.Juego;
import es.santander.ascender.proyecto20.MiExcepcion;

@RestController
@RequestMapping("/juego")
public class JuegoController {

    // To store session ID for isolation within the controller
    private long sesion = 0;

    /**
     * Starts a new game and stores it in the HTTP session.
     * @param getUsuario The player name sent by the client.
     * @param session The HTTP session for managing session attributes.
     * @return A response containing the session ID.
          * @throws Exception 
          */
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
     * Cancels the game for the player and returns the result.
     * 
     * @param session The HTTP session to retrieve the game instance.
     * @return A response containing the result of cancelling the game.
     */
    @PutMapping("/cancelar")
    public ResponseEntity<Integer> cancelarJuego(@RequestBody Map<String, Long> request, HttpSession session) {

        long sessionID = request.get("sessionID");
        Juego juego = (Juego) session.getAttribute("juego");

        int respuesta = juego.cancelarPartida(sessionID);

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
    @PostMapping("/adivinar/")
    public ResponseEntity<Map<String, Number>> adivinaNumero(@RequestBody int numero, Map<String, Long> request, HttpSession session) throws MiExcepcion {

    long sessionID = request.get("sessionID");
    Juego juego = (Juego) session.getAttribute("juego");

    Map<String, Number> respuesta = juego.jugarIntento(numero, sessionID);

    return ResponseEntity.ok(respuesta);

    }

}
