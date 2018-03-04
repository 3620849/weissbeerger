angular.module('mainApp').controller('allFilmsCtrl', function($scope,$rootScope,filmsService){

    $rootScope.getBackHome = function(){
        $rootScope.foundFilms = $rootScope.lastSerchResult;
    }

    /*$scope.getFilm=function(val){
        console.log("film is: ");
        console.log(val);
        $rootScope.pickedFilm = val;
        $rootScope.loadingDesc = true;
        filmsService.getFilm(val.imdbID);
    }*/
    $rootScope.$on('filmDescription:updated',function(){
        $rootScope.loadingDesc = false;
        //$rootScope.filmDescription = filmsService.filmDescription;
    })

    /*filmsService.getFilmsList();
    $rootScope.$on('filmList:updated', function(){
        $scope.filmsList = filmsService.filmsList;


        for(var i = 0; i<$scope.filmsList.length; ++i){
            (function() {
                var index = i;
                filmsService.getFilmImgById($scope.filmsList[index].id)
                    .then(function (response, status, headers, config) {
                        $scope.filmsList[index].img = response.data.image;
                    }, function (error) {
                        console.log(error.data)
                    })
            })();
        }
        console.log( $scope.filmsList)
    });*/

});