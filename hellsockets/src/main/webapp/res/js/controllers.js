angular.module('hellsockets')
.controller('LoaderController', function($scope, Loader){
  $scope.start = function(){
    Loader.start($scope.config).then(function(result){
        $scope.result = result;
    });
  };
  
});