ventanillaVirtual.controller('mostrarObservacionesController', function (
  $scope,
  $uibModalInstance,
  solcitudId,
  mpeService
) {
  mpeService.getObservacionDocumentoById(solcitudId).then(function (response) {
    $scope.requestsData = response.data.objeto;
    console.log(response);
  });

  $scope.close = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
