// add the filter to your application module
//angular.module('myApp', ['filters']);
//angular.module('app.core')
/**
 * Filesize Filter
 * @Param length, default is 0
 * @example
 * var myFile = 5678;
 * {{myText|filesize}}
 * Output
 * "5.54 Kb"
 * @return string
 */
angular.module('app.core').filter('Filesize', function () {
  return function (size) {
    if (isNaN(size)) size = 0;

    if (size < 1024) return size + ' Bytes';

    size /= 1024;

    if (size < 1024) return size.toFixed(2) + ' KB';

    size /= 1024;

    if (size < 1024) return size.toFixed(2) + ' MB';

    size /= 1024;

    if (size < 1024) return size.toFixed(2) + ' GB';

    size /= 1024;

    return size.toFixed(2) + ' TB';
  };
});
