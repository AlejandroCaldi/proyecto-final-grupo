$(document).ready(function() {

// https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes

    $("#nombreJugador").val("");
    $("#divNombre").show();  // Muestra input para que el jugador introduzca el nombre
    $("#nombreJugador").focus(); 
    $("#btnNuevaPartida").show();

    // listar registros
    function listar() {
        $.get("https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes", function(data) {
            $("#registros").empty();  // Limpiar el contenido antes de agregar nuevos registros
            console.log(data);
    
            data.forEach(function(solicitud) {
                $("#registros").append(
                    $("<li>")
                        .text(solicitud.nombre + ' ' + solicitud.intento + ' ' + 
                            solicitud.contador + ' ' + solicitud.adivinar)
                        .val(solicitud)  // Establecer el valor del elemento (esto puede ser opcional)
                        .attr("id", "id" + solicitud.id)  // Asignar un ID único al elemento
                );
            });
        
        }).fail(function() {
            alert("Error al cargar las solicitudes.");
        });
    } // fin bloque listar registros

    // Crear nueva partida ************************************************************************
    $("#btnNuevaPartida").on("click", function() {

    var nombreNuevo = $("#nombreJugador").val();
    //     var adivinarNuevo = 77;   // generar aleatoriamente en cada nueva partida
         if (nombreNuevo === "") { // Ni vacíos ni de tipo distinto a cadena
            $("#respuestaServidor").text("Introduce un nombre si quieres retarme.");
            $("#nombreJugador").focus(); 
            return;
         } else {
            $("#btnNuevaPartida").prop("disabled", true);  // Deshabilitar el botón
            var intentoNuevo = $("#intento").val();
            var contaIntentosNuevo = 1;
            $("#divIntento").show();
            $("#btnsIntentarYCancelar").show();
         }
        $.ajax({
            url: "https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes",
            method: "POST",
            "data": JSON.stringify({
                id: 0,     // Cero indica creación 
                nombreJugador: nombreNuevo,
                numIntento: intentoNuevo,  // pte. añadir a un array de intentos
                contaIntentos: contaIntentosNuevo,
            //    adivinar: adivinaNum;
            }),
            success: function(data) {
                $("#respuestaServidor").text("Partida creada. POST."); 
             //   listar();
                console.log(data);
            },
            error: function(data) {
                console.log(data);
            }
        }); 
    }) // fin bloque nueva partida

    $("#cancelar").on("click", function(){
        // Volver a la situación inicial de la pantalla. Ocultar elementos.
    
        
        $("#divIntento").hide();    // ocultar bloque que pregunta el número que quieres probar
        $("#btnsIntentarYCancelar").hide(100); // ocultar botones intentar (adivinar) y cancelar partida

        $("#divNombre").show();  // Muestra input para que el jugador introduzca el nombre
        $("#nombreJugador").focus(); 
      //  $("#btnNuevaPartida").show(); // Mostrar botón nueva partida
        $("#btnNuevaPartida").prop("disabled", false);  // habilitar el botón nueva partida
        $("#nombreJugador").val(""); // Vaciar input de entrada de nombre del jugador
        $("#respuestaServidor").val("").text("");


    })
});

console.log("jquery funcionando");