// Code goes here
'use strict';

ventanillaVirtual.controller('olvideclaveModalInstanceController', function ($scope,BASE_URL_CAPTCHA,$window,toaster,loginService,$ngBootbox, $uibModalInstance,gRecaptcha,userForm) {
    //https://plnkr.co/edit/gt7FS4DNRjwLVyFOCZqx?p=preview&preview
    // var viewOlvideClave = this;    
    // viewOlvideClave.data = userForm;
	
    $scope.form = {};
    $scope.capchaUrl=BASE_URL_CAPTCHA;
	$scope.id = 0;
	$scope.refreshCaptcha = function(){
	   	 $scope.id++;
	   	 $scope.capchaUrl=BASE_URL_CAPTCHA + '?id=' + $scope.id ;
	}
    $scope.submit_correo = function () {           
        if ($scope.form.userForm.$valid) {             
            // parameters get passed in to Google Recaptcha's execute function
                         
                var usuario = {
                    UCodigo: "",
                    UCorreo: $scope.form.UCorreo,
                    UCaptcha: $scope.form.UCaptcha
                };                
                loginService.EnviarCorreoLogin(usuario).then(function(response){  
                    //debugger;                    
                    if(response.data.ejecucion_procedimiento == true){
                        if (response.data.rechazar == false) {
                            $scope.P_CorreoNoEncontrado = false;
                            $scope.P_MostrarCodigo = true;
                            toaster.pop({
                                type: 'success',
                                title: 'Mensaje',
                                body: "El código de verificación fue enviado a su correo electrónico.",
                                timeout: 5000,
                                showCloseButton: true,
                                progressBar: true,
                            });  
                        }else{ 
                            toaster.pop({
                                type: 'warning',
                                title: 'Mensaje',
                                body: response.data.mensaje_salida,
                                timeout: 5000,
                                showCloseButton: true,
                                progressBar: true,
                            });                                       
                            // $ngBootbox.alert({
                            //     size: "small",
                            //     title: "Mensaje",
                            //     message: response.data.mensaje_salida,
                            //     buttons: {
                            //         ok: {
                            //             label: '<i class="fa fa-check"></i> Aceptar',
                            //             className: 'btn-default'
                            //         }
                            //     }                    
                            // }).then(function() {                    
                            //     $scope.usuario.password = "";
                            // });
                        }
                    } else {
                        $scope.Correo = "";
                        //$scope.P_CorreoNoEncontrado = true;
                        toaster.pop({
                            type: 'warning',
                            title: 'Mensaje',
                            body: response.data.mensaje_salida,
                            timeout: 2000,
                            showCloseButton: true,
                            progressBar: true,
                        });       
                    }
                    // console.log(response);
                    // if(response.data.ejecucion_procedimiento==true){
                    //     $scope.P_MostrarCodigo = true;
                    // }else{
                    //     $scope.P_CodigoNoEncontrado = true;
                    // }                   
                    // $uibModalInstance.close();
                });
            
        }  
    };

    $scope.submit_verificarCodigo = function () {         
        //console.log("asdasd");     
        // if ($scope.form.userForm.$valid) {   
            var usuario = {
                UCodigo:  $scope.form.UCodigo,
                UCorreo: $scope.form.UCorreo                
            };            
            loginService.verifiedcode(usuario).then(function(response){   
                if(response.data.ejecucion_procedimiento == true){
                    if (response.data.rechazar == false) {
                        $scope.P_CodigoNoEncontrado = false; 
                        //autenticar
                        localStorage.setItem("accessToken", response.data.objeto.token);                
                        $window.location.href = "index.html#!";
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
                }else{
                    $scope.P_CodigoNoEncontrado = true;
                    toaster.pop({
                        type: 'error',
                        title: 'Mensaje',
                        body: response.data.mensaje_salida,
                        timeout: 2000,
                        showCloseButton: true,
                        progressBar: true,
                    }); 
                }               
            });
        // }
    };

    $scope.cancel = function () {
        //{...}
        ///alert("You clicked the cancel button."); 
        //$uibModalInstance.dismiss('cancel');
        $uibModalInstance.close();
    };
});