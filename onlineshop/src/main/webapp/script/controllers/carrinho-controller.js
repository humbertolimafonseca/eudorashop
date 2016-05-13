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
			  	$rootScope.message = data;
			  	console.log("Retorno addItem");
			  	console.log(data);
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
	
	$scope.update = function() {
		var form = $('#form')[0] ;
		 $http.post('../resources/carrinho/update',$(form).serialize(), {
			  headers: {
				  'Content-Type': 'application/x-www-form-urlencoded'
             }
		  }).success(function(data){
			    var message;
			  	$scope.message = data;
			  	$scope.messageError = false;
			  	$('#messageDiv').show();
		  }).error(function(data){
			  $scope.message = data;
			  $('#messageDiv').show();
			  $scope.messageError = true;
		  })
      };
      
      
      
	});