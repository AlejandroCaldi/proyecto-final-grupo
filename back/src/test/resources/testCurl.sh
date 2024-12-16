#!/bin/bash

# URL del servidor
URL="http://localhost:8080/juego/"



# Primer request inicializando
curl -X POST http://localhost:8080/juego/inicio \
     -H "Content-Type: application/json" \
     -d '{"getUsuario": "Alejandro"}'
 # Esto guardaìa en una cookie el número de sesión para ser recuperado por los siguientes dos curls. 


echo -e "\n"


# curl intentando adivinar el número. 
curl -X POST http://localhost:8080/juego/cancelar \
     -H "Content-Type: application/json" \
     -d '{"sessionID": 123456789}'  # con - b recupera el valor de la cookie del primer curl. 

echo -e "\n"

# curl cancelando el juego. 
curl -X POST http://localhost:8080/juego/adivinar \
     -H "Content-Type: application/json" \
     -d '{"sessionID": 123456789, "numero": 42}'  # Reemplaza 123456789 con un ID de sesión válido y 42 con el número que deseas adivinar.


echo -e "\n"
