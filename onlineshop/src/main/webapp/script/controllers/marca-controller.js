eudoraShop.controller('marcaCtrl', function ($scope, $http) {
	
	 $scope.listar = function (){
		 $http.get('../resources/marca').success(function(data) {
			    $scope.marcas = data;
		  });
	 }
	 
	 $scope.load = function (id){
		 $http.get('../resources/marca/'+ id).success(function(data) {
			    $scope.marca = data;
			    console.log(data);
			    $scope.nome = data.nome;
			    $scope.descricao = data.descricao;
			    $scope.logomarca = data.logomarca.nome;
			    $scope.imgLogo = data.logomarca.nome;
			    $scope.id = data.id;
			    $('#imagem')[0].src="../resources/marca/logomarca/" + $scope.logomarca + "/"+$scope.id;
		  });
	 }
	 
	 $scope.listar();
	 
	 $scope.cancel = function (){
		$scope.limparForm();
	 }
	 
	 $scope.sendImage = function (){
		  var data = $("#formMarcaImg")[0]; 
		  
		  imgLogo.value =  logomarca.value;
		  
		  $http.post('../resources/marca/addImg',new FormData(data), {
			  headers: {
                 'Content-Type': undefined
             }
		  }).success(function(data){
			  $('#imagem')[0].src ="../resources/marca/logomarca/" + logomarca.value;
			  console.log("Imagem modificada:" + logomarca.value);
		  });
		  
	  };
	 
	 $scope.remover = function(nome) {
		 $http.delete("../resources/marca/" + nome).success(function(data){
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
	 
	 $scope.limparForm = function(){
		 $scope.nome="";
		 $scope.descricao="";
		 $scope.logomarca="";
		 $scope.id = "";
		 
		 $('#formMarcaImg')[0].reset();
//		 $('#imagemTemp')[0].src = "";
		 $('#imagem')[0].src = "";
	 }
	
	
	$scope.send = function() {
		var form = $('#formMarca')[0] ;
		
		
		 $http.post('../resources/marca/add',$(form).serialize(), {
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
			  $('#messageDiv').show();
			  $scope.messageError = true;
		  })
     };
     
     $scope.edit = function() {
 		var form = $('#formMarca')[0] ;

 		$http.post('../resources/marca/edit/'+$scope.id + '/' + imgLogo.value,$(form).serialize(), {
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
 			  $('#messageDiv').show();
 			  $scope.messageError = true;
 		  })
      };
     
     
	});