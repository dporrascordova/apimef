'use strict';

ventanillaVirtual
		.controller(
				'visualizarmovimientosstdController',
				function($scope, $rootScope, DTOptionsBuilder, $sce,
						$uibModalInstance, mpiService, IdSolicitud) {
					var vm = this;
					$scope.dtOptions = DTOptionsBuilder.newOptions()
							.withDisplayLength(10);

					$scope.dtOptions.withOption('fnRowCallback', function(nRow,
							aData, iDisplayIndex, iDisplayIndexFull) {
						nRow.children[0].innerText = iDisplayIndexFull + 1;
					});
					
					$scope.maxSize = 5;

					$scope.OnInit = function() {
						$scope.form = {};
						mpiService
								.getDocumentoPorId(IdSolicitud)
								.then(
										function(response) {
											console.log(response.data.objeto);
											var model = response.data.objeto;
											model.hoja_ruta = `${model.numero_sid}-${model.anio}`;
											model.file = '';
											mpiService
													.exportFile(
															model.id_documento,
															'pdf',
															model.codigo_archivo)
													.then(
															function(responses) {
																var file = new Blob(
																		[ (responses.data) ],
																		{
																			type : $rootScope
																					.applicationType('pdf')
																		});
																// $scope.documento.file
																// =
																// $sce.trustAsResourceUrl(URL.createObjectURL(file));
																model.file = $sce
																		.trustAsResourceUrl(URL
																				.createObjectURL(file));
															});
											$scope.movimientosSTD = [];
											if (model.anio > 0
													&& model.numero_sid != null) {
												mpiService
														.getExpediente(
																model.anio,
																model.numero_sid)
														.then(
																function(res) {
																	// $scope.movimientosSTD
																	// =
																	// res.movimientos;
																	$scope.movimientosSTD = res.data.objeto.movimientos;
																	// console.log($scope.movimientosSTD);
																});
											}
											$scope.documento = model;
										});
					}
					$scope.cancel = function() {
						$uibModalInstance.close();
					};
					/* Init Page */
					$scope.OnInit();
				});