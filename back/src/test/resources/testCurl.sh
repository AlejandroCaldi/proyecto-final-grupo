#!/bin/bash

# URL del servidor
URL="http://localhost:8080/juego/inicio"

# Payload
USERNAME='"player1"'  # The username that you want to test with

# Send the POST request using curl
curl -X POST $URL -H "Content-Type: application/json" -d $USERNAME

