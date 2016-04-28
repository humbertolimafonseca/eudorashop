eudoraShop.controller('marcaCtrl', function ($scope, $http) {
	
	 $scope.listar = function (){
		 $http.get('../resources/marca').success(function(data) {
			    $scope.marcas = data;
			    console.log($scope.marcas);
			    console.log($scope.marcas[0].selfLink);
		  });
	 }
	 
	 $scope.load = function (marca){
		 $http.get(marca.selfLink).success(function(data) {
			    $scope.marca = data;
			    console.log(data);
			    $scope.nome = data.entity.nome;
			    $scope.descricao = data.entity.descricao;
			    $scope.logomarca = data.entity.logomarca.nome;
			    $scope.imgLogo = data.entity.logomarca.nome;
			    $scope.imagem = data.entity.logomarca;
			    $scope.id = data.entity.id;
			    $('#img')[0].src="../resources/imagem/marca/" + $scope.id + "/" + $scope.imagem.nome;
		  });
	 }
	 
	 $scope.listar();
	 
	 $scope.cancel = function (){
		$scope.limparForm();
	 }
	 
	 $scope.sendImage = function (){
		  var data = $("#formMarcaImg")[0]; 
		  
//		  logomarca.value = $("#imagem")[0].value;
		  
//		  console.log(imagem.value);
//		  console.log($("#imgHidden")[0]);
//		  console.log(img);
		  
		  console.log(imagem.files[0]);
		  $scope.imgLogo = imagem.files[0].name;
			  
		  $http.post('../resources/marca/addImg',new FormData(data), {
			  headers: {
                 'Content-Type': undefined
             }
		  }).success(function(data){
			  console.log("Imagem modificada:" + $scope.imgLogo);
		  });
		  
	  };
	 
	 $scope.remover = function(hateosObj) {
		 $http.delete(hateosObj.deleteLink).success(function(data){
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
		 
		 $('#formMarca')[0].reset();
		 $('#img')[0].src = "";
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
			  $scope.messageError = true;
			  $('#messageDiv').show();
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