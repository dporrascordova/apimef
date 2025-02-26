'use strict';

ventanillaVirtual.controller('updatepasswordModalInstanceController', function ($scope,BASE_URL_CAPTCHA,$window,jwtHelper,toaster,loginService,$ngBootbox, $uibModalInstance,userForm) {
    //https://plnkr.co/edit/gt7FS4DNRjwLVyFOCZqx?p=preview&preview
    // var viewOlvideClave = this;    
    // viewOlvideClave.data = userForm;
	
    $scope.form = {};
    $scope.submit_UpatePassword = function () {           
        if ($scope.form.userForm.$valid) { 
            //debugger;
          /*  var usuario = {
                UId_Usuario: jwtHelper.decodeToken(localStorage.getItem("accessToken")).UsuarioId,
                UClave: $scope.form.UClave,
                UClaveRepetir: $scope.form.UClaveRepetir,
                UCorreo: "",
                UCodigo: "",
                UCaptcha: "",
            };  */
            
            var usuario = {
                token:  localStorage.getItem("accessToken"),
                clave_usuario: $scope.form.UClaveRepetir
                 
            }; 

            loginService.updatepassword(usuario).then(function(response){  
                //debugger;                    
                if(response.data.ejecucion_procedimiento == true){
                    if (response.data.rechazar == false) {                        
                        toaster.pop({
                            type: 'success',
                            title: 'Mensaje',
                            body: "Credenciales actualizadas correctamente",
                            timeout: 3000,
                            showCloseButton: true,
                            progressBar: true,
                        });
                        $uibModalInstance.close();  
                    }else{ 
                        toaster.pop({
                            type: 'warning',
                            title: 'Mensaje',
                            body: response.data.mensaje_salida,
                            timeout: 2000,
                            showCloseButton: true,
                            progressBar: true,
                        });  
                    }
                } else {
                    $scope.Correo = "";
                    $scope.P_CorreoNoEncontrado = true;
                }
            });
        }  
    };

    $scope.cancel = function () {
        //{...}
        ///alert("You clicked the cancel button."); 
        $uibModalInstance.dismiss('cancel');
    };
});