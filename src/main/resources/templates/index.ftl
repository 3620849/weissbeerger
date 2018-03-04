<!DOCTYPE html>
<html lang="en" ng-app="mainApp">
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap core CSS -->
    <link href="common/bootstrap-4.0.0-beta/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body ng-controller="AppCtrl" ng-init="frName = '${username}'">
<ng-include src="'app/navbar/navbar.html'"></ng-include>
<div>
   <login-pop-up></login-pop-up>
   <div ng-view></div>
</div>
</body>

<script type="text/javascript" src="common/angular.js"></script>
<script type="text/javascript" src="common/angular-route.min.js"></script>
<script type="text/javascript" src="common/ngStorage.min.js"></script>
<script type="text/javascript" src="app/main.js"></script>
<script src="common/jquery-3.2.1.min.js"></script>
<script src="common/ui-bootstrap-tpls-2.5.0.js"></script>
<script src="common/bootstrap-4.0.0-beta/assets/js/vendor/popper.min.js"></script>
<script src="common/bootstrap-4.0.0-beta/dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="common/bootstrap-4.0.0-beta/assets/js/ie10-viewport-bug-workaround.js"></script>

<script src="app/login-popup/loginCtrl.js"></script>
<script src="app/login-popup/loginPopUpDirective.js"></script>
<script src="app/login-popup/registerService.js"></script>
<script src="app/user/userDataService.js"></script>
<script src="app/user/accountCtrl.js"></script>
<script src="app/home/allFilmsCtrl.js"></script>
<script src="app/home/filmsService.js"></script>
<script src="app/search/searchCtrl.js"></script>
<script src="app/film-description-popup/descriptionCtrl.js"></script>
</html>