
var eudoraShop = angular.module('eudoraShop', ['ngRoute', 'filtrosEudora']);

eudoraShop.service('tagService', function($http){

	this.lista = function() {
    	return $http.get('../resources/tag'); 
    }
    
    this.teste = function (){
    	alert("teste");
    }
 });
    

eudoraShop.config(['$routeProvider',
                    function($routeProvider) {
                      $routeProvider.
                        when('/cart', {
                          templateUrl: '../partes/cart.html',
                          controller: 'cartCtrl'
                        }).
                        when('/cart/:produtoId', {
                            templateUrl: '../partes/detalhe-produto.html',
                            controller: 'cartDetalheCtrl'
                          }).
                          when('/', {
                              templateUrl: '../partes/login.html',
                              controller: 'loginCtrl'
                            }).
                          when('/produto', {
                              templateUrl: '../partes/novo-produto.html',
                              controller: 'produtoCtrl'
                            }).
                            when('/login', {
                                templateUrl: '../partes/login.html',
                                controller: 'loginCtrl'
                              }).
                            when('/tag', {
                                templateUrl: '../partes/incluir-tag.html',
                                controller: 'tagCtrl'
                              }).
                              when('/marca', {
                                  templateUrl: '../partes/incluir-marca.html',
                                  controller: 'marcaCtrl'
                                });
                    }]);




