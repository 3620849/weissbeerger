app.controller("searchCtrl", function($scope,$http,$rootScope){
    var _selected;

    $scope.selected = undefined;
    $scope.onSelect = function ($item, $model, $label) {
        $scope.getLocation($item);
    };
    $scope.getLocation = function(val) {
        return $http.get('//localhost:8080/api/searchFilms', {
            params: {
                name: val
            }
        }).then(function(response){
            $rootScope.foundFilms = response.data.Search;
            $rootScope.lastSerchResult = $rootScope.foundFilms;
            return response.data.Search.map(function(item){
                return item.Title;
            });
        });
    };
});