package es.santander.ascender.proyecto20.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.proyecto20.Juego;

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
     */
    @PostMapping("/inicio")
    public ResponseEntity<Map<String, Object>> iniciarJuego(@RequestBody String getUsuario, HttpSession session) {
        try {
            // Create a new Juego instance for the user
            Juego juego = new Juego(getUsuario);
            
            // Initialize the game and generate a session ID
            this.sesion = juego.inicializarJuego();
            
            // Store the Juego object in the session for later access
            session.setAttribute("juego", juego);
            session.setAttribute("sessionID", this.sesion);
            
            // Log the stored session ID
            System.out.println("Stored juego object in session with session ID: " + this.sesion);

            // Create a response map with session ID to send to the client
            Map<String, Object> response = new HashMap<>();
            response.put("sessionID", this.sesion);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error during game initialization: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Cancels the game for the player and returns the result.
     * @param session The HTTP session to retrieve the game instance.
     * @return A response containing the result of cancelling the game.
     */
    @PutMapping("/cancelar")
    public ResponseEntity<String> cancelarJuego(@RequestBody Map<String, Long> request, HttpSession session) {
        try {
            // Retrieve the session ID from the request
            long sessionID = request.get("sessionID");
            
            // Retrieve the Juego object from the session using the session ID
            Juego juego = (Juego) session.getAttribute("juego");

            if (juego == null) {
                // If no game is found in the session, return a bad request response
                System.out.println("Game not initialized in session.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game not initialized");
            }

            // Debugging: Log the session ID and game status
            System.out.println("Cancelling game with session ID: " + sessionID);

            // Call the cancel method and return the result
            String respuesta = juego.cancelarPartida(sessionID);
            
            // Return the response with cancellation details
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error during game cancellation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing cancellation");
        }
    }

    // /**
    //  * @param number
    //  * @return
    //  * @throws MiExcepcion
    //  */
    // @GetMapping("/adivina/{number}/{sesion}")
    // public int[] adivinaNumber(@PathVariable int number) throws MiExcepcion {

    //     int[] result = juego.jugarIntento(number, number);
    //     return result;
    // }

    // /**
    //  * @return
    //  */
    // @GetMapping("/attempts")
    // public int intentos() {
    //     return juego.getNumTarget();
//    }
}