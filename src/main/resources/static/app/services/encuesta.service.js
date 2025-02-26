'use strict';
ventanillaVirtual.factory('encuestaService', encuestaService);
function encuestaService($http, BASE_URL) {
	return {
        crear: function (modelo) {
            let endpoint = `/encuesta/crear`
            return $http.post(`${BASE_URL}${endpoint}`, modelo);
        }, 
    	getEncuestaActiva : function() {
			return $http.get(`${BASE_URL}/encuesta/encuesta-activa`);
		},
	};
}