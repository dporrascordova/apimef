ventanillaVirtual.controller('verobseracionController', function (
  $scope,
  notiService,
  $ngBootbox,
  $uibModalInstance,
  item,
  $filter,


) {

  $scope.submitted = false;
  $scope.form = {};

  $scope.response; 
  $scope.contador = 0;
  $scope.item = item;
  $scope.DataObservacion = [];
  
  $scope.model = {
    nro_documento:item.nro_documento,
    fec_recepcion:item.fec_recepcion,
    destinatario: item.remitente,
    tipo_documento:item.desc_tipo_documento ,
    hoja_ruta:item.hoja_ruta
  };

  $scope.submitForm = function () {
    var valido = false;
    var mensaje = "";

    $scope.submitted = true;
    if ($scope.form.MensajeBody.$invalid) {
      return false;
    }
    $ngBootbox
      .confirm({
        message: '¿Estás seguro de enviar la notificación?',
        buttons: {
          confirm: {
            label: 'Aceptar',
            className: 'btn-danger'
          },
          cancel: {
            label: 'Cerrar',
            className: 'btn-secondary'
          }
        }
      })
      .then(

        function () {

          notiService.EnviarNotifiacion($scope.ListaDetalle,$scope.model.mensaje,$scope.files)
            .then(function (response) {
          console.log(response)
              if (response.data.ejecucion_procedimiento) {
                if (!response.data.rechazar) {
                  valido = true;
                  $ngBootbox.alert({
                    type:"success",
                    size: "small",
                    title: "Mensaje",
                    message: "Notifación enviada correctamente",
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-check"></i> Aceptar',
                            className: 'btn-default'
                        }
                    }
                });
                $scope.response=response; 
                $scope.close(); 
                //$scope.searchUsuario(); 
                    // Si todo fue ejecutado bien
                }
            }

            $scope.MostrarErrores(response); 
            }); 


        },
        function () {}
      );

  };



  $scope.MostrarErrores = function (response) {
    var mensaje = "";
    var valido = false;
    if (response.data.ejecucion_procedimiento) {
      if (!response.data.rechazar) { valido = true; }
    }
    if (!valido) {
      if (response.data.error_lista) {
        if (response.data.objeto.length > 0) {
          mensaje += "<ul>"
          for (var i = 0; i < response.data.objeto.length; i++) {
            mensaje += "<li>" + response.data.objeto[i].message + "</li>";
          }
          mensaje += "</ul>"
        }
      } else {
        mensaje = response.data.mensaje_salida;
      }

      $ngBootbox.alert({
        size: "small",
        title: "Mensaje",
        message: mensaje,
        buttons: {
          ok: {
            label: '<i class="fa fa-check"></i> Aceptar',
            className: 'btn-default'
          }
        }
      });
    }
  }
  $scope.close = function () {
    $uibModalInstance.close($scope.response);
  };

  $scope.contarCaracteres = function () {
    $scope.contador = event.target.value.length;
  };


  $scope.ListarObservacion = function () {
    notiService.getDocumentosobs( $scope.item.id_documento).then(function (res) {  
      if (res.data.ejecucion_procedimiento == true) {
        $scope.DataObservacion = res.data.objeto; 
        }
      $scope.MostrarErrores(res); 
    });
  };

  $scope.ListarObservacion(); 

});
