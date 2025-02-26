'use strict';
var inicio = false;
ventanillaVirtual
		.controller(
				'casillaController',
				function(DTOptionsBuilder, $ngBootbox, $scope, $uibModal,
						casillaService, $log,loginService) {
					var vm = this;
					$scope.dtOptions = DTOptionsBuilder.newOptions()
							.withDisplayLength(10);

					$scope.dtOptions.withOption('fnRowCallback', function(nRow,
							aData, iDisplayIndex, iDisplayIndexFull) {
						nRow.children[0].innerText = iDisplayIndexFull + 1;
					});

					$scope.form = {};
					$scope.model2 = {
						solicitudNoti : '',
						organoDestinoNoti : '0',
						tipoDocumentoNoti : '0',
						observacion : '',
						numeroDocumentoNoti : '',
						fechaInicio : '',
						estado : '',
						Tipo_noti : '0'
					};

					$scope.model1 = {
						solicitud : '',
						organoDestino : '',
						tipoDocumento : '',
						observacion : '',
						numeroDocumento : '',
						fechaInicio : '',
						fechaFin : '',
						estadodoc : ''
					};
					$scope.tipoDocumento = [];
					$scope.tipoDocumentoNoti = [];
					$scope.oficinas = [];
					$scope.oficinasNoti = [];

					$scope.Limpiar = function(tab) {
						$scope.model2 = {
							solicitudNoti : '',
							organoDestinoNoti : '0',
							tipoDocumentoNoti : '0',
							observacion : '',
							numeroDocumentoNoti : '',
							fechaInicio : '',
							estado : '',
							Tipo_noti : '0'
						};
						$scope.model1 = {
							solicitud : '',
							organoDestino : '',
							tipoDocumento : '',
							observacion : '',
							numeroDocumento : '',
							fechaInicio : '',
							fechaFin : '',
						};

						if (tab == '1')
							$scope.search();
						else
							$scope.searchNoti();
					};

					$scope.init = function() {
						//setInterval(function() { $scope.UpdateAlertCount() },30000); // 30 seg actualiza count notificaciones			
						if (inicio) {
							inicio = false;
							$scope.getMEFOficinas();
							$scope.getMEFTipoDocumento();
							// $scope.search();
							// $scope.searchNoti();

							$scope.dataSolSolicutes = [];
							$scope.dataSolNotificaciones = [];

						} else {
							inicio = true;
						}
					};

					$scope.getMEFOficinas = function() {
						casillaService.getMEFOficinas().then(function(res) {
							if (res.status === 200) {
								$scope.oficinas = res.data.objeto;
								$scope.oficinasNoti = res.data.objeto;

							}
						});
					};

					$scope.getMEFTipoDocumento = function() {
						casillaService
								.getMEFTipoDocumento()
								.then(
										function(res) {
											if (res.status === 200) {
												$scope.tipoDocumento = res.data.objeto;
												$scope.tipoDocumentoNoti = res.data.objeto;
											}
										});
					};

					$scope.search = function() {
						casillaService.getListaSolicitudes_Documentos(
								$scope.model1).then(function(res) {
							console.log(res);
							if (res.status === 200) {
								$scope.dataSolSolicutes = res.data.objeto;
							}
						});
					};

					$scope.searchNoti = function() {
						casillaService
								.getListaNotificaciones($scope.model2)
								.then(
										function(res) {
											//console.log(res)
											if (res.data.ejecucion_procedimiento == true) {
												$scope.dataSolNotificaciones = res.data.objeto;										
												// update alerta 
												$scope.UpdateAlertCount(); 											
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

					$scope.openViewSolicitud = function(id) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpi/visualizar-solicitud/visualizar-solicitud.html',
									controller : 'visualizarsolicitudController',
									controllerAs : 'vm',
									size : 'xl',
									backdrop : 'static',
									scope : $scope,
									resolve : {
										IdSolicitud : function() {
											return id;
										}
									}
								});
						modalInstance.result.then(function(response) {
						}, function(error) {
						});
					};

					$scope.VerHtml = function(idcasilla) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/casilla/vernotificacion/vernotifiacion.html',
									controller : 'nvernotiController',
									controllerAs : 'vm',
									size : 'lg',
									backdrop : 'static',
									scope : $scope,
									resolve : {
										idcasilla : function() {
											return idcasilla;
										},
										modulo : function() {
											return 1;
										}

									}
								});
						modalInstance.result.then(function(response) {				 
							$scope.searchNoti();
						}, function(error) {
						});
					};

					$scope.exportarExcel = function() {
						casillaService.getExcel($scope.model2).then(
								function(response) {
									
									var fec = new Date();
									//debugger;
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
									a.download = "Casilla" + "_" + fec.getDay()
											+ "" + fec.getMonth() + ""
											+ fec.getFullYear() + "_"
											+ fec.getHours() + ""
											+ fec.getMinutes() + ""
											+ fec.getSeconds() + ".xlsx";
									a.click();
									window.URL.revokeObjectURL(url);
									
									//window.open(window.URL
											//.createObjectURL(response.data));
								});
					};
					
					$scope.UpdateAlertCount = function() {	
						// update alerta 
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
								}
							$scope.MostrarErrores(res);
						});			
					}; 

					$scope.init();

				});