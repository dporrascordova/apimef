'use strict';
ventanillaVirtual
		.controller(
				'mpeController',
				function($rootScope, $scope, $uibModal, DTOptionsBuilder,
						DTColumnDefBuilder, $resource, mpeService, $log) {
					$scope.requestsData = [];
					$scope.form = {};
					$scope.model = {};
					$scope.tipoDocumento = [];
					$scope.oficinas = [];
					$scope.maxSize = 5;
					$scope.totalItems = 0;

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

					$scope.currentPage = 1;
					$scope.itemsPerPage = 10;
					$scope.dtOptions = DTOptionsBuilder.newOptions()
							.withDisplayLength($scope.itemsPerPage).withOption(
									'paging', false).withOption('bInfo', false)
							.withOption('bDestroy', true).withOption(
									'bAutoWidth', false);

					$scope.$watch('currentPage', function() {
						$scope.getSolicitudes();
					});

					$scope.searchSolicitudes = function() {
						$scope.currentPage = 1;
						$scope.totalItems = 0;
						$scope.getSolicitudes();
					};

					$scope.init = function() {
						var fechaInicio = new Date();
						fechaInicio.setDate(fechaInicio.getDate() - 30) // un
																		// mes
																		// antes
						$scope.model.fechaInicio = fechaInicio;
						$scope.model.fechaFin = new Date();

						$scope.getMEFOficinas();
						$scope.getMEFTipoDocumento();
					};

					$scope.pageChanged = function() {
						$scope.currentPage = this.currentPage;
						$scope.getSolicitudes();
					};

					$scope.getSolicitudes = function() {
						mpeService
								.getListaPaginadoDocs($scope.model,
										$scope.currentPage, $scope.itemsPerPage)
								.then(
										function(res) {
											console.log(res);
											if (res.status === 200) {

												$scope.requestsData = res.data.rows;
												$scope.totalItems = res.data.totalreg;

												$scope
														.AnularDocumento($scope.requestsData);
											}
										});
					};

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

					$scope.exportarAExcel = function() {
						mpeService.getExcel($scope.model).then(
								function(response) {

									var fec = new Date();
									// debugger;
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
									a.download = "MisSolicitudes" + "_"
											+ fec.getDay() + ""
											+ fec.getMonth() + ""
											+ fec.getFullYear() + "_"
											+ fec.getHours() + ""
											+ fec.getMinutes() + ""
											+ fec.getSeconds() + ".xlsx";
									a.click();
									window.URL.revokeObjectURL(url);

									// var file = new Blob([response.data], {
									// type: $rootScope.applicationType("xlsx")
									// });
									// window.open(window.URL.createObjectURL(file));

									// console.log(response);
									// window.open(window.URL
									// .createObjectURL(response.data));
								});
					};

					$scope.limpiar = function() {
						$scope.model = {};
						var fechaInicio = new Date();
						fechaInicio.setDate(fechaInicio.getDate() - 30) // un
																		// mes
																		// antes
						$scope.model.fechaFin = new Date();
						$scope.model.fechaInicio = fechaInicio;
						$scope.searchSolicitudes();
					};

					$scope.openCreate = function() {
						var modalInstance = $uibModal
								.open({
									animation : true,
									backdrop : 'static',
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpe/nueva-solicitud/nueva-solicitud.html',
									controller : 'nuevaSolicitudController',
									size : 'lg',
									resolve : {
										tipoDocumento : function() {
											return $scope.tipoDocumento;
										},
										oficinas : function() {
											return $scope.oficinas;
										}
									}
								});
						modalInstance.result.then(function(response) {
						}, function(error) {
						});
					};

					$scope.openEdit = function(id) {
						var modalInstance = $uibModal
								.open({
									animation : true,
									backdrop : 'static',
									ariaLabelledBy : 'modal-title',
									ariaDescribedBy : 'modal-body',
									templateUrl : 'pages/mpe/editar-solicitud/editar-solicitud.html',
									controller : 'editarSolicitudController',
									size : 'xl',
									resolve : {
										solcitudId : function() {
											return id;
										},
										tipoDocumento : function() {
											return $scope.tipoDocumento;
										},
										oficinas : function() {
											return $scope.oficinas;
										}
									}
								});
						modalInstance.result.then(function(response) {
						}, function(error) {
						});
					};

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

					$scope.init();
					$rootScope.$on('reloadGrid', function(event, data) {
						$scope.searchSolicitudes();
					});

					$scope.AnularDocumento = function(data) {

						var f = new Date();
						var fechaActual = '';
						var mes_a = (f.getMonth() + 1);
						var dias_a = (f.getDate());
						if (String(mes_a).length == 1)
							mes_a = "0" + mes_a;
						if (String(dias_a).length == 1)
							dias_a = "0" + dias_a;
						fechaActual = (dias_a + "/" + mes_a + "/" + f
								.getFullYear());

						for (let x = 0; x < data.length; x++) {
							// debugger;
							if (data[x]['fecha_anulacion'] == fechaActual
									&& data[x]['id_estado_documento'] != 6) {
								// debugger;
								var id_documento = data[x]['id_documento'];
								let model = {
									id_documento : id_documento,
									id_estado_documento : 6
								}
								mpeService.actulizarEstadoDoc(model).then(
										function(response) {
											// debugger;

										});
							}
						}

					};
				});
