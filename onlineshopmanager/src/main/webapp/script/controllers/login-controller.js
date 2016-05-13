eudoraShop.controller('loginCtrl', function ($scope, $http) {
	
	$scope.usuario = {
		    nome: '',
		    email: ''
		  };
	
	$scope.getLogged = function() {
		 $http.get('../resources/usuario/logged').success(function(data){
			  	
		  	$scope.usuario.nome="Ana Flávia";
		  	$scope.usuario.email="flaviannafonseca@gmail.com";
			  	
			  	
		  }).error(function(data){
			  $scope.usuario.nome="";
			  $scope.usuario.email="";
		  })
	};
	
	$scope.getLogged();
	
	$scope.reload = function()
	{
	   location.reload(); 
	}
	
	$scope.cancel = function(){
		 $scope.limparForm();
	 }
	
	 $scope.limparForm = function(){
		 $scope.login="";
		 $scope.senha="";
	 }
	
	$scope.logout = function() {

		$http.get('../resources/usuario/logout').success(function(data){
			  	$scope.reload();
			  	
		  }).error(function(data){
			  $scope.message = data;
			  $('#messageDiv').show();
			  $scope.messageError = true;
		  })
      };
	
	$scope.logar = function() {
		var form = $('#loginForm')[0] ;
		//alert(form.elements[0].value);
		 $http.post('../resources/usuario/login',$(form).serialize(), {
			  headers: {
				  'Content-Type': 'application/x-www-form-urlencoded'
             }
		  }).success(function(data){
			    var message;
			  	$scope.message = data;
			  	$scope.messageError = false;
//			  	$('#messageDiv').show();
			  	$scope.reload();
			  	
		  }).error(function(data){
			  $scope.message = data;
			  $('#messageDiv').show();
			  $scope.messageError = true;
		  })
      };
      
      
      
	});