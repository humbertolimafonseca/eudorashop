eudoraShop.controller('produtoCtrl', function ($scope, $http, tagService) {
	
	 $scope.listar = function (){
		 $http.get('../resources/produto').success(function(data) {
			    $scope.produtos = data;
		  });
	 }
	 
	 $scope.listar();
	 
	 $scope.load = function (id){
		 $http.get('../resources/produto/'+ id).success(function(data) {
			    $scope.produto = data;
			    console.log(data);
			    $scope.nome = data.nome;
			    $scope.descricao = data.descricao;
			    $scope.logomarca = data.logomarca.nome;
			    $scope.imgLogo = data.logomarca.nome;
			    $scope.id = data.id;
			    $('#imagemTemp').hide();
			    $('#imagem')[0].src="../resources/produto/logomarca/" + $scope.logomarca + "/"+$scope.id;
		  });
	 }
	 
	
	 
	 $scope.cancel = function (){
		$scope.limparForm();
	 }
	 
	 $scope.sendImage = function (file){
		  		  		  
		  var fd = new FormData();
	      fd.append('file', file);
		  
		  $http.post('../resources/marca/addImg',new FormData(data), {
			  headers: {
                 'Content-Type': undefined
             }
		  }).success(function(data){
			  $('#imagemTemp')[0].src = "";
			  $('#imagem')[0].src = "";
			  $('#imagemTemp')[0].src="../resources/produto/logomarca/" + file.value ;
			  $('#imagemTemp').show();

		  });
		  
	  };
	 
	 $scope.remover = function(id) {
		 $http.delete("../resources/produto/" + id).success(function(data){
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
		 $scope.preco="";
		 $scope.selectedTags=[];
		 tagService.lista().success(function (data){
	    	 $scope.tags = data;
	     });
	 }
	
	
	$scope.send = function() {
		var form = $('#formProduto')[0] ;
		
		console.log($(form).serialize());
		
		 $http.post('../resources/produto/add',$(form).serialize(), {
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
     
     function Tag(nome, checked){
         this.nome = nome;
         this.checked = checked;
     }

     $scope.tags=[];
     $scope.selectedTags=[];
     
     tagService.lista().success(function (data){
    	 $scope.tags = data;
     });
	
     $scope.selectTag = function(tag){
    	 if(tag.checked){
    		 $scope.removerTag(tag)
    	 }else{
    		 tag.checked=true;
        	 console.log($scope.tags);
    	 }
    	 
    	 $scope.updateSelectedTags();
     }
     
     $scope.removerTag = function(tag){
    	 console.log(tag);
    	 tag.checked = false;
     }
     
     $scope.updateSelectedTags = function() {
    	 $scope.selectedTags = [];
    	 for (var t in $scope.tags){
    		 if($scope.tags[t].checked){
    			 $scope.selectedTags.push($scope.tags[t].nome);
    		 }
    	 }
    	 
    	 console.log($scope.selectedTags.toString());
     }
     
     $scope.edit = function() {
 		var form = $('#formProduto')[0] ;

 		$http.post('../resources/produto/edit/'+$scope.id + '/' + imgLogo.value,$(form).serialize(), {
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