'use strict';
ventanillaVirtual.factory('personaService', personaService);

function personaService($http,BASE_URL) { 
    return { 
        buscarpersona: function(modelo,valido){   
              
            let endpoint = "/listarunapersona";
            return $http.get(`${BASE_URL}${endpoint}/${modelo.id_persona}/${modelo.id_tipo_persona}/${valido}`); 
    
        },   
        
        validarpersona: function(modelo){  
            
            const endpoint = '/validapersona'; 

            return $http.post(`${BASE_URL}${endpoint}`, modelo, {
                headers: {
                    'Content-Type': undefined, 
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            }); 
        }, 

        getMEFPerfil: function () {
            const endpoint = '/seg_perfil'; 
            // return $http.get(`${BASE_URL}/seg_perfil`);
            return $http.post(`${BASE_URL}${endpoint}`, {
                headers: {
                    'Content-Type': undefined, 
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            }); 
        },

        getMEFPersona: function () {
            const endpoint = '/seg_personal'; 
           // return $http.get(`${BASE_URL}/seg_personal `);
            return $http.post(`${BASE_URL}${endpoint}`, {
                headers: {
                    'Content-Type': undefined, 
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            }); 
        },
        

        getMEFPersonaNew: function () {
            
          const endpoint = '/consultapersonalmef'; 
          return $http.get(`${BASE_URL}${endpoint}`, {
              headers: {
                  'Content-Type': undefined, 
                  Authorization: `Bearer ${localStorage.getItem('accessToken')}`
              }
          }); 
        },


        


        registrarUsuario: function (data,fechas) {
     
           var persona = JSON.parse(data.Persona); 
    
            var formData = new FormData();
            formData.append('nombre', persona.usNombre);
            formData.append('apellido_paterno', persona.usApellidoPaterno);
            formData.append('apellido_materno', persona.usApellidoMaterno);
            formData.append('cod_usuario', persona.usUsername);
            formData.append('id_oficina', persona.usIdunidad);
            formData.append('id_usuario', persona.usIduser);
            formData.append('correo', persona.usEmail);

            formData.append('tipo_usuario', '1');
            formData.append('id_perfil', data.Perfil);
            formData.append('fec_activacion', fechas.fec_acti);
            formData.append('fec_desactivacion', fechas.fec_desa); 
            return $http.post(`${BASE_URL}/registrausuariointerno`, formData, {
              headers: {
                'Content-Type': undefined,
                'mimeType': 'multipart/form-data'
              }
            });
          },

          

          ActualizarUsuario: function (data,fechas) {
              
            var formData = new FormData();
            formData.append('id_usuario', data.id_usuario);
            formData.append('fec_activacion', fechas.fec_acti);
            formData.append('fec_desactivacion', fechas.fec_desa);
            // /formData.append('id_usuario', 'dss');
            // formData.append('fec_activacion', '20/02/2020');
            // formData.append('fec_desactivacion', '20/02/2020');
   
            return $http.post(`${BASE_URL}/activausuario`, formData, {
              headers: {
                'Content-Type': undefined,
                'mimeType': 'multipart/form-data'
              }
            });
          },



          EliminarUsuario: function (data) {
              
            var formData = new FormData();
            formData.append('id_usuario', data.id_usuario);

   
            return $http.post(`${BASE_URL}/eliminausuario`, formData, {
              headers: {
                'Content-Type': undefined,
                'mimeType': 'multipart/form-data'
              }
            });
          },

          CambiarEstado: function (data) {
        	  // debugger;
            var formData = new FormData();
            formData.append('id_usuario', data.id_usuario);
            formData.append('flg_estado', data.flg_estado);
            return $http.post(`${BASE_URL}/estadousuario`, formData, {
              headers: {
                'Content-Type': undefined,
                'mimeType': 'multipart/form-data'
              }
            });
          },

          
          getListaUsuariosPersona: function (data,flg_estado,valido,numpagina,numregistro) {
            debugger;
            const payload = {
                id_tipo_usuario : data.personatab2 || '',
                flg_estado : flg_estado,
                nom_persona : data.nombretab2 || '',
                nro_documento : data.nro_documetotab2 || '',
                celular : data.celulartab2 || '',
                telefono: data.telefonotab2 || '',
                correo: data.correotab2 || '',
                direccion:'',
                id_tipo_documento: data.Id_tipo_documento_tab2  || '4', // null todos los documentos
                fec_inicio:  data.fechaInicio_tab2 != null?moment(data.fechaInicio_tab2).format('DD/MM/YYYY'):'',
                fec_fin:  data.fechaFin_tab2 != null?moment(data.fechaFin_tab2).format('DD/MM/YYYY'):'',  
                
                flg_valido : valido,
                numpag: numpagina,
                numreg: numregistro
              };

              return $http.post(`${BASE_URL}/listarusuariospersonas`, payload);
            }, 
            
            getExcel_Usuarios(data,flg_estado,valido) {
                var payload = {
                	     id_tipo_usuario : data.personatab2 || '',
                         flg_estado : flg_estado,
                         nom_persona : data.nombretab2 || '',
                         nro_documento : data.nro_documetotab2 || '',
                         celular : data.celulartab2 || '',
                         telefono: data.telefonotab2 || '',
                         correo: data.correotab2 || '',
                         direccion:'',
                         id_tipo_documento: data.Id_tipo_documento_tab2  || '4', // null todos los documentos
                         fec_inicio:  data.fechaInicio_tab2 != null?moment(data.fechaInicio_tab2).format('DD/MM/YYYY'):'',
                         fec_fin:  data.fechaFin_tab2 != null?moment(data.fechaFin_tab2).format('DD/MM/YYYY'):'',  
                         
                         flg_valido : valido,
                };

                return $http.post(`${BASE_URL}/reporte_usuarios`, payload, {
                  responseType: 'blob'
                });
              },
            

            getListaSolicitudesUsuarios: function (data,flg_estado,valido,numpagina,numregistro) {
              const payload = {
                  id_tipo_usuario : data.personatab1 || '',
                  flg_estado: flg_estado,
                  nom_persona : data.nombretab1  || '',
                  nro_documento : data.nro_documetotab1 || '',
                  direccion : data.direcciontab1  || '',
                  celular : data.celulartab1  || '',
                  telefono: data.telefonotab1  || '',
                  correo: data.correotab1  || '',
                  id_tipo_documento: data.Id_tipo_documento_tab1  || '4',  //si es null  4 todos los documentos
                  fec_inicio:  data.fechaInicio_tab1 != null?moment(data.fechaInicio_tab1).format('DD/MM/YYYY'):'',
                  fec_fin:  data.fechaFin_tab1 != null?moment(data.fechaFin_tab1).format('DD/MM/YYYY'):'',                 
                  flg_valido : valido ,
                  numpag: numpagina,
                  numreg: numregistro
                };
  
                return $http.post(`${BASE_URL}/listarpersonas`, payload);
              }, 

              getListaObservadas: function (data,flg_estado,valido,numpagina,numregistro) {
                
                  const payload = {
                    id_tipo_usuario : data.personatab3 || '',
                    flg_estado: flg_estado,
                    nom_persona : data.nombretab3 || '',
                    nro_documento : data.nro_documetotab3 || '',
                    direccion : data.direcciontab3 || '',
                    celular : data.celulartab3 || '',
                    telefono: data.telefonotab3 || '',
                    correo: data.correotab3 || '',
                    
                    id_tipo_documento: data.Id_tipo_documento_tab3  || '4', // si null  4 todos los documentos
                    fec_inicio:  data.fechaInicio_tab3 != null?moment(data.fechaInicio_tab3).format('DD/MM/YYYY'):'',
                    fec_fin:  data.fechaFin_tab3 != null?moment(data.fechaFin_tab3).format('DD/MM/YYYY'):'',  
                    
                    flg_valido : valido,
                    numpag: numpagina,
                    numreg: numregistro
                  }
                  return $http.post(`${BASE_URL}/listarpersonas`, payload);
                }, 
          
       

                exportFile(fileType, fileName) {                    
                  let endpoint = "/exportsolo"
                  return $http.get(`${BASE_URL}${endpoint}/${fileType}/${fileName}`, 
                  {
                      headers: {
                          'Content-Type': undefined,
                          'mimeType': 'multipart/form-data',
                      },
                      responseType: 'blob',              
                  });
              },
          
              persona_actualizar: function (userData, pdf) {
                  //              
                  let endpoint = "";
                  console.log(userData); 
                  var formData = new FormData();
                  formData.append('idpersona', userData.idpersona);
                  formData.append('tipopersona', userData.tipopersona);
                  var blob = new Blob([JSON.stringify([0,1,2])], {type : 'application/json'});
                 // formData.append('mifile', blob, "-");
                  // 
                  debugger; 
                  if(pdf == "")
                	  formData.append('mifile', blob, "-");
                  else
                	  formData.append('mifile', pdf, pdf.name);
                  if (userData.tipopersona === "1") {
                      endpoint = "/personaNactualiza";
                      formData.append('apellidopaterno', "-");
                      formData.append('apellidomaterno', "-");
                      formData.append('nombres', "-");
                      formData.append('nrodocumento', "12345678");
                      formData.append('tipodoc', "6");
                      formData.append('fecnacimiento', "01/01/2000");
                      formData.append('codigoverificadni', "2");
                      formData.append('codigoubigeo', "123456");
                  } else {
                      endpoint = "/personaJactualiza";
                      formData.append('ruc', "12345678938");
                      formData.append('razon_social', "-");

                      formData.append('rep_legal_dni', userData.rep_legal_dni);
                      formData.append('rep_legal_nombres', userData.rep_legal_nombres);
                      formData.append('rep_legal_ape_pat', userData.rep_legal_ape_pat);
                      formData.append('rep_legal_ape_mat', userData.rep_legal_ape_mat);
                      formData.append('rep_legal_direccion', userData.rep_legal_direccion);
                      formData.append('rep_legal_celular', userData.rep_legal_celular);
                      formData.append('rep_legal_correo', userData.rep_legal_correo);
                      formData.append('rep_legal_id_tipo_documento', userData.rep_legal_id_tipo_documento);
                      
                      formData.append('delegado_dni', userData.delegado_dni);
                      formData.append('delegado_nombres', userData.delegado_nombres);
                      formData.append('delegado_ape_pat', userData.delegado_ape_pat);
                      formData.append('delegado_ape_mat', userData.delegado_ape_mat);
                      formData.append('delegado_direccion', userData.delegado_direccion);
                      formData.append('delegado_celular', userData.delegado_celular);
                      formData.append('delegado_correo', userData.delegado_correo); 
                      formData.append('delegado_id_tipo_documento', userData.delegado_id_tipo_documento); 
                     debugger; 
                  }
                  debugger; 
                  formData.append('celular', (userData.celular === undefined) ? 0 : userData.celular);
                  formData.append('telefono', (userData.telefono === undefined) ? 0 : userData.telefono);
                  formData.append('correo', (userData.correo === undefined) ? "" : userData.correo);
                   
                  formData.append('direccion',   userData.direccion);
                  formData.append('captcha',   "-");
                  formData.append('iddepartamento',  userData.iddepartamento);
                  formData.append('idprovincia',   userData.idprovincia);
                  formData.append('iddistrito',   userData.iddistrito);

                  return $http.post(`${BASE_URL}${endpoint}`, formData, {
                      headers: {
                          'Content-Type': undefined,
                          'mimeType': 'multipart/form-data', 
                      }
                  });
              }

        
    }
}