package es.santander.ascender.proyecto20.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.proyecto20.Juego;

@RestController
@RequestMapping("/juego")
public class JuegoController {

    /**
    * @param usuario
    * @return retorna el nùmero de sesiòn. 
    */
    @PostMapping("/inicio")
    public ResponseEntity<Map<String, Object>> iniciarJuego(@RequestBody String getUsuario) {
        try {
            Juego juego = new Juego(getUsuario); 
            long sesion = juego.inicializarJuego();  
            Map<String, Object> response = new HashMap<>();
            response.put("sessionID", sesion); 

            return ResponseEntity.ok(response);

         } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error during game initialization: " + e.getMessage());
            e.printStackTrace();  // This will print the stack trace to help debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
