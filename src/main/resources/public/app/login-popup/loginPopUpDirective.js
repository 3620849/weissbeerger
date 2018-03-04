angular.module('mainApp').directive('loginPopUp',function(){
    return {
        restrict:'E',
        scope:false,
        templateUrl: 'app/login-popup/loginForm.html',
        controller:function($scope){
            $scope.showPopUpDialog=false ;
            $scope.openCloseRegform = function(op){
                if(op)$scope.showPopUpDialog?$scope.showPopUpDialog=false:$scope.showPopUpDialog=true;
            }
        }
    };
});