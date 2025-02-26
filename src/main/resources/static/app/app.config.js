'use strict';
angular.module('app.config', []).config(configs).run(runs);
function configs($httpProvider) {
  $httpProvider.interceptors.push('httpResponseInterceptor');
  $httpProvider.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('accessToken');
}
// function configs($httpProvider) {
//     var interceptor = function($location, $log, $q,toaster) {
//         function error(response) {
//             debugger;
//             if (response.status === 401) {
//                 toaster.error('You are unauthorised to access the requested resource (401)');
//             } else if (response.status === 404) {
//                 toaster.error('The requested resource could not be found (404)');
//             } else if (response.status === 500) {
//                 toaster.error('Internal server error (500)');
//             }
//             return $q.reject(response);
//         }
//         function success(response) {
//             //Request completed successfully
//             return response;
//         }
//         return function(promise) {
//             return promise.then(success, error);
//         }
//     };
//     $httpProvider.interceptors.push(interceptor);
// }
function runs($rootScope){
  $rootScope.$on('$routeChangeSuccess', function (e, current, pre) {
      $rootScope.rootNav = current.rootNav;   
      localStorage.setItem('originalPath',current.$$route.originalPath);
  });
  $.extend(true, $.fn.DataTable.defaults, {
    language: {
        "decimal": ".",
        "thousands": ",",
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ registros",
        "sZeroRecords": "No se encontraron resultados",
        "sEmptyTable": "Ningún dato disponible en esta tabla",
        "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
        "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
        "sInfoPostFix": "",
        "sSearch": "Buscar:",
        "sUrl": "",
        "sInfoThousands": ",",
        "sLoadingRecords": "Cargando...",
        "oPaginate": {
            "sFirst": "Primero",
            "sLast": "Último",
            "sNext": "Siguiente",
            "sPrevious": "Anterior"
        },
        "oAria": {
            "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
            "sSortDescending": ": Activar para ordenar la columna de manera descendente"
        }
    },
    // pageLength: 10,
    //ordering: false,
    searching: false,
    responsive: false,
    processing: true,
    //serverSide: true,
    'autoWidth': false,
    "bLengthChange": false
  });
  // $.extend( $.fn.dataTable.defaults, {
  //   "language": {
  //     "sProcessing":     "Procesando...",
  //     "sLengthMenu":     "Mostrar _MENU_ registros",
  //     "sZeroRecords":    "No se encontraron resultados",
  //     "sEmptyTable":     "Ningún dato disponible en esta tabla",
  //     "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
  //     "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
  //     "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
  //     "sInfoPostFix":    "",
  //     "sSearch":         "Buscar:",
  //     "sUrl":            "",
  //     "sInfoThousands":  ",",
  //     "sLoadingRecords": "Cargando...",
  //     "oPaginate": {
  //         "sFirst":    "Primero",
  //         "sLast":     "Último",
  //         "sNext":     "Siguiente",
  //         "sPrevious": "Anterior"
  //     },
  //     "oAria": {
  //         "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
  //         "sSortDescending": ": Activar para ordenar la columna de manera descendente"
  //     },
  //     "buttons": {
  //         "copy": "Copiar",
  //         "colvis": "Visibilidad"
  //     }      
  //   }
  // });
};

// function runs($http, toaster) {
//   toaster.success({ title: 'title', body: 'text1' });
// }
// function runs($rootScope, PageValues) {
//     $rootScope.$on('$routeChangeStart', function() {
//         PageValues.loading = true;
//     });
//     $rootScope.$on('$routeChangeSuccess', function() {
//         PageValues.loading = false;
//     });
// }
