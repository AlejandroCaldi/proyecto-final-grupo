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
            Scanner scanner = new Scanner(System.in);

            while (sesion != 0) {
                try {
                    System.out.println("Introduce tu nùmero entre 0 y 100; si quieres abandonar una letra: ");
                    int numeroaenviar = (int) scanner.nextInt();
                    int[] contestacion2;

                    contestacion2 = jueguito.jugarIntento(usuarioJugador, numeroaenviar, sesion);
                    resultado = contestacion2[0];
                    intentos = contestacion2[1];
                    sesion = contestacion2[2];

                    if (resultado == -1) {
                        System.out.println("El número es menor. Intentos hasta ahora " + intentos);
                    } else if (resultado == 0) {
                        System.out.println("¡ACERTASTEIS  EN " + intentos + "!");
                        break;
                    } else if (resultado == 1) {
                        System.out.println("El número es mayor. Intentos hasta ahora " + intentos);
                    }

                } catch (MiExcepcion e) {
                    continue;
                } catch (InputMismatchException e) {

                    System.out.println(jueguito.cancelarPartida(usuarioJugador, sesion));
                    break;
                }

            }

            entrada.close();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}