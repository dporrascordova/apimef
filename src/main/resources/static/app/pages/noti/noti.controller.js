'use strict';
var inicio = false;
ventanillaVirtual.controller('notiController', function (
  DTOptionsBuilder, 
  $rootScope,
  $ngBootbox,
  $http,
  $scope,
  $uibModal,
  notiService,
  personaService,
  casillaService,
  transversalService,
  $log,
  ) {
    var vm = this;

    $scope.model1 = {
       tipoDocumento:'',
       nro_documeto:'',
       correo:'',
       organoDestino:'',
       estado:'',
       fecharecepcion:'',
       destinatario:''
    };
    
    $scope.model2 = {
      tipoDocumento:'',
      estado:'',
      destinatario:'',
      nro_documeto:'',
      correo:'',
      organoDestino:'',
      hoja_ruta : '',
      nro_solicitud: '',
       hoja_envio : '', 
      
    };
    
    $scope.modelusuarios = {
      personatab2: '2',
      nombretab2:'',
      telefonotab2:'',
      estadotab2:'',
      nro_documetotab2:'',
      correotab2:'',
      celulartab2:'',
    };
    
    $scope.tipoDocumentoTab1 = [];
    $scope.tipoDocumentoTab2 = [];
    $scope.oficinasTab1 = [];
    $scope.oficinasTab2 = [];
    $scope.ListaDetalle ;
    $scope.ListaDetalleUsuarios; 
    
	$scope.maxSize = 5;
	$scope.totalItems = 0;
	$scope.currentPage = 1;
	$scope.itemsPerPage = 10;
	
	$scope.totalItemsNoti = 0;
	$scope.currentPageNoti = 1;

    $scope.Limpiar= function (tab) {
      $scope.model1 = {
        tipoDocumento:'',
        nro_documeto:'',
        correo:'',
        organoDestino:'',
        estado:'',
        fecharecepcion:'',
        destinatario:''
      };
      $scope.model2 = {
        tipoDocumento:'',
        estado:'',
        destinatario:'',
        nro_documeto:'',
        correo:'',
        organoDestino:'',
        hoja_ruta : '',
        nro_solicitud: '',
        hoja_envio : '', 
        fechaInicio : new Date(),
        // fechaFin : '',
        
      };
      $scope.modelusuarios = {
        personatab2: '2',
        nombretab2:'',
        telefonotab2:'',
        estadotab2:'',
        nro_documetotab2:'',
        correotab2:'',
        celulartab2:'',
      };
         
      if(tab =='1')
      $scope.searchPorEnviar();
      else if( tab =='2')
      $scope.searchEnviados();
      else
      $scope.searchUsuario();
    }; 
   
    $scope.init = function () {
      $scope.getMEFOficinas();
      $scope.getMEFTipoDocumento();
      $scope.dataPorEnviar = [];
      $scope.dataEnviados= []; 
      $scope.model2.fechaInicio = new Date();
      //var fec = new Date();
     // $scope.model2.fechaInicio= fec.getDay() + "/" +fec.getMonth()+ "/" +fec.getFullYear();
   
	 
    };
   
    $scope.getMEFOficinas = function () {
      notiService.getMEFOficinas().then(function (res) {
        if (res.status === 200) {
          $scope.oficinasTab2 = res.data.objeto;
          $scope.oficinasTab1 = res.data.objeto; 
        }
      });
    };
  
    $scope.getMEFTipoDocumento = function () {
      notiService.getMEFTipoDocumento().then(function (res) {
        if (res.status === 200) {
          $scope.tipoDocumentoTab2 = res.data.objeto;
          $scope.tipoDocumentoTab1 = res.data.objeto;
        }
      });
    };

    $scope.getindex = function () {
        $index = 0;
      };

     $scope.searchUsuario = function () {
      var flg_estado = '1'; 
      var valido = '0'; 
      $scope.modelusuarios.personatab2 = 2;
       personaService.getListaUsuariosPersona($scope.modelusuarios,flg_estado, valido,$scope.currentPage, $scope.itemsPerPage)
       .then(function (res) {    	  
         if (res.data.ejecucion_procedimiento == true) {
           $scope.dataUsuarios = res.data.objeto;   
           debugger; 
           if($scope.dataUsuarios.length>0)
        	   $scope.totalItems = res.data.objeto[0].totalreg;
           else
        	   $scope.totalItems = 0;
         }
         $scope.MostrarErrores(res);
       });
     };
     
     $scope.pageChanged = function() {    	 
		$scope.currentPage = this.currentPage;
		$scope.searchUsuario();
	};

    $scope.searchEnviados = function () { 
      notiService.getListaEnviados($scope.model2,$scope.currentPageNoti, $scope.itemsPerPage)
      .then(function (res) {
        console.log(res);
        if (res.data.ejecucion_procedimiento == true) {        	
          $scope.dataEnviados = res.data.objeto;   
          
          if($scope.dataEnviados.length>0)
       	   $scope.totalItemsNoti = res.data.objeto[0].totalreg;
          else
        	$scope.totalItemsNoti = 0;
        }
       // $scope.MostrarErrores(res);
      });
    };
    
    $scope.pageChangedNoti = function() {    	
		$scope.currentPageNoti = this.currentPageNoti;
		$scope.searchEnviados();
	};
    
    $scope.SelecAllcheck = function (event) {
    var chek_ = document.getElementById("check_pi");
    var estado = chek_.checked; 
    var miscasillas=document.getElementsByTagName('input');
    for(var i=0;i<miscasillas.length;i++) 
    {
      if(miscasillas[i].type == "checkbox") 
      {
        miscasillas[i].checked=estado;
      }
    }
    };

    $scope.SelecAllcheckusuarios = function (event) {
      var chek_ = document.getElementById("check_piusuaarios");
      var estado = chek_.checked; 
      var miscasillas=document.getElementsByTagName('input');
      for(var i=0;i<miscasillas.length;i++) 
      {
        if(miscasillas[i].type == "checkbox") 
        {
          miscasillas[i].checked=estado;
        }
      }
      };

    $scope.MostrarErrores = function (response) {
      var mensaje = "";
      var valido = false;
      if (response.data.ejecucion_procedimiento) {
        if (!response.data.rechazar) { valido = true; }
      }
      if (!valido) {
        if (response.data.error_lista) {
          if (response.data.objeto.length > 0) {
            mensaje += "<ul>"
            for (var i = 0; i < response.data.objeto.length; i++) {
              mensaje += "<li>" + response.data.objeto[i].message + "</li>";
            }
            mensaje += "</ul>"
          }
        } else {
          mensaje = response.data.mensaje_salida;
        }
        $ngBootbox.alert({
          size: "small",
          title: "Mensaje",
          message: mensaje,
          buttons: {
            ok: {
              label: '<i class="fa fa-check"></i> Aceptar',
              className: 'btn-default'
            }
          }
        });
      }
    }
  
    $scope.ArraySeleccionados = function() {
      $scope.codeMaterie = "";
      var  ListaDet = new Array(); 
          var materiesSeleccionadas = document.querySelectorAll("input[name=check_noti]:checked");
          // concatenamos los valores en la cadena
          for (var x = 0; x < materiesSeleccionadas.length; x++) {
              var datcheck= materiesSeleccionadas[x].value.split('-'); 
              var item = { 
                     id_usuario: datcheck[0],
                     id_persona: datcheck[1],
                     id_documento: datcheck[2],
                     correo: datcheck[3],
                   }
                ListaDet.push(item)
          }
          $scope.ListaDetalle = ListaDet; 
  }


    $scope.EnviarNoti = function (item) {
        $scope.ArraySeleccionados(); 
        console.log($scope.ListaDetalle); 
        if($scope.ListaDetalle.length > 0 ) { 
            let modalInstance = $uibModal.open({
              animation: true,
              backdrop: 'static',
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'pages/noti/enviar-notifiacion/enviar-notificacion.html',
              controller: 'enviarnotiController',
              size: 'lg',
              scope: $scope,
              resolve: {
                request: function () {
                  // console.log(response.data.objeto);
                  return item;
                },
                ListaDetalle: function () {
                  // console.log(response.data.objeto);
                  return  $scope.ListaDetalle;
                },
              
                item: function () {
                  // console.log(response.data.objeto);
                  return item;
                }
              }
            });
            modalInstance.result.then(function (response) {
              if(response != undefined)
              { 
                $scope.searchPorEnviar(); 
              }
  
            });
          }else{
            $ngBootbox.alert({
              type:"success",
              size: "small",
              title: "Mensaje",
              message: "Seleccione al menos un destinatario",
              buttons: {
                  ok: {
                      label: '<i class="fa fa-check"></i> Aceptar',
                      className: 'btn-default'
                  }
              }
          });
          } 


    }
  
    $scope.ArraySeleccionadosUsuarios = function() {
      $scope.codeMaterie = "";
      var  ListaDet = new Array(); 
          var materiesSeleccionadas = document.querySelectorAll("input[name=check_noti]:checked");
          // concatenamos los valores en la cadena
          for (var x = 0; x < materiesSeleccionadas.length; x++) {
              var datcheck= materiesSeleccionadas[x].value.split('-'); 
              var item = { 
                     id_usuario: datcheck[0],
                     id_persona: datcheck[1],   
                     id_documento:0      
                   }
                ListaDet.push(item)
          }
          $scope.ListaDetalleUsuarios = ListaDet; 
  }


    $scope.EnviarNoti_Usuarios = function (item) {
      $scope.ArraySeleccionadosUsuarios(); 
      console.log($scope.ListaDetalleUsuarios); 
      if($scope.ListaDetalleUsuarios.length > 0 ) { 
          let modalInstance = $uibModal.open({
            animation: true,
            backdrop: 'static',
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'pages/noti/enviar-noti-usuarios/enviar-noti-usuarios.html',
            controller: 'enviarnotiusuariosController',
            size: 'lg',
            scope: $scope,
            resolve: {
              request: function () {
                // console.log(response.data.objeto);
                return item;
              },
              ListaDetalle: function () {
                // console.log(response.data.objeto);
                return  $scope.ListaDetalleUsuarios;
              },
            
              item: function () {
                // console.log(response.data.objeto);
                return item;
              }
            }
          });
          modalInstance.result.then(function (response) {
                
            if(response != undefined)
            { 
              $scope.searchUsuario(); 
            }

          });
        }else{
          $ngBootbox.alert({
            type:"success",
            size: "small",
            title: "Mensaje",
            message: "Seleccione al menos un destinatario",
            buttons: {
                ok: {
                    label: '<i class="fa fa-check"></i> Aceptar',
                    className: 'btn-default'
                }
            }
        });
        } 


  }



  
    $scope.openViewSolicitud = function (id) {
      var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'pages/mpi/visualizar-solicitud/visualizar-solicitud.html',
        controller: 'visualizarsolicitudController',
        controllerAs: 'vm',
        size: 'xl',
        backdrop: 'static',
        scope: $scope,
        resolve: {
          IdSolicitud: function () {
            return id;
          }
        }
      });
      modalInstance.result.then(
        function (response) {},
        function (error) {}
      );
    };

    $scope.VerHtml = function (idcasilla) {    
      var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'pages/casilla/vernotificacion/vernotifiacion.html',
        controller: 'nvernotiController',
        controllerAs: 'vm',
        size: 'lg',
        backdrop: 'static',
        scope: $scope,
        resolve: {
          idcasilla: function () {
            return idcasilla;
          },
          modulo: function () {
            return 0;
          }
        }
      });
      modalInstance.result.then(
        function (response) {},
        function (error) {}
      );
    };


    $scope.VerObseracion = function (item) {
      var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'pages/noti/ver_observacion/ver_observacion.html',
        controller: 'verobseracionController',
        controllerAs: 'vm',
        size: 'lg',
        backdrop: 'static',
        scope: $scope,
        resolve: {
          item: function () {
            return item;
          }
    
        }
      });
      modalInstance.result.then(
        function (response) {},
        function (error) {}
      );
    };

    $scope.exportarExcel2 = function() {
    	notiService.getExcel($scope.model2).then(
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
					a.download = "Notificaciones" + "_" + fec.getDay()
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

	
    $scope.init(); 

});