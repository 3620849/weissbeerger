var app = angular.module("mainApp",['ngRoute','ui.bootstrap','ngStorage']);
app.controller("AppCtrl",function($scope,$rootScope,userDataService,filmsService){
    $scope.getFilm=function(val){
        $rootScope.pickedFilm = val;
        $rootScope.loadingDesc = true;
        filmsService.getFilm(val.imdbID);

    }

    $rootScope.$on('filmDescription:updated',function(){
        filmsService.isFilmLiked($rootScope.pickedFilm.imdbID);
    });
    $rootScope.$on('userLogin:updated',function(){
        userDataService.getCurrentUserData()
    });
    $rootScope.getBackHome=function(){
        $rootScope.foundFilms = $rootScope.lastSerchResult;
    };
    $rootScope.updateLikeList = function(){
        filmsService.updateLikeList($rootScope.myUser.id);
    };
    $rootScope.user={};
});
app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'app/home/allFilms.html',
            controller:'allFilmsCtrl'
        })

        .when('/home', {
        templateUrl: 'app/home/allFilms.html',
            controller:'allFilmsCtrl'
    })
        .when('/account', {
        templateUrl: 'app/user/account.html',
        controller: 'accountCtrl'})
});
