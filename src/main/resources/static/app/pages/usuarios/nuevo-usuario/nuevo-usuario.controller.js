ventanillaVirtual.controller('nuevoUsuarioController', function (
  $scope,
  personaService,
  $ngBootbox,
  $uibModalInstance,
  $filter,
  Perfil,
  Persona
) {

  $scope.submitted = false;
  $scope.form = {};
  $scope.model = {};
  $scope.listaDePerfil = Perfil;
  $scope.listaDePersona = Persona;
  $scope.filesAnexo = [];
  $scope.files = [];
  $scope.contador = 0;
  $scope.response; 

  $scope.getEstadoExpediente = function (numeroSid, anio) {
    personaService.getEstadoExpediente(numeroSid, anio).then(function (res) {
      if (res.status === 200) {
        console.log(res);
      }
    });
  };

  $scope.contarCaracteres = function () {
    $scope.contador = event.target.value.length;
  };

  $scope.submitForm = function () {
    var valido = false;
    var mensaje = "";
    var fec_activacion = $filter('date')($scope.model.FechaActivacion, "dd/MM/yyyy"); 
    var fec_desactivar = $filter('date')($scope.model.fechaDesactivacion, "dd/MM/yyyy"); 
    var fechas ={
      fec_acti: fec_activacion,
      fec_desa: fec_desactivar
    }

    $scope.submitted = true;
    if ($scope.form.nuevoUsuario.$invalid) {
      return false;
    }
    if($scope.Validarvechas(fechas)){

    $ngBootbox
      .confirm({
        message: '¿Estás seguro de guardar el usuario?',
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
          personaService.registrarUsuario($scope.model,fechas)
            .then(function (response) {
              if (response.data.ejecucion_procedimiento) {
                if (!response.data.rechazar) {
                  valido = true;
                  $ngBootbox.alert({
                    type:"success",
                    size: "small",
                    title: "Mensaje",
                    message: "Usuario registrado correctamente",
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


            });
        },
        function () {}
      );
    } else{
      $ngBootbox.alert({
        size: "small",
        title: "Mensaje",
        message: 'La fecha de activación debe ser menor a la fecha de desactivación',
        buttons: {
          ok: {
            label: '<i class="fa fa-check"></i> Aceptar',
            className: 'btn-default'
          }
        }
      });
     }
  };

  $scope.exportarAExcel = function () {};

  $scope.close = function () {
    $uibModalInstance.close($scope.response);
  };

  $scope.Validarvechas = function(fechas){
    var valido =true;
    var valuesStart=fechas.fec_acti.split("/");
    var valuesEnd=fechas.fec_desa.split("/");
    var dateStart=new Date(valuesStart[2],(valuesStart[1]-1),valuesStart[0]);
    var dateEnd=new Date(valuesEnd[2],(valuesEnd[1]-1),valuesEnd[0]);
      
    if(dateStart>=dateEnd)
    {
     valido =false;
    }
   return valido; 
   }; 
});
