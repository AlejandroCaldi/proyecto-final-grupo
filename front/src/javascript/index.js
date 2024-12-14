$(document).ready(function() {

// https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes

    $("#nombreJugador").val("");
  // BORRAR  $("#divNombre").show();  // Muestra input para que el jugador introduzca el nombre
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
        $("#numero").val("");
        var nombreNuevo = $("#nombreJugador").val();
    //     var adivinarNuevo = 77;   // generar aleatoriamente en cada nueva partida
         if (nombreNuevo === "") { // Ni vacíos ni de tipo distinto a cadena
            $("#respuestaServidor").text("Introduce un nombre si quieres retarme.");
            $("#nombreJugador").focus(); 
            return;
         } else {
            $("#btnNuevaPartida").prop("disabled", true).hide(50);  // Deshabilitar el botón

            var intentoNuevo = $("#intento").val();
            var contaIntentosNuevo = 1;
            $("#divIntento").show(100);
            $("#btnsIntentarYCancelar").show(100);
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

    // Cancelar partida. Boton cancelar. ******************************************************************
    $("#cancelar").on("click", function(){
        // Volver a la situación inicial de la pantalla. Ocultar elementos.
    
        
        $("#divIntento").hide(100);    // ocultar bloque que pregunta el número que quieres probar
        $("#btnsIntentarYCancelar").hide(100); // ocultar botones intentar (adivinar) y cancelar partida

        $("#divNombre").show();  // Muestra input para que el jugador introduzca el nombre
        $("#nombreJugador").focus(); 
      //  $("#btnNuevaPartida").show(); // Mostrar botón nueva partida
        $("#btnNuevaPartida").prop("disabled", false).show(100);  // habilitar el botón nueva partida
        $("#nombreJugador").val(""); // Vaciar input de entrada de nombre del jugador
        $("#respuestaServidor").val("").text("");
    })

    // Validación del campo #numero
    $('#numero').on('input', function() {
        var value = $(this).val();

        // Si el valor es menor que 1 o mayor que 100, lo restauramos a 50 (o cualquier valor predeterminado)
        if (value < 1 || value > 100) {
            $("#servidorRespuestas").text("Por favor, ingresa un número entre 1 y 100.");
            $(this).val(50);  // Restaurar el valor predeterminado, en este caso 50
        } else {
            $("#servidorRespuestas").text(""); // Limpiar el mensaje de error si el valor es válido
        }
    });

    // Intentar (PUT) Jugador confirma pulsando botón intentar tras haber fijado un número
    $('#intentar').on('click', function() {
        var intentoNuevo = $("#numero").val();

        // Solo si el número es válido (entre 1 y 100), se envía el PUT al servidor
        if (intentoNuevo >= 1 && intentoNuevo <= 100) {
            $.ajax({                                                                        // núm ?
                url: "https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes/2",
                method: "PUT",
                data: JSON.stringify({
                    numIntento: intentoNuevo  // Enviar el intento
                }),
                success: function(data) {
                    $("#respuestaServidor").text("Enviado número seleccionado al servidor. PUT");
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);
                }
            });
        } else {
            $("#servidorRespuestas").text("Por favor, indica un número entre 1 y 100.");
        }
    });

});

console.log("Funcionando");