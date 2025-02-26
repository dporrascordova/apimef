'use strict';
ventanillaVirtual.controller('viewUserModalInstanceController', function ($scope, $rootScope,  $uibModalInstance, $ngBootbox, personaService, $http, BASE_URL, request) {

    $scope.showExtraFields = true;
    $scope.request = request;
    $scope.comments = '';
    $scope.model={
        comments:''
    }


    $scope.cancelar = function () {
       // $uibModalInstance.dismiss('cancel');
        $uibModalInstance.close($scope.response);
    };

    $scope.aprobar = function () {
        const message = {
            'success': 'Se generó correctamente el usuario',
            'question': '¿ Estás seguro de generar el usuario para esta persona ?'
        }
        validate('1', message);
    };

    $scope.anular = function () {
        const message = {
            'success': 'Se anuló correctamente la persona',
            'question': '¿ Estás seguro de anular a esta persona ?'
        }
        validate('3', message);
    };

    $scope.eliminar = function () {
        const message = {
            'success': 'Se rachazó correctamente la persona',
            'question': '¿ Estás seguro de rechazar a esta persona ?'
        }
        validate('2', message);
    };

    function validate(flag, message) {
        $ngBootbox.confirm(message.question).then(function () {
            callAPI(flag, message.success)
        }, function () { });
    }

    function callAPI(flag, message) { 
         
        const endpoint = '/validapersona';
        var formData = new FormData();
        formData.append('idpersona', $scope.request.idpersona);
        formData.append('flgvalida', flag);
        formData.append('idtipousuario', 2);
        formData.append('observacion', $scope.model.comments == "" ? " " :  $scope.model.comments);

        personaService.validarpersona(formData).then(function (response) {
            // 
            //console.log(response);
            var valido = false;
            var mensaje = "";
            if (response.data.ejecucion_procedimiento == true) {
                if (!response.data.rechazar) {
                    valido = true;
                    $ngBootbox.alert({
                        size: "small",
                        title: "Mensaje",
                        message: message,
                        buttons: {
                            ok: {
                                label: '<i class="fa fa-check"></i> Aceptar',
                                className: 'btn-sencondary'
                            }
                        }
                    });
                    $scope.response=response; 
                    $scope.cancelar(); 
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

    }

    $scope.downloadFile = function() {
          
        var arrayarchivo = $scope.request.codigoarchivo.split('.'); 
        var name = arrayarchivo[0]; 
        var fileType = arrayarchivo[1];
        debugger;
        personaService.exportFile(fileType, name).then(function(response) {
        	debugger;
            var file = new Blob([(response.data)], {type: $rootScope.applicationType(fileType)});
            var fileURL = URL.createObjectURL(file);
            const url = window.URL.createObjectURL(file);
            window.open(url);
        });  
    }   

});
