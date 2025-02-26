'use strict';
ventanillaVirtual
		.controller(
				'actualizarDatosController',
				function($scope, $rootScope, $uibModalInstance, $ngBootbox,
						personaService, transversalService, $http, BASE_URL,
						request, Fechas, $filter, ubigeo, item) {					

					$scope.listDepartamentos = $rootScope.getDepartamentos(ubigeo);	
					
					$scope.listProvincias = $rootScope.isNullOrEmpty(request.iddepartamento) ? []
						: $rootScope.getProvincias(ubigeo, request.iddepartamento);

					$scope.listDistrito = $rootScope.isNullOrEmpty(request.idprovincia) ? []
						: $rootScope.getDistritos(ubigeo,request.iddepartamento,request.idprovincia);
					
					$scope.showExtraFields = true;
					$scope.request = request || {};
					$scope.Fechas = Fechas;
					$scope.item = item;
					$scope.comments = '';
					
					$scope.isPeruvian = !(request.tipopersona=== '1' && request.tipodoc!= 1);
	
					var valuesStart = Fechas.fec_activacion.split("/");
					var valuesEnd = Fechas.fec_desactivacion.split("/");
					var dateStart = new Date(valuesStart[2],
							(valuesStart[1] - 1), valuesStart[0]);
					var dateEnd = new Date(valuesEnd[2], (valuesEnd[1] - 1),
							valuesEnd[0]);

					$scope.fechasmodel = {
						fec_activa : dateStart,
						fec_inactiva : dateEnd
					}
					
					   $scope.litTipoDocumento = [
					        { id: "1", descripcion: 'DNI' },
					        { id: "2", descripcion: 'Carnet de Extranjería' },
					        { id: "3", descripcion: 'Pasaporte' }
					    ];
					

					$scope.onSelectChangeDepartamento = function() {
						$scope.listDistrito = [];
						$scope.listProvincias = [];						
						$scope.request.idprovincia= '';
						$scope.request.iddistrito = '';						
						$scope.listProvincias = $rootScope.getProvincias(
								ubigeo, $scope.request.iddepartamento);
					}					

					$scope.onSelectChangeProvincia = function() {
						$scope.listDistrito = [];
						$scope.request.iddistrito = '';	
						$scope.listDistrito = $rootScope.getDistritos(ubigeo,
								$scope.request.iddepartamento,
								$scope.request.idprovincia);
					}
					var fileToUpload;
					$scope.UFile_DNI_handle = function() {

						if (event.target.files.length == 0) {
							event.target.value = '';
							$scope.persona.fileArchivo = null;
							return false;
						}
						
				    	if (event.target.files[0].size ==0) {
				            $scope.showAlert({
				          	  message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat(event.target.files[0].size)})`
				            });
				            event.target.value = '';
				            $scope.persona.fileArchivo=null;
				            return false;
				          }

						this.fileToUpload = event.target.files;
						$scope.fileToUpload = this.fileToUpload;
						// console.log(this.fileToUpload[0]);
						if (!validateExtensionFile(this.fileToUpload[0].name)) {
							$ngBootbox
									.alert({
										size : "small",
										title : "Mensaje",
										message : "Formato no válido: solo es posible agregar extensiones de tipo (PNG, JPG, JPEG y PDF)",
										buttons : {
											ok : {
												label : '<i class="fa fa-check"></i> Aceptar',
												className : 'btn-default'
											}
										}
									});
							// event.reset;
							event.target.value = '';
							$scope.persona.fileArchivo = null;

							return false;
						}
						if (!validateSizeFile(this.fileToUpload[0].size)) {
							$ngBootbox
									.alert({
										size : "small",
										title : "Mensaje",
										message : "El archivo debe tener un peso máximo de 10Mb.",
										buttons : {
											ok : {
												label : '<i class="fa fa-check"></i> Aceptar',
												className : 'btn-default'
											}
										}
									});
							// event.reset;
							event.target.value = '';
							$scope.persona.fileArchivo = null;
							return false;
						}
					}
					function validateSizeFile(name) {
						var fileSize = name;
						if (fileSize < 52428800) {
							return true;
						} else {
							return false;
						}
					}
					function validateExtensionFile(name) {
						var ext = name.substring(name.lastIndexOf('.') + 1);
						if (ext.toLowerCase() == 'png'
								|| ext.toLowerCase() == 'jpg'
								|| ext.toLowerCase() == 'jpeg'
								|| ext.toLowerCase() == 'pdf') {
							return true;
						} else {
							return false;
						}
					}
				
					$scope.submitForm = function() {
						var valido = false;
						var mensaje = "";
						var myFileSelected = "";
						if ($scope.fileToUpload != undefined)
							// console.log($scope.fileToUpload);
							myFileSelected = $scope.fileToUpload[0];

						// if (1 = 1) {

						// if($scope.Validarvechas(fechas))
						{
							$ngBootbox
									.confirm(
											{
												message : '¿Estás seguro de actualizar?',
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
							            		if (!$scope.isPeruvian) {
							            			$scope.request.iddepartamento = '0';
							            			$scope.request.idprovincia = '0';
							            			$scope.request.iddistrito = '0'
							            		}
							            														
												personaService.persona_actualizar($scope.request,myFileSelected).then(function(response) {
																	if (response.data.ejecucion_procedimiento) {
																		if (!response.data.rechazar) {
																			valido = true;
																			$ngBootbox
																					.alert({
																						type : "success",
																						size : "small",
																						title : "Mensaje",
																						message : "Datos actualizados correctamente",
																						buttons : {
																							ok : {
																								label : '<i class="fa fa-check"></i> Aceptar',
																								className : 'btn-default'
																							}
																						}
																					});
																			$scope.response = response;
																			$scope
																					.close();
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
						}
					};

					$scope.close = function() {
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
