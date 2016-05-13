
var eudoraShop = angular.module('eudoraShop', ['ngRoute', 'filtrosEudora', 'ui.bootstrap']);


function testInterceptor($rootScope) {
	  return {
	    request: function(config) {
	    	console.log(config);
	    	return config;
	    },

	    requestError: function(config) {
	      return config;
	    },

	    response: function(res) {
	    	console.log(res);
	    	console.log("LIMPAR MENSAGEM!!");
	    	$rootScope.limpaMensagem();
	      return res;
	    },

	    responseError: function(res) {
	      return res;
	    }
	  }
	}

eudoraShop.factory('testInterceptor', testInterceptor)
.config(function($httpProvider) {
$httpProvider.interceptors.push('testInterceptor');
});

eudoraShop.run(function($rootScope, $location, $anchorScroll){
	
	
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
	
	 $rootScope.confirmAndDo = function(msg, doIt, param){
    	 if( confirm(msg) )
    	 {
    		 doIt(param);
    	 };
     }
	 
	 $rootScope.redirect = function(url, message){
		 
		 document.location = url;
		 $rootScope.message = message;
		
	 }
	 
	 $rootScope.alert = function( message){
		 console.log(message);
	 }
	 
	 
	 
	 $rootScope.gotoAnchor = function(anchor) {
	      // set the location.hash to the id of
	      // the element you wish to scroll to.
	      $location.hash(anchor);

	      // call $anchorScroll()
	      $anchorScroll();
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
                              when('/item-produto/:id', {
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




