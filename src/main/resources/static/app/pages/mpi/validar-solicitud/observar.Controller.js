'use strict';
ventanillaVirtual.controller('observarsolicitudController', function ($scope,
                                                                    $uibModalInstance,    
                                                                    $ngBootbox,
                                                                    toaster,
                                                                    mpiService,
                                                                    IdSolicitud) {        
 
 
    $scope.contador = 0;    
  
    //debugger;                                                                            
    $scope.OnInit = function(){
        //$scope.getMEFOficinas();        
        $scope.form = {};    
        $scope.IdSolicitud = IdSolicitud;    
      
    }
    $scope.contarCaracteres = function () {
        $scope.contador = event.target.value.length;
    };
      
    $scope.registrarObservacionSolcitud = function (IdSolicitud) {
        $ngBootbox.confirm({
            message: "¿Está seguro que desea observar la solicitud?",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> Aceptar',
                    className: 'btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn-secondary'
                }
            }
        }).then(function () {
            //console.log('Confirmed!');  
        	debugger; 
        	var fech_anu = $scope.fechaAnulacion(); 
            let model = {
                id_documento :  $scope.IdSolicitud,
                observacion : $scope.modelo.observacionSolicitud.toUpperCase() 
            //    fecha_anulacion: $scope.fechaAnulacion()
            };  
     
            mpiService.validarRecepcionYObservacion($scope.IdSolicitud).then(function(res){
            	if(res.data.ejecucion_procedimiento && !$scope.isNullOrEmpty(res.data.mensaje_salida))		{			
					$scope.showAlert({
			      	  message: res.data.mensaje_salida
			        });
					return;
            	}
            	
                mpiService.observarSolicitud(model).then(function(response){                        
                    //debugger;
                  var valido=false;
                    if (response.data.ejecucion_procedimiento == true) {
                        if (response.data.rechazar == false) {
                            valido = true;
                        }else   {
                            valido = false;
                        }
                    }else{
                        valido = false;
                    }
                    if (valido) {
                      //console.log(response);  
                        $uibModalInstance.close({status: true});
                        toaster.pop({
                            type: 'success',
                            title: 'Mensaje',
                            body: 'Se registro correctamente la observación.',
                            timeout: 3000,
                            showCloseButton: true,
                            progressBar: true,
                        });   
                    }else{
                        $uibModalInstance.close({status: false});
                        var mensaje='';
                        if(response.data.mensaje_salida != null && response.data.mensaje_salida != "" ){
                            mensaje = response.data.mensaje_salida;
                        }
                        toaster.pop({
                            type: 'warning',
                            title: 'Mensaje',
                            body: mensaje,
                            timeout: 3000,
                            showCloseButton: true,
                            progressBar: true,
                        });   
                    }         
                }); 
            });
            
        }, function () {
            //CANCEL
            //console.log('Confirm dismissed!');
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.close({status: false});
    };
    /*Init App */
    $scope.OnInit();

    $scope.fechaAnulacion = function () {
        var f = new Date();
        var fechaActual = ''; 
        var fechaanulacion=""
        var mes_a = (f.getMonth() + 1); 
        var dias_a = (f.getDate()); 
        if(String(mes_a).length == 1)
        mes_a = "0" + mes_a; 
        if(String(dias_a).length == 1)
        dias_a = "0" + dias_a; 
        fechaActual=(dias_a + "/" + mes_a + "/" + f.getFullYear()); 
        
        f.setDate(f.getDate() + 2);
        var mes_b = (f.getMonth() + 1); 
        var dias_b = (f.getDate()); 
        if(String(mes_b).length == 1)
        mes_b = "0" + mes_b; 
        if(String(dias_b).length == 1)
        dias_b = "0" + dias_b;   
        fechaanulacion=(dias_b + "/" + mes_b  + "/" + f.getFullYear()); 


      //console.log(fechaanulacion); 
        return fechaanulacion; 
    };
});