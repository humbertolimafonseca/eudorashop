eudoraShop.controller('tagCtrl', function ($scope, $http) {
	
	 $scope.listar = function (){
		 $http.get('../resources/tag').success(function(data) {
			    $scope.tags = data;
		  });
	 }
	 
	 $scope.listar();
	 
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
			  	$scope.message = data;
			  	$scope.messageError = false;
			  	$('#messageDiv').show();
			  	$scope.nome="";
			  	$scope.listar();
		  }).error(function(data){
			  $scope.message = data;
			  $('#messageDiv').show();
			  $scope.messageError = true;
		  })
      };
      
      
      
	});