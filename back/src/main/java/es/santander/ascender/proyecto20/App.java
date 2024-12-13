package es.santander.ascender.proyecto20;

import java.util.*;

public class App {

    public static void main(String args[]) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("Introduce tu nombre: ");

        String usuarioJugador = entrada.nextLine();

        Juego jueguito;
        try {
            jueguito = new Juego(usuarioJugador);

            int[] contestacion = jueguito.inicializarJuego();
            int intentos = contestacion[0];
            int sesion = contestacion[1];

            int resultado;

            while (sesion != 0) {
                Scanner scanner = new Scanner(System.in);
                int numeroaenviar = (int) scanner.nextInt();
                int[] contestacion2;
                try {
                    contestacion2 = jueguito.jugarIntento(usuarioJugador, numeroaenviar, sesion);
                    resultado = contestacion2[0];
                    intentos = contestacion2[1];
                    sesion = contestacion2[2];

                    if (resultado == -1) {
                        System.out.println("El número es menor. Intentos hasta ahora " + intentos);
                    } else if (resultado == 0) {
                        System.out.println("¡ACERT  EN " + intentos + "!");
                        break;
                    } else if (resultado == 1) {
                        System.out.println("El número es mayor. Intentos hasta ahora " + intentos);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                entrada.close();
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}