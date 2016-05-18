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

function testInterceptor($rootScope, $q) {
	  return {
	    request: function(config) {
	    	console.log(config);
	    	return config;
	    },

	    requestError: function(config) {
	      return $q.reject(config.data);
	    },

	    response: function(res) {
	    	console.log(res);
	    	console.log("LIMPAR MENSAGEM!!");
	    	$rootScope.limpaMensagem();
	      return res;
	    },

	    responseError: function(res) {
	    	console.log(res);
	    	console.log("LIMPAR MENSAGEM!!");
	    	$rootScope.limpaMensagem();
	      return $q.reject(res.data);
	    }
	  }
	}

eudoraShop.factory('testInterceptor', testInterceptor)
.config(function($httpProvider) {
  $httpProvider.interceptors.push('testInterceptor');
});


eudoraShop.run(function($rootScope, $location, $anchorScroll) {

	$rootScope.carrinho = null;
	
	$rootScope.usuario = {
			nome : '',
			email : ''
		};

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
