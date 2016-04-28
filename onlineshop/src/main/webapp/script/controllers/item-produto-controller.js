eudoraShop.controller('itemProdutoCtrl', function ($scope, $http,$rootScope) {
	
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
		 
		 $http.get('../resources/produto/codigo/'+$scope.codigoProduto)
		 
		 .success(function(data) {
			 console.log(data);
			 $scope.produto = data;
			 $('#img')[0].src = '../resources/imagem/produto/'+ data.id + "/" + data.imagens[0].nome;
		 })
		 
		 .error(function(data){
			 $rootScope.message = data;
			 $rootScope.messageError = true;
		 });
	 }
	 
	 $scope.load = function (itemProduto){
		 $http.get('../resources/item-produto/'+itemProduto).success(function(data) {
			    $scope.itemProduto = data;
			    console.log(data);
//			    $scope.nome = data.entity.nome;
//			    $scope.descricao = data.entity.descricao;
//			    $scope.logomarca = data.entity.logomarca.nome;
//			    $scope.imgLogo = data.entity.logomarca.nome;
//			    $scope.imagem = data.entity.logomarca;
//			    $scope.id = data.entity.id;
//			    $('#img')[0].src="../resources/imagem/marca/" + $scope.id + "/" + $scope.imagem.nome;
		  });
	 }
	 
	 $scope.cancel = function (){
		$scope.limparForm();
	 }
	 
	 $scope.remover = function(hateosObj) {
		 $http.delete(hateosObj.deleteLink).success(function(data){
			 $rootScope.message = data;
			 $rootScope.messageError = false;
			 $scope.listar();
		 }).error(function(data){
			 $rootScope.message = data;
			 $('#messageDiv').show();
			 $rootScope.messageError = true;
		 });
		 
	 }
	 
	 $scope.limparForm = function(){
		 $scope.nome="";
		 $scope.descricao="";
		 $scope.logomarca="";
		 $scope.id = "";
		 
		 $('#formMarca')[0].reset();
		 $('#img')[0].src = "";
	 }
	
	
	$scope.send = function() {
		var form = $('#formItemProduto')[0] ;
		
		
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
 		var form = $('#formMarca')[0] ;
 		
 		console.log($(form).serialize());
 		$http.post($scope.marca.editLink + '/' + $scope.imagem.nome,$(form).serialize(), {
 			  headers: {
 				  'Content-Type': 'application/x-www-form-urlencoded'
             }
 		  }).success(function(data){
 			    var message;
 			  	$scope.message = data;
 			  	$scope.messageError = false;
 			  	$('#messageDiv').show();
 			  	$scope.limparForm();
 			  	$scope.listar();
 		  }).error(function(data){
 			  $scope.message = data;
 			  $scope.messageError = true;
 			  $('#messageDiv').show();
 			  
 		  })
      };
     
     
	});