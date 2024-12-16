$(document).ready(function() {

    var sesion = 0; // Variable para guardar la sesión actual
    
    const URL_SERVIDOR = "http://localhost:8080/juego/";

    $("#nombreJugador").val("");
    $("#nombreJugador").focus(); 
    $("#btnNuevaPartida").show();

    // listar registros. No implementado del lado del servidor (todavía) 
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

    // Crear nueva partida 
    $("#btnNuevaPartida").on("click", function() {
        $("#numero").val("");
        var nombreNuevo = $("#nombreJugador").val();
        if (nombreNuevo === "") { // Ni vacíos ni de tipo distinto a cadena
            $("#respuestaServidor").text("Introduce un nombre si quieres retarme.");
            $("#nombreJugador").focus(); 
            return;
         } else {
            $("#btnNuevaPartida").prop("disabled", true).hide(50);  

            var intentoNuevo = $("#intento").val();
            var contaIntentosNuevo = 1;
            $("#nombreJugador").prop("disabled", true).hide();
            $("#nombreJugador-container").hide();

            $("#divIntento").show(100);
            $("#btnsIntentarYCancelar").show(100);
        }
        $.ajax({
            url: URL_SERVIDOR + "inicio",
            method: "POST",
            data: JSON.stringify({"getUsuario": nombreNuevo }),
            
            success: function(data) {
                $("#respuestaServidor").text("Partida creada. POST. usuario:" + data.sessionID); 
             // listar();   // No implementado del lado del servidor aún.
                console.log(data);
            },
            error: function(data) {
                console.log(data);
            }
        }); 
    }) // fin bloque nueva partida

    // Cancelar partida. Boton cancelar.
    $("#cancelar").on("click", function(){

        $.ajax({                                                                                   
            url: URL_SERVIDOR+"cancelar",
            type: 'POST',
            success: function(data) {
                $("#respuestaServidor").text("El número a adivinar era " + data.Numero);
                console.log(data);
            },
            error: function(data) {
                console.log("error en la cancelación");
                console.log(data);
            }
        });
    
        
        $("#divIntento").hide(100);    // ocultar bloque que pregunta el número que quieres probar
        $("#btnsIntentarYCancelar").hide(100); // ocultar botones intentar (adivinar) y cancelar partida

        $("#divNombre").show();  // Muestra input para que el jugador introduzca el nombre
        $("#nombreJugador").prop("disabled", false).show(); // el show está justo debajo...
        $("#nombreJugador-container").show();
        $("#nombreJugador").focus(); 

        $("#btnNuevaPartida").prop("disabled", false).show(100);  // habilitar el botón nueva partida
        $("#nombreJugador").val(""); // Vaciar input de entrada de nombre del jugador
        $("#respuestaServidor").val("").text("");
    })

    $('#numero').on('input', function() {
        var value = $(this).val();
        
        if (value < 0 || value > 100) {
            $("#servidorRespuestas").text("Por favor, ingresa un número entre 1 y 100.");
        //    $(this).val(50);  // Restaurar el valor predeterminado, en este caso 50
        } else {
            $("#servidorRespuestas").text(""); // Limpiar el mensaje de error si el valor es válido
        }
    });

    // Intentar. Jugador confirma pulsando botón intentar tras haber fijado un número
    $('#intentar').on('click', function() {
        var intentoNuevo = $("#numero").val();

        // Solo si el número es válido (entre 1 y 100)
        console.log(intentoNuevo);
        if (intentoNuevo >= 1 && intentoNuevo <= 100) {

            intentoPayload = {numero: intentoNuevo};
            $.ajax({                                                                 
                url: URL_SERVIDOR+"adivinar/",
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(intentoPayload),

                success: function(data) {
                    $("#respuestaServidor").text("Enviado número seleccionado al servidor. PUT");
                    let mensaje = "";
                    
                    if (data.estado == "-1") {
                        mensaje = "El número es mayor.";
                    } else if (data.estado == "1") {
                        mensaje = "El número es menor.";
                    } else {
                        mensaje = "¡Acertaste!.";
                        data.estado = "0"; 
                    }
                    $("#respuestaServidor").text(mensaje);
                    console.log(data);
                },
                success: function(data) {
                    var mensaje = data.resultado.respuesta === 0 ? "¡Acertaste!" :
                                  (data.resultado.respuesta === 1 ? "El número es mayor." : "El número es menor.");
                    $("#respuestaServidor").text(mensaje);
                },
                error: function(data) {
                    console.log(data);
                }
            });
        } else {
            $("#servidorRespuestas").text("Por favor, indica un número entre 0 y 100.");
        }
    });
});
