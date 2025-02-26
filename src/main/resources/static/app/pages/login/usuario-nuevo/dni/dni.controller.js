'use strict';
ventanillaVirtual.controller('dniInstaceController', function ($scope,$uibModalInstance,data) {
    var pc = this;
    //debugger;
    pc.data = data;
    $scope.cancel = function () { 
        //$uibModalInstance.dismiss('cancel');
        $uibModalInstance.close();
    };
});