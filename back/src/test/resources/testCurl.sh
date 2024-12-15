#!/bin/bash

# URL del servidor
URL="http://localhost:8080/juego/inicio"

# Payload
USERNAME='"jugador"'  # The username that you want to test with

# Send the POST request using curl

curl -X POST $URL \
-H "Content-Type: application/json" \
-d $USERNAME \
-c cookies.txt

echo -e "\n"

NUMERO_JUGADOR=5
curl -X POST "http://localhost:8080/juego/adivinar/" \
-H "Content-Type: application/json" \
-d '{"numero": 5}' \
-b cookies.txt

echo -e "\n"

curl -X PUT http://localhost:8080/juego/cancelar \
-H "Content-Type: application/json" \
-d '{"sessionID": 129917921}' \
-b cookies.txt  # Use the stored cookies to persist the session