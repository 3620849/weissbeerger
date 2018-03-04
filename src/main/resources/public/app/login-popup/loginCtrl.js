angular.module("mainApp").controller("loginCtrl",function($scope,registerService){
    $scope.currentForm="app/login-popup/loginForm.html";
    $scope.registerForm={};
    $scope.registerForm.name="Login";
    $scope.showRegisterForm = false;
    $scope.showLoginForm = true;

    $scope.loginUser = function(op){
        registerService.login(op);
        $scope.openCloseRegform(true);
    }
    $scope.registerUser = function(user){
        registerService.register(user);
        $scope.user = registerService.user;
        $scope.getLoginForm(false);}


    $scope.getLoginForm = function(op){
            $scope.showLoginForm=true;
            $scope.showRegisterForm=false;
            $scope.registerForm.name="Login";
            $scope.openCloseRegform(op);
    }

    $scope.getRegisterForm = function(){
        $scope.showLoginForm=false;
        $scope.showRegisterForm=true;
        $scope.registerForm.name="Sign up";
    }
})