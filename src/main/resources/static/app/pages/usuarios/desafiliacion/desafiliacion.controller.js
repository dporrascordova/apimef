'use strict';
ventanillaVirtual
		.controller(
				'desafiliacionController',
				function($scope, $rootScope, $uibModalInstance, $ngBootbox,
						personaService, $http, BASE_URL, request, Fechas,
						$filter, item) {

					$scope.Mifecha = new Date()
					$scope.showExtraFields = true;
					$scope.request = request;
					$scope.Fechas = Fechas;
					$scope.item = item;
					$scope.comments = '';

					var valuesStart = Fechas.fec_activacion.split("/");
					var valuesEnd = Fechas.fec_desactivacion.split("/");
					var dateStart = new Date(valuesStart[2],
							(valuesStart[1] - 1), valuesStart[0]);
					var dateEnd = new Date(valuesEnd[2], (valuesEnd[1] - 1),
							valuesEnd[0]);

					$scope.fechasmodel = {
						fec_activa : dateStart,
						fec_inactiva : dateEnd
					};

					$scope.submitForm = function() {
						var valido = false;
						var mensaje = "";
						console.log($scope.request);

						var items = {
							id_usuario : $scope.item.id_usuario,
							flg_estado : 0
						}
						
						//console.log($scope.item);
						console.log(items);
						// {
						$ngBootbox
								.confirm(
										{
											message : '¿Estás seguro de desafiliarse del sistema, en caso acepte, su sesión sera cerrada automáticamente y quedará fuera del sistema?',
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
													.CambiarEstado(items)
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
																					message : "Muchas gracias por usar el sistema de ventanilla electrónica",
																					buttons : {
																						ok : {
																							label : '<i class="fa fa-check"></i> Aceptar',
																							className : 'btn-default'
																						}
																					}
																				});

																		localStorage
																				.clear();
																		$window.location.href = "login.html#!";
																		// $scope
																		// .searchUsuario();
																		// Si
																		// todo
																		// fue
																		// ejecutado
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

										}, function() {
										});
						// }

					};

					$scope.close = function() {
						// $uibModalInstance.dismiss('cancel');
						$uibModalInstance.close($scope.response);
					};

					$scope.Validarvechas = function(fechas) {
						var valido = true;
						var valuesStart = fechas.fec_acti.split("/");
						var valuesEnd = fechas.fec_desa.split("/");
						var dateStart = new Date(valuesStart[2],
								(valuesStart[1] - 1), valuesStart[0]);
						var dateEnd = new Date(valuesEnd[2],
								(valuesEnd[1] - 1), valuesEnd[0]);
						if (dateStart >= dateEnd) {
							valido = false;
						}
						return valido;
					};

					$scope.downloadFile = function() {

						var arrayarchivo = $scope.request.codigoarchivo
								.split('.');
						var name = arrayarchivo[0];
						var fileType = arrayarchivo[1];
						personaService.exportFile(fileType, name).then(
								function(response) {
									var file = new Blob([ (response.data) ], {
										type : $rootScope
												.applicationType(fileType)
									});
									var fileURL = URL.createObjectURL(file);
									const url = window.URL
											.createObjectURL(file);
									window.open(url);
								});
					}

				});
