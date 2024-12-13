package es.santander.ascender.proyecto20;

import java.util.*;

public class App {
   
    public static void main(String args[]) {
    
        Scanner entrada = new Scanner (System.in);
    
        System.out.println("Introduce tu nombre: ");
            
        String usuarioJugador = entrada.nextLine();

        Juego jueguito = new Juego(usuarioJugador);
    
        int[] contestacion = jueguito.inicializarJuego();
        int intentos =  contestacion[0] , sesion = contestacion [1] ,resultado = ;
        
        Scanner scanner = new Scanner (System.in);
        while (sesion != 0) {
            int[] contestacion2[] jueguito.jugarIntento(usuarioJugador, intentos , sesion);

                resultado = contestacion2[0] ;
                intentos = contestacion2[1] ;
                sesion = contestacion2[2];

            System.out.println("Resultado: " + contestacion2[0] + ", Intento: " + contestacion2[1] + ", Sesión: " + contestacion2[2]);  
        }
        if (resultado == -1) {
            System.out.println("El número es menor");
        } else if (resultado == 0) {
            System.out.println("¡ACERTÓ!");
        } else if (resultado == 1) {
            System.out.println("El número es mayor");
        }
    }
}