angular.module('app.core').directive('ngEnter', function () {
  return function (scope, element, attrs) {
    element.bind('keydown keypress', function (event) {
      if (event.which === 13) {
        scope.$apply(function () {
          scope.$eval(attrs.ngEnter);
        });
        event.preventDefault();
      }
    });
  };
});
angular.module('app.core').directive('numbersOnly', function () {
  return {
      require: 'ngModel',
      link: function (scope, element, attr, ngModelCtrl) {
          function fromUser(text) {
              if (text) {
                  var transformedInput = text.replace(/[^0-9]/g, '');

                  if (transformedInput !== text) {
                      ngModelCtrl.$setViewValue(transformedInput);
                      ngModelCtrl.$render();
                  }
                  return transformedInput;
              }
              return undefined;
          }
          ngModelCtrl.$parsers.push(fromUser);
      }
  };
});
angular.module('app.core').directive('onlyLettersInput', function () {
  return {
    require: 'ngModel',
    link: function (scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        //var transformedInput = text.replace(/[^a-zñA-ZÑ]/g, '');
        var transformedInput = text.replace(/[^a-zA-Z ñáéíóú ÑÁÉÍÓÚ]/g, '');
        //console.log(transformedInput);
        if (transformedInput !== text) {
          ngModelCtrl.$setViewValue(transformedInput);
          ngModelCtrl.$render();
        }
        return transformedInput;
      }
      ngModelCtrl.$parsers.push(fromUser);
    },
  };
});
angular.module('app.core').directive('phoneInput', function ($filter, $browser) {
  return {
    require: 'ngModel',
    link: function ($scope, $element, $attrs, ngModelCtrl) {
      var listener = function () {
        var value = $element.val().replace(/[^0-9]/g, '');
        $element.val($filter('tel')(value, false));
      };

      // This runs when we update the text field
      ngModelCtrl.$parsers.push(function (viewValue) {
        return viewValue.replace(/[^0-9]/g, '').slice(0, 10);
      });

      // This runs when the model gets updated on the scope directly and keeps our view in sync
      ngModelCtrl.$render = function () {
        $element.val($filter('tel')(ngModelCtrl.$viewValue, false));
      };

      $element.bind('change', listener);
      $element.bind('keydown', function (event) {
        var key = event.keyCode;
        // If the keys include the CTRL, SHIFT, ALT, or META keys, or the arrow keys, do nothing.
        // This lets us support copy and paste too
        if (key == 91 || (15 < key && key < 19) || (37 <= key && key <= 40)) {
          return;
        }
        $browser.defer(listener); // Have to do this or changes don't get picked up properly
      });

      $element.bind('paste cut', function () {
        $browser.defer(listener);
      });
    },
  };
});

function validateMail(idMail) {
  //Creamos un objeto
  object = document.getElementById(idMail);
  valueForm = object.value;

  // Patron para el correo
  var patron = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
  if (valueForm.search(patron) == 0) {
    //Mail correcto
    object.style.color = '#000';
    return;
  }
  //Mail incorrecto
  object.style.color = '#f00';
}

angular.module('app.core').directive('sameAs', function () {
  return {
    restrict: 'A',
    require: {
      ngModelCtrl: 'ngModel',
    },
    scope: {
      reference: '<sameAs',
    },
    bindToController: true,
    controller: function ($scope) {
      var $ctrl = $scope.$ctrl;

      $ctrl.$onInit = function () {
        function sameAsReference(modelValue, viewValue) {
          if (!$ctrl.reference || !modelValue) {
            //nothing to compare
            return true;
          }
          return modelValue === $ctrl.reference;
        }
        $ctrl.ngModelCtrl.$validators.sameas = sameAsReference;
      };

      $ctrl.$onChanges = function (changesObj) {
        $ctrl.ngModelCtrl.$validate();
      };
    },
    controllerAs: '$ctrl',
  };
});

angular.module('app.core').directive('activeMenu', [
  '$location',
  function ($location) {
    return {
      restrict: 'A',
      scope: false,
      link: function (scope, element) {
        function setActive() {
          var path = $location.path();
          if (path) {
            angular.forEach(element.find('li'), function (li) {
              var anchor = li.querySelector('a');
              if (anchor.href.match('#!' + path.replace('/', ''))) {
                angular.element(anchor).addClass('active');
              } else {
                angular.element(anchor).removeClass('active');
              }
            });
          }
        }

        setActive();

        scope.$on('$locationChangeSuccess', setActive);
      },
    };
  },
]);

angular.module('app.core').directive('inputFile', function () {
  return {
    restrict: 'A',
    scope: {
      ngModel: '=',
      ngChange: '&',
      type: '@',
    },
    link: function (scope, element, attrs) {
      if (scope.type.toLowerCase() != 'file') {
        return;
      }
      element.bind('change', function () {
        let files = element[0].files;
        scope.ngModel = files;
        scope.$apply();
        scope.ngChange();
      });
    },
  };
});

angular.module('app.core').directive('limitTo', [
  function () {
    return {
      restrict: 'A',
      link: function (scope, elem, attrs) {
        var limit = parseInt(attrs.limitTo);
        angular.element(elem).on('keypress', function (e) {
          if (this.value.length == limit) e.preventDefault();
        });
      },
    };
  },
]);

angular.module('app.core').directive('loading', [
  '$http',
  function ($http) {
    return {
      restrict: 'A',
      link: function (scope, elm, attrs) {
        scope.isLoading = function () {
          return $http.pendingRequests.length > 0;
        };
        scope.$watch(scope.isLoading, function (v) {
          debugger;
          if (v) {
            //elm.show();
            getSpinner();
          } else {
            //elm.hide();
            deleteSpinner();
          }
        });
      },
    };
  },
]);

/*
  SPINNER
  */
window.getSpinner = function () {
  var msg = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 'Cargando...';
  var spinner = '\n        <div class="spinner d-flex flex-column align-items-center justify-content-center position-fixed w-100" style="z-index: 1040">\n            <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">\n                <span class="sr-only">'
    .concat(msg, '</span>\n            </div>\n            \n            <strong class="text-white mt-2">')
    .concat(msg, '</strong>\n        </div>\n    '); // let spinner =
  var backdrop = '<div class="spinner-backdrop fade show"></div>';
  $('body').addClass('with-spinner');
  $('body').append(backdrop);
  $('body').append(spinner);
};

window.deleteSpinner = function () {
  $('body').removeClass('with-spinner');
  $('.spinner').fadeOut(300, function () {
    $(this).remove();
    $('.spinner-backdrop').removeClass('show').addClass('fade').remove();
  });
};
