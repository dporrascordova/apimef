'use strict';
ventanillaVirtual.factory('loginService', loginService);

function loginService($http, BASE_URL) {

	return {

		 exportManualExterno() {                    
             let endpoint = "/exportmanualexterno"
             return $http.get(`${BASE_URL}${endpoint}`, 
             {
                 headers: {
                     'Content-Type': undefined,
                     'mimeType': 'multipart/form-data',
                 },
                 responseType: 'blob',              
             });
         },
         
		prueba2 : function() {
			var formData = new FormData();
			formData.append('mensaje', "mensaje del sistema API");
			formData.append('asunto', "asunto del sistema API");

			// formData.append('numero_sid', "117104");
			// formData.append('anio', "2020");
			formData.append('usu_modificacion', "API_tramite");

			formData.append('id_usuario', "97");
			formData.append('id_persona', "153");

			return $http.post(`${BASE_URL}/VE_atendersolicitud`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data'
				}
			});
		},

		Login : function(userData) {
			return $http.post(BASE_URL + '/login', userData);
		},
		EnviarCorreoLogin : function(modelo) {
			let endpoint = "/quieromiclave"
			var formData = new FormData();
			formData.append('UCodigo', "");
			formData.append('UCorreo', modelo.UCorreo);
			formData.append('UCaptcha', modelo.UCaptcha);
			// 
			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data'
				}
			});
		},
		verifiedcode : function(modelo) {
			let endpoint = "/verificamicodigo"
			var formData = new FormData();
			formData.append('UCodigo', modelo.UCodigo);
			formData.append('UCorreo', modelo.UCorreo);
			// 
			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data'
				}
			});
		},
		buscarpersona : function(modelo) {
			let endpoint = "/mefconsultapersona";
			return $http
					.get(`${BASE_URL}${endpoint}/${modelo.nrodocumento}/${modelo.tipopersona}`);
		},
		updatepassword : function(modelo) {
			let endpoint = "/actualizamiclave"
			var formData = new FormData();
			formData.append('token', modelo.token);
			formData.append('clave_usuario', modelo.clave_usuario);
			// 
			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data',
				// Authorization: `Bearer
				// ${localStorage.getItem('accessToken')}`
				}
			});
		},
		verificarCambioClave : function(modelo) {
			let endpoint = "/verificamiclave"
			var formData = new FormData();
			formData.append('UId_Usuario', modelo.UId_Usuario);
			// 
			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data',
				// Authorization: `Bearer
				// ${localStorage.getItem('accessToken')}`
				}
			});
		},
		
		
		mismodulos : function(modelo) {
			let endpoint = "/mismodulos"
			var formData = new FormData();
			formData.append('UId_Usuario', modelo.UId_Usuario);
			// 
			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data',
				// Authorization: `Bearer
				// ${localStorage.getItem('accessToken')}`
				}
			});
		},
		
		alerta_count : function(modelo) {
			let endpoint = "/count_alerta"
			var formData = new FormData();
			formData.append('UId_Usuario', modelo.UId_Usuario);
			// 
			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data',
				// Authorization: `Bearer
				// ${localStorage.getItem('accessToken')}`
				}
			});
		},
		

		Login2 : function(userData) {
			return $http.post(BASE_URL + '/login', userData);
		},

		registerUser : function(userData, pdf) {
			//              
			let endpoint = "";
			console.log(userData);
			var formData = new FormData();
			formData.append('tipopersona', userData.UTipoPersona);
			
			if(pdf != null)
			formData.append('mifile', pdf, pdf.name);
			// 
			if (userData.UTipoPersona === "01") {
				endpoint = "/crearpersonanatural";
				formData.append('tipodoc',
						(userData.UTipoDoc === undefined) ? 0
								: userData.UTipoDoc);
				formData.append('nrodocumento',
						(userData.UDocumento === undefined) ? 0
								: userData.UDocumento);
				formData.append('nombres',
						(userData.Nat_Nombres === undefined) ? ""
								: userData.Nat_Nombres);
				formData.append('apellidopaterno',
						(userData.Nat_Ape_Pat === undefined) ? ""
								: userData.Nat_Ape_Pat);
				formData.append('apellidomaterno',
						(userData.Nat_Ape_Mat === undefined) ? ""
								: userData.Nat_Ape_Mat);
				formData
						.append('fecnacimiento',
								userData.UNacimiento != null ? moment(
										userData.UNacimiento).format(
										'DD/MM/YYYY') : '');// (userData.UNacimiento
															// === undefined) ?
															// "" :
															// userData.UNacimiento);
				formData.append('codigoverificadni',
						(userData.UCodigoDNI === undefined) ? ""
								: userData.UCodigoDNI);
				formData.append('codigoubigeo',
						(userData.UCodigoUbigeo === undefined) ? ""
								: userData.UCodigoUbigeo);
			} else {
				debugger; 
				
				endpoint = "/crearpersonajuridica";
				formData.append('ruc', userData.Jur_RUC);
				formData.append('razon_social', userData.Jur_RazonSocial);

				formData.append('rep_legal_id_tipo_documento', userData.rep_legal_id_tipo_documento);
				
				formData.append('rep_legal_dni', userData.rep_legal_DNI);
				formData
						.append('rep_legal_nombres', userData.rep_legal_Nombres);
				formData
						.append('rep_legal_ape_pat', userData.rep_legal_Ape_Pat);
				formData
						.append('rep_legal_ape_mat', userData.rep_legal_Ape_Mat);
				formData.append('rep_legal_direccion',
						userData.rep_legal_direccion);
				formData
						.append('rep_legal_celular', userData.rep_legal_celular);
				formData.append('rep_legal_correo', userData.rep_legal_correo);

				
				formData.append('delegado_id_tipo_documento',
						(userData.UTipoDoc_Delegado === undefined) ? 0
								: userData.UTipoDoc_Delegado);
				
				formData.append('delegado_dni',
						(userData.delegado_DNI === undefined) ? ""
								: userData.delegado_DNI);
				formData.append('delegado_nombres',
						(userData.delegado_Nombres === undefined) ? ""
								: userData.delegado_Nombres);
				formData.append('delegado_ape_pat',
						(userData.delegado_Ape_Pat === undefined) ? ""
								: userData.delegado_Ape_Pat);
				formData.append('delegado_ape_mat',
						(userData.delegado_Ape_Mat === undefined) ? ""
								: userData.delegado_Ape_Mat);
				formData.append('delegado_direccion',
						(userData.delegado_Direccion === undefined) ? ""
								: userData.delegado_Direccion);
				formData.append('delegado_celular',
						(userData.delegado_Celular === undefined) ? ""
								: userData.delegado_Celular);
				formData.append('delegado_correo',
						(userData.delegado_Correo === undefined) ? ""
								: userData.delegado_Correo);
			}
			formData.append('celular', (userData.UCelular === undefined) ? 0
					: userData.UCelular);
			formData.append('telefono', (userData.UTelefono === undefined) ? 0
					: userData.UTelefono);
			formData.append('correo', (userData.UCorreo === undefined) ? ""
					: userData.UCorreo);
			formData.append('direccion',
					(userData.UDireccion === undefined) ? ""
							: userData.UDireccion);
			formData.append('iddepartamento',
					(userData.UDepartamento === undefined) ? ""
							: userData.UDepartamento);
			formData.append('idprovincia',
					(userData.UProvincia === undefined) ? ""
							: userData.UProvincia);
			formData.append('iddistrito',
					(userData.UDistrito === undefined) ? ""
							: userData.UDistrito);
			formData.append('terminos', (userData.UTerminos === undefined) ? ""
					: userData.UTerminos);
			formData.append('declaracion',
					(userData.UDeclaracion === undefined) ? ""
							: userData.UDeclaracion);
			formData.append('captcha', (userData.UCaptcha === undefined) ? ""
					: userData.UCaptcha);

			return $http.post(`${BASE_URL}${endpoint}`, formData, {
				headers : {
					'Content-Type' : undefined,
					'mimeType' : 'multipart/form-data',
				}
			});
		},
	}
}