angular.module('mainApp').controller('descriptionCtrl', function($scope,$rootScope,filmsService){
    $scope.likeFilm =  function (val){
        filmsService.likeFilm(val);
        $rootScope.likeStatus = !$rootScope.likeStatus;
    }


});