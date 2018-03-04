angular.module('mainApp').factory('filmsService',function($http,$rootScope,$localStorage) {

    var fact = {
        isFilmLiked:function(val){
            $http.get("/api/isFilmLiked/"+val,{
                headers: {'X-Auth-Token': $rootScope.user.token}}).then(function (response, status, headers, config) {
                $rootScope.likeStatus = response.data;
                console.log("Film liked"+response.data)
            }, function (error) {
                console.log(error.data)
            })
        },
        updateLikeList:function(val){
            $http.get("/api/getLikedList/"+val,{
                headers: {'X-Auth-Token': $rootScope.user.token}}).then(function (response, status, headers, config) {
                $rootScope.foundFilms = response.data;
            }, function (error) {
                console.log(error.data)
            })
        },
        likeFilm:function(film){
            $http.post("/api/addToLiked",film,{
                    headers: {'X-Auth-Token': $rootScope.user.token}})
                .then(function(response, status, headers, config){
                },function(error){
                    console.log(error.data)
                })
        },
        filmDescription:{},
        getFilm:function(val){
            //search film inside local storage if null call this
            var arrayFilms = $localStorage.localeFilms;
            if(arrayFilms==undefined)arrayFilms= new Array();
            var isMatches = false;
            for(var i=0;i<arrayFilms.length;++i){
                if(arrayFilms[i].film.imdbID==val){
                    console.log("found in local storage");
                    $rootScope.filmDescription = arrayFilms[i];
                    isMatches=true;
                    $rootScope.$broadcast('filmDescription:updated')
                    break;
                }
            }
            if(!isMatches){
            $http.get("/api/getFilmByImdbID?imdbID="+val).then(function (response, status, headers, config) {
                $rootScope.filmDescription = response.data;
                $rootScope.$broadcast('filmDescription:updated')
                var newFilm = jQuery.extend({},response.data);
                newFilm.sorceOmdb=false;
                newFilm.isLiked=undefined;
                arrayFilms.push(newFilm);
                $localStorage.localeFilms = arrayFilms;

            }, function (error) {
                console.log(error.data)
            })
            }
        },
        filmsList:{},
        getFilmsList: function(){
            $http.get("/api/getAllFilms").then(function (response, status, headers, config) {
                fact.filmsList = response.data;
                $rootScope.$broadcast('filmList:updated');
            }, function (error) {
                console.log(error.data)
            })
        },
        getFilmImgById:function(id){
            return $http.get("api/getFilmImgById/"+id); }
        };

    return fact;

});
