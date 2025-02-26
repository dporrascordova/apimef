ventanillaVirtual.controller('editarSolicitudController', function (
  $rootScope,
  $scope,
  $sce,
  mpeService,
  mpiService,
  $ngBootbox,
  $uibModal,
  $uibModalInstance,
  solcitudId,
  tipoDocumento,
  oficinas
) {
  $scope.submitted = false;
  $scope.showLocalFile = false;
  $scope.localFile;
  $scope.form = {};
  $scope.model = {};
  $scope.listaDeTipoDocumento = tipoDocumento;
  $scope.listaDeOficinas = oficinas;
  $scope.filesAnexo = [];
  $scope.files = [];
  $scope.contador = 0;
  $scope.check_link = false; 
  $scope.tipo_anexo_file = true; // file  // input link
  $scope.files_link = [];

  $scope.foliosChange = function () {
	  
	  if( parseInt(event.target.value)>0){
		 $scope.documento.nro_folios = parseInt(event.target.value);
		  return;
	  }
	  $scope.documento.nro_folios = null;
  }
  
  mpiService.getDocumentoPorId(solcitudId).then(function (response) {
    console.log(response.data);
    $scope.documento = response.data.objeto;
    if ($scope.documento) {
      $scope.contador = $scope.documento.asunto.length;
      $scope.documento.id_oficina += '';
      $scope.documento.hoja_ruta =  $scope.documento.numero_sid +'-'+$scope.documento.anio;
    }

    if ($scope.documento.anio > 0 && $scope.documento.numero_sid != null) {
      mpiService
        .getExpediente($scope.documento.anio, $scope.documento.numero_sid)
        .then(function (res) {
          $scope.movimientosSTD = res.movimientos;
        });
    }

    mpiService
      .exportFile(
        response.data.objeto.id_documento,
        'pdf',
        response.data.objeto.codigo_archivo
      )
      .then(function (responses) {
        var file = new Blob([responses.data], {
          type: $rootScope.applicationType('pdf')
        });
        var fileURL = URL.createObjectURL(file);
        $scope.file = {
          file: $sce.trustAsResourceUrl(fileURL)
        };
      });
  });

  $scope.contarCaracteres = function () {
    $scope.contador = event.target.value.length;
  };

  $scope.downloadFile = function (id, fileType, fileName) {
    mpiService.exportFile(id, fileType, fileName).then(function (response) {
      var file = new Blob([response.data], {
        type: $rootScope.applicationType(fileType)
      });
      window.open(window.URL.createObjectURL(file));
    });
  };

  $scope.getEstadoExpediente = function (numeroSid, anio) {
    mpeService.getEstadoExpediente(numeroSid, anio).then(function (res) {
      if (res.status === 200) {
        console.log(res);
      }
    });
  };


  $scope.validarPDF = function () {
	if(event.target.files.length==0){
	  event.target.value = '';
	  $scope.model.fileInput = null;
	    $scope.showLocalFile = false;
	  return false;
	}
		
    var type = event.target.files[0].type;
    
    if (!this.validateExtensionFile(event.target.files[0].name, /(pdf)$/gi)) {
      $scope.showAlert({
        message:
          'Formato no válido: solo es posible agregar la extensión de tipo PDF'
      });
      event.target.value = '';
      $scope.model.fileInput = null;
      $scope.showLocalFile = false;
      return false;
    }
    
    if (event.target.files[0].size ==0) {
        $scope.showAlert({
      	  message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat(event.target.files[0].size)})`
        });
        event.target.value = '';
        $scope.model.fileInput = null;
        $scope.showLocalFile = false;
        return false;
      }

    if (event.target.files[0].size > $scope.docMaxFileSize) {
      $scope.showAlert({
    	  message: 'El archivo debe tener un peso máximo de '+ this.bytesToFormat($scope.docMaxFileSize) 
      });
      event.target.value = '';
      $scope.model.fileInput = null;
      $scope.showLocalFile = false;
      return false;
    }
    
    $scope.showLocalFile = true;
    var reader  = new FileReader();
    
//    reader.onload = function () {    	
//        $scope.localFile = $sce.trustAsResourceUrl(reader.result);
//      }
//  reader.readAsDataURL(event.target.files[0]);
  
    var filepdf = event.target.files[0];
    var fileURL = URL.createObjectURL(filepdf);
    $scope.localFile = $sce.trustAsResourceUrl(fileURL);
  };
  
  $scope.Check_link = function (){
	  if($scope.check_link) {
		  $scope.tipo_anexo_file = false; 	  
	  }else{
		  $scope.tipo_anexo_file = true; 	 
	  }
	
  }
  
  
  
  
  $scope.addFile = function () {
	  
	 if(!$scope.check_link){  
    if (!$scope.filesAnexo.length) {
    	toaster
		.pop({
			type : 'warning',
			title : 'Mensaje',
			body :'Seleccione un archivo para anexar.',
			timeout : 3000,
			showCloseButton : true,
			progressBar : true,
		});
    	  return;
        }

	 }else{
   	  if($scope.model.link_archivo ==""){
			toaster
			.pop({
				type : 'warning',
				title : 'Mensaje',
				body :'ingrese link del anexo.',
				timeout : 3000,
				showCloseButton : true,
				progressBar : true,
			});
     return;
	  }	
    }
	 
	 
	 
if(!$scope.check_link){  
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
        return;
      }    

    if ($scope.filesAnexo[0].size > this.docMaxFileSize) {
        $scope.showAlert({
      	  message: 'El archivo debe tener un peso máximo de '+ this.bytesToFormat($scope.docMaxFileSize) 
        });
        $scope.resetFileAnexo();
        return;
    }
    
    var exist = $scope.files.find(
      t => t.fileName === $scope.filesAnexo[0].name
    );

    if ($scope.files.length >= $scope.AnexoTotalMaxFiles) {
      $scope.showAlert({
        message: `Solo le es permitido adjuntar un total de ${$scope.AnexoTotalMaxFiles} archivos.`
      });
      $scope.resetFileAnexo();
      return;
    }

    var total = $scope.filesAnexo[0].size;
    $scope.files.forEach(el => {
      total += el.size;
    });

    if (total > $scope.AnexoTotalMaxFileSize) {
      $scope.showAlert({
    	  message: `No se puede agregar el archivo porque la suma supera los ${this.bytesToFormat($scope.AnexoTotalMaxFileSize)} permitidos.`
      });
      $scope.resetFileAnexo();
      return;
    }
    
    if(!angular.isUndefined($scope.model.fileInput)){
    	if($scope.model.fileInput && ($scope.model.fileInput.length> 0  && $scope.filesAnexo[0].name === $scope.model.fileInput[0].name)){
    	$scope.showAlert({
            message: `No se puede agregar el archivo como anexo, debido a que ya fue agregado como archivo principal.`
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
        flg_link : "1", // input file
        flg_nuevo : "1"	
      });
    } else {
      $scope.showAlert({ message: 'El archivo existe en la lista' });
    }
    
  }else { // link 
	   var exist = $scope.files.find(
		      t => t.fileName === $scope.model.link_archivo
	    );
	   
	 if (!exist) {
	    $scope.files.push({
	        file: null,
	        fileName: $scope.model.link_archivo,
	        size: 0,
	        date: new Date().toLocaleDateString('es-ES'),
	        flg_link : "2", // link
	        flg_nuevo : "1"	
	     });   
	   } else {
		      $scope.showAlert({ message: 'El archivo existe en la lista' });
	   }
	  $scope.model.link_archivo =""; 
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
        message: '¿Estás seguro de subsanar la solicitud?',
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
          $scope.documento.id_estado_documento = 4; // subsanado;
          // solo nuevos
        	if( $scope.files.length > 0){
     		 $scope.files.forEach(file => {
  	   		if(file.flg_link == 2 && file.flg_nuevo == "1")  { 
  	   		  $scope.files_link.push({
  	   	        nombre_archivo: file.fileName,
  	   	        flg_link : 2 ,// link
  	   	        flg_nuevo :file.flg_nuevo
  		       });
  		        }
  		      });
  		    }
          
    
          mpeService
            .actualizarDocumento(
              $scope.documento,
              $scope.files,
              $scope.model.fileInput, 
              $scope.files_link
            )
            .then(function (response) {
              console.log(response);
              if (
                response.data.ejecucion_procedimiento == true &&
                response.data.rechazar == false
              ) {
                $scope.showAlert({
                  type: 'success',
                  title: '!Exito!',
                  message: response.data.mensaje_salida
                });
                $scope.close();
                $scope.$emit('reloadGrid');
              } else {
                $scope.showAlert({
                  type: 'danger',
                  title: '!Error!',
                  message: response.data.mensaje_salida
                });
              }
            });
        },
        function () {}
      );
  };

  $scope.onAnexoFileDelete = function (index) {
    $scope.documento.anexos.splice(index, 1);
  };

  $scope.showObservaciones = function () {
    var modalInstance = $uibModal.open({
      animation: true,
      backdrop: 'static',
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'pages/mpe/editar-solicitud/mostar-observaciones.html',
      controller: 'mostrarObservacionesController',
      size: 'lg',
      resolve: {
        solcitudId: function () {
          return solcitudId;
        }
      }
    });
    modalInstance.result.then(
      function (response) {},
      function (error) {}
    );
  };

  $scope.close = function () {
    $uibModalInstance.dismiss('cancel');
  };

  $scope.close = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
