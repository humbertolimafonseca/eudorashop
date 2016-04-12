eudoraShop.service('tagService', function($http){

	this.lista = function() {
    	return $http.get('../resources/tag'); 
    }
    
 });

eudoraShop.service('marcaService', function($http){

	this.lista = function() {
    	return $http.get('../resources/marca'); 
    }
 });

eudoraShop.service('produtoService', function($http){

	this.lista = function() {
    	return $http.get('../resources/produto'); 
    }
 });