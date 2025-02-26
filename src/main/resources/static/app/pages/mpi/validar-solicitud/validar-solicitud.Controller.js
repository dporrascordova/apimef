'use strict';

ventanillaVirtual.controller(
  'validarsolicitudController',
  function (
    $scope,
    $uibModal,
    $ngBootbox,
    toaster,
    $uibModalInstance,
    DTOptionsBuilder,
    DTColumnDefBuilder,
    $sce,
    $rootScope,
    mpeService,
    transversalService,
    mpiService,
    IdSolicitud,
  ) {
    // debugger;
    var vm = this;
    vm.dtOptions = DTOptionsBuilder.newOptions()
      // .withPaginationType('full_numbers')
      // .withDisplayLength(25)
      .withOption('paging', false);
    vm.dtColumnDefs = [DTColumnDefBuilder.newColumnDef(0), DTColumnDefBuilder.newColumnDef(1), DTColumnDefBuilder.newColumnDef(2)];
    vm.TablaOrganos = [];
    vm.ConfigurarOrgano = {};
    vm.addOrganos = addOrganos;
    vm.modifyOrganos = modifyOrganos;
    vm.removeOrganos = removeOrganos;
    vm.GetTextOrgano = GetTextOrgano;
    vm.GetTextAcciones = GetTextAcciones;

    $scope.filesAnexo = [];
    $scope.files = [];
    $scope.validoHojaReferencia = false;

    mpiService.asignarDocumento(IdSolicitud).then(function (res) {
      if (res.data.ejecucion_procedimiento && !$scope.isNullOrEmpty(res.data.mensaje_salida))
        $scope.showAlert({
          message: res.data.mensaje_salida,
        });
    });

    $scope.getCongresistas = function () {
      transversalService.getCongresistas().then(function (response) {
        $scope.Congresistas = response.data.objeto == null ? [] : response.data.objeto;
      });
    };
    
    // list clasificaciones
    
    $scope.getClasificaciones = function () {
        transversalService.getClasificaciones().then(function (response) {
        	debugger; 
          $scope.Clasificaciones = response.data.objeto == null ? [] : response.data.objeto;
        });
      };
      
      // list comisiones
      $scope.getComisiones = function () {
          transversalService.getComisiones().then(function (response) {
            $scope.Comisiones  = response.data.objeto == null ? [] : response.data.objeto;
          });
        };

    $scope.foliosChange = function () {
      if (parseInt(event.target.value) > 0) {
        $scope.documento.nro_folios = parseInt(event.target.value);
        return;
      }
      $scope.documento.nro_folios = null;
    };

    function _buildConfigurarOrganoAdd(index) {
      return vm.TablaOrganos.find(function (item, i) {
        if (i === index) {
          return item;
        }
      });
    }

    function addOrganos() {
      var estado = false;
      var estadoprincipal = false;
      if (vm.ConfigurarOrgano.idorgano === undefined) {
        estado = true;
      } else if (vm.ConfigurarOrgano.idAcciones === undefined) {
        estado = true;
      } else {
        /*
         * else if(vm.ConfigurarOrgano.comentario===
         * undefined){ estado = true; }
         */
        vm.TablaOrganos.find(function (item, i) {
          if (item.idorgano == vm.ConfigurarOrgano.idorgano) {
            estado = true;
          }
        });

        if ($scope.documento.id_oficina == vm.ConfigurarOrgano.idorgano) {
          estadoprincipal = true;
        }
        if (estadoprincipal) {
          toaster.pop({
            type: 'warning',
            title: 'Mensaje',
            body: 'La oficina no puede ser igual a la principal',
            timeout: 3000,
            showCloseButton: true,
            progressBar: true,
          });
        } else if (estado) {
          toaster.pop({
            type: 'warning',
            title: 'Mensaje',
            body: 'La oficina ya se encuentra agregado',
            timeout: 3000,
            showCloseButton: true,
            progressBar: true,
          });
        } else {
          if (ValidarOficina() == false) {
            vm.TablaOrganos.push(angular.copy(vm.ConfigurarOrgano));
            vm.ConfigurarOrgano = {};
          }
        }
      }
    }

    function ValidarOficina() {
      var estado = false;

      vm.TablaOrganos.find(function (item, i) {
        if (item.idorgano == $scope.documento.id_oficina) {
          estado = true;
        }
      });
      if (estado) {
        toaster.pop({
          type: 'warning',
          title: 'Mensaje',
          body: 'La oficina ya se encuentra agregado',
          timeout: 3000,
          showCloseButton: true,
          progressBar: true,
        });
      }
      return estado;
    }

    function modifyOrganos(index) {
      vm.ConfigurarOrgano = angular.copy(_buildConfigurarOrganoAdd(index));
      vm.TablaOrganos.splice(index, 1);
      // debugger;
    }
    function removeOrganos(index) {
      vm.TablaOrganos.splice(index, 1);
    }
    function GetTextOrgano() {
      // debugger;
      try {
        vm.ConfigurarOrgano.selectOrganoText = $.grep($scope.oficinas, function (item) {
          return item.idunidad == vm.ConfigurarOrgano.idorgano;
        })[0].descripcioncompleta;
      } catch (error) {}
    }
    function GetTextAcciones() {
      // debugger;
      try {
        vm.ConfigurarOrgano.selectAccionText = $.grep($scope.ListAcciones, function (item) {
          return item.id == vm.ConfigurarOrgano.idAcciones;
        })[0].valor;
      } catch (error) {}
    }

    $scope.OnInit = function () {
      $scope.form = {};
      $scope.IdSolicitud = IdSolicitud;
      $scope.getMEFOficinas();
      $scope.getCongresistas();
      $scope.getClasificaciones();
      $scope.getComisiones(); 
    };
    $scope.contador = 0;
    $scope.contadorComentario = 0;
    $scope.contadorComentario2 = 0;
    $scope.contarCaracteres = function () {
      $scope.contador = event.target.value.length;
    };

    $scope.contarCaracteresComentario = function () {
      $scope.contadorComentario = event.target.value.length;
    };

    $scope.contarCaracteresComentario2 = function () {
      $scope.contadorComentario2 = event.target.value.length;
    };

    $scope.getMEFOficinas = function () {
      mpiService.getMEFOficinas().then(function (res) {
        if (res.status === 200) {
          $scope.oficinas = res.data.objeto;
          // console.log( $scope.oficinas);
          $scope.getMEFTipoDocumento();
        }
      });
    };
    $scope.getMEFTipoDocumento = function () {
      mpeService.getMEFTipoDocumento().then(function (res) {
        if (res.status === 200) {
          // debugger;
          $scope.tipoDocumento = res.data.objeto;
          $scope.getSolicitud();
          $scope.getAcciones();
        }
      });
    };
    $scope.getAcciones = function () {
      mpiService.consultainstrucciones().then(function (response) {
        // debugger;
        $scope.ListAcciones = response.data.objeto;
      });
    };
    $scope.getSolicitud = function () {
      mpiService.getDocumentoPorId($scope.IdSolicitud).then(function (response) {
    	  debugger; 
        $scope.documento = response.data.objeto;
        $scope.documento.id_prioridad = '0';
        $scope.contador = $scope.documento.asunto.length;
        $scope.documento.situacionactual = '';
        $scope.documento.hoja_ruta = $scope.documento.hoja_ruta == 'null' ? '' : $scope.documento.hoja_ruta;
        $scope.documento.file = '';
        mpiService.exportFile(response.data.objeto.id_documento, 'pdf', response.data.objeto.codigo_archivo).then(function (responses) {
          var file = new Blob([responses.data], {
            type: $rootScope.applicationType('pdf'),
          });
          $scope.documento.file = $sce.trustAsResourceUrl(URL.createObjectURL(file));
        });
      });
    };
    $scope.downloadFile = function (id, fileType, fileName) {
      //console.log(fileType);

      mpiService.exportFile(id, fileType, fileName).then(function (response) {
        if (fileType == 'xls') fileType = 'application/vnd.ms-excel';
        else fileType = $rootScope.applicationType(fileType);
        var file = new Blob([response.data], {
          type: fileType,
        });
        var fileURL = URL.createObjectURL(file);
        const url = window.URL.createObjectURL(file);
        //var fileURL = URL.createObjectURL(file);
        // const url = window.URL
        // .createObjectURL(file);
        //console.log(fileURL);
        window.open(fileURL);
      });
    };
    $scope.validarExpediente = function (hoja_ruta) {
      var model = {
        anio: hoja_ruta.split('-')[1],
        numeroSid: hoja_ruta.split('-')[0],
        nombrecorto: 'mchavez',
      };
      mpiService.consultaexpediente(model.anio, model.nombrecorto, model.numeroSid).then(function (response) {
        var valido = false;
        if (response.data.ejecucion_procedimiento == true) {
          if (response.data.rechazar == false) {
            valido = true;
            $scope.validoHojaReferencia = valido;
          } else {
            valido = false;
          }
        } else {
          valido = false;
          $scope.validoHojaReferencia = valido;
        }
        if (valido) {
          $scope.documento.situacionactual = response.data.objeto.valor;
          // console.log(response.data.objeto);
          toaster.pop({
            type: 'success',
            title: 'Mensaje',
            body: 'Se validó correctamente el Expediente',
            timeout: 3000,
            showCloseButton: true,
            progressBar: true,
          });
        } else {
          toaster.pop({
            type: 'warning',
            title: 'Mensaje',
            body: response.data.mensaje_salida,
            timeout: 3000,
            showCloseButton: true,
            progressBar: true,
          });
        }
        // console.log(response.data.objeto);
      });
    };

    $scope.recibirSolicitud = function (data) {
      var estado = false;
      /* Para obtener el texto */
      if ($scope.documento.id_congresista != null) {
        var combo = document.getElementById('id_congresista');
        var desc_congresista = combo.options[combo.selectedIndex].text;
        $scope.documento.desc_congresista = desc_congresista;
      }

      if ($scope.documento.id_oficina === undefined) {
        toaster.pop({
          type: 'warning',
          title: 'Mensaje',
          body: 'Se debe seleccionar una oficina',
          timeout: 3000,
          showCloseButton: true,
          progressBar: true,
        });
        return;
      }

      if ($scope.documento.id_prioridad == null) {
        toaster.pop({
          type: 'warning',
          title: 'Mensaje',
          body: 'Se debe seleccionar una prioridad',
          timeout: 3000,
          showCloseButton: true,
          progressBar: true,
        });
        return;
      }

      vm.TablaOrganos.find(function (item, i) {
        if (item.idorgano == $scope.documento.id_oficina) {
          estado = true;
        }
      });
      if (estado) {
        toaster.pop({
          type: 'warning',
          title: 'Mensaje',
          body: 'La oficina ya se encuentra agregado',
          timeout: 3000,
          showCloseButton: true,
          progressBar: true,
        });
      } else {
	  // validar repetidos
    	  var _Mensaje ="¿Está seguro que desea recibir la solicitud?"; 
    	  transversalService.get_repetidos(parseInt($scope.documento.tipopersona), $scope.documento.nrodocumento_persona, 
    			  		parseInt($scope.documento.id_tipo_documento),$scope.documento.nro_documento).then(function (res) {
          if (res.data.ejecucion_procedimiento && !res.data.rechazar) {
        	  if(res.data.objeto != null && res.data.objeto.length > 0){
        		  _Mensaje =`Se encontro <b>${res.data.objeto.length}</b> registros con la misma información ingresada.¿Está seguro que desea recibir la solicitud?<br /> `; 
        		  _Mensaje += $scope.MostrarRegistrosRepetidos(res.data.objeto); 
        	  	}
        $ngBootbox
          .confirm({
            message: _Mensaje,
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
              mpiService.validarRecepcionYObservacion($scope.IdSolicitud).then(function (res) {
                if (res.data.ejecucion_procedimiento && !$scope.isNullOrEmpty(res.data.mensaje_salida)) {
                  $scope.showAlert({
                    message: res.data.mensaje_salida,
                  });
                  return;
                }

                var model = {};
                model.oficinas = [];
                model.oficinas.push({
                  idunidadDestino: $scope.documento.id_oficina,
                  original: true,
                  idinstr: 1,
                  observacion: $scope.documento.comentario,
                });

                if ($scope.documento.situacionactual == 'EN PROCESO' && $scope.documento.hoja_ruta != '') {
                  mpiService.validarHojaRuta($scope.IdSolicitud, $scope.documento.hoja_ruta).then(function (res) {
                    if (res.data.ejecucion_procedimiento && res.data.rechazar) {
                      $ngBootbox
                        .confirm({
                          message: res.data.mensaje_salida,
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
                        .then(function () {
                          $scope.anexarSolicitud();
                        });
                      return;
                    }
                    $scope.anexarSolicitud();
                  });
                } else {
                  vm.TablaOrganos.find(function (item, i) {
                    model.oficinas.push({
                      idunidadDestino: item.idorgano,
                      original: false,
                      idinstr: item.idAcciones,
                      observacion: item.comentario,
                    });
                  });
                  model.id_documento = $scope.IdSolicitud;
                  model.id_oficina = $scope.documento.id_oficina;
                  /**/
                  model.nro_folios = $scope.documento.nro_folios;
                  model.asunto = $scope.documento.asunto;
                  model.hoja_ruta = $scope.documento.hoja_ruta;
                  model.id_tipo_documento = $scope.documento.id_tipo_documento;
                  model.id_oficina = $scope.documento.id_oficina;
                  model.nro_documento = $scope.documento.nro_documento;
                  model.id_oficina = $scope.documento.id_oficina;

                  model.id_prioridad = $scope.documento.id_prioridad;
                  model.id_congresista = $scope.documento.id_congresista;
                  model.desc_congresista = $scope.documento.desc_congresista; 
                  model.id_comision = $scope.documento.id_comision;
                  model.id_clasificacion = $scope.documento.id_clasificacion;
  
                  mpiService.recepcionarSlicitud(model).then(function (response) {
                    var valido = false;
                    if (response.data.ejecucion_procedimiento == true) {
                      if (response.data.rechazar == false) {
                        valido = true;
                      }
                    }
                    if (valido) {
                      $uibModalInstance.close({
                        status: true,
                      });
                      toaster.pop({
                        type: 'success',
                        title: 'Mensaje',
                        body: 'Se recibió correctamente la solicitud.',
                        timeout: 3000,
                        showCloseButton: true,
                      });
                    } else {
                      toaster.pop({
                        type: 'warning',
                        title: 'Mensaje',
                        body: response.data.mensaje_salida,
                        timeout: 3000,
                        showCloseButton: true,
                      });
                    }
                  });
                }
              });          
            },
            function () {},
          );
    	  }else{
    		  $scope.MostrarErrores(res); 
    	  } 
          }); 
      }
    };

    $scope.openModalObservarSolicitud = function (data) {
      var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'pages/mpi/validar-solicitud/observar.html',
        controller: 'observarsolicitudController',
        controllerAs: 'vm',
        size: 'md',
        backdrop: 'static',
        scope: $scope,
        resolve: {
          IdSolicitud: function () {
            return data;
          },
        },
      });
      modalInstance.result.then(function (transferData) {
        if (transferData.status) {
          $uibModalInstance.close({
            status: transferData.status,
          });
        }
      });
    };

    $scope.cancel = function () {
      $uibModalInstance.close({
        status: false,
      });
    };

    $scope.addFile = function () {
      mpiService.validarRecepcionYObservacion($scope.IdSolicitud).then(function (res) {
        if (res.data.ejecucion_procedimiento && !$scope.isNullOrEmpty(res.data.mensaje_salida)) {
          $scope.showAlert({
            message: res.data.mensaje_salida,
          });
          return;
        }

        if (!$scope.filesAnexo.length) {
          $scope.showAlert({
            message: 'Seleccione un archivo para anexar.',
          });
          return;
        }

        if (!$scope.validateExtensionFile($scope.filesAnexo[0].name)) {
          $scope.showAlert({
            message: `Formato no válido: solo es posible agregar archivos con la extensión de tipo ${$scope.AnexoExtensions}.`,
          });
          $scope.resetFileAnexo();
          return;
        }

        if ($scope.filesAnexo[0].size == 0) {
          $scope.showAlert({
            message: `El archivo no tiene contenido o está  dañado (${this.bytesToFormat($scope.filesAnexo[0].size)})`,
          });
          $scope.resetFileAnexo();
          return;
        }

        if ($scope.filesAnexo[0].size > $scope.docMaxFileSize) {
          $scope.showAlert({
            message: 'El archivo debe tener un peso máximo de ' + this.bytesToFormat($scope.docMaxFileSize),
          });
          $scope.resetFileAnexo();
          return;
        }

        var exist = $scope.documento.anexos.find((t) => t.nombre_archivo === $scope.filesAnexo[0].name);

        if ($scope.documento.anexos.length > $scope.AnexoTotalMaxFiles) {
          $scope.showAlert({
            message: `Solo le es permitido adjuntar un total de ${$scope.AnexoTotalMaxFiles} archivos.`,
          });
          $scope.resetFileAnexo();
          return;
        }

        var total = $scope.filesAnexo[0].size;

        $scope.documento.anexos.forEach((el) => {
          total += el.tamanio_archivo;
        });

        if (total > $scope.AnexoTotalMaxFileSize) {
          $scope.showAlert({
            message: `No se puede agregar el archivo porque la suma supera los ${this.bytesToFormat(
              $scope.AnexoTotalMaxFileSize,
            )} permitidos.`,
          });
          $scope.resetFileAnexo();
          return;
        }

        if (!exist) {
          mpeService.registrarAnexo(IdSolicitud, $scope.filesAnexo[0], true).then(function (res) {
            if (res.data.ejecucion_procedimiento && !res.data.rechazar) {
              mpeService.getAnexosByDocumentoId(IdSolicitud).then(function (responseAnexos) {
                if (responseAnexos.data.ejecucion_procedimiento) $scope.documento.anexos = responseAnexos.data.objeto;
              });
              $scope.resetFileAnexo();
              return;
            }

            $scope.showAlert({
              message: res.data.mensaje_salida,
            });
          });
        } else {
          $scope.showAlert({ message: 'El archivo existe en la lista' });
        }
      });
    };

    $scope.resetFileAnexo = function () {
      angular.element('#filesAnexo').val('');
      $scope.filesAnexo = [];
    };

    $scope.removeFile = function (codigo) {
      mpiService.validarRecepcionYObservacion(IdSolicitud).then(function (res) {
        if (res.data.ejecucion_procedimiento && !$scope.isNullOrEmpty(res.data.mensaje_salida)) {
          $scope.showAlert({
            message: res.data.mensaje_salida,
          });
          return;
        }

        $ngBootbox
          .confirm({
            message: '¿Estás seguro de eliminar el anexo?',
            buttons: {
              confirm: {
                label: 'Aceptar',
                className: 'btn-danger',
              },
              cancel: {
                label: 'Cerrar',
                className: 'btn-secondary',
              },
            },
          })
          .then(
            function () {
              mpeService.anularAnexo(IdSolicitud, codigo).then(function (res) {
                if (res.data.ejecucion_procedimiento && !res.data.rechazar) {
                  mpeService.getAnexosByDocumentoId(IdSolicitud).then(function (responseAnexos) {
                    if (responseAnexos.data.ejecucion_procedimiento) $scope.documento.anexos = responseAnexos.data.objeto;
                  });
                  $scope.resetFileAnexo();
                  return;
                }

                $scope.showAlert({
                  message: res.data.mensaje_salida,
                });
              });
            },
            function () {},
          );
      });
    };

    $scope.anexarSolicitud = function () {
      var model = {};
      model.oficinas = [];
      model.oficinas.push({
        idunidadDestino: $scope.documento.id_oficina,
        original: true,
        idinstr: 1,
        observacion: $scope.documento.comentario,
      });
      
      vm.TablaOrganos.find(function (item, i) {
        model.oficinas.push({
          idunidadDestino: item.idorgano,
          original: false,
          idinstr: item.idAcciones,
          observacion: item.comentario,
        });
      });

      model.numero_sid = $scope.documento.hoja_ruta.split('-')[0];
      model.anio = $scope.documento.hoja_ruta.split('-')[1];
      model.id_documento = $scope.IdSolicitud;
      model.id_tipo_documento = $scope.documento.id_tipo_documento;
      model.id_oficina = $scope.documento.id_oficina;
      model.nro_documento = $scope.documento.nro_documento;
      model.nro_folios = $scope.documento.nro_folios;
      model.asunto = $scope.documento.asunto;
      model.hoja_ruta = $scope.documento.hoja_ruta;

      model.id_prioridad = $scope.documento.id_prioridad;
      model.id_congresista = $scope.documento.id_congresista;
      model.desc_congresista = $scope.documento.desc_congresista;

      mpiService.anexarsolicitud(model).then(function (response) {
        var valido = false;
        if (response.data.ejecucion_procedimiento == true) {
          if (response.data.rechazar == false) {
            valido = true;
          }
        }
        if (valido) {
          $uibModalInstance.close({
            status: true,
          });
          toaster.pop({
            type: 'success',
            title: 'Mensaje',
            body: 'Se recibió correctamente la solicitud.',
            timeout: 3000,
            showCloseButton: true,
          });
        } else {
          toaster.pop({
            type: 'warning',
            title: 'Mensaje',
            body: response.data.mensaje_salida,
            timeout: 3000,
            showCloseButton: true,
          });
        }
      });
    };
    $scope.OnInit();
    
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
	},
	
	$scope.MostrarRegistrosRepetidos = function(Lista) {
		var _html="";
		_html+="<div class=\"bootbox-body mt-3\">";
		_html+="<table class=\"table table-bordered\">";
		_html+="<thead>";
		_html+=	"<tr>";
		_html+=		"<th>Asunto</th>";
		_html+=		"<th>Nro Documento</th>";
		_html+=	     "<th>Hoja de Ruta</th>";
		_html+=		"<th>Estado</th>";
		_html+=		"</tr>";
		_html+=	"</thead>";
		Lista.forEach(function(documento) {
			debugger; 
		_html+=	"<tbody class=\"ng-scope\" >"; 
		_html+=	"<tr class=\"ng-scope\">"; 
		_html+=		"<td class=\"ng-binding\">"+documento.asunto+"</td>"; 
		_html+=		"<td class=\"ng-binding\">"+documento.numeroDoc+"</td>"; 
		_html+=		"<td class=\"ng-binding\">"+documento.numeroSid+"-"+documento.numeroAnio +"</td>"; 
		_html+=		"<td class=\"ng-binding\">"+documento.estadotxt+"</td>"; 
		_html+=	"</tr>"; 
		_html+="</tbody>"; 
		});
		_html+="</table>";
		_html+="</div>"; 
		return _html; 
		
	}
	
  },
);