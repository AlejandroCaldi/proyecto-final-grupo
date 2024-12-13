$(document).ready(function() {

// https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes

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


    // Crear partida
    $("#nuevaPartida").on("click",function(){
        
        $("#divNombre").show();
        var nombreNuevo = $("#nombre").val();
   //     var adivinarNuevo = 77;   // generar aleatoriamente en cada nueva partida
        if (nombreNuevo === "") { // Ni vacíos ni de tipo distinto a cadena
            alert("Por favor, introduzca un nombre");
            return;
        } else {
            $("#nuevaPartida").hide();  // En lugar de esconde, mejor deshabilitar
            var intentoNuevo = $("#intento").val();
            var contaIntentosNuevo = 1;
            $("#divIntento").show();
        }
        $.ajax({
            url: "https://my-json-server.typicode.com/juanmgp888/myjsonserver/solicitudes",
            method: "POST",
            "data": JSON.stringify({
                id: 0,          // Cero indica creación 
                nombreJugador: nombreNuevo,
                numIntento: intentoNuevo,  // pte. añadir a un array de intentos
                contaIntentos: contaIntentosNuevo,
            //    adivinar: adivinaNum;
            }),
            success: function(data) {
                $("#resCrear").text("OK. Nuevo registro."); 
             //   listar();
                console.log(data);
            },
            error: function(data) {
                console.log(data);
            }
        }); 
    
    }) // fin bloque nueva partida
        
});

console.log("jquery funcionando");