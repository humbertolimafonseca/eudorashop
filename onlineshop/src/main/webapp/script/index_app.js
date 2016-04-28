var eudoraShop = angular.module('eudoraShop', ['ngRoute', 'filtrosEudora', 'ui.bootstrap']);

eudoraShop.config(['$routeProvider',
                   function($routeProvider, $stateProvider) {
                     $routeProvider.
                       when('/', {
                         templateUrl: '../pages/home.html',
                         controller: 'indexCtrl'
                       }).when('/home', {
                           templateUrl: '../pages/home.html',
                           controller: 'indexCtrl'
                       }).when('/produto/:produtoId', {
                           templateUrl: '../pages/produto.html',
                           controller: 'produtoCtrl'
                       });
                   }]);