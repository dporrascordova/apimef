'use strict';
ventanillaVirtual.factory('mpeService', mpeService);

function mpeService($http, BASE_URL) {
  return {
    getMEFTipoDocumento: function () {
      return $http.get(`${BASE_URL}/listatipodedoc`);
    },

    getMEFOficinas: function () {
      return $http.get(`${BASE_URL}/listaoficinasexterno`);
    },

    getEstadoExpediente: function (numeroSid, anio) {
      return $http.get(
        `${BASE_URL}/consultaestadoexpediente/${anio}/lmauricio/${numeroSid}`
      );
    },

    getExcel(data, grupoEstado) {
      var payload = {
        id_estado_documento: data.estado || '',
        id_documento: data.numeroSolicitud || '',
        id_oficina: data.organoDestino || '',
        id_tipo_documento: data.tipoDocumento || '',
        nro_documento: data.numeroDocumento || '',
        fec_ini: data.fechaInicio != null?moment(data.fechaInicio).format('DD/MM/YYYY'):'',
        fec_fin: data.fechaFin != null?moment(data.fechaFin ).format('DD/MM/YYYY'):'',
        asunto: data.asunto || '',
        grupo_estados: grupoEstado || ''
      };

      return $http.post(`${BASE_URL}/reporte`, payload, {
        responseType: 'blob'
      });
    },

    getListaDocs: function (data, grupoEstado) {
      var payload = {
        id_estado_documento: data.estado || '',
        id_documento: data.numeroSolicitud || '',
        id_oficina: data.organoDestino || '',
        id_tipo_documento: data.tipoDocumento || '',
        nro_documento: data.numeroDocumento || '',
        fec_ini: data.fechaInicio != null?moment(data.fechaInicio).format('DD/MM/YYYY'):'',
        fec_fin: data.fechaFin != null?moment(data.fechaFin ).format('DD/MM/YYYY'):'',
        asunto: data.asunto || '',
        grupo_estados: grupoEstado || ''
      };
      return $http.post(`${BASE_URL}/listdoc3`, payload);
    },
    
    getListaPaginadoDocs: function (data,numpagina,numregistro, grupoEstado) {  
    	debugger; 
        var payload = {
          id_estado_documento: data.estado || '',
          id_documento: data.numeroSolicitud || '',
          id_oficina: data.organoDestino || '',
          id_tipo_documento: data.tipoDocumento || '',
          nro_documento: data.numeroDocumento || '',
          fec_ini: data.fechaInicio != null?moment(data.fechaInicio).format('DD/MM/YYYY'):'',
          fec_fin: data.fechaFin != null?moment(data.fechaFin ).format('DD/MM/YYYY'):'',
          asunto: data.asunto || '',
          razon_social: data.RazonSocial || '',
          destino: data.Destino || '',
          grupo_estados: grupoEstado || '', 
          orden:data.orden!=null ?data.orden.join():'',
          numpag: numpagina,
          numreg: numregistro
        };
        return $http.post(`${BASE_URL}/listdoc-paginado`, payload);
      },

    getListaAtendidos: function (data, grupoEstado) {
       
      var payload = {
        id_estado_documento: data.estado || '',
        id_documento: data.numeroSolicitud || '',
        id_oficina: data.organoDestino || '',
        id_tipo_documento: data.tipoDocumento || '',
        nro_documento: data.numeroDocumento || '',
        fec_ini: data.fechaInicio || '',
        fec_fin: data.fechaFin || '',
        asunto: data.asunto || '',
        grupo_estados: grupoEstado || ''
      };
      return $http.post(`${BASE_URL}/listadocatendido`, payload);
    },




    registrarDocumento: function (data, anexos , files_link) {
      var formData = new FormData();
       
      formData.append('id_tipo_documento', data.tipoDocumento);
      formData.append('nro_documento', data.nroDocumento);
      formData.append('id_oficina', data.organoDestino);
      formData.append('nro_folios', data.nroFolios);
      formData.append('t_num_doc', data.t_num_doc);
      formData.append('asunto', data.asunto);
      formData.append('hoja_ruta', data.nroExpediente || '');
      formData.append('file', data.fileInput[0], data.fileInput[0].name);
      //formData.append('anexos', files_link);
 
      files_link.forEach(file => {
         formData.append('anexoslink',  file.nombre_archivo); 
       });

      
      anexos.forEach(file => {
    	 if(file.flg_link == 1 )
        formData.append('filesAnexos', file.file, file.file.name);
      });

      
      return $http.post(`${BASE_URL}/registrar-solicitud`, formData, {
        headers: {
          'Content-Type': undefined,
          mimeType: 'multipart/form-data'
        }
      });
      
    },

    actualizarDocumento(data, anexos, pdf, files_link) {
      var formData = new FormData();
      formData.append('id_documento', data.id_documento);
      formData.append('id_tipo_documento', data.id_tipo_documento);
      formData.append('nro_documento', data.nro_documento);
      formData.append('id_oficina', data.id_oficina);
      formData.append('nro_folios', data.nro_folios);
      formData.append('asunto', data.asunto);
      formData.append('id_estado_documento', data.id_estado_documento);
      formData.append('hoja_ruta', data.hoja_ruta || '');
       
      files_link.forEach(file => {
          formData.append('anexoslink',  file.nombre_archivo); 
        });


      if (typeof pdf != 'undefined') {
        formData.append('file', pdf[0], pdf[0].name);
      }      
      
      data.anexos.forEach(file => {
        formData.append('codigo_archivo_anexos', file.codigo_archivo);
        formData.append('codigo_archivo_anexos', file.extension_archivo);
      });

      anexos.forEach(file => {
    	  if(file.flg_link == 1 && file.flg_nuevo == 1  )
        formData.append('filesAnexos', file.file, file.file.name);
      });

      return $http.post(`${BASE_URL}/actualizar-solicitud`, formData, {
        headers: {
          'Content-Type': undefined,
          mimeType: 'multipart/form-data'
        }
      });
    },

    getObservacionDocumentoById(documentoId) {
      return $http.get(
        `${BASE_URL}/obtener-observacion-solicitud/${documentoId}`
      );
    },

    actulizarEstadoDoc: function (modelo) {
      debugger; 
      let endpoint = "/actualizarEstado-doc"
      return $http.post(`${BASE_URL}${endpoint}`, modelo);
  },   

  	getDocumentSettings() {
      return $http.get(
        `${BASE_URL}/document-settings`
      );
    },   

    registrarAnexo(documentoId, file, isMPI) {
        var formData = new FormData();
        formData.append('id_documento', documentoId);
        formData.append('crea_MPI', isMPI || false);
        formData.append('file', file, file.name);

        return $http.post(`${BASE_URL}/registrar-anexo`, formData, {
          headers: {
            'Content-Type': undefined,
            mimeType: 'multipart/form-data'
          }
        });
      },
      
      getAnexosByDocumentoId(documentoId) {
          return $http.get(
            `${BASE_URL}/obtener-anexos/${documentoId}`
          );
        },
        
    anularAnexo: function (documentoId, codigoArchivo) {
        var payload = {
        		id_documento : documentoId,
        		codigo_archivo: codigoArchivo
        };
        return $http.post(`${BASE_URL}/anular-anexo`,payload);
    },
  };
}
