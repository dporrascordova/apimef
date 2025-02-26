'use strict';
var inicio = false;
ventanillaVirtual
		.controller(
				'usuariosController',
				function($rootScope, $ngBootbox, DTOptionsBuilder, $http,
						$scope, $uibModal, personaService, transversalService,
						$log, BASE_URL) {

					var vm = this;
					
					$scope.requests = [];

					$scope.form = {};
					$scope.model = {
						personatab1 : '0',
						nombretab1 : '',
						telefonotab1 : '',
						estadotab1 : '',
						nro_documetotab1 : '',
						correotab1 : '',
						celulartab1 : '',
						direcciontab1 : '',

						personatab2 : '0',
						nombretab2 : '',
						telefonotab2 : '',
						estadotab2 : '',
						nro_documetotab2 : '',
						correotab2 : '',
						celulartab2 : '',

						personatab3 : '0',
						nombretab3 : '',
						telefonotab3 : '',
						estadotab3 : '',
						nro_documetotab3 : '',
						correotab3 : '',
						celulartab3 : '',
						estadoobstab3 : ''
					};

					$scope.Perfil = [];
					$scope.maxSize = 5;		
					$scope.itemsPerPage = 10;
					
					$scope.totalItems = 0;
					$scope.currentPage = 1;
					
					$scope.totalItemsObs = 0;
					$scope.currentPageObs = 1;
					
					$scope.totalItemsReg = 0;
					$scope.currentPageReg = 1;
					
					   $scope.litTipoDocumento = [
					        { id: "1", descripcion: 'DNI' },
					        { id: "2", descripcion: 'Carnet de Extranjería' },
					        { id: "3", descripcion: 'Pasaporte' }
					    ];


					$scope.init = function() {

						if (inicio) {
							inicio = false;
							$scope.dataUsuSolictudes = [];
							$scope.dataUsuarios = [];
							$scope.dataUsuObservadas = [];
							$scope.Persona = [];
							// $scope.searchUsuario();
							$scope.getMEFPerfil();
							$scope.getMEFPersona();
							// $scope.searchSolicitudesUsuario();
							// $scope.searchObasrevadas();

						} else {
							inicio = true;
						}
					};

					$scope.Limpiar = function(tab) {
						$scope.model = {
							personatab1 : '0',
							nombretab1 : '',
							telefonotab1 : '',
							estadotab1 : '',
							nro_documetotab1 : '',
							correotab1 : '',
							celulartab1 : '',
							direcciontab1 : '',
							personatab2 : '0',
							nombretab2 : '',
							telefonotab2 : '',
							estadotab2 : '',
							nro_documetotab2 : '',
							correotab2 : '',
							celulartab2 : '',

							personatab3 : '0',
							nombretab3 : '',
							telefonotab3 : '',
							estadotab3 : '',
							nro_documetotab3 : '',
							correotab3 : '',
							celulartab3 : '',
							estadoobstab3 : ''
						};

						if (tab == '1')
							$scope.searchSolicitudesUsuario();
						else if (tab == '2')
							$scope.searchUsuario();
						else
							$scope.searchObasrevadas();
					};

					$scope.searchUsuario = function() {
						var flg_estado = $scope.model.estadotab2;
						var valido = '0';
						if (flg_estado == undefined)
							flg_estado = "1";

						personaService.getListaUsuariosPersona($scope.model,
								flg_estado, valido,$scope.currentPage, $scope.itemsPerPage).then(function(res) {
									
							if (res.data.ejecucion_procedimiento == true) {
								$scope.dataUsuarios = res.data.objeto;
								$scope.FiltrarPersona($scope.dataUsuarios);
								
					           if($scope.dataUsuarios.length > 0)
					        	   $scope.totalItems = res.data.objeto[0].totalreg;   
					           else
					        	 	 $scope.totalItems = 0;
							}
							$scope.MostrarErrores(res);
						});
					};
					
				     $scope.pageChanged = function() {				   
				 		$scope.currentPage = this.currentPage;
				 		$scope.searchUsuario();
				 	};

					$scope.FiltrarPersona = function(LisUsuario) {
						var ListPersona = $scope.Persona;
						for (let i = 0; i < LisUsuario.length; i++) {
							const codusuario = LisUsuario[i]['cod_usuario'];
							for (let j = 0; j < ListPersona.length; j++) {
								const codpersona = ListPersona[j]['usUsername'];
								if (codpersona == codusuario) {
									ListPersona.splice(j, 1);
								}
							}
						}
					};

					$scope.searchSolicitudesUsuario = function() {
						var flg_estado = '1';
						var valido = '0';
						personaService
								.getListaSolicitudesUsuarios($scope.model,
										flg_estado, valido,$scope.currentPageReg, $scope.itemsPerPage)
								.then(
										function(res) {
											if (res.data.ejecucion_procedimiento == true) {
												$scope.dataUsuSolictudes = res.data.objeto;
												
									           if($scope.dataUsuSolictudes.length > 0)
									        	   $scope.totalItemsReg = res.data.objeto[0].totalreg;
									           else
									        	   $scope.totalItemsReg = 0;
											}
											$scope.MostrarErrores(res);
										});
					};
					
				     $scope.pageChangedReg = function() {				    	   
					 		$scope.currentPageReg = this.currentPageReg;
					 		$scope.searchSolicitudesUsuario();
					 	};

					// / tab 3

					$scope.searchObasrevadas = function() {
						var flg_estado = '0';
						personaService
								.getListaObservadas($scope.model, flg_estado,
										$scope.model.estadoobstab3,$scope.currentPageObs, $scope.itemsPerPage)
								.then(
										function(res) {
											if (res.data.ejecucion_procedimiento == true) {
												$scope.dataUsuObservadas = res.data.objeto;
												
										           if($scope.dataUsuObservadas.length > 0)
										        	   $scope.totalItemsObs = res.data.objeto[0].totalreg;
										           else
										        	    $scope.totalItemsObs = 0;
											}
											$scope.MostrarErrores(res);
										});
					};

				     $scope.pageChangedObs = function() {				    	
					 		$scope.currentPageObs = this.currentPageObs;
					 		$scope.searchObasrevadas();
					 	};

					$scope.validate = function(item) {
						var valido = 0;
						personaService
								.buscarpersona(item, valido)
								.then(
										function(response) {
											console.log(response);
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													debugger; 
													 if(response.data.objeto.tipopersona == "2"){
														 response.data.objeto.rep_legal_id_tipo_documento = response.data.objeto.rep_legal_id_tipo_documento.toString();
														 response.data.objeto.delegado_id_tipo_documento = response.data.objeto.delegado_id_tipo_documento.toString();
														 }
													let modalInstance = $uibModal
															.open({
																animation : true,
																ariaLabelledBy : 'modal-title',
																ariaDescribedBy : 'modal-body',
																templateUrl : 'pages/usuarios/view-user/view-user.html',
																controller : 'viewUserModalInstanceController',
																size : 'lg',
																backdrop : 'static',
																scope : $scope,
																resolve : {
																	request : function() {
																		return response.data.objeto;
																	},
																	ubigeo : function() {
																		return transversalService.getUbigeo().then(
																				function(response) {
																					return response.data.objeto == null?[]:response.data.objeto;
																				});
																	},
																}
															});
													modalInstance.result
															.then(function(
																	response) {
																if (response != undefined) {
																	if (response.data.ejecucion_procedimiento) {
																		$scope
																				.searchUsuario();
																		$scope
																				.searchSolicitudesUsuario();
																	}
																}
															});
												}
											} else {
												$scope.Correo = "";
												$scope.P_CorreoNoEncontrado = true;
											}
										});
					}

					$scope.openModalActionUser = function(size) {
					};

					$scope.getMEFPerfil = function() {
						personaService.getMEFPerfil().then(function(res) {
							if (res.status === 200) {
								$scope.Perfil = res.data.objeto;
								$scope.Perfil.splice(1, 1);
							}
						});
					};

					$scope.getMEFPersona = function() {
						personaService.getMEFPersonaNew().then(function(res) {
							if (res.status === 200) {
								$scope.Persona = res.data.objeto;

							}
						});
					};

					$scope.openCreate = function() {

						var modalInstance = $uibModal
								.open({
									animation : true,
									backdrop : 'static',
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/usuarios/nuevo-usuario/nuevo-usuario.html',
									controller : 'nuevoUsuarioController',
									size : 'lg',
									resolve : {
										Perfil : function() {
											return $scope.Perfil;
										},
										Persona : function() {
											return $scope.Persona;
										}
									}
								});
						modalInstance.result.then(function(response) {
							if (response != undefined) {
								if (response.data.ejecucion_procedimiento)
									$scope.searchUsuario();
							}
						}, function(error) {
						});
					};

					$scope.openUpdate = function(item) {
						var valido = parseInt(item.flg_estado);
						var fechas = {
							fec_activacion : item.fec_activacion,
							fec_desactivacion : item.fec_desactivacion
						}
						debugger; 
						personaService
								.buscarpersona(item, valido)
								.then(			
										function(response) {
											debugger; 
											//console.log(response);
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													let modalInstance = $uibModal
															.open({
																animation : true,
																backdrop : 'static',
																ariaLabelledBy : 'modal-title',
																ariaDescribedBy : 'modal-body',
																templateUrl : 'pages/usuarios/actualizar-usuario/actualizar-usuario.html',
																controller : 'actualizarUsuarioController',
																size : 'lg',
																scope : $scope,
																resolve : {
																	request : function() {
																		return response.data.objeto;
																	},
																	Fechas : function() {
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

					}

					$scope.VerObs = function(item) {
						personaService
								.buscarpersona(item, item.flg_valido)
								.then(
										function(response) {
											console.log(response);
											if (response.data.ejecucion_procedimiento == true) {
												if (response.data.rechazar == false) {
													debugger; 
													let modalInstance = $uibModal
															.open({
																animation : true,
																backdrop : 'static',
																ariaLabelledBy : 'modal-title',
																ariaDescribedBy : 'modal-body',
																templateUrl : 'pages/usuarios/observada/observada.html',
																controller : 'observadaController',
																size : 'lg',
																scope : $scope,
																resolve : {
																	request : function() {
																		// console.log(response.data.objeto);
																		return response.data.objeto;
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

																}

															});
												}
											} else {
												$scope.Correo = "";
												// $scope.P_CorreoNoEncontrado =
												// true;
											}
										});

					}

					$scope.Eliminar_Usuario = function(item) {
						$ngBootbox
								.confirm({
									message : '¿Estás seguro de eliminar?',
									buttons : {
										confirm : {
											label : 'Aceptar',
											className : 'btn-danger'
										},
										cancel : {
											label : 'Cerrar',
											className : 'btn-secondary'
										}
									}
								})
								.then(
										function() {
											personaService
													.EliminarUsuario(item)
													.then(
															function(response) {
																// var valido =
																// false;

																if (response.data.ejecucion_procedimiento) {
																	if (!response.data.rechazar) {
																		// valido
																		// =
																		// true;
																		$ngBootbox
																				.alert({
																					size : "small",
																					title : "Mensaje",
																					message : "Usuario eliminado correctamente",
																					buttons : {
																						ok : {
																							label : '<i class="fa fa-check"></i> Aceptar',
																							className : 'btn-default'
																						}
																					}
																				});
																		$scope
																				.searchUsuario();
																		$scope
																				.getMEFPersona();
																		// Si
																		// todo
																		// fue
																		// ejecutado
																		// bien
																	}

																}
																$scope
																		.MostrarErrores(response);
															});
										}, function() {
										});

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
										mensaje += "<li>"
												+ response.data.objeto[i].message
												+ "</li>";
									}
									mensaje += "</ul>"
								}
							} else {
								mensaje = response.data.mensaje_salida;
							}
							$ngBootbox
									.alert({
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

					$scope.CambiarEstado = function(item) {
						personaService
								.CambiarEstado(item)
								.then(
										function(response) {
											var valido = false;
											var mensaje = "";
											if (response.data.ejecucion_procedimiento) {
												if (!response.data.rechazar) {
													valido = true;
													$ngBootbox
															.alert({
																size : "small",
																title : "Mensaje",
																message : "Estado cambaido correctamente",
																buttons : {
																	ok : {
																		label : '<i class="fa fa-check"></i> Aceptar',
																		className : 'btn-default'
																	}
																}
															});
													$scope.searchUsuario();
													// Si todo fue ejecutado
													// bien
												}

											}
											if (!valido) {
												if (response.data.error_lista) {
													if (response.data.objeto.length > 0) {
														mensaje += "<ul>"
														for (var i = 0; i < response.data.objeto.length; i++) {
															mensaje += "<li>"
																	+ response.data.objeto[i].message
																	+ "</li>";
														}
														mensaje += "</ul>"
													}
												} else {
													mensaje = response.data.mensaje_salida;
												}
												$ngBootbox
														.alert({
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
										});
					};

					$scope.init();
					$rootScope.$on('reloadGrid', $scope.init);

				});