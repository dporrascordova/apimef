'use strict';
ventanillaVirtual
		.controller(
				'mpiController',
				function($scope, $uibModal, DTOptionsBuilder,
						DTColumnDefBuilder, $resource, mpeService, $log,$ngBootbox,toaster,mpiService) {
					var vm = this;
					// vm.dtOptions = DTOptionsBuilder.newOptions()
					// .withDisplayLength(10);

					$scope.activeTab = 1;
					$scope.maxSize = 5;
					$scope.maxMonthsPerReport = 3;
					
					$scope.currentPagePendiente = 1;
					$scope.currentPageRecibida = 1;
					$scope.currentPageObservada = 1;
					$scope.currentPageFinalizada = 1;
					$scope.currentPageAnulado = 1;
					$scope.itemsPerPage = 10;

					$scope.orden = [ {
						valor : 'T.ID_DOCUMENTO DESC',
						descripcion : 'Nro. Solicitud'
					}, {
						valor : 'T.NRO_DOCUMENTO ASC',
						descripcion : 'Nro. Documento'
					}, {
						valor : 'T.DESC_OFICINA ASC',
						descripcion : 'Órgano Destino'
					}, {
						valor : 'T.NOM_PERSONA ASC',
						descripcion : 'Remitente'
					} ]

					$scope.pageChanged = function() {
						switch ($scope.activeTab) {
						case 1:
							$scope.currentPagePendiente = this.currentPagePendiente;
							$scope.searchSolPendiente();
							break;
						case 2:
							$scope.currentPageRecibida = this.currentPageRecibida;
							$scope.searchSolRecibida();
							break;
						case 3:
							$scope.currentPageObservada = this.currentPageObservada;
							$scope.searchSolObservada();
							break;
						case 4:
							$scope.currentPageFinalizada = this.currentPageFinalizada;
							$scope.searchSolFinalizados();
							break;

						default:
							$scope.currentPageAnulado = this.currentPageAnulado;
							$scope.searchSolAnulado();
							break;
						}
					};

					$scope.OnInit = function() {
						// $scope.getMEFOficinas();
						$scope.modelPendiente = {};
						$scope.modelRecibida = {};
						$scope.modelObservada = {};
						$scope.modelfinalizados = {};
						$scope.modelAtendidos = {};
						$scope.modelAnulados = {};

						$scope.dataSolPendiente = [];
						$scope.dataSolRecibida = [];
						$scope.dataSolObservada = [];
						$scope.dataSolAtentidos = [];
						$scope.dataSolFinalizados = [];
						$scope.dataSolAnulados = [];

						$scope.getMEFOficinas();
						$scope.getMEFTipoDocumento();
						$scope.documentSettings();
					}
					
					$scope.documentSettings = function(){
						mpeService.getDocumentSettings().then(function (response) {
						    if( response.data.ejecucion_procedimiento)
						    	$scope.maxMonthsPerReport = response.data.objeto.months_per_report;
						    	
						    console.log(response.data.objeto);
						});
					}

					$scope.getMEFOficinas = function() {
						mpeService.getMEFOficinas().then(function(res) {
							if (res.status === 200) {
								$scope.oficinas = res.data.objeto;
							}
						});
					};

					$scope.getMEFTipoDocumento = function() {
						mpeService.getMEFTipoDocumento().then(function(res) {
							if (res.status === 200) {
								$scope.tipoDocumento = res.data.objeto;
							}
						});
					};

					$scope.searchSolPendiente = function() {
						$scope.activeTab = 1;
						mpeService
								.getListaPaginadoDocs($scope.modelPendiente,
										$scope.currentPagePendiente,
										$scope.itemsPerPage, 'TAB_PENDIENTE')
								.then(
										function(res) {
											if (res.status === 200) {
												console.log(res.data.rows);
												$scope.dataSolPendiente = res.data.rows;
												$scope.totalItemsPendiente = res.data.totalreg;
											}
										});
					};

					$scope.searchSolRecibida = function() {
						$scope.activeTab = 2;
						$scope.modelRecibida.estado = 3;
						mpeService
								.getListaPaginadoDocs($scope.modelRecibida,
										$scope.currentPageRecibida,
										$scope.itemsPerPage, 'TAB_RECIBIDO')
								.then(
										function(res) {
											if (res.status === 200) {
												$scope.dataSolRecibida = res.data.rows;
												$scope.totalItemsRecibida = res.data.totalreg;
											}
										});
					};

					$scope.searchSolAtendidos = function() {
						$scope.modelAtendidos.estado = 3;
						mpeService.getListaDocs($scope.modelAtendidos,
								'TAB_RECIBIDO').then(function(res) {
							$scope.dataSolAtentidos = res.data;
						});
					};

					$scope.searchSolFinalizados = function() {
						$scope.activeTab = 4;
						$scope.modelfinalizados.estado = 5;
						mpeService
								.getListaPaginadoDocs($scope.modelfinalizados,
										$scope.currentPageFinalizada,
										$scope.itemsPerPage, 'TAB_FINALIZADO')
								.then(
										function(res) {
											if (res.status === 200) {
												$scope.dataSolFinalizados = res.data.rows;
												$scope.totalItemsFinalizados = res.data.totalreg;
											}
										});
					};

					$scope.searchSolObservada = function() {
						$scope.activeTab = 3;
						$scope.modelObservada.estado = 2;
						mpeService
								.getListaPaginadoDocs($scope.modelObservada,
										$scope.currentPageObservada,
										$scope.itemsPerPage, 'TAB_OBSERVADO')
								.then(
										function(res) {
											if (res.status === 200) {
												$scope.dataSolObservada = res.data.rows;
												$scope.totalItemsObservada = res.data.totalreg;
											}
										});
					};

					$scope.searchSolAnulado = function() {
						$scope.activeTab = 5;
						$scope.modelAnulados.estado = 6;
						debugger;
						mpeService.getListaPaginadoDocs($scope.modelAnulados,
								$scope.currentPageAnulado, $scope.itemsPerPage,
								'TAB_ANULADO').then(function(res) {
							debugger;
							if (res.status === 200) {
								$scope.dataSolAnulados = res.data.rows;
								$scope.totalItemsAnulados = res.data.totalreg;
							}
						});
					};

					$scope.limpiar = function(tipo) {
						switch (tipo) {
						case 1:
							$scope.modelPendiente = {};
							$scope.currentPagePendiente = 1;
							$scope.searchSolPendiente();
							break;
						case 2:
							$scope.modelRecibida = {};
							$scope.currentPageRecibida = 1;
							$scope.searchSolRecibida();
							break;
						case 3:
							$scope.modelObservada = {};
							$scope.currentPageObservada = 1;
							$scope.searchSolObservada();
							break;

						case 4:
							$scope.modelfinalizados = {};
							$scope.currentPageFinalizada = 1;
							$scope.searchSolFinalizados();
							break;
						default:
							$scope.modelAnulados = {};
							$scope.currentPageAnulado = 1;
							$scope.searchSolAnulado();
							break;
						}
					};

					$scope.exportarAExcel = function(tipo) {
						let model, grupo, nombre;
						switch (tipo) {
						case 1:
							model = $scope.modelPendiente;		
							grupo = 'TAB_PENDIENTE';
							nombre = 'Pendientes';
							break;
						case 2:
							model = $scope.modelRecibida;
							grupo = 'TAB_RECIBIDO';
							nombre = 'Recibidos';
							break;
						case 3:
							model = $scope.modelObservada;
							grupo = 'TAB_OBSERVADO';
							nombre = 'Observados';
							break;
						case 4:
							model = $scope.modelfinalizados;
							grupo = 'TAB_FINALIZADO';
							nombre = 'Finalizados';
							break;

						default:
							model = $scope.modelAnulados;
							grupo = 'TAB_ANULADO';
							nombre = 'Anulados';
							break;
						}
						
						var isDateStart = $scope.dateIsNullOrUndefined(model.fechaInicio),
						    isDateEnd = $scope.dateIsNullOrUndefined(model.fechaFin);
						
						var message = isDateStart && isDateEnd ? 'Debe de seleccionar el rango de fechas (inicio y fin) para generar el reporte.'
								: isDateStart ?  'Debe de seleccionar la fecha de inicio para generar el reporte.'
										:isDateEnd ?  'Debe de seleccionar la fecha fin para generar el reporte.':'';
						
						if(message.length == 0 && $scope.monthDiff(model.fechaInicio,model.fechaFin) > $scope.maxMonthsPerReport)
							message = `La selección del rango de fecha no debe superar los ${$scope.maxMonthsPerReport} meses.`;

						if(message.length>0){
							$scope.showAlert({
						        message:message
						      });
							return false;
						}
						
						console.log('Download Excel',model);
						
						mpeService.getExcel(model, grupo, nombre).then(
								function(response) {
									var fec = new Date();

									var a = document.createElement("a");
									document.body.appendChild(a);
									a.style = "display: none";
									var url = window.URL
											.createObjectURL(response.data);
									a.href = url;
									a.download = nombre + "_" + fec.getDay()
											+ "" + fec.getMonth() + ""
											+ fec.getFullYear() + "_"
											+ fec.getHours() + ""
											+ fec.getMinutes() + ""
											+ fec.getSeconds() + ".xlsx";
									a.click();
									window.URL.revokeObjectURL(url);
								});
					};

					/*
					 * MODALES
					 */

					/* VISUALIZAR SOLICITUD */
					$scope.openModalVisualizarSolicitud = function(data, size) {
						// debugger
						var modalInstance = $uibModal
								.open({
									animation : true,
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpi/visualizar-solicitud/visualizar-solicitud.html',
									controller : 'visualizarsolicitudController',
									controllerAs : 'vm',
									size : size,
									backdrop : 'static',
									scope : $scope,
									resolve : {
										IdSolicitud : function() {
											return data;
										}
									}
								});
						modalInstance.result.then(function() {
							// alert("now I'll close the modal");
						});
					};

					/* VALIDAR SOLICITUD */
					$scope.openModalValidarSolicitud = function(data, size) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpi/validar-solicitud/validar-solicitud.html',
									controller : 'validarsolicitudController',
									controllerAs : 'vm',
									size : size,
									backdrop : 'static',
									scope : $scope,
									resolve : {
										IdSolicitud : function() {
											return data;
										}
									}
								});
						modalInstance.result.then(function(transferData) {
							// alert("now I'll close the modal");
							//if (transferData.status) {
								$scope.searchSolPendiente();
							//}
						});
					};
					
					$scope.desasignarSolicitud = function(idSolicitud) {
						$ngBootbox
				          .confirm({
				            message: '¿Está seguro de realizar la desasignación de la solicitud?',
				            buttons: {
				              confirm: {
				                label: '<i class="fa fa-check"></i> Aceptar',
				                className: 'btn-primary',
				              },
				              cancel: {
				                label: 'Cancelar',
				                className: 'btn-light-secondary',
				              },
				            },
				          })
				          .then(
				            function () {
				            	  mpiService.desasignarSolicitud(idSolicitud).then(function (res) {				                      
				                      var type = res.data.ejecucion_procedimiento && res.data.rechazar?'warning':'success';
				                      				                      
				                      toaster.pop({
						                     type: type,
						                     title: 'Mensaje',
						                     body:res.data.mensaje_salida,
						                     timeout: 3000,
						                     showCloseButton: true,
						                     progressBar: true,
						                   });
				                      
				                      if(!(res.data.ejecucion_procedimiento && res.data.rechazar))
				                    	  $scope.searchSolPendiente();
				            	  });
				            	
				            	 
				            },
				            function () {},
				          );
					};
					
					/* VISUALIZAR MOVIMIENTO */
					$scope.openModalVisualizarMovimientos = function(data, size) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpi/visualizar-movimientos-std/visualizar-movimientos-std.html',
									controller : 'visualizarmovimientosstdController',
									controllerAs : 'vm',
									size : size,
									backdrop : 'static',
									scope : $scope,
									resolve : {
										IdSolicitud : function() {
											return data;
										}
									}
								});
						modalInstance.result.then(function() {
							// alert("now I'll close the modal");
						});
					};

					/* FINALIZAR */
					$scope.Finalizar = function(idcoumento) {
						// debugger
						var modalInstance = $uibModal.open({
							animation : true,
							ariaLabelledBy : 'modal-title',
							ariaDescribedBy : 'modal-body',
							templateUrl : 'pages/mpi/finalizar/finalizar.html',
							controller : 'finalizarController',
							controllerAs : 'vm',
							size : 'lg',
							backdrop : 'static',
							scope : $scope,
							resolve : {
								idcoumento : function() {
									return idcoumento;
								}
							}
						});
						modalInstance.result.then(function() {
							// alert("now I'll close the modal");
						});
					};

					$scope.showObservaciones = function(solcitudId, size) {
						debugger;
						var solcitudId = solcitudId;
						var modalInstance = $uibModal
								.open({
									animation : true,
									backdrop : 'static',
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpe/editar-solicitud/mostar-observaciones.html',
									controller : 'mostrarObservacionesController',
									size : 'lg',
									resolve : {
										solcitudId : function() {
											return solcitudId;
										}
									}
								});
						modalInstance.result.then(function(response) {
						}, function(error) {
						});
					};

					/* Init Page */
					$scope.OnInit();
				});
