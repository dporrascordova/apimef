'use strict';
angular    
.module('app.services')
    .constant('BASE_URL', 'http://181.64.50.131:1234/mef/api/')
    .factory('usuariosFactory', usuarioFactory);

    function usuarioFactory($http, BASE_URL) {

        loginUser(userData) {
            return this.http.post(`${environment.apiUrl}login`, userData)
        },
        
        
    }