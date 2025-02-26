'use strict';
//var App = angular.module('app.routes', ['ngRoute']);
angular.module('app.routes', ['ngRoute'])
    .config(config);

//angular.config (function ($routeProvider, $locationProvider) {
function config ( $routeProvider) {
	/*$locationProvider.html5Mode({
	    enabled:true
	 });*/
	 //$locationProvider.html5Mode(true);
	//$locationProvider.hashPrefix('');
    $routeProvider
        .when('/', {
            templateUrl: 'pages/home/home.html'
        })
        .when('/login', {
            templateUrl: 'login.html',
            controller: 'loginController',
            controllerAs: 'login'
        })
        .when('/home', {
            templateUrl: 'pages/home/home.html',
            controller: 'homeController',
            controllerAs: 'home',
            rootNav : ''           
        })
        .when('/casilla', {
            templateUrl: 'pages/casilla/casilla.html',
            controller: 'casillaController',
            rootNav : 'Casilla'           
        })
        .when('/mesa-partes-externa', {
            templateUrl: 'pages/mpe/mpe.html',
            controller: 'mpeController',
            controllerAs: 'mpe', 
            rootNav : 'Mesa de partes externa'
        })       
        .when('/mesa-partes-interna', {
            templateUrl: 'pages/mpi/mpi.html',
            controller: 'mpiController',
            controllerAs: 'mpi',
            rootNav : 'Mesa de partes interna'
        })
        .when('/noti', {
            templateUrl: 'pages/noti/noti.html',
            controller: 'notiController',
            controllerAs:'noti',
            rootNav : 'Notificación'           
        })

        .when('/ofi', {
            templateUrl: 'pages/ofi/ofi.html',
            controller: 'ofiController',
            controllerAs:'ofi',
            rootNav : 'Oficinas Externas'           
        })

        .when('/usuarios', {
            templateUrl: 'pages/usuarios/usuarios.html',
            controller: 'usuariosController',
            rootNav : 'Usuarios'           
            // resolve: {
            //     shows: function(ShowService) {
            //         return ShowService.getPopular();
            //     }
            // }
        })     
        .when('/reportes', {
            templateUrl: 'pages/reportes/reportes.html',
            controller: 'ReportesController',
            rootNav : 'Reportes'           
        }) 
        .when('/encuestas', {
            templateUrl: 'pages/encuesta/encuesta.html',
            controller: 'encuestaController',
            rootNav : 'Configuración de Encuestas'           
        }) 
        .otherwise({
            redirectTo: '/'
        });
}


// .when('/view/:id', {
//     templateUrl: 'sections/view/view.tpl.html',
//     controller: 'ViewController as view',
//     resolve: {
//         show: function(ShowService, $route) {
//             return ShowService.get($route.current.params.id);
//         }
//     }
// })