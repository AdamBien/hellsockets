angular.module('hellsockets')
.factory('Loader', ['$http', function($http){
    return {
        start: function(config){
            return $http.post('resources/loader', config)
            .then(function(result){
                if(result.status == 202){
                    return {status: 'Started'}
                }else{
                    return {status: 'Not started'}
                }
            }, function(){
                return {status: 'Failed'}
            });
        }
    };
}]);