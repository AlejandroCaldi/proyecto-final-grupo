$(document).ready(function() {

    let sesion = 0; // Variable para guardar la sesión actual
    
    const URL_SERVIDOR = "http://localhost:8080/juego/";
    const INTENTOS_MAXIMOS = 10;
    let sessionID = 0;

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
        let nombreNuevo = $("#nombreJugador").val();
         if (nombreNuevo === "") { // Ni vacíos ni de tipo distinto a cadena
            $("#respuestaServidor").text("Introduce un nombre si quieres retarme.").addClass("aviso");
            $("#nombreJugador").focus(); 
            return;
         } else {
            $("#btnNuevaPartida").prop("disabled", true).hide(50);  

            let intentoNuevo = $("#intento").val();
            let contaIntentosNuevo = 1;
            $("#nombreJugador").prop("disabled", true).hide();
            $("#nombreJugador-container").hide();

            $("#divIntento").show(100);
            $("#btnsIntentarYCancelar").show(100);

                 // Foco en el campo número después de mostrar los controles del juego
            setTimeout(function() {
               $("#numero").focus();
             }, 200);  // Retraso de 200ms para asegurarse de que los elementos estén visibles
             $("#respuestaServidor").text("").removeClass("aviso");
         }
        $.ajax({
            url: URL_SERVIDOR + "inicio",
            method: "POST",
            data: JSON.stringify({"getUsuario": nombreNuevo }),
            contentType: 'application/json',
            success: function(data) {
                $("#respuestaServidor").text("Partida creada. Sesión: " + data.sessionID);
                sessionID = data.sessionID; 
             // listar();   // No implementado del lado del servidor aún.
                console.log(data);
            },
            error: function(data) {
                console.log(data);
            }
        }); 
    }) // fin bloque nueva partida

    // Cancelar partida. Botón cancelar.
    $("#cancelar").on("click", function(){

        $.ajax({                                                                                   
            url: URL_SERVIDOR+"cancelar",
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({"sessionID": sessionID}),
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


        $("#nombreJugador").prop("disabled", false).show().focus().val(""); // el show está justo debajo...
        $("#nombreJugador-container").show();

        $("#btnNuevaPartida").prop("disabled", false).show(100);  // habilitar el botón nueva partida
        $("#respuestaServidor").val("").text("").removeClass("acertaste perdiste aviso mayor menor error");
    })

    // Validación del campo #numero
    $('#numero').on('input', function() {
        let value = $(this).val();
     //   $("#respuestasServidor").text("").removeClass("acertaste error aviso mayor menor");
        // Si no es un valor numérico [0-100] pone valor por defecto: 50.
        if (!value || isNaN(value) || value < 0 || value > 100) {
            $("#numero").focus();

            $("#servidorRespuestas").text("Por favor, ingresa un número entre 0 y 100.").addClass("aviso");;
            $(this).val(50);  // Restaurar el valor predeterminado, en este caso 50
            
        } else {
            $("#respuestaServidor").val("").text("").removeClass("acertaste perdiste aviso mayor menor error");

        }
    });

    // Intentar. Jugador confirma pulsando botón intentar tras haber fijado un número
    $('#intentar').on('click', function() {
        let intentoNuevo = $("#numero").val();
            // Si el campo de número está vacío o no es válido, asigna 50 como valor predeterminado
        if (!intentoNuevo) {
            intentoNuevo = 50;
            $("#numero").val(50);  // Asigna el valor 50 al campo de entrada
        }

        // Solo si el número es válido (entre 0 y 100)
        console.log(intentoNuevo);
        if (intentoNuevo >= 0 && intentoNuevo <= 100) {

            $.ajax({
                url: URL_SERVIDOR+"adivinar",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ "sessionID": sessionID,
                                        "numero": Number(intentoNuevo)}),
                success: function(data) {

                    let mensaje = "";
                    if (INTENTOS_MAXIMOS - data.intentos < 1) {
                        $.ajax({                                                                                   
                            url: URL_SERVIDOR+"cancelar",
                            type: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify({"sessionID": sessionID}),
                            success: function(dataC) {
                                $("#respuestaServidor").val("").text("").removeClass("acertaste perdiste aviso mayor menor error");
                                $("#respuestaServidor").addClass("perdiste");
                                $("#respuestaServidor").text("¡Oh!. Perdiste. El número a adivinar era " + dataC.Numero);
                                console.log(dataC);
                                // Esperar unos segundos y reiniciar la partida
                                setTimeout(function() {
                                reiniciarPartida();
                                }, 3000);  
                                
                            },
                            error: function(dataC) {
                                console.log("error en la cancelación");
                                console.log(dataC);
                            }
                        });
                    } else {
                       // $("#respuestaServidor").val("").text("").removeClass("acertaste perdiste aviso mayor menor error");
                        if (data.respuesta == 1) {
                            mensaje = "El número es MAYOR. ";
                            $("#respuestaServidor").addClass("mayor");
                        } else if (data.respuesta == -1) {
                            mensaje = "El número es MENOR.  ";
                            $("#respuestaServidor").addClass("menor");
                    } else {
                            mensaje = "¡Acertaste!. ";
                            $("#respuestaServidor").addClass("acertaste"); 
                            data.estado = 0; 
                            // Esperar unos segundos y reiniciar la partida
                            setTimeout(function() {
                            reiniciarPartida();
                            }, 3000);  // 3 segundos de espera antes de reiniciar
                    }
                        if(INTENTOS_MAXIMOS-data.intentos==0) {

                            ("#respuestaServidor").text(mensaje + "¡Último intento!");
                        } else{
                            $("#respuestaServidor").text(mensaje + "Intentos restantes: " + (INTENTOS_MAXIMOS-data.intentos));
                        }
                    }
                    
                },
                error: function(data) {
                    console.log(data);
                }
            });
        } else {
            $("#numero").val(50).placeholder("50");
            $("#servidorRespuestas").text("Por favor, indica un número entre 0 y 100.").addClass("aviso");
        }
        // Función para reiniciar la partida
        function reiniciarPartida() {
            // Limpiar el campo de nombre y respuestas
            $("#nombreJugador").val("").prop("disabled", false).show().focus();
            $("#respuestaServidor").val("").text("").removeClass("acertaste perdiste aviso mayor menor error");

            // Mostrar el formulario de nueva partida
            $("#btnNuevaPartida").prop("disabled", false).show();
            
            // Ocultar los controles de juego
            $("#divIntento").hide();
            $("#btnsIntentarYCancelar").hide();

            // Volver a mostrar el campo de nombre del jugador
            $("#nombreJugador-container").show();
        }
        // -1, el número pensado es menor que el propuesto. 
        //  0, ¡Acertaste!
        // +1, el número pensado es mayor que el propuesto por el jugador.
    });
});
