'use strict';
ventanillaVirtual.controller('LoginController', LoginController);

function LoginController($scope,$rootScope, gRecaptcha, BASE_URL_CAPTCHA, $window,
		$uibModal, $ngBootbox, loginService, transversalService) {
	  'use strict';
	// key ReCapchat

	$scope.anio = new Date().getFullYear();
	// debugger;
	$scope.capchaUrl = BASE_URL_CAPTCHA;
	$scope.id = 100;
	$scope.setControles = function() {
		$scope.usuario.username = '';
		$scope.usuario.password = '';
		$scope.usuario.captcha = '';
	};

	$scope.refreshCaptcha = function() {
		$scope.id++;
		$scope.capchaUrl = BASE_URL_CAPTCHA + '?id=' + $scope.id;
	    $scope.usuario.captcha = "";
		//console.log($scope.capchaUrl);
	}; 

	$scope.refreshCaptchai = function() {
		$scope.id++;
		$scope.capchaUrl = BASE_URL_CAPTCHA + '?id=' + $scope.id;
		// $scope.usuario.captcha = "x";
		//console.log($scope.capchaUrl);
	}; 
	
	$scope.downloadFile = function() {

		/*var arrayarchivo = $scope.request.codigoarchivo
				.split('.');
		var name = arrayarchivo[0];
		var fileType = arrayarchivo[1];*/
		var fileType = "pdf";
		loginService.exportManualExterno().then(
				function(response) {
					
					var a = document.createElement("a");
					document.body.appendChild(a);
					a.style = "display: none";
					// return function (data, fileName) {
					// var json = JSON.stringify(data),
					// blob = new Blob([json], {type:
					// "octet/stream"}),
					var url = window.URL
							.createObjectURL(response.data);
					a.href = url;
					a.download =  "Manual_Externo" + ".pdf";
					a.click();
					
					//debugger;
					/*if (fileType == "png")
						fileType = "image/png"
					else if (fileType == "jpeg")
						fileType = "image/jpeg"
					else  
						fileType = $rootScope
								.applicationType(fileType);*/
					/*var file = new Blob([ (response.data) ], {
						type :  $rootScope
						.applicationType(fileType)
					});
					var fileURL = URL.createObjectURL(file);
					const url = window.URL
							.createObjectURL(file);
					window.open(url);*/
				});
	}
	
	$scope.login = function() {
		var model = {
			user : $scope.usuario.username,
			password : $scope.usuario.password,
			captcha : $scope.usuario.captcha
		};
		// console.log(model);
		loginService
				.Login(model)
				.then(
						function(response) {
							//   
							var valido = false;
							var mensaje = "";
							if (response.data.ejecucion_procedimiento == true) {

								if (response.data.rechazar == false) {
									valido = true;
									var token = response.data.objeto.body.token;
									localStorage.setItem("accessToken", token);
									$window.location.href = "index.html#!/";
								}
							}
							if (!valido) {
								if (response.data.mensaje_salida != null
										&& response.data.mensaje_salida != "") {
									mensaje = response.data.mensaje_salida;
								} else if (response.data.objeto.length > 0) {
									for (var i = 0; i < response.data.objeto.length; i++) {
										mensaje += response.data.objeto[i].message
												+ "\r\n";
									}
								}
								$ngBootbox
										.alert(
												{
													size : "small",
													title : "Mensaje",
													message : mensaje,
													buttons : {
														ok : {
															label : '<i class="fa fa-check"></i> Aceptar',
															className : 'btn-default'
														}
													}
												}).then(function() {
											$scope.usuario.password = "";
											$scope.refreshCaptcha();
										});
							}
						});
	};

	$scope.probarAPI = function() {
		loginService
				.prueba2()
				.then(
						function(response) {
							var valido = false;
							var mensaje = "";
							if (response.data.ejecucion_procedimiento == true) {
								if (response.data.rechazar == false) {
									valido = true;
									console.log(response.data.objeto);
								}
							}
							if (!valido) {
								if (response.data.mensaje_salida != null
										&& response.data.mensaje_salida != "") {
									mensaje = response.data.mensaje_salida;
								} else if (response.data.objeto.length > 0) {
									for (var i = 0; i < response.data.objeto.length; i++) {
										mensaje += response.data.objeto[i].message
												+ "\r\n";
									}
								}
								$ngBootbox
										.alert(
												{
													size : "small",
													title : "Mensaje",
													message : mensaje,
													buttons : {
														ok : {
															label : '<i class="fa fa-check"></i> Aceptar',
															className : 'btn-default'
														}
													}
												}).then(function() {

										});
							}
						});
	}
	// CONTROLLER MODAL
	/*
	 * MODAL REGISTRAR USUARIO NUEVO
	 */

	$scope.openModalUsuarioRegistrar = function(size) {

		var modalInstance = $uibModal.open({
			animation : true,
			ariaLabelledBy : 'modal-title',
			ariaDescribedBy : 'modal-body',
			templateUrl : 'pages/login/usuario-nuevo/usuarionuevo.html',
			controller : 'usuarionuevoModalInstaceController',
			controllerAs : 'usuarionuevoModalInstace',
			size : size,
			backdrop : 'static',
			scope : $scope,
			resolve : {
				ubigeo : function() {			
					return transversalService.getUbigeo().then(
							function(response) {
								return response.data.objeto == null?[]:response.data.objeto;
							});
				},
				data : function() {
					return "prueba"
				}
			}
		// resolve: {
		// data: function () {
		// return viewLogin.data;
		// }
		// }
		});
		modalInstance.result.then(function() {
			$scope.refreshCaptcha();
			 //alert("now I'll close the modal");
		});
	};
	/*
	 * MODAL RECUPERAR PASSWORD
	 */
	$scope.openModalOlvideClave = function(size) {

		$scope.P_MostrarCodigo = false;
		// gRecaptcha.initialize({key: KEY_RECHAPTCHA});
		var modalInstance = $uibModal.open({
			animation : true,
			ariaLabelledBy : 'modal-title',
			ariaDescribedBy : 'modal-body',
			templateUrl : 'pages/login/olvide-clave/olvideclave.html',
			controller : 'olvideclaveModalInstanceController',
			controllerAs : 'vm',
			size : size,
			backdrop : 'static',
			scope : $scope,
			resolve : {
				userForm : function() {
					return $scope.userForm;
				}
			}
		});
		modalInstance.result.then(function(selectedItem) {
		});
	};
	$scope.refreshCaptchai();
	$scope.refreshCaptchai();

}