'use strict';
var inicio = false;
ventanillaVirtual
		.controller(
				'ofiController',
				function($rootScope, DTOptionsBuilder, $ngBootbox, $scope,
						$uibModal, ofiService, $log) {
					var vm = this;
					$scope.dtOptions = DTOptionsBuilder.newOptions()
							.withDisplayLength(10);

					$scope.dtOptions.withOption('fnRowCallback', function(nRow,
							aData, iDisplayIndex, iDisplayIndexFull) {
						nRow.children[0].innerText = iDisplayIndexFull + 1;
					});
					/*
					 * vm.dtOptions = DTOptionsBuilder.newOptions()
					 * .withDisplayLength(10)
					 */

					$scope.form = {};
					$scope.model = {};
					$scope.model2 = {};
					$scope.tipoDocumento = [];
					$scope.oficinas = [];
					$scope.maxSize = 5;

					$scope.Limpiar = function() {
						$scope.model = {
							descripcion : ''

						};
						$scope.model = {
								desc_tipodocumento : ''

							};
						$scope.search();
					};

					$scope.LimpiarT = function() {
						 
						$scope.model2 = {
								desc_tipodocumento : ''

							};
						$scope.getMEFTipoDocumento();
					};
					
					$scope.init = function() {

						if (inicio) {
							inicio = false;
							$scope.search();
						} else {
							inicio = true;
						}
					};

					$scope.getMEFOficinas = function() {
						ofiService.getMEFOficinas().then(function(res) {
							if (res.status === 200) {
								$scope.oficinas = res.data.objeto;
								$scope.oficinasEnviados = res.data.objeto;
							}
						});
					};

					$scope.getMEFTipoDocumento = function() {
						ofiService.getListaTipoDocL($scope.model2).then(function(res) {
							if (res.status === 200) {
								$scope.tipoDocumento = res.data.objeto;
								$scope.tipoDocumentoEnviados = res.data.objeto;
							}
						});
					};

					$scope.search = function() {
						ofiService
								.getListaOficinas($scope.model)
								.then(
										function(res) {
											if (res.data.ejecucion_procedimiento == true) {
												$scope.dataListaOficinas = res.data.objeto;
											}
											$scope.MostrarErrores(res);
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
						ofiService
								.CambiarEstado(item)
								.then(
										function(response) {
											var valido = false;
											var mensaje = "";
											// console.log(response);
											if (response.data.ejecucion_procedimiento) {
												if (!response.data.rechazar) {
													valido = true;
													$scope
															.showAlert({
																message : 'El estado se atualiz&oacute; correctamente'
															}); 
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
												$scope.showAlert({
													message : mensaje
												}); 
											}
										});
					};

					$scope.CambiarEstadoT = function(item) {
						ofiService
								.CambiarEstadoTipoDoc(item)
								.then(
										function(response) {
											var valido = false;
											var mensaje = "";
											// console.log(response);
											if (response.data.ejecucion_procedimiento) {
												if (!response.data.rechazar) {
													valido = true;
													$scope
															.showAlert({
																message : 'El estado se atualiz&oacute; correctamente'
															}); 
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
												$scope.showAlert({
													message : mensaje
												}); 
											}
										});
					};

					
					$scope.init();
				});