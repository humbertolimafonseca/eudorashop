
var eudoraShop = angular.module('eudoraShop', ['ngRoute', 'filtrosEudora', 'ui.bootstrap']);

eudoraShop.run(function($rootScope){
	
	
	$rootScope.limpaMensagem = function(){
		 $rootScope.message = null;
	};
	
	$rootScope.dateOptions = {
		    formatYear: 'yy',
		    maxDate: new Date(2020, 5, 22),
		    minDate: new Date(),
		    language: 'pt-BR',
		    startingDay: 1
	}; 
	
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
                            when('/item-produto', {
                                templateUrl: '../partes/incluir-item-produto.html',
                                controller: 'itemProdutoCtrl'
                              }).
                              when('/lista-item-produto', {
                                  templateUrl: '../partes/lista-item-produto.html',
                                  controller: 'itemProdutoCtrl'
                                }).
                          when('/produto', {
                              templateUrl: '../partes/lista-produto.html',
                              controller: 'produtoCtrl'
                            }).
                            when('/novo-produto/:produtoId', {
                                templateUrl: '../partes/novo-produto.html',
                                controller: 'produtoCtrl'
                              }).
                              when('/novo-produto', {
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




