'use strict';
ventanillaVirtual.controller('actualizarUsuarioController', function ($scope,$rootScope, $uibModalInstance, $ngBootbox, personaService, $http, BASE_URL, request, Fechas, $filter, item) {
  debugger; 
 if(request != null && request.tipopersona == "2"){
	 request.rep_legal_id_tipo_documento = request.rep_legal_id_tipo_documento.toString();
	 request.delegado_id_tipo_documento = request.delegado_id_tipo_documento.toString();
  } 
  
  
  $scope.showExtraFields = true;
  $scope.request = request;
  $scope.Fechas = Fechas;
  $scope.item = item;
  $scope.comments = '';
  

  $scope.isPeruvian = request == null || !(request.tipopersona=== '1' && request.tipodoc!= 1);

var valuesStart=Fechas.fec_activacion.split("/");
var valuesEnd=Fechas.fec_desactivacion.split("/");
var dateStart=new Date(valuesStart[2],(valuesStart[1]-1),valuesStart[0]);
var dateEnd=new Date(valuesEnd[2],(valuesEnd[1]-1),valuesEnd[0]);

  $scope.fechasmodel = {
    fec_activa: dateStart,
    fec_inactiva: dateEnd
  };


  $scope.submitForm = function () {
	  debugger; 
    var valido = false;
    var mensaje = "";
     
    var fec_activacion = $filter('date')($scope.fechasmodel.fec_activa, "dd/MM/yyyy");
    var fec_desactivar = $filter('date')($scope.fechasmodel.fec_inactiva, "dd/MM/yyyy");
    var fechas = {
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
        message: '¿Estás seguro de actualizar este registro?',
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
          personaService.ActualizarUsuario(item, fechas)
            .then(function (response) {
              console.log(response);
              if (response.data.ejecucion_procedimiento) {
                if (!response.data.rechazar) {
                  valido = true;
                  $ngBootbox.alert({
                    type: "success",
                    size: "small",
                    title: "Mensaje",
                    message: "Usuario actualizado correctamente",
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
        function () { }
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


  $scope.close = function () {
    //$uibModalInstance.dismiss('cancel');
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



  $scope.downloadFile = function() {
      
    var arrayarchivo = $scope.request.codigoarchivo.split('.'); 
    var name = arrayarchivo[0]; 
    var fileType = arrayarchivo[1];
    personaService.exportFile(fileType, name).then(function(response) {  
        var file = new Blob([(response.data)], {type: $rootScope.applicationType(fileType)});
        var fileURL = URL.createObjectURL(file);
        const url = window.URL.createObjectURL(file);
        window.open(url);
    });  
}   


});
