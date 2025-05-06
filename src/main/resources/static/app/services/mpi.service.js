'use strict';
ventanillaVirtual.factory('mpiService', mpiService);

function mpiService($http, BASE_URL) {
  return {
        getDocumentoPorId(documentoId) {    
            let endpoint = "/obtener-solicitud"    
            return $http.get(`${BASE_URL}${endpoint}/${documentoId}`,{
                    headers: {
                        'Content-Type': undefined,
                        'mimeType': 'multipart/form-data',
                    },
                });
        },
        getMEFOficinas: function () {
            return $http.get(`${BASE_URL}/listaoficinasinterno`);
          },
      
        getExpediente(anio, numeroSid) {            
            let endpoint = "/consultaexpediente"
            return $http.get(`${BASE_URL}${endpoint}/${anio}/${numeroSid}`);
        },
        exportFile(id, fileType, fileName) {  

            let endpoint = "/export"
        	 
            return $http.get(`${BASE_URL}${endpoint}/${id}/${fileType}/${fileName}`, 
            {
                headers: {
                    'Content-Type': undefined,
                    'mimeType': 'multipart/form-data',
                },
                responseType: 'blob',              
            });
        },
        observarSolicitud: function (modelo) {
            let endpoint = "/observar-solicitud"
            return $http.post(`${BASE_URL}${endpoint}`, modelo);
        },   
        

        consultainstrucciones: function () {
            let endpoint = "/consultainstrucciones"
            return $http.get(`${BASE_URL}${endpoint}`);
        }, 
        consultaexpediente: function (anio,nombrecorto,numeroSid) {
            //let endpoint = "/consultaexpediente"
            //return $http.get(`${BASE_URL}${endpoint}/${anio}/${numeroSid}`);
            let endpoint = `/consultaestadoexpediente`
            return $http.get(`${BASE_URL}${endpoint}/${anio}/${nombrecorto}/${numeroSid}`);
        },
        recepcionarSlicitud: function (modelo) {
        	debugger;
        	
            let endpoint = "/recepcionar-solicitud"
            return $http.post(`${BASE_URL}${endpoint}`, modelo);
            
        },    
        anexarsolicitud: function (modelo) {
            let endpoint = `/anexar-solicitud`
            return $http.post(`${BASE_URL}${endpoint}`, modelo);
        }, 

        
        registroFinalizar: function (idcoumento,data,files) {
              
               var formData = new FormData();
               formData.append('id_documento', idcoumento);
               formData.append('mensaje', data.mensaje);
              // formData.append('archivos', files);
   
              files.forEach(file => {
               formData.append('archivos', file.file, file.file.name);
             });
   
               return $http.post(`${BASE_URL}/finalizar-solicitud`, formData, {
                 headers: {
                   'Content-Type': undefined,
                   mimeType: 'multipart/form-data'
                 }
               });
   
   
               
           }, 

       asignarDocumento: function (documentoId) {
           var payload = {id_documento : documentoId};
           return $http.post(`${BASE_URL}/asignar-documento`,payload);
       },
       
       validarRecepcionYObservacion(documentoId) {
	      return $http.get(
	        `${BASE_URL}/validar-recepcion-observacion/${documentoId}`
	      );
	    },

	    validarHojaRuta(documentoId,horaRuta) {
	      return $http.get(
	        `${BASE_URL}/validar-hoja-ruta/${documentoId}/${horaRuta}`
	      );
	    },
	    
	    desasignarSolicitud(documentoId) {
	    	var payload = {
	        		id_documento : documentoId
	        };
	        return $http.post(`${BASE_URL}/desasignar-solicitud`,payload);
		    },
      ejecutarAgregarAExpediente(id_documento,numero_sid,anio) {    
          var payload = {id_documento:id_documento, numero_sid : numero_sid, anio:anio };
          return $http.post(`${BASE_URL}/agregar-expediente-reload`,payload);
      },
    };
}
