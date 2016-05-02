eudoraShop.controller('itemProdutoCtrl', function ($scope, $http,$rootScope,$routeParams,$window) {
	$scope.params = $routeParams;
	
	 $scope.listar = function (){
		 $http.get('../resources/item-produto').success(function(data) {
			    $scope.itemsProdutos = data;
			    console.log($scope.itemsProdutos);
			    
			    $scope.totalItems = $scope.itemsProdutos.length;
			    $scope.maxsize = 5;
				$scope.currentPage = 1;
				$scope.start = (($scope.currentPage-1)*$scope.maxsize);
		  });
	 }
	 
	 $scope.listar();
	 
	 $scope.precosCiclo = [];
	 
	 
	 $scope.popup1 = {
			 opened: false
	 };
	 
	 $scope.popup2 = {
			 opened: false
	 };
	 
	 $scope.popup3 = {
			 opened: false
	 };
	 
	 $scope.popup4 = {
			 opened: false
	 };
	 
	 $scope.openDatePicker = function(popup){
		 popup.opened = !popup.opened;
	 };
	 
	 $scope.loadProduto = function()
	 {
		 
		 $http.get('../resources/produto/codigo/'+$scope.itemProduto.produto.codigo)
		 
		 .success(function(data) {
			 console.log(data);
			 $scope.produto = data;
			 
			 for (var i in $scope.produto.marca.ciclos){
				 
				 $scope.produto.marca.ciclos[i].inicio = new Date($scope.produto.marca.ciclos[i].inicio);
				 $scope.produto.marca.ciclos[i].fim = new Date($scope.produto.marca.ciclos[i].fim);
				 console.log($scope.itemProduto.precosCiclo);
			 }
			 
			 $('#img')[0].src = '../resources/imagem/produto/'+ data.id + "/" + data.imagens[0].nome;
		 })
		 
		 .error(function(data){
			 $rootScope.message = data;
			 $rootScope.messageError = true;
		 });
	 }
	 
	 $scope.pageChanged = function(){
		 console.log('Page changed to: ' + $scope.currentPage);
		 $scope.start = (($scope.currentPage-1)*$scope.maxsize);
		 console.log('Page start: ' + $scope.start);
	 }
	 
	 $scope.load = function (id){
		 if(id){
			 $http.get('../resources/item-produto/'+id).success(function(data) {
				 	console.log(data);
				    $scope.itemProduto = data;
				    $scope.precosCiclo =  $scope.itemProduto.precosCiclo;
				    $scope.loadProduto();
	// $scope.nome = data.entity.nome;
	// $scope.descricao = data.entity.descricao;
	// $scope.logomarca = data.entity.logomarca.nome;
	// $scope.imgLogo = data.entity.logomarca.nome;
	// $scope.imagem = data.entity.logomarca;
	// $scope.id = data.entity.id;
	// $('#img')[0].src="../resources/imagem/marca/" + $scope.id + "/" +
	// $scope.imagem.nome;
			  });
		 }
	 }
	 
	 $scope.cancel = function (){
		$scope.limparForm();
	 }
	 
	 $scope.remover = function(id) {
		 $http.delete("../resources/item-produto/" + id).success(function(data){
			 $rootScope.message = data;
			 $rootScope.messageError = false;
			 $scope.listar();
		 }).error(function(data){
			 $rootScope.message = data;
			 $rootScope.messageError = true;
		 });
		 
	 }
	 
	 $scope.limparForm = function(){
		 $scope.itemProduto = null;
		 $scope.produto = null;
		 
		 $('#form')[0].reset();
		 
	 }
	
	
	$scope.send = function() {
		var form = $('#form')[0] ;
		precosCiclos =  JSON.stringify($scope.precosCiclo); 

		console.log($(form).serialize());
		
		$http.post('../resources/item-produto/add',$(form).serialize(), {
			  headers: {
				  'Content-Type': 'application/x-www-form-urlencoded'
            }
		  }).success(function(data){
			    
			  	$rootScope.message = data;
			  	$rootScope.messageError = false;
			  	$scope.limparForm();
		  }).error(function(data){
			  $rootScope.message = data;
			  $rootScope.messageError = true;
		  })
     };
     
     $scope.edit = function() {
 		var form = $('#form')[0] ;
 		precosCiclos.value =  JSON.stringify($scope.precosCiclo); 
 		
 		console.log($(form).serialize());
 		
 		$http.post('../resources/item-produto/edit/' + $scope.itemProduto.id,$(form).serialize(), {
 			  headers: {
 				  'Content-Type': 'application/x-www-form-urlencoded'
             }
 		  }).success(function(data){
			    var message;
			  	$rootScope.message = data;
			  	$rootScope.messageError = false;
 		  }).error(function(data){
 			  	$rootScope.message = data;
 			  	$rootScope.messageError = true;
 			  
 		  })
 		  
 		 $rootScope.gotoAnchor('home');
 		  
 		  
      };
     
      
     
	});