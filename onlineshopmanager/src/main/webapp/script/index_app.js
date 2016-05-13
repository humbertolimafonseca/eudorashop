var eudoraShop = angular.module('eudoraShop', [ 'ngRoute', 'filtrosEudora',
		'ui.bootstrap' ]);

eudoraShop.config([ '$routeProvider', function($routeProvider, $stateProvider) {
	$routeProvider.when('/', {
		templateUrl : '../pages/home.html',
		controller : 'indexCtrl'
	}).when('/carrinho', {
		templateUrl : '../pages/checkout.html',
		controller : 'carrinhoCtrl'
	}).when('/home', {
		templateUrl : '../pages/home.html',
		controller : 'indexCtrl'
	}).when('/produto/:produtoId', {
		templateUrl : '../pages/produto.html',
		controller : 'itemProdutoCtrl'
	});

} ]);

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


eudoraShop.run(function($rootScope, $location, $anchorScroll) {

	$rootScope.carrinho = null;

	$rootScope.confirmAndDo = function(msg, doIt, param) {
		if (confirm(msg)) {
			doIt(param);
		}
		;
	}
	
	$rootScope.limpaMensagem = function(){
		 $rootScope.message = null;
	};
	
});
