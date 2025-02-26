'use strict';

var env = location.origin + "/" + location.pathname.split("/")[1] + "/api";// "/mef";
var getUrl = window.location;
var baseUrl = getUrl.protocol + "//" + getUrl.host + "/api";

var ventanillaVirtual = angular.module('app', [ 'ngRoute', 'app.routes',
		'app.core', 'app.services', 'app.config', 'toaster', 'angular-jwt',
		'ngAnimate', 'ngSanitize', 'ui.bootstrap', 'fayzaan.gRecaptcha.v3',
		'ngBootbox', 'datatables', 'ngResource', ]);

ventanillaVirtual
 .constant('BASE_URL', env) // produccion
 .constant('BASE_URL_CAPTCHA', env.replace('/api', '/captcha-servlet'))

//.constant('BASE_URL', 'http://localhost:8888/api') // local
//.constant('BASE_URL_CAPTCHA', 'http://localhost:8888/captcha-servlet')

 //.constant('BASE_URL', 'http://localhost:8888/api') // local
// .constant('BASE_URL_CAPTCHA', 'http://localhost:8888/captcha-servlet') //
// local
// .constant('KEY_RECHAPTCHA', '6Lc2HMQZAAAAAOdNlIWk1ODRHIVI0s0GvGf_TZAF');

ventanillaVirtual
		.run(function($rootScope, $ngBootbox) {
			
			$rootScope.AnexoTotalMaxFiles = 10;
			$rootScope.docMaxFileSize = 26214400; // 25MB
			$rootScope.AnexoTotalMaxFileSize = 157286400; // 150MB
			$rootScope.AnexoExtensions = 'PDF, DOC, DOCX, XLS, XLSX, PPT, PPTX,TXT y JPG';
			  
			$rootScope.showAlert = function(options) {
				var data = angular.extend({
					type : 'warning',
					size : 'small',
					title : '!Advertencia¡',
					message : 'Sin mensaje',
					buttons : {
						ok : {
							label : '<i class="fa fa-check"></i> Aceptar',
							className : 'btn-secondary'
						}
					}
				}, options);
				// data.buttons.ok.className = `btn-${data.type}`;
				$ngBootbox.alert(data).then(function(response) {
				}, function(error) {
				});
			};

			$rootScope.validateExtensionFile = function(file, patern) {
				var extension = file.substr(file.lastIndexOf('.') + 1), patern = patern || /(pdf|txt|doc|docx|xls|xlsx|ppt|pptx|jpg|jpeg)$/gi;
				if (!patern.test(extension)) {
					return false;
				}
				return true;
			};

			$rootScope.applicationType = function(type) {
				var applicationType = null;
				type = type.toLowerCase();
				switch (type) {
				case 'pdf':
					applicationType = 'application/pdf';
					break;
				case 'txt':
					applicationType = 'text/plain';
					break;
				case 'xls':
					applicationType = 'application/vnd.ms-excel';
					break;
				case 'xlsx':
					applicationType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
					break;
				case 'doc':
					applicationType = 'application/msword';
					break;
				case 'docx':
					applicationType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
					break;
				case 'ppt':
					applicationType = 'application/vnd.ms-powerpoint';
					break;
				case 'pptx':
					applicationType = 'application/vnd.openxmlformats-officedocument.presentationml.presentation';
					break;
				case 'jpg' || 'jpeg':
					applicationType = 'image/jpeg';
					break;
				case 'png':
					applicationType = 'image/png';
					break;
				default:
					applicationType = 'application/octet-stream';
					break;
				}
				return applicationType;
			};
			
			$rootScope.bytesToFormat= function(bytes){
				var units = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
				let l = 0, n = parseInt(bytes, 10) || 0;

				  while(n >= 1024 && ++l){
				      n = n/1024;
				  }
				  
				  return(n.toFixed(n < 10 && l > 0 ? 1 : 0) + ' ' + units[l]);
			}
			
			$rootScope.getDepartamentos= function(ubigeo){
				return ubigeo.filter(x=>x.coddist==0 && x.codprov==0)
				.map(function(element){
					return {id_departamento:element.coddpto,desc_departamento:element.nombre}
				});
			}
			
			$rootScope.getProvincias= function(ubigeo,iddepartamento){
				return ubigeo.filter(x=>x.coddpto ==iddepartamento && x.coddist==0 && x.codprov!=0)
				.map(function(element){
					return {id_provincia:element.codprov,desc_provincia:element.nombre}
				});
			}
			
			$rootScope.getDistritos= function(ubigeo,iddepartamento, idprovincia){
				return ubigeo.filter(x=>x.coddpto ==iddepartamento && x.codprov==idprovincia && x.coddist!=0)
				.map(function(element){
					return {id_distrito:element.coddist,desc_distrito:element.nombre}
				});
			}
			
			$rootScope.isNullOrEmpty = function(value){
				return value == null || value.length == 0;
			}			
			
			$rootScope.dateIsNullOrUndefined = function(value){
				return value === undefined || value=== null;
			}	
			
			$rootScope.monthDiff = function(dateFrom, dateTo) {
				return (dateTo.getDate() - dateFrom.getDate()) / 30 +
				dateTo.getMonth() - dateFrom.getMonth() +
			    (12 * (dateTo.getFullYear() - dateFrom.getFullYear()));
			}
			
			$rootScope.abbreviateNumber= function(value) {
			  let newValue = value; // Asignamos el valor que llega a la función
									// en una variable

			  const suffixes = ["", "K", "M", "B","T"]; // establecemos un array
														// de sufijos,
			// los cuales representan: Menor que Mil (""), Miles (K), Millones
			// (M), Billones (B) y Trillones (T)

			  let suffixNum = 0; // Se declara suffixNum para establecer la
									// posición del Array

			// El siguiente While nos dice "Mientras el valor es mayor o igual a
			// 1000" entonces divídelo entre mil
			// y suma uno a "suffixNum" (así va estableciendo la posición del
			// array)
			  while (newValue >= 1000) { 
			    newValue /= 1000;
			    suffixNum++;
			  }

			// A nuestro valor le especificamos una "precisión de 3", lo que
			// hace es que redondeada a una precision de dígitos significativos.
			  newValue = newValue.toPrecision(3);

			// Por último concatenamos el sufijo establecido anteriormente
			  newValue += suffixes[suffixNum];
			  return newValue;
			}
			
			$rootScope.getStatusClass = function(controlClass, statusId) {
				console.log('Estado ID: ',statusId);
				   var classes = {},
				   bsClass = statusId == 1? 'warning' : statusId == 2? 'primary' :statusId == 3? 'success' :statusId == 4? 'orange' :statusId == 5? 'success' :statusId == 6? 'dark' :statusId == 7? 'info' :statusId == 8? 'secondary':'light';
				     classes[controlClass+'-'+bsClass] = true;	
				   return classes;
				}
		});
