'use strict';
ventanillaVirtual.factory('ofiService', ofiService);

function ofiService($http, BASE_URL) {
	return {
		getMEFTipoDocumento : function() {
			return $http.get(`${BASE_URL}/listatipodedoc`);
		},

		getMEFOficinas : function() {
			return $http.get(`${BASE_URL}/listaoficinas`);
		},

		getEstadoExpediente : function(numeroSid, anio) {
			return $http
					.get(`${BASE_URL}/consultaestadoexpediente/${anio}/lmauricio/${numeroSid}`);
		},

		getListaofificaciones : function(data, grupoEstado) {

			let endpoint = "/miofi"
			var formData = new FormData();
			formData.append('id_usuario', 2);
			formData.append('id_tipo_documento', data.tipoDocumentoofi);
			formData.append('nro_documento', data.numeroDocumentoofi);
			formData.append('id_oficina', data.organoDestinoofi);
			formData.append('numero_sid', data.solicitudofi);
			formData.append('fec_ini', data.fechaInicioofi);
			formData.append('fec_fin', data.fechaFinofi);

			// 
			return $http
					.post(
							`${BASE_URL}${endpoint}`,
							formData,
							{
								headers : {
									'Content-Type' : undefined,
									'mimeType' : 'multipart/form-data',
									Authorization : `Bearer ${localStorage.getItem('accessToken')}`
								}
							});
		},

		getListaOficinas : function(data) {

			const payload = {
				descripcion : data.descripcion || '',
				acronimo : data.acronimo || '',
				codigo : data.codigo || '',
				descripcioncompleta : data.desc_completa || '',
				jefe : data.jefe || ''
			};
			return $http.post(`${BASE_URL}/listaoficinastodo`, payload);
		},

		getListaTipoDocL : function(data) {

			const payload = {
					desc_tipodocumento : data.desc_tipodocumento || '' 
			};
			return $http.post(`${BASE_URL}/listatipodoctodo`, payload);
		},
		
		CambiarEstadoTipoDoc : function(data) {
 

			var formData = new FormData();
			formData.append('id_tipodocumento', data.id_tipodocumento);
			formData.append('flg_estado', data.flg_estado);
			return $http.post(`${BASE_URL}/tipodocestado`, formData, {
				headers : {
					'Content-Type' : undefined,
				// mimeType: 'multipart/form-data'
				}

			});
		},
		
		CambiarEstado : function(data) {

			/*
			 * const payload = { idunidad: data.idunidad , flg_externo:
			 * data.flg_externo, }; return
			 * $http.post(`${BASE_URL}/oficinaestadoex`, payload);
			 */

			var formData = new FormData();
			formData.append('idunidad', data.idunidad);
			formData.append('flg_externo', data.flg_externo);
			return $http.post(`${BASE_URL}/oficinaestadoex`, formData, {
				headers : {
					'Content-Type' : undefined,
				// mimeType: 'multipart/form-data'
				}

			});
		},

	};
}
