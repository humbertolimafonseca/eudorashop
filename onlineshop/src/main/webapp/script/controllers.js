
eudoraShop.controller('cartCtrl', function ($scope, $http) {
  
  $scope.orderProp = 'age';
  
  $http.get('resources/cart').success(function(data) {
	    $scope.cart = data.splice(0,5);
	  });
  
});




eudoraShop.controller('cartDetalheCtrl', function ($scope, $http, $routeParams) {
  
	$http.get('resources/cart/' + $routeParams.produtoId).success(function(data) {
		$scope.item = data;
    });
	
	$scope.hello = function(name) {
	    alert('Hello ' + (name || 'world') + '!');
	}
		    
});