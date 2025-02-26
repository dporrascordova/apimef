'use strict';
ventanillaVirtual.factory('transversalService', transversalService);

    function transversalService($http,BASE_URL) {
        
        return {            
            getUbigeo: function () {                 
                let endpoint = "/listaubigeo";
                return $http.get(`${BASE_URL}${endpoint}`);            
            },
            get_departamento: function () {                 
                let endpoint = "/listadep";
                return $http.get(`${BASE_URL}${endpoint}`);            
            },
            
            get_repetidos: function (tipopersona,nrodocpersona,idclaseRep,nrodocumento) {  
                let endpoint = "/listarepetidos" 
              //  return $http.get(`${BASE_URL}${endpoint}/${tipopersona}/${nrodocpersona}/${idclaseRep}/${nrodocumento}`);     
             return $http.get(`${BASE_URL}${endpoint}?tipopersona=${tipopersona}&nrodocpersona=${nrodocpersona}&idclaseRep=${idclaseRep}&nrodocumento=${nrodocumento}`);   
                	
            },

            get_provincia: function (id_departamento) {                 
                let endpoint = "/listaprovincia";                
                return $http.get(`${BASE_URL}${endpoint}/${id_departamento}`);           
            },

            get_distrito: function (id_provincia,id_departamento) {        
                let endpoint = "/listadistrito" 
                return $http.get(`${BASE_URL}${endpoint}/${id_provincia}/${id_departamento}`);         
            },
            
            getCongresistas: function () {                 
                let endpoint = "/listacongresistas";
                return $http.get(`${BASE_URL}${endpoint}`);            
            },
            
            getClasificaciones: function () {                 
                let endpoint = "/listaClasificaciones";
                return $http.get(`${BASE_URL}${endpoint}`);            
            },
            getComisiones: function () {                 
                let endpoint = "/listaComisiones";
                return $http.get(`${BASE_URL}${endpoint}`);            
            }
            
            
            
            
        }
    }