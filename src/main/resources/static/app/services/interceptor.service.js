'use strict';

ventanillaVirtual
  .factory('httpResponseInterceptor', [
    '$q',
    '$rootScope',
    '$location',
    '$log',
    function ($q, $rootScope, $location, $log) {
      return {
        responseError: function (response) {
          if (response.status === 401) {
            //location.reload();
            //$location.path('signin/invalidSession');
            //$log.error('You are unauthorised to access the requested resource (401)');
            //location.reload();
          } else if (response.status === 404) {
            $log.error('The requested resource could not be found (404)');
            return response;
          } else if (response.status === 500) {
            $log.error('Internal server error (500)');
            return response;
          } else {
              return $q.reject(response);
          }
        },
      };
    },
  ]);
