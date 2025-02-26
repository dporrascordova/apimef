ventanillaVirtual.controller('nvernotiController', function($scope, $rootScope,
		personaService, $ngBootbox, $uibModalInstance, $filter, idcasilla,
		casillaService, modulo, $uibModal,notiService,loginService

) {

	$scope.submitted = false;
	$scope.form = {};
	$scope.model = {
		html : ''
	};
	$scope.idcasilla = idcasilla;
	$scope.modulo = modulo;
	$scope.DataArchivos = [];
	$scope.response;

	$scope.close = function() {
		$uibModalInstance.close($scope.response);
	   //$scope.search();
	};

	$scope.VimiCasilla = function() {
		casillaService.getVimiCasilla($scope.idcasilla).then(function(res) {
			if (res.data.ejecucion_procedimiento == true) {
				var usuario = {
						UId_Usuario : $scope.currentUser.UsuarioId
					};
				
				loginService.alerta_count(usuario).then(function(res) {
					if (res.data.ejecucion_procedimiento == true) {
                        var Alert = document.getElementsByClassName("Show_Alert"); 
						for(var i = 0; i < Alert.length; i++)
						{														   
				          var _this = Alert[i];
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
			 }
			$scope.MostrarErrores(res);
		});
	};

	$scope.ListarUno_CasillaView = function() {
		casillaService
				.getListarUno_CasillaView($scope.idcasilla, $scope.modulo)
				.then(function(res) {
					if (res.data.ejecucion_procedimiento == true) {
			
						if(res.data.objeto.mensaje_html !=null && res.data.objeto.mensaje_html !='' )
						var _html = res.data.objeto.mensaje_html.replace('logo-mef?id='+res.data.objeto.id_casilla,'logo-mef?id=0'); 
						$scope.model = {
							
							html : _html,
							usuario_creacion : res.data.objeto.usu_creacion,
							fecha_creacion : res.data.objeto.fec_creacion,
							ipcreacion : res.data.objeto.ip_creacion,
							estado : res.data.objeto.estado,
							id_casilla : res.data.objeto.id_casilla,
							id_persona : res.data.objeto.id_persona,
							id_usuario : res.data.objeto.id_usuario

						};

						$scope.DataArchivos = res.data.objeto.archivos;
						console.log($scope.DataArchivos);
						console.log(res.data.objeto.mensaje_html);
					}
					$scope.MostrarErrores(res);
				});
	};


	$scope.Notificacion_Reenviar = function() {
		debugger; 
		var id_casilla = $scope.model.id_casilla; 
		notiService
				.Reenviar_Email($scope.model)
				.then(function(res) {
					debugger;
					if (res.data.ejecucion_procedimiento == true) {
						$ngBootbox.alert({
							type: "success",
							size: "small",
							title: "Mensaje",
							message: "Correo reenviado correctamente",
							buttons: {
							  ok: {
								label: '<i class="fa fa-check"></i> Aceptar',
								className: 'btn-default'
							  }
							}
						  });
						$scope.close (); 
					}
					$scope.MostrarErrores(res);
				}); 
	};



/*
	$scope.$watch('content', function() {
		iframe.contentWindow.document.open('text/htmlreplace');
		iframe.contentWindow.document.write($scope.model.html);
		iframe.contentWindow.document.close();
	});
	*/

	if ($scope.modulo == "1") {
		$scope.VimiCasilla();
	}

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
						mensaje += "<li>" + response.data.objeto[i].message
								+ "</li>";
					}
					mensaje += "</ul>"
				}
			} else {
				mensaje = response.data.mensaje_salida;
			}
			$ngBootbox.alert({
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

	$scope.downloadFile = function(codigo, extension) {
		var arrayarchivo = codigo.split('.');
		var name = arrayarchivo[0];
		var fileType = arrayarchivo[1];
		personaService.exportFile(extension, name).then(function(response) {
			
			if (fileType == "xls")
				fileType = "application/vnd.ms-excel"
			else
				fileType = $rootScope
						.applicationType(fileType);
			var file = new Blob([ (response.data) ], {
				type : fileType
			});
			/*var file = new Blob([ (response.data) ], {
				type : $rootScope.applicationType(fileType)
			});*/
			var fileURL = URL.createObjectURL(file);
			//const url = window.URL.createObjectURL(file);
			window.open(fileURL);
		});
	}

	$scope.ListarUno_CasillaView();

});

ventanillaVirtual
		.directive(
				"preview",
				function() {

					function link(scope, element) {
						var iframe = document.createElement('iframe');
						var element0 = element[0];
						element0.appendChild(iframe);
						var body = iframe.contentDocument.body;

						scope
								.$watch(
										'content',
										function() {
											body.innerHTML = scope.content
													.replace('cid:logo.png',
															'assets/images/logo_mef.jpg')
													.replace(
															'cid:peruprimero.png',
															'assets/images/peruprimero.jpg');
											// body.innerHTML =
											// scope.content.replace('cid:peruprimero.png','assets/images/peruprimero.jpg');

											if (body.innerHTML != "") {
												var altura = iframe.contentWindow.document.documentElement.scrollHeight;
												var scroll = 250 * 0.25;
												var total = altura + scroll;
												iframe.style.height = total
														+ 'px';
											}

										});

					}

					return {
						link : link,
						restrict : 'E',
						scope : {
							content : '='
						}
					};
				});