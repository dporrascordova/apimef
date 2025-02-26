'use strict';
ventanillaVirtual.factory('notiService', casillaService);

function casillaService($http, BASE_URL, $filter) {
    return {
        getMEFTipoDocumento: function () {
            return $http.get(`${BASE_URL}/listatipodedoc`);
        },

        getMEFOficinas: function () {
            return $http.get(`${BASE_URL}/listaoficinasexterno`);
        },

        getEstadoExpediente: function (numeroSid, anio) {
            return $http.get(`${BASE_URL}/consultaestadoexpediente/${anio}/lmauricio/${numeroSid}`);
        },


        getListaNotificaciones: function (data, grupoEstado) {
             
            let endpoint = "/micasilla"
            var formData = new FormData();
            formData.append('id_usuario', 2);
            formData.append('id_tipo_documento', data.tipoDocumentoNoti);
            formData.append('nro_documento', data.numeroDocumentoNoti);
            formData.append('id_oficina', data.organoDestinoNoti);
            formData.append('numero_sid', data.solicitudNoti);
            formData.append('fec_ini', data.fechaInicioNoti);
            formData.append('fec_fin', data.fechaFinNoti);

            return $http.post(`${BASE_URL}${endpoint}`, formData, {
                headers: {
                    'Content-Type': undefined,
                    'mimeType': 'multipart/form-data',
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            });
        },


        getListaEnviados: function (data,numpagina,numregistro) {
        	debugger; 
            const payload = {
                id_tipo_documento: data.tipoDocumento,
                nro_documento: data.nro_documeto,
                correo: data.correo,
                id_oficina: data.organoDestino,
                id_estado: data.estado,
                remitente: data.destinatario,
                nro_solicitud : data.nro_solicitud,
                hoja_ruta : data.hoja_ruta,
                hoja_envio : data.hoja_envio,
                fec_creacion: data.fechaInicio!= null?moment(data.fechaInicio ).format('DD/MM/YYYY'):'',
                fec_notificacion: data.fechaFin != null?moment(data.fechaFin ).format('DD/MM/YYYY'):'',
                numpag: numpagina,
                numreg: numregistro
            };
            debugger; 
            return $http.post(`${BASE_URL}/not_enviado`, payload);
        },


        getListaPorEnviar: function (data) { 
            var fec_recepcion = $filter('date')(data.fecharecepcion, "dd/MM/yyyy");
            const payload = {
                id_tipo_documento: data.tipoDocumento,
                nro_documento: data.nro_documeto,
                correo: data.correo,
                id_oficina: data.organoDestino,
                id_estado: data.estado,
                fec_recepcion: fec_recepcion || '',
                remitente: data.destinatario
            };
            return $http.post(`${BASE_URL}/not_porenviar`, payload);
        },

        EnviarNotifiacion: function (ListaDetalle, observacion, files) {
            var formData = new FormData();
             debugger;
            ListaDetalle.forEach(lista => {
                var concatena = lista.id_persona + " " + lista.id_usuario+ " "+ lista.id_documento ;
                formData.append('notificar', concatena);
            });
            formData.append('observacion', observacion);
            files.forEach(file => {
                formData.append('archivos', file.file, file.file.name);
            });
            return $http.post(`${BASE_URL}/insertaenmicasilla`, formData, {
                headers: {
                    'Content-Type': undefined,
                    mimeType: 'multipart/form-data'
                }
            });
        },

        EnviarNotifiacionUsuarios: function (ListaDetalle, observacion, files) { 
        	debugger;
            var formData = new FormData();
            ListaDetalle.forEach(lista => {
                var concatena = lista.id_persona + " " + lista.id_usuario;
                formData.append('notificar', concatena);
            });
            formData.append('observacion', observacion);
            files.forEach(file => {
                formData.append('archivos', file.file, file.file.name);
            });
            return $http.post(`${BASE_URL}/casilla_enviar`, formData, {
                headers: {
                    'Content-Type': undefined,
                    mimeType: 'multipart/form-data'
                }
            });
        },


        Reenviar_Email: function (model) { 
        	debugger;
            var formData = new FormData();
            formData.append('id_casilla', model.id_casilla);
            formData.append('id_persona', model.id_persona);
            formData.append('id_usuario', model.id_usuario);

            return $http.post(`${BASE_URL}/casilla_reenviar_Email`, formData, {
                headers: {
                    'Content-Type': undefined,
                    mimeType: 'multipart/form-data'
                }
            });
        },





        getDocumentosobs: function (iddocumento) {
             
            var formData = new FormData();
            formData.append('id_documento', iddocumento);

            return $http.post(`${BASE_URL}/documentoobs`, formData, {
                headers: {
                    'Content-Type': undefined,
                    mimeType: 'multipart/form-data'
                }
            });
        },

        getExcel(data, grupoEstado) {
        	let endpoint = "/reportenotificaciones"
        		
        		 var fechaInicio = ""; var fechaFin = "";
             	if(data.fechaInicio!=''){
             		fechaInicio = $filter('date')(data.fechaInicio, "dd/MM/yyyy");
             	}
     			if(data.fechaFin!=''){
     				fechaFin = $filter('date')(data.fechaFin, "dd/MM/yyyy");
     			        	}
                 
                var formData = new FormData();

        	 formData.append('id_tipo_documento', data.tipoDocumento);
             formData.append('nro_documento', data.nro_documeto);
             formData.append('correo', data.correo);
             formData.append('id_oficina', data.organoDestino);
             formData.append('id_estado', data.estado); 
             formData.append('remitente', data.destinatario); 
             formData.append('fec_creacion', data.fechaInicio!= null?moment(data.fechaInicio ).format('DD/MM/YYYY'):''); 
             formData.append('fec_notificacion', data.fechaFin != null?moment(data.fechaFin ).format('DD/MM/YYYY'):''); 
             
                return $http.post(`${BASE_URL}${endpoint}`, formData, {
                	responseType: 'blob',
                    headers: {
                        'Content-Type': undefined,
                        'mimeType': 'multipart/form-data',
                        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                    }
                }); 
          },
     };
}
