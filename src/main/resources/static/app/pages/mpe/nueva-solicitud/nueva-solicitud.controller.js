ventanillaVirtual.controller(
  'nuevaSolicitudController',
  function ($scope, mpeService, $ngBootbox, $uibModalInstance, tipoDocumento, oficinas, toaster) {
    $scope.submitted = false;
    $scope.form = {};
    $scope.model = {
      t_num_doc: '',
    };
    $scope.listaDeTipoDocumento = tipoDocumento;
    $scope.listaDeOficinas = oficinas;
    $scope.filesAnexo = [];
    $scope.files = [];
    $scope.contador = 0;
    $scope.check_link = false;
    $scope.tipo_anexo_file = true; // file  // input link
    $scope.files_link = [];

    if ($scope.listaDeOficinas.length > 0) $scope.model.organoDestino = '524';

    $scope.contarCaracteres = function () {
      $scope.contador = event.target.value.length;
    };

    $scope.foliosChange = function () {
      if (parseInt(event.target.value) > 0) {
        $scope.model.nroFolios = parseInt(event.target.value);
        return;
      }
      $scope.model.nroFolios = null;
    };

    $scope.validarPDF = function () {
      if (event.target.files.length == 0) {
        event.target.value = '';
        $scope.model.fileInput = null;
        return false;
      }

      var type = event.target.files[0].type;

      if (!this.validateExtensionFile(event.target.files[0].name, /(pdf)$/gi)) {
        $scope.showAlert({
          message: 'Formato no válido: solo es posible agregar la extensión de tipo PDF',
        });
        event.target.value = '';
        $scope.model.fileInput = null;
        return false;
      }

      if (event.target.files[0].size == 0) {
        $scope.showAlert({
          message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat(event.target.files[0].size)})`,
        });
        event.target.value = '';
        $scope.model.fileInput = null;
        return false;
      }

      if (event.target.files[0].size > $scope.docMaxFileSize) {
        $scope.showAlert({
          message: 'El archivo debe tener un peso máximo de ' + this.bytesToFormat($scope.docMaxFileSize),
        });
        event.target.value = '';
        $scope.model.fileInput = null;
        return false;
      }

      // var exist = $scope.files.find(
      // t => t.fileName === $scope.model.fileInput[0].name
      // );
      //
      // if (exist) {
      // $scope.showAlert({
      // message: 'El archivo existe como adjunto en los anexos.'
      // });
      // event.target.value = '';
      // $scope.model.fileInput = null;
      // return false;
      // }
    };

    $scope.Check_link = function () {
      if ($scope.check_link) {
        $scope.tipo_anexo_file = false;
      } else {
        $scope.tipo_anexo_file = true;
      }
    };

    $scope.addFile = function () {
      if (!$scope.check_link) {
        if (!$scope.filesAnexo.length) {
          toaster.pop({
            type: 'warning',
            title: 'Mensaje',
            body: 'Seleccione un archivo para anexar.',
            timeout: 3000,
            showCloseButton: true,
            progressBar: true,
          });
          return;
        }
      } else {
        if ($scope.model.link_archivo == '') {
          toaster.pop({
            type: 'warning',
            title: 'Mensaje',
            body: 'ingrese link del anexo.',
            timeout: 3000,
            showCloseButton: true,
            progressBar: true,
          });
          return;
        }
      }

      if (!$scope.check_link) {
        if (!$scope.validateExtensionFile($scope.filesAnexo[0].name)) {
          $scope.showAlert({
            message:
              'Formato no válido: solo es posible agregar archivos con la extensión de tipo PDF, DOC, DOCX, XLS, XLSX, PPT, PPTX,TXT y JPG',
          });
          $scope.resetFileAnexo();
          return;
        }

        if ($scope.filesAnexo[0].size == 0) {
          $scope.showAlert({
            message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat($scope.filesAnexo[0].size)})`,
          });
          $scope.resetFileAnexo();
          return false;
        }

        if ($scope.filesAnexo[0].size > this.docMaxFileSize) {
          $scope.showAlert({
            message: 'El archivo debe tener un peso máximo de ' + this.bytesToFormat($scope.docMaxFileSize),
          });
          $scope.resetFileAnexo();
          return;
        }

        var exist = $scope.files.find((t) => t.fileName === $scope.filesAnexo[0].name);

        if ($scope.files.length >= $scope.AnexoTotalMaxFiles) {
          $scope.showAlert({
            message: `Solo le es permitido adjuntar un total de ${$scope.AnexoTotalMaxFiles} archivos.`,
          });
          $scope.resetFileAnexo();
          return;
        }

        var total = $scope.filesAnexo[0].size;
        $scope.files.forEach((el) => {
          total += el.size;
        });

        if (total > $scope.AnexoTotalMaxFileSize) {
          $scope.showAlert({
            message: `No se puede agregar el archivo porque la suma supera los ${this.bytesToFormat(
              $scope.AnexoTotalMaxFileSize,
            )} permitidos.`,
          });
          $scope.resetFileAnexo();
          return;
        }

        if (!angular.isUndefined($scope.model.fileInput)) {
          if ($scope.model.fileInput && $scope.model.fileInput.length > 0 && $scope.filesAnexo[0].name === $scope.model.fileInput[0].name) {
            $scope.showAlert({
              message: `No se puede agregar el archivo como anexo, debido a que ya fue agregado como archivo principal.`,
            });
            $scope.resetFileAnexo();
            return;
          }
        }

        if (!exist) {
          $scope.files.push({
            file: $scope.filesAnexo[0],
            fileName: $scope.filesAnexo[0].name,
            size: $scope.filesAnexo[0].size,
            date: new Date().toLocaleDateString('es-ES'),
            flg_link: '1', // input
          });
        } else {
          $scope.showAlert({ message: 'El archivo existe en la lista' });
        }
      } else {
        // link
        var exist = $scope.files.find((t) => t.fileName === $scope.model.link_archivo);
        if (!exist) {
          $scope.files.push({
            file: null,
            fileName: $scope.model.link_archivo,
            size: 0,
            date: new Date().toLocaleDateString('es-ES'),
            flg_link: '2', // link
          });
        } else {
          $scope.showAlert({ message: 'El archivo existe en la lista' });
        }
        $scope.model.link_archivo = '';
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

    $scope.submitForm = function () {
      $scope.submitted = true;
      if ($scope.form.nuevaSolicitud.$invalid) {
        return false;
      }

      $ngBootbox
        .confirm({
          message: '¿Estás seguro de enviar la solicitud?',
          buttons: {
            confirm: {
              label: 'Aceptar',
              className: 'btn-danger',
            },
            cancel: {
              label: 'Cerrar',
              className: 'btn-secondary',
            },
          },
        })
        .then(
          function () {
            if ($scope.files.length > 0) {
              $scope.files.forEach((file) => {
                if (file.flg_link == 2) {
                  $scope.files_link.push({
                    nombre_archivo: file.fileName,
                    flg_link: 2, // link
                  });
                }
              });
            }
           // if($scope.model.asunto.trim().length() < 12){
           // 	 $scope.showAlert({ message: 'Asunto, debe de ingresar como minimo 12 caracteres' });
           // 	 return;
           // }
            mpeService.registrarDocumento($scope.model, $scope.files, $scope.files_link).then(function (response) {
            	 
              if (response.data.ejecucion_procedimiento == true && response.data.rechazar == false) {
                $scope.showAlert({
                  type: 'success',
                  title: '!Exito!',
                  message: response.data.mensaje_salida,
                });
                $scope.close();
                $scope.$emit('reloadGrid');
              } else {
                $scope.showAlert({
                  type: 'danger',
                  title: '!Error!',
                  message: response.data.mensaje_salida,
                });
              }
            });
          },
          function () {},
        );
    };

    $scope.exportarAExcel = function () {};

    $scope.close = function () {
      $uibModalInstance.dismiss('cancel');
    };
  },
);
