'use strict';
var inicio = false;
ventanillaVirtual
		.controller(
				'encuestaController',
				function($rootScope, DTOptionsBuilder, $ngBootbox, $scope,
						$uibModal, encuestaService, $log) {

					var vm = this;
					$scope.model = {
						id_encuesta : 0,
						mensaje_encuesta : 'Con el objetivo de mejorar nuestro servicio de recepción documental, solicitamos su apoyo para completar la Encuesta de satisfacción del servicio brindado en la Ventanilla Electrónica del Ministerio de Economía y Finanzas haciendo',
						url_encuesta : ''
					};

					$scope.init = function() {
						encuestaService
								.getEncuestaActiva()
								.then(
										function(response) {
											if (response.data.ejecucion_procedimiento == true
													&& response.data.rechazar == false)
												$scope.model = response.data.objeto;

											console.log(response.data.objeto);
										});
					};

					$scope.search = function() {
						debugger
						encuestaService
								.crear($scope.model)
								.then(
										function(response) {
											if (response.data.ejecucion_procedimiento == true
													&& response.data.rechazar == false) {
												$scope
														.showAlert({
															type : 'success',
															title : '!Exito!',
															message : response.data.mensaje_salida,
														});
												$scope.model.id_encuesta = response.data.objeto;
											} else {
												$scope
														.showAlert({
															type : 'danger',
															title : '!Error!',
															message : response.data.mensaje_salida,
														});
											}
										});
					};

					$scope.init();
				});