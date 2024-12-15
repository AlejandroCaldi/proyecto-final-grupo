package es.santander.ascender.proyecto20;

import java.util.*;

public class App {

    public static void main(String args[]) {

        final int INTENTOS_MAXIMO = 10;
        final String CANCELACION = "Partida terminada: El número a adivinar era: ";

        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce tu nombre: ");
        
        String usuarioJugador = entrada.nextLine();

        Juego jueguito;

        try {

            jueguito = new Juego(usuarioJugador);

            long contestacion = jueguito.inicializarJuego();
            int intentos = 0;
            long sesion = contestacion;

            int resultado;
            Scanner scanner = new Scanner(System.in);

            while (sesion != 0) {
                try {
                    System.out.println("Introduce tu número entre 0 y 100; si quieres abandonar introduce una letra: ");
                    int numeroaenviar = (int) scanner.nextInt();
                    Map<String, Number> contestacion2;

                    contestacion2 = jueguito.jugarIntento(numeroaenviar, intentos);
                    resultado = contestacion2.get("respuesta").intValue();
                    intentos = contestacion2.get("intentos").intValue();

                    if (resultado == -1) {
                        System.out.println("El número es menor. Intentos hasta ahora " + intentos + ". Quedan " + (INTENTOS_MAXIMO - intentos) + ".");
                    } else if (resultado == 0) {
                        System.out.println("¡ACERTASTEIS  EN " + intentos + "!");
                        break;
                    } else if (resultado == 1) {

                        if (INTENTOS_MAXIMO - intentos != 0) {
                            System.out.println("El número es mayor. Intentos hasta ahora " + intentos + ". Quedan " + (INTENTOS_MAXIMO - intentos) + ".");
                        } else {
                            System.out.println("El número es mayor. último intento!");
                        }

                    }

                } catch (MiExcepcion e) {
                    if (e.getMessage().equals("No quedan mas intentos.")){
                        System.out.println(e.getMessage());
                        break;
                    }
                    continue;

                } catch (InputMismatchException e) {
                    
                    String cancelacion = CANCELACION + jueguito.cancelarPartida(sesion);
                    System.out.println(cancelacion);
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