'use strict';

ventanillaVirtual.controller('visualizarsolicitudController', function ($scope,
                                                                        $rootScope,
                                                                        $sce,
                                                                        $uibModalInstance,
                                                                        IdSolicitud,
                                                                        mpiService,
                                                                        mpeService) {            
    $scope.form = {};    
    $scope.OnInit = function(){
        $scope.getMEFOficinas();        
    }

    $scope.getMEFOficinas = function () {
        mpiService.getMEFOficinas().then(function (res) {
            if (res.status === 200) {
                $scope.oficinas = res.data.objeto;
                //console.log( $scope.oficinas);
                $scope.getMEFTipoDocumento();
            }
        });
    };

    $scope.getMEFTipoDocumento = function () {
        mpeService.getMEFTipoDocumento().then(function (res) {
            if (res.status === 200) {
                //debugger;
                $scope.tipoDocumento = res.data.objeto;
                $scope.getSolicitud();                
            }
        });
    };

    $scope.getSolicitud = function(){
        mpiService.getDocumentoPorId(IdSolicitud).then(function(response){                      
            $scope.documento = response.data.objeto;  
            console.log($scope.documento);
            $scope.documento.hoja_ruta = $scope.documento.hoja_ruta =="null" ? "": $scope.documento.hoja_ruta; 
            $scope.documento.hoja_ruta_generado = `${$scope.documento.numero_sid}-${$scope.documento.anio}`;             
            $scope.documento.hoja_ruta_generado = $scope.documento.hoja_ruta_generado=="-0" ? "": $scope.documento.hoja_ruta_generado; 
            $scope.movimientosSTD = [];  
            if ($scope.documento.anio > 0 && $scope.documento.numero_sid != null) {
                mpiService.getExpediente($scope.documento.anio, $scope.documento.numero_sid)
                .then(function(res) {
                    //debugger;
                    $scope.movimientosSTD = res.data.objeto.movimientos;
                    //console.log($scope.movimientosSTD);
                })
                .catch(function(error) {
                    console.error('Error al exportar archivo:', error);
                });
            } 
            
            
            mpiService.exportFile(response.data.objeto.id_documento,'pdf',response.data.objeto.codigo_archivo).then(function(response){  
                var file = new Blob([(response.data)],{type: $rootScope.applicationType('pdf')});            
                $scope.file = {
                    file : $sce.trustAsResourceUrl(window.URL.createObjectURL(file))
                }
            });        
            
        });   
    }

    $scope.ejecutarAgregarAExpediente = function(documento,item){
        //debugger;
        mpiService.ejecutarAgregarAExpediente(documento,item).then(function(res){
            if (res.data.ejecucion_procedimiento && !$scope.isNullOrEmpty(res.data.mensaje_salida)){
                $scope.showAlert({ message: res.data.mensaje_salida });
                //$scope.cancel();
                //$scope.refreshModal();
                $scope.getSolicitud(); 
            }else{
                $scope.showAlert({ message: res.data.mensaje_salida });
            }   
                //$uibModalInstance.close('refresh');
                //$scope.refreshModal();
        });
    }

    $scope.downloadFile = function(id, fileType, fileName) {
        mpiService.exportFile(id, fileType, fileName).then(function(response) {            
            var file = new Blob([(response.data)], {type: $rootScope.applicationType(fileType)});
            var fileURL = URL.createObjectURL(file);
            const url = window.URL.createObjectURL(file);
            window.open(url);
        });
    }
       
    $scope.cancel = function () {
        $uibModalInstance.close();
    };
    /*INIT LOAD*/
    $scope.OnInit();

    $scope.refreshModal = function() {
        $uibModalInstance.close();
        $scope.$parent.abrirModal(id_documento,'xl'); // Llama al padre para reabrir
    };
});