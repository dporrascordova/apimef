ventanillaVirtual.controller('finalizarController', function (
  $scope,
  mpiService,
  $ngBootbox,
  $uibModalInstance,
  $filter,
  idcoumento

) {

  $scope.submitted = false;
  $scope.form = {};
  $scope.model = {};
  $scope.filesAnexo = [];
  $scope.files = [];
  $scope.contador = 0;
  $scope.TotalMaxFiles = 10; // 10 files
  $scope.TotalMaxFileSize = 20000000; // 20MB
  $scope.idcoumento=idcoumento; 
  $scope.response; 



  $scope.submitForm = function () {
    var valido = false;
    var mensaje = "";
    $scope.submitted = true;
    if ($scope.form.finalizar.$invalid) {
      return false;
    }
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
          mpiService.registroFinalizar($scope.idcoumento,$scope.model, $scope.files)
            .then(function (response) {
              if (response.data.ejecucion_procedimiento) {
                if (!response.data.rechazar) {
                  valido = true;
                  $ngBootbox.alert({
                    type:"success",
                    size: "small",
                    title: "Mensaje",
                    message: "Archivos enviados  correctamente",
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


  };

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


  // $scope.getEstadoExpediente('045566', 2020);
});
