'use strict';
ventanillaVirtual.controller('observadaController', function ($scope,$rootScope, $uibModalInstance, $ngBootbox, personaService, $http, BASE_URL, request, $filter, item) {

  $scope.showExtraFields = true;
  $scope.request = request;
  if($scope.request != null){
   if($scope.request.tipopersona == "2"){
	  $scope.request.delegado_id_tipo_documento = request.delegado_id_tipo_documento.toString();
	  $scope.request.rep_legal_id_tipo_documento = request.rep_legal_id_tipo_documento.toString();
	  }
  }
  $scope.item = item;
  $scope.comments = '';
  $scope.isPeruvian = request == null || !(request.tipopersona=== '1' && request.tipodoc!= 1);

  $scope.downloadFile = function() {
      
    var arrayarchivo = $scope.request.codigoarchivo.split('.'); 
    var name = arrayarchivo[0]; 
    var fileType = arrayarchivo[1];
    personaService.exportFile(fileType, name).then(function(response) {  
        var file = new Blob([(response.data)], {type: $rootScope.applicationType(fileType)});
        var fileURL = URL.createObjectURL(file);
        const url = window.URL.createObjectURL(file);
        window.open(url);
    });  
}   

  $scope.close = function () {
    //$uibModalInstance.dismiss('cancel');
    $uibModalInstance.close($scope.response);
  };
});
