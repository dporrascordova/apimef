// Code goes here
'use strict';
ventanillaVirtual.controller('usuarionuevoModalInstaceController', function ($scope,$rootScope, BASE_URL_CAPTCHA,$uibModal, $uibModalInstance, $ngBootbox, toaster, loginService, transversalService, ubigeo, data) {
    $scope.capchaUrl= BASE_URL_CAPTCHA;
    $scope.id = 0;
    $scope.showExtraFields = false;
    $scope.showTipoNumeroDocumento = true;
    $scope.showTipoNumeroDocumento_Reple_Legal = true;
    $scope.showTipoNumeroDocumento_Delegado = true;
    
    
    $scope.submitted = false;
    $scope.isPeruvian = true;
    $scope.form = {};
    
    $scope.listDepartamentos = $rootScope.getDepartamentos(ubigeo);
    $scope.litTiporPersona = [
        { id: "01", descripcion: 'Natural' },
        { id: "02", descripcion: 'Juridica' }
    ];
    $scope.persona = { UTipoPersona: "01" };

    $scope.litTipoDocumento = [
        { id: "1", descripcion: 'DNI' },
        { id: "2", descripcion: 'Carnet de Extranjería' },
        { id: "3", descripcion: 'Pasaporte' }
    ];

    $scope.onSelectChangeTipoPersona = function () {
        if ($scope.persona.UTipoPersona == "01") {
        	$scope.isPeruvian = $scope.persona.UTipoDoc == "1" || $scope.isNullOrEmpty($scope.persona.UTipoDoc);
            $scope.showExtraFields = false;
        } else {
        	$scope.isPeruvian = true;
            $scope.showExtraFields = true;
        }
    }
    
    // change tipo doc represntante
    $scope.onSelectChangeTipoDocumento_RepreLegal = function () {
       if ($scope.persona.rep_legal_id_tipo_documento == "1") {      
            $scope.showTipoNumeroDocumento_Reple_Legal = true;
        } 
        else if ($scope.persona.rep_legal_id_tipo_documento == "2" || $scope.persona.rep_legal_id_tipo_documento == "3") {
            $scope.showTipoNumeroDocumento_Reple_Legal = false;
        }
    }
    
    // change tipo doc delegado
    $scope.onSelectChangeTipoDocumento_Delegado = function () {

        if ($scope.persona.UTipoDoc_Delegado == "1") {      
            $scope.showTipoNumeroDocumento_Delegado = true;
        } 
        else if ($scope.persona.UTipoDoc_Delegado == "2" ||  $scope.persona.UTipoDoc_Delegado == "3") {
            $scope.showTipoNumeroDocumento_Delegado = false;
        }
    }
    
    
    $scope.onSelectChangeTipoDocumento = function () {
      	$scope.isPeruvian = true;
      	
        if ($scope.persona.UTipoDoc == "1") {      
            $scope.showTipoNumeroDocumento = true;
        } 
        else {
        	$scope.isPeruvian = false;
            $scope.showTipoNumeroDocumento = false;
        }
    }
    
    
    
    $scope.onSelectChangeDepartamento = function () {
		 $scope.listDistrito = [];
		 $scope.listProvincias=[];
		 $scope.persona.UProvincia=null;
		 $scope.persona.UDistrito = null;	
		 $scope.listProvincias = $rootScope.getProvincias(ubigeo,$scope.persona.UDepartamento);
    }
    
    $scope.onSelectChangeProvincia = function () {
		 $scope.listDistrito = [];
		 $scope.persona.UDistrito = null;	
		 $scope.listDistrito = $rootScope.getDistritos(ubigeo,$scope.persona.UDepartamento,$scope.persona.UProvincia);
    }
    
    $scope.refreshCaptcha = function(){
    	 $scope.id++;
    	 $scope.capchaUrl=BASE_URL_CAPTCHA + '?id=' + $scope.id ;
    }
    
    var fileToUpload;
    $scope.UFile_DNI_handle = function () {
    	if(event.target.files.length==0){
   		 	event.target.value = '';
            $scope.persona.fileArchivo=null;
            return false;
    	}
    	
    	if (event.target.files[0].size ==0) {
            $scope.showAlert({
          	  message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat(event.target.files[0].size)})`
            });
            event.target.value = '';
            $scope.persona.fileArchivo=null;
            return false;
          }
    	
        this.fileToUpload = event.target.files;
        $scope.fileToUpload = this.fileToUpload;     
        if (!validateExtensionFile(this.fileToUpload[0].name)) {
            $ngBootbox.alert({
                size: "small",
                title: "Mensaje",
                message: "Formato no válido: solo es posible agregar extensiones de tipo (PNG, JPG, JPEG y PDF)",
                buttons: {
                    ok: {
                        label: '<i class="fa fa-check"></i> Aceptar',
                        className: 'btn-default'
                    }
                }
            });
            event.target.value = '';
            $scope.persona.fileArchivo=null;
            
            return false;
        }
        if (!validateSizeFile(this.fileToUpload[0].size)) {
            $ngBootbox.alert({
                size: "small",
                title: "Mensaje",
                message: "El archivo debe tener un peso máximo de 10Mb.",
                buttons: {
                    ok: {
                        label: '<i class="fa fa-check"></i> Aceptar',
                        className: 'btn-default'
                    }
                }
            });
            event.target.value = '';
            $scope.persona.fileArchivo=null;
            return false;
        }
    }
    function validateExtensionFile(name) {
        var ext = name.substring(name.lastIndexOf('.') + 1);
        if (ext.toLowerCase() == 'png' || ext.toLowerCase() == 'jpg' || ext.toLowerCase() == 'jpeg' || ext.toLowerCase() == 'pdf') {
            return true;
        } else {
            return false;
        }
    }

    function validateSizeFile(name) {
        var fileSize = name;
        if (fileSize < 52428800) {
            return true;
        } else {
            return false;
        }
    }
    $scope.buscarPersona = function () {
        var persona = {
            nrodocumento: $scope.persona.UDocumento,
            Jur_RUC: $scope.persona.Jur_RUC,
            tipopersona: $scope.persona.UTipoPersona,
            UTipoDoc: $scope.persona.UTipoDoc,
        };

        var valido=true; var mensaje ="";
        if(persona.tipopersona=="01"){
        	if(persona.UTipoDoc=="1" &&  persona.nrodocumento=== undefined){
        		mensaje="Ingrese el DNI de 8 dígitos";
            	valido =false;	
        	}else if(persona.UTipoDoc=="2" && persona.nrodocumento=== undefined){
        		mensaje="Ingrese el CE.";
            	valido =false;
        	}else if (persona.UTipoDoc=== undefined){
        		mensaje="Seleccione el tipo de documento.";
            	valido =false;
        	}
        	
        }else if(persona.tipopersona=="02" && persona.Jur_RUC=== undefined ){
        	mensaje="Ingrese el RUC de 11 dígitos";
        	valido= false;
        }
        if(!valido){
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
        	 return;	
        }
        
        if(persona.tipopersona=="02"  ){
        	persona.nrodocumento= persona.Jur_RUC;
        }

        loginService.buscarpersona(persona).then(function (response) {
            var valido = false;
            var mensaje = "";

            if (response.data.ejecucion_procedimiento) {
                if (!response.data.rechazar) {
                    valido = true;
                    if (persona.tipopersona == "01") {
                        $scope.persona.Nat_Nombres = response.data.objeto.nat_Nombres;
                        $scope.persona.Nat_Ape_Pat = response.data.objeto.nat_Ape_Pat;
                        $scope.persona.Nat_Ape_Mat = response.data.objeto.nat_Ape_Mat;
                    } else {
                        $scope.persona.Jur_RUC = response.data.objeto.jur_RUC;
                        $scope.persona.Jur_RazonSocial = response.data.objeto.jur_RazonSocial;
                    }
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
    }
    $scope.openVerImagenDNI = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'pages/login/usuario-nuevo/dni/dni.html',
            controller: 'dniInstaceController',
            controllerAs: 'dnioModalInstace',
            //size: size,
            backdrop: 'static',
            scope: $scope,
            resolve: {
                data: function () {
                    return id;
                }
            }
        });
        modalInstance.result.then(function () {
        });
    };


    $scope.submit_RegistrarUsuario = function () {
    	$scope.submitted = true;
        if ($scope.MiForm.$invalid) {
        	console.log($scope.MiForm); 
        	 $ngBootbox.alert({
                 size: "small",
                 title: "Mensaje",
                 message: "Faltan ingresar algunos campos obligatorios.",
                 buttons: {
                     ok: {
                         label: '<i class="fa fa-check"></i> Aceptar',
                         className: 'btn-sencondary'
                     }
                 }
             });
            return;
        } 
        $ngBootbox.confirm({
            message: "¿Está seguro que desea generar su registro de usuario?",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> Aceptar',
                    className: 'btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn-light-secondary'
                }
            }
        }).then(function () {

                    var model = $scope.persona;
 
            		if (model.UTipoPersona === '01' && model.UTipoDoc !=='1') {
        				model.UCodigoDNI = '0';
        				model.UCodigoUbigeo = '000000';
        				model.UDepartamento = '0';
        				model.UProvincia = '0';
        				model.UDistrito = '0'
            		}
            		var myFileSelected =null; 
                    if( $scope.fileToUpload != undefined)            
                     myFileSelected = $scope.fileToUpload[0];
                    
                    loginService.registerUser(model, myFileSelected).then(function (response) {
                        var valido = false;
                        var mensaje = "";
                        if (response.data.ejecucion_procedimiento == true) {
                            if (response.data.rechazar == false) {
                                valido = true;
                                $ngBootbox.alert({
                                    size: "small",
                                    title: "Mensaje",
                                    message: "Se te enviará un correo con la confirmación de tu registro, luego del proceso de validación",
                                    buttons: {
                                        ok: {
                                            label: '<i class="fa fa-check"></i> Aceptar',
                                            className: 'btn-sencondary'
                                        }
                                    }
                                }).then(function () {
                                });
                                $uibModalInstance.close();
                            }
                        }

                        if (!valido) {
                            if (response.data.mensaje_salida != null && response.data.mensaje_salida != "") {
                                mensaje = response.data.mensaje_salida;
                            }
                            else if (response.data.objeto.length > 0) {
                                mensaje += "<ul>"
                                for (var i = 0; i < response.data.objeto.length; i++) {
                                    mensaje += "<li>" + response.data.objeto[i].message + "</li>";
                                }
                                mensaje += "</ul>"
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
                });
     
    }
    $scope.cancel = function () {
        $uibModalInstance.close();
    };
});
