eudoraShop.controller('marcaCtrl', function ($scope, $http) {
	$scope.ciclos = [];
	
	var Ciclo = function(inicio, fim){
		 this.inicio = inicio;
		 this.fim = fim;
	 };
	 
	 $scope.ciclo = new Ciclo();
	 $scope.cicloClone = new Ciclo();
	
	 $scope.listar = function (){
		 $http.get('../resources/marca').success(function(data) {
			    $scope.marcas = data;
			    console.log($scope.marcas);
			    console.log($scope.marcas[0].selfLink);
		  });
	 }
	 
	 $scope.loadCiclo = function(ciclo){
		 $scope.ciclo = ciclo;
		 if(!$scope.ciclo.id){
			 $scope.ciclo.id = 'temp';
		 }
		 
		 $scope.cicloClone.inicio = $scope.ciclo.inicio;
		 $scope.cicloClone.fim = $scope.ciclo.fim;
		 $scope.cicloClone.id = $scope.ciclo.id;
	 }
	 
	 $scope.removerCiclo = function(index){
		 console.log($scope.ciclos);
		 $scope.ciclos.splice(index, 1);
	 }
	 
	 $scope.editCiclo = function(){
		 var inicio = new Date($scope.ciclo.inicio);
		 var fim = new Date($scope.ciclo.fim);
		 $scope.ciclo.nome = inicio.getMonth() + 1 + "/" + inicio.getFullYear()
		 $scope.ciclo.descricao= "de " + inicio.getDate() + " a " + fim.getDate();
	    	
//		 $scope.ciclo.nome = new Date($scope.ciclo.inicio).getMonth() + 1 + "/" + new Date($scope.ciclo.inicio).getFullYear();
		
		 if($scope.ciclo.id == 'temp'){
			 $scope.ciclo.id = null; 
		 }
		 
		 $scope.ciclo = new Ciclo();
	 }
	 
	 $scope.cancelCiclo= function(){
		 if($scope.ciclo.id == 'temp'){
			 $scope.ciclo.id = null; 
		 }
		 
		 $scope.ciclo.inicio = $scope.cicloClone.inicio;
		 $scope.ciclo.fim = $scope.cicloClone.fim;
		 $scope.ciclo.id = $scope.cicloClone.id;
		 
		 $scope.ciclo = new Ciclo();
	 }
	 
	 $scope.addCiclo = function(){
		 if($scope.ciclo.inicio && $scope.ciclo.fim){
			 console.log($scope.ciclo.inicio);
			 console.log(new Date($scope.ciclo.inicio));
			 var inicio = new Date($scope.ciclo.inicio);
			 var fim = new Date($scope.ciclo.fim);
			 
			 $scope.ciclo.nome = inicio.getMonth() + 1 + "/" + inicio.getFullYear();
			 
			 $scope.ciclo.descricao= "de " + inicio.getDate() + " a " + fim.getDate();
			 
			 $scope.ciclos.push($scope.ciclo);
			 $scope.ciclo = new Ciclo();
		 }else{
			 alert('Preencha a data inicial e final do ciclo!');
		 }
	 }
	 
	 
	 
	 $scope.popup = {
			 opened: false
	 };
	 
	 $scope.popup2 = {
			 opened: false
	 };
	 
	 $scope.openDatePicker = function(popup){
		 popup.opened = !popup.opened;
	 };
	 
	 
	 $scope.load = function (marca){
		 $http.get(marca.selfLink).success(function(data) {
			    $scope.marca = data;
			    console.log("LOAD");
			    console.log(data);
			    $scope.nome = data.entity.nome;
			    $scope.descricao = data.entity.descricao;
			    $scope.logomarca = data.entity.logomarca.nome;
			    $scope.imgLogo = data.entity.logomarca.nome;
			    $scope.imagem = data.entity.logomarca;
			    $scope.id = data.entity.id;
			    $scope.ciclos = data.entity.ciclos;
			    
			    for (var i in $scope.ciclos){
			    	var inicio = new Date($scope.ciclos[i].inicio);
			    	var fim = new Date($scope.ciclos[i].fim);
			    	$scope.ciclos[i].nome = inicio.getMonth() + 1 + "/" + inicio.getFullYear()
			    	$scope.ciclos[i].descricao= "de " + inicio.getDate() + " a " + fim.getDate();
			    }
			    
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
		 $scope.ciclos=[];
		 
		 $('#formMarca')[0].reset();
		 $('#img')[0].src = "";
	 }
	
	
	$scope.send = function() {
		var form = $('#formMarca')[0] ;
		form.ciclos.value = JSON.stringify($scope.ciclos);
		
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
 		form.ciclos.value = JSON.stringify($scope.ciclos);
 		
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