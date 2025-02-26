'use strict';
var inicio = false;
ventanillaVirtual
		.controller(
				'ReportesController',
				function($rootScope, $ngBootbox, DTOptionsBuilder, $http,
                    $scope, $uibModal, personaService,mpeService, transversalService,
                    $log, BASE_URL) {
                    var vm = this;
                    $scope.requests = [];
					$scope.model = {
						personatab2 : '0',
						nombretab2 : '',
						telefonotab2 : '',
						estadotab2 : '',
						nro_documetotab2 : '',
						correotab2 : '',
						celulartab2 : '',

		
                    };
					$scope.init = function() {
						if (inicio) {
							inicio = false;
							$scope.dataUsuarios = [];
							$scope.modelDocumento = {};
							$scope.getMEFOficinas();
							$scope.getMEFTipoDocumento();
						} else {
							inicio = true;
						}
                    };
                                
            $scope.Limpiar = function(tab) {
            	switch (tab) {
				case 1:
					$scope.model = {
						personatab2 : '0'
					};
					$scope.currentPage = 1;
				    $scope.searchUsuario();
					break;
				case 2: 
					$scope.modelDocumento = {};
					$scope.currentPageDocumento = 1;
					$scope.searchSolDocumento();
					break;
				}
       
            };

      /////////////////////////////////////////////// tab 1 
            $scope.maxSize = 5;		
            $scope.itemsPerPage = 10;
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.pageChanged = function() {				    	   
                $scope.currentPage = this.currentPage;
                $scope.searchUsuario();
            };
		
            $scope.searchUsuario = function() {
                var flg_estado = $scope.model.estadotab2;
                var valido = '0';
                if (flg_estado == undefined)
                    flg_estado = "1";

                personaService.getListaUsuariosPersona($scope.model,
                        flg_estado, valido,$scope.currentPage, $scope.itemsPerPage).then(function(res) {
                            debugger; 
                    if (res.data.ejecucion_procedimiento == true) {
                        $scope.dataUsuarios = res.data.objeto;
                      //  $scope.FiltrarPersona($scope.dataUsuarios);
                        
                       if($scope.dataUsuarios.length > 0)
                           $scope.totalItems = res.data.objeto[0].totalreg;   
                       else
                              $scope.totalItems = 0;
                    }
                    $scope.MostrarErrores(res);
                });
            };
            

			$scope.exportarAExcel_Usuarios = function () {
			       var valido = '0';
			       var flg_estado = $scope.model.estadotab2;
	                var valido = '0';
	                if (flg_estado == undefined)
	                    flg_estado = "1";
				personaService.getExcel_Usuarios($scope.model,flg_estado,valido).then(
					function (response) {

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
						a.download = "Usuarios" + "_" + fec.getDay()
								+ "" + fec.getMonth() + ""
								+ fec.getFullYear() + "_"
								+ fec.getHours() + ""
								+ fec.getMinutes() + ""
								+ fec.getSeconds() + ".xlsx";
						a.click();
						window.URL.revokeObjectURL(url);
						
					});
			};
      
    
           /////////////////// fin tab 1 
			
			///////////////////  tab2 	 
			
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
			} ]; 		
			$scope.currentPageDocumento = 1;
			
			$scope.pageChanged = function() {
					$scope.currentPageDocumento = this.currentPageDocumento;
					$scope.searchSolDocumento();
			}
			
			$scope.searchSolDocumento = function() {
				//$scope.modelDocumento.estado = 0;
				mpeService
						.getListaPaginadoDocs($scope.modelDocumento,
								$scope.currentPageDocumento,
								$scope.itemsPerPage, null)
						.then(
								function(res) {
									if (res.status === 200) {
										$scope.dataSolDocumento = res.data.rows;
										$scope.totalItemsDocumento = res.data.totalreg;
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
			
			$scope.exportarAExcel_Documento = function () {
					let model, grupo, nombre;
					var tipo = $scope.modelDocumento.estado; 
					switch (tipo) {
					case 1:
						model = $scope.modelDocumento;		
						grupo = 'TAB_PENDIENTE';
						nombre = 'Pendientes';
						break;
					case 2:
						model = $scope.modelDocumento;
						grupo = 'TAB_RECIBIDO';
						nombre = 'Recibidos';
						break;
					case 3:
						model = $scope.modelDocumento;
						grupo = 'TAB_OBSERVADO';
						nombre = 'Observados';
						break;
					case 4:
						model = $scope.modelDocumento;
						grupo = 'TAB_FINALIZADO';
						nombre = 'Finalizados';
						break;

					case 6:
						model = $scope.modelDocumento;
						grupo = 'TAB_ANULADO';
						nombre = 'Anulados';
						break;
					default:
						model = $scope.modelDocumento;
						grupo = null;
						nombre = 'Documentos';
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
			/////////////////// fin tab2 	

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
				
                    $scope.init();
                    $rootScope.$on('reloadGrid', $scope.init);
			});