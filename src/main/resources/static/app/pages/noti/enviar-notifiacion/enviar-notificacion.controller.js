ventanillaVirtual.controller('enviarnotiController', function (
  $scope,
  notiService,
  $ngBootbox,
  $uibModalInstance,
  $filter,
  request,
  ListaDetalle
) {

  $scope.submitted = false;
  $scope.form = {};
  $scope.model = {};
  $scope.response; 
  $scope.ListaDetalle=ListaDetalle; 
  $scope.filesAnexo = [];
  $scope.files = [];
  $scope.contador = 0;
  $scope.TotalMaxFiles = 10; // 10 files
  $scope.TotalMaxFileSize = 20000000; // 20MB



  $scope.submitForm = function () {
	   debugger;
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


  $scope.InsertarNotifiacionFor = function (observacion,ListaDetalle) {
     var mensaje =""; 
  console.log(ListaDetalle); 
   for (var i = 0; i < ListaDetalle.length; i++) {
         var correo = ListaDetalle[i]['correo']; 
         var idusurio = ListaDetalle[i]['id_usuario']; 
         var idpersona =ListaDetalle[i]['id_persona'];
         var iddocumento =ListaDetalle[i]['id_documento'];
      notiService.EnviarNotifiacion(idusurio,idpersona,iddocumento,observacion).then(function (response) {
        console.log(response); 
          
        if (response.data.ejecucion_procedimiento) {
          if (!response.data.rechazar) {
           mensaje+= correo + ' => ' +'Enviado con exito\n'; 
          }
        }else {
          if (response.data.error_lista) {
            if (response.data.objeto.length > 0) {
              mensaje += "<ul>"
              for (var i = 0; i < response.data.objeto.length; i++) {
                mensaje += "<li>" + correo +" => "+  response.data.objeto[i].message + "</li>";
              }
              mensaje += "</ul>"
            }
          } else {
            mensaje += correo +" => "+ response.data.mensaje_salida;
          }
        }
      });
      alert(mensaje); 
   } 

   return mensaje; 

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
  //  $uibModalInstance.dismiss('cancel');
    $uibModalInstance.close($scope.response);
  };



  $scope.addFile = function () {
    if (!$scope.filesAnexo.length) {
      return;
    }

    if (!$scope.validateExtensionFile($scope.filesAnexo[0].name)) {
      $scope.showAlert({
        message:
          'Formato no válido: solo es posible agregar archivos con la extensión de tipo PDF, DOC, DOCX, XLS, XLSX, PPT, PPTX,TXT y JPG'
      });
      $scope.resetFileAnexo();
      return;
    }
    
    if ($scope.filesAnexo[0].size ==0) {
        $scope.showAlert({
      	  message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat($scope.filesAnexo[0].size)})`
        });
        $scope.resetFileAnexo();
        return false;
      }

    var exist = $scope.files.find(
      t => t.fileName === $scope.filesAnexo[0].name
    );

    if ($scope.files.length === $scope.TotalMaxFiles) {
      $scope.showAlert({
        message: `Solo le es permitido adjuntar un total de ${$scope.TotalMaxFiles} archivos.`
      });
      $scope.resetFileAnexo();
      return;
    }

    var total = $scope.filesAnexo[0].size;
    $scope.files.forEach(el => {
      total += el.size;
    });

    if (total > $scope.TotalMaxFileSize) {
      $scope.showAlert({
        message: `No se puede agregar el archivo porque la suma supera los ${20} MB permitidos.`
      });
      $scope.resetFileAnexo();
      return;
    }

    if (!exist) {
      $scope.files.push({
        file: $scope.filesAnexo[0],
        fileName: $scope.filesAnexo[0].name,
        size: $scope.filesAnexo[0].size,
        date: new Date().toLocaleDateString('es-ES')
      });
    } else {
      $scope.showAlert({ message: 'El archivo existe en la lista' });
    }
    $scope.resetFileAnexo();
  };


  $scope.resetFileAnexo = function () {
    angular.element('#filesAnexo').val('');
    $scope.filesAnexo = [];
  };


  $scope.onArrayFileDelete = function (index) {
    $scope.files.splice(index, 1);
  };




  $scope.contarCaracteres = function () {
    $scope.contador = event.target.value.length;
  };








});
