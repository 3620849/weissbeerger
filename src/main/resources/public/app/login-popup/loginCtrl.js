angular.module("mainApp").controller("loginCtrl",function($scope,registerService,$rootScope){
    $scope.currentForm="app/login-popup/loginForm.html";
    $scope.registerForm={};
    $scope.registerForm.name="Login";
    $scope.showRegisterForm = false;
    $scope.showLoginForm = true;

    $scope.loginUser = function(op){
        registerService.login(op);
        $('#loginModal').modal('hide');
    }
    $scope.registerUser = function(user){
        registerService.register(user);
        $scope.user = registerService.user;
        $('#registerModal').modal('hide');
    }
    $rootScope.$on('userRegister:saved',function(event,user){
        $scope.loginUser(user)
    });
})