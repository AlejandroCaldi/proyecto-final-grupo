# proyecto-final-grupo
Trabajo en Grupo de cierre 1er parte Ascender.


en la carpeta front

Para instalar:

```sh
npm install 
npm install jquery
npm install parcel
npm install bootstrap@3.4.1
```


para correr:

```sh
npm server run
```


En el back:

instalar un nuevo proyecto en maven en otra carpeta fuera del proyecto y pegarlo todo dentro de la carpeta back


servidor falso json:

https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes


Marco genral par el lado de Back:


```sh
Juego jueguito = new Juego();

int[] contestacion = jueguito.inicializarJuego(usuarioJugador);
```

[intentos, sesion];

contestacion[0] esto es el numero de intentos de esa seion 
contestacion[1] esto es el numero de sesion

```sh
int[] contestacion2[] jueguito.jugarIntento(usuario, intento, sesion);
````

[resultado] -1 es que el numero es menor 
             0 ACERTÓ
             1 es que el numero es mayor
contestacion2[0] = resultado
contestacion2[1] = intento
contestacion2[2] = sesion;

´´´´sh
String cartelito jueguito.cancelarPartida();

System.out.println(cartelito);
````
quieres salir del juego ? (SCANNNER)