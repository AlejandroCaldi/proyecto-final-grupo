package es.santander.ascender.proyecto20;

import java.util.*;

public class App {

    public static void main(String args[]) {

        final int INTENTOS_MAXIMO = 10;
        int intentos_restantes = 10;

        Scanner entrada = new Scanner(System.in);

        System.out.println("Introduce tu nombre: ");

        String usuarioJugador = entrada.nextLine();

        Juego jueguito;
        try {
            jueguito = new Juego(usuarioJugador);

            int contestacion = jueguito.inicializarJuego();
            int intentos = 0;
            int sesion = contestacion;

            int resultado;
            Scanner scanner = new Scanner(System.in);

            while (sesion != 0) {
                try {
                    System.out.println("Introduce tu nùmero entre 0 y 100; si quieres abandonar una letra: ");
                    int numeroaenviar = (int) scanner.nextInt();
                    int[] contestacion2;

                    contestacion2 = jueguito.jugarIntento(numeroaenviar, intentos);
                    resultado = contestacion2[0];
                    intentos = contestacion2[1];

                    if (resultado == -1) {
                        System.out.println("El número es menor. Intentos hasta ahora " + intentos + ". Quedan " + (INTENTOS_MAXIMO - intentos));
                    } else if (resultado == 0) {
                        System.out.println("¡ACERTASTEIS  EN " + intentos + "!");
                        break;
                    } else if (resultado == 1) {
                        System.out.println("El número es mayor. Intentos hasta ahora " + intentos + ". Quedan " + (INTENTOS_MAXIMO - intentos));
                    }

                } catch (MiExcepcion e) {
                    if (e.getMessage().equals("No quedan mas intentos")){
                        System.out.println(e.getMessage());
                        break;
                    }
                    continue;

                } catch (InputMismatchException e) {

                    System.out.println(jueguito.cancelarPartida(usuarioJugador));
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