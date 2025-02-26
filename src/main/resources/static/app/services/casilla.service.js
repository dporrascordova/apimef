'use strict';
ventanillaVirtual.factory('casillaService', casillaService);

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


        getListaNotificaciones: function (data) { 
            let endpoint = "/micasilla"
            var formData = new FormData();
            // formData.append('id_usuario', 2);

            formData.append('id_tipo_documento', data.tipoDocumentoNoti);
            formData.append('nro_documento', data.numeroDocumentoNoti);
            formData.append('nro_solicitud', data.solicitudNoti);
            formData.append('id_oficina', data.organoDestinoNoti);
            formData.append('observacion', data.observacion);     
            formData.append('id_estado_documento', data.estado);
            formData.append('flg_tipo_noti', parseInt(data.Tipo_noti));
            debugger;
             if (data.fechaInicio == '') data.fechaInicio = null;
            formData.append('fec_creacion', data.fechaInicio != null?moment(data.fechaInicio).format('DD/MM/YYYY'):''  );

            // 
            return $http.post(`${BASE_URL}${endpoint}`, formData, {
                headers: {
                    'Content-Type': undefined,
                    'mimeType': 'multipart/form-data',
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            });
        },
 
        getVimiCasilla: function (idcasilla) {
             
            const payload = {
                id_casilla: idcasilla
            };
            return $http.post(`${BASE_URL}/vimicasilla`, payload);

        },
 
        getListaSolicitudes_Documentos: function (data) {
            var fechaInicio = $filter('date')(data.fechaInicio, "dd/MM/yyyy");
            var fechaFin = $filter('date')(data.fechaFin, "dd/MM/yyyy");
             
            const payload = {
                id_estado_documento: data.estadodoc || '',
                id_documento: data.solicitud || '',
                id_oficina: data.organoDestino || '',
                id_tipo_documento: data.tipoDocumento || '',
                nro_documento: data.numeroDocumento || '',
                fec_ini: fechaInicio || '',
                fec_fin: fechaFin || '',
                asunto: data.asunto || '' 
            };
            return $http.post(`${BASE_URL}/listamisdocumentos`, payload);
        },

        getListarUno_CasillaView: function (idcasilla,modulo) {

             
            let endpoint = "/micasillauno"
            var formData = new FormData();
            formData.append('id_casilla', idcasilla);
            formData.append('tipo_modulo', modulo);
            // 
            return $http.post(`${BASE_URL}${endpoint}`, formData, {
                headers: {
                    'Content-Type': undefined,
                    'mimeType': 'multipart/form-data',
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            });

        },
        
        getExcel(data, grupoEstado) {
        	let endpoint = "/reportecasilla"
                var formData = new FormData();
                // formData.append('id_usuario', 2);

        	 formData.append('id_tipo_documento', data.tipoDocumentoNoti);
             formData.append('nro_documento', data.numeroDocumentoNoti);
             formData.append('nro_solicitud', data.solicitudNoti);
             formData.append('id_oficina', data.organoDestinoNoti);
             formData.append('observacion', data.observacion);
             
             formData.append('id_estado_documento', data.estado); 
              if (data.fechaInicio == '') data.fechaInicio = null;
             formData.append('fec_creacion', data.fechaInicio != null?moment(data.fechaInicio).format('DD/MM/YYYY'):''  );
 
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
