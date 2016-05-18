eudoraShop.controller('tagCtrl', function ($scope, $http, $rootScope) {
	
	 $scope.listar = function (){
		 $http.get('../resources/tag').success(function(data) {
			    $scope.tags = data;
		  });
	 }
	 
	 $scope.listar();
	 
	 $scope.confirmaRemover = function (tag){
		 if(confirm("Deseja realmente apagar a tag: " + tag)){
			$scope.remover(tag); 
		 }
	 }
	 
	 $scope.remover = function(nome) {
		 $http.delete("../resources/tag/" + nome).success(function(data){
			 $scope.message = data;
			 $scope.messageError = false;
			 $('#messageDiv').show();
			 $scope.listar();
		 }).error(function(data){
			 $scope.message = data;
			 $('#messageDiv').show();
			 $scope.messageError = true;
		 });
		 
	 }

	 $scope.send = function() {
		var form = $('#formTag')[0] ;
		//alert(form.elements[0].value);
		 $http.post('../resources/tag/add',$(form).serialize(), {
			  headers: {
				  'Content-Type': 'application/x-www-form-urlencoded'
             }
		  }).success(function(data){
			    var message;
			  	$rootScope.message = data;
			  	$rootScope.messageError = false;
			  	$scope.nome="";
		  }).error(function(data){
			  $rootScope.message = data;
			  $rootScope.messageError = true;
		  })
      };
      
      
      
	});