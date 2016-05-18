eudoraShop.controller('carrinhoCtrl', function ($scope, $http, $rootScope) {
	
	 $scope.load = function (){
		 $http.get('../resources/carrinho').success(function(data) {
			    $rootScope.carrinho = data;
			    console.log( $rootScope.carrinho);
		  });
	 }
	 
	 $scope.load();
	 
	 $scope.addItem = function(itemProduto, qtd) {
		 $http.get('../resources/carrinho/addItem/'+itemProduto+"/"+qtd).success(function(data){
			    var message;
			  	$rootScope.carrinho = data;
			  	console.log("Retorno addItem");
			  	console.log(data);
			  	$rootScope.message = "Item adcionado ao carrinho.";
			  	$rootScope.messageError = false;
		  }).error(function(data){
			  $rootScope.message = data;
			  console.log("Retorno addItem");
			  console.log(data);
			  $rootScope.messageError = true;
		  })
	 }
	 
	 $scope.limpar = function(itemProduto, qtd) {
		 $http.get('../resources/carrinho/limpar').success(function(data){
			 $scope.load();
			 $rootScope.message = "Seu carrinho está vazio!";
			  $rootScope.messageError = false;
		  }).error(function(data){
			  $rootScope.message = data;
			  $rootScope.messageError = true;
		  })
	 }
	
	$scope.removeItem = function(item) {
		
		 $http.get('../resources/carrinho/removeItem/' + item ).success(function(data){
			    var message;
			  	$scope.message = "Item Removido com sucesso.";
			  	$rootScope.carrinho = data;
			  	$scope.messageError = false;
		  }).error(function(data){
			  $scope.message = data;
			  $scope.messageError = true;
		  })
      };
      
      
      
	});