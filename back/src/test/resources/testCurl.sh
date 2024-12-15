#!/bin/bash

# URL del servidor
URL="http://localhost:8080/juego/"

# varaibles para testeo 
USERNAME='"jugador"' # usuario en el primer payload. 

GUESS_NUMBER=50 # numero pasado para adivinar por parte del jugador. 
 

# Primer request inicializando
curl -X POST $URL"inicio/" \
-H "Content-Type: application/json" \
-d $USERNAME \
-c cookies.txt  # Esto guardaìa en una cookie el número de sesión para ser recuperado por los siguientes dos curls. 


echo -e "\n"


# curl intentando adivinar el número. 
curl -X POST $URL"adivinar/" \
-H "Content-Type: application/json" \
-d "$GUESS_NUMBER" \
-b cookies.txt  # con - b recupera el valor de la cookie del primer curl. 

echo -e "\n"

# curl cancelando el juego. 
curl -X PUT $URL"cancelar/" \
-H "Content-Type: application/json" \
-d '{"sessionID": 129917921}' \
-b cookies.txt

echo -e "\n"
