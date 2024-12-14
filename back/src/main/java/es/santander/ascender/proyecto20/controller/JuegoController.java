package es.santander.ascender.proyecto20.controller;

import org.springframework.web.bind.annotation.*;

import es.santander.ascender.proyecto20.Juego;
import es.santander.ascender.proyecto20.MiExcepcion;

@RestController
@RequestMapping("/juego")
public class JuegoController {

    private Juego juego;

    /**
     * @param usuario
     * @return
     */
    @GetMapping("/start")
    public int inicializar(String usuario) {
        int sesion = juego.inicializarJuego();  
        return sesion;
    }

    /**
     * @param number
     * @return
     * @throws MiExcepcion
     */
    @GetMapping("/adivina/{number}")
    public int[] adivinaNumber(@PathVariable int number) throws MiExcepcion {
        int[] result = juego.jugarIntento(number, number);
            return result;
    }

    /**
     * @return
     */
    @GetMapping("/attempts")
    public int intentos() {
        return juego.getNumTarget();
    }
}