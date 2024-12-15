#!/bin/bash

URL="http://localhost:8080/juego/inicio"

# Payload for starting the session with a username
USERNAME='"player1"'  # The username that you want to test with

# Send the POST request using curl to start the session
curl -X POST $URL -H "Content-Type: application/json" -d $USERNAME -c cookies.txt

# Send the GET request for "adivinaNumero" with a number to guess
# Replace <GUESS_NUMBER> with the number you want to test
GUESS_NUMBER=5  # Example number to guess
curl -X GET "http://localhost:8080/juego/adivinar/?numero=$GUESS_NUMBER" \
-H "Content-Type: application/json" \
-b cookies.txt  # Use the stored cookies to persist the session

# Send the PUT request to cancel the game (with session ID)
curl -X PUT http://localhost:8080/juego/cancelar \
-H "Content-Type: application/json" \
-d '{"sessionID": 129917921}' \
-b cookies.txt  # Use the stored cookies to persist the session