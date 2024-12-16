package es.santander.ascender.proyecto20.controller;

import java.util.HashMap;
import java.util.Map;

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
     * @param partida el numero de sesion
     * @return el valor qeu habìa que adivinar. 
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
     * @param guess el valor arriesgado por el usuario
     * @return retora si acertó y la cantiadd de intentos que lleva la sesiòn
     * @throws MiExcepcion en cas de que el numero estè fuera de rango. 
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
