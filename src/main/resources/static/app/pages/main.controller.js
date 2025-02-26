'use strict';
ventanillaVirtual
		.controller(
				'mainController',
				function($scope, $rootScope, $uibModal, toaster, $window,
						$interval, jwtHelper, loginService, personaService,transversalService) {
					/*
					 * VERIFICA LA AUTENCICACION DEL USUARIO
					 */
					$scope.checkSession = function() {
						if (localStorage.getItem("accessToken")) {
							var currentUser = $scope.currentUser;
							var fecha = new Date(currentUser.exp * 1000);
							var expires = moment(fecha).format();
							var now = moment(Date.now()).format();
							// console.log(currentUser);
							if (expires < now) {
								toaster
										.pop({
											type : 'warning',
											title : 'Mensaje',
											body : 'No está autorizado a ver esta página o su conexión ha caducado. Por favor, pruebe a identificarse de nuevo usando el formulario inferior...',
											timeout : 2000,
											showCloseButton : true,
											progressBar : true,
										});
								$scope.logout();
							}
						} else {
							$scope.logout();
						}
					}
					$scope.logout = function() {
						// var objects = storagesData.obtenerTodosCookies();
						// var keyNames = Object.keys(objects);
						// $.each(keyNames, function (i, key) {
						// storagesData.eliminarItemDeCookies(key);
						// });
						localStorage.clear();
						$window.location.href = "login.html#!";
					}
					$scope.getToken = function() {
						return localStorage.getItem("accessToken");
					}
					/*
					 * MODAL UPDATE PASSWORD
					 */
					$scope.openModalUpdatePassword = function(size) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/login/update-password/updatepassword.html',
									controller : 'updatepasswordModalInstanceController',
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
						modalInstance.result.then(function() {
							// alert("now I'll close the modal");
						});
					};

					$scope.openModalDesafiliar = function(size) {
						/*
						 * $scope.currentUser = jwtHelper
						 * .decodeToken(localStorage .getItem("accessToken"));
						 */
						console.log($scope.currentUser);

						var valido = 1;
						// return
						// $http.get(`${BASE_URL}${endpoint}/${modelo.id_persona}/${modelo.id_tipo_persona}/${valido}`);
						var item = {
							id_persona : $scope.currentUser.PersonaId,
							id_tipo_persona : $scope.currentUser.TipoPersonaId,
							id_usuario : $scope.currentUser.UsuarioId
						}
						/*
						 * var valido = 1; var fechas = { fec_activacion:
						 * item.fec_activacion, fec_desactivacion:
						 * item.fec_desactivacion }
						 */
						var fechas = {
							fec_activacion : '',
							fec_desactivacion : ''
						}
						personaService
								.buscarpersona(item, valido)
								.then(
										function(response) {
											console.log(response);
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													let modalInstance = $uibModal
															.open({
																animation : true,
																backdrop : 'static',
																ariaLabelledBy : 'modal-title',
																ariaDescribedBy : 'modal-body',
																templateUrl : 'pages/usuarios/desafiliacion/desafiliacion.html',
																controller : 'desafiliacionController',
																size : 'lg',
																scope : $scope,
																resolve : {
																	request : function() {
																		// console.log(response.data.objeto);
																		return response.data.objeto;
																	},
																	Fechas : function() {
																		// console.log(response.data.objeto);
																		return fechas;
																	},

																	item : function() {
																		// console.log(response.data.objeto);
																		return item;
																	}
																}
															});
													modalInstance.result
															.then(function(
																	response) {
																if (response != undefined) {
																	if (response.data.ejecucion_procedimiento)
																		$scope
																				.searchUsuario();
																}

															});
												}
											} else {
												$scope.Correo = "";
												// $scope.P_CorreoNoEncontrado =
												// true;
											}
										}); 
					};
					
					$scope.openModalUpdatePassword2 = function(size) {
						/*
						 * $scope.currentUser = jwtHelper
						 * .decodeToken(localStorage .getItem("accessToken"));
						 */
						console.log($scope.currentUser);

						var valido = 1;
						// return
						// $http.get(`${BASE_URL}${endpoint}/${modelo.id_persona}/${modelo.id_tipo_persona}/${valido}`);
						var item = {
							id_persona : $scope.currentUser.PersonaId,
							id_tipo_persona : $scope.currentUser.TipoPersonaId
						}
						/*
						 * var valido = 1; var fechas = { fec_activacion:
						 * item.fec_activacion, fec_desactivacion:
						 * item.fec_desactivacion }
						 */
						var fechas = {
							fec_activacion : '',
							fec_desactivacion : ''
						}
						personaService
								.buscarpersona(item, valido)
								.then(
										function(response) {
											
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													
													 if(response.data.objeto.tipopersona == "2"){
													 response.data.objeto.rep_legal_id_tipo_documento = response.data.objeto.rep_legal_id_tipo_documento.toString();
													 response.data.objeto.delegado_id_tipo_documento = response.data.objeto.delegado_id_tipo_documento.toString();
													 }
													 
													let modalInstance = $uibModal
										                  .open({
																animation : true,
																backdrop : 'static',
																ariaLabelledBy : 'modal-title',
																ariaDescribedBy : 'modal-body',
																templateUrl : 'pages/usuarios/actualizar-datos/actualizar-datos.html',
																controller : 'actualizarDatosController',
																size : 'lg',
																scope : $scope,
																resolve : {
																	ubigeo : function() {
																		return transversalService.getUbigeo().then(
																				function(response) {
																					return response.data.objeto == null?[]:response.data.objeto;
																				});
																	},
																	request : function() {
																		// console.log(response.data.objeto);
																		
																		return response.data.objeto;
																	},
																	Fechas : function() {
																		// console.log(response.data.objeto);
																		return fechas;
																	},

																	item : function() {
																		return item;
																	}
																}
															});
													modalInstance.result
															.then(function(
																	response) {
																if (response != undefined) {
																	if (response.data.ejecucion_procedimiento)
																		$scope
																				.searchUsuario();
																}

															});
												}
											} else {
												$scope.Correo = "";
												// $scope.P_CorreoNoEncontrado =
												// true;
											}
										});
 					};
					/*
					 * VERIFICA SI REQUIERE CAMBIO DE CLAVE
					 */
					$scope.checkverificarCambioClave = function() {
						var usuario = {
							UId_Usuario : $scope.currentUser.UsuarioId
						};
						loginService
								.verificarCambioClave(usuario)
								.then(
										function(response) {
											// debugger;
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													if (response.data.objeto == 1) {
														// SHOW MODAL UPDATE
														// PASSWORD
														$scope
																.openModalUpdatePassword();
													}
												}
											}
										});
					};
					/*
					 * OBTENER MÓDULOS
					 */
					$scope.generarMenu = function() {
						var usuario = {
							UId_Usuario : $scope.currentUser.UsuarioId
						};
						 debugger;
						loginService
								.mismodulos(usuario)
								.then(
										function(response) {
											 debugger;
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													var currentUser = jwtHelper
															.decodeToken($scope
																	.getToken()), originalPath = localStorage
															.getItem("originalPath");
													localStorage
															.setItem(
																	"Menu",
																	response.data.objeto);
													$scope.MenuUser = response.data.objeto;

													if (originalPath !== '/' && originalPath !== null)
														$window.location.href = "index.html#!"
																+ originalPath;
													else if (currentUser.TipoUsuario == 1)
														$window.location.href = "index.html#!/mesa-partes-interna";
													else
														$window.location.href = "index.html#!/mesa-partes-externa";

													// $window.location.href =
													// "#!" +
													// $scope.MenuUser[0].id_a;
												}
											}
										});
					};
					
					/*actualizar alerta notificacion*/
					setInterval(function() { 
						var usuario = {
								UId_Usuario : $scope.currentUser.UsuarioId
							};											
						loginService.alerta_count(usuario).then(function(res) {
							if (res.data.ejecucion_procedimiento == true) {
		                        var Alert = document.getElementsByClassName("Show_Alert"); 
								for(var i = 0; i < Alert.length; i++)
								{														   
						         var  _this = Alert[i];
							    	for(var j = 0; j < res.data.objeto.length; j++)
									 {
							    		if(_this.id == "Alert_"+res.data.objeto[j].id_sistema_modulo ){
							    			_this.innerText =  res.data.objeto[j].count_alera; 			    			
							    		 }
									   }
							    		    	
							     	}
								}else{ 
									$scope.MostrarErrores(res);						
								}				
						});		
					},300000); // 1 min 
					
					/*
					 * INIT()
					 */
					if (localStorage.getItem("accessToken")) {
						try {
							$scope.currentUser = jwtHelper
									.decodeToken(localStorage
											.getItem("accessToken"))
											
							$scope.UOficina = $scope.currentUser.Oficina;
							$scope.UNombres = $scope.currentUser.Nombres;
							$scope.UTipoUsuario = $scope.currentUser.TipoUsuario;
							
							$scope.checkverificarCambioClave();
							$scope.checkSession();
							$scope.generarMenu();
							$interval(function() {
							
								$scope.checkSession();
								// $scope.checkverificarCambioClave()
							}, 5000);
						} catch (error) {
							$scope.logout();
						}
					} else {
						$scope.logout();
					}; 
					
					$scope.MostrarErrores = function(response) {
						var mensaje = "";
						var valido = false;
						if (response.data.ejecucion_procedimiento) {
							if (!response.data.rechazar) {
								valido = true;
							}
						}
						if (!valido) {
							if (response.data.error_lista) {
								if (response.data.objeto.length > 0) {
									mensaje += "<ul>"
									for (var i = 0; i < response.data.objeto.length; i++) {
										mensaje += "<li>" + response.data.objeto[i].message
												+ "</li>";
									}
									mensaje += "</ul>"
								}
							} else {
								mensaje = response.data.mensaje_salida;
							}
							$ngBootbox.alert({
								size : "small",
								title : "Mensaje",
								message : mensaje,
								buttons : {
									ok : {
										label : '<i class="fa fa-check"></i> Aceptar',
										className : 'btn-default'
									}
								}
							});
						}
					}
					
					

				});