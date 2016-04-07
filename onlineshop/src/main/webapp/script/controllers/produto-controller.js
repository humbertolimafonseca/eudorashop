eudoraShop.controller('produtoCtrl', function ($scope, $http, tagService) {
	
	 $scope.listar = function (){
		 $http.get('../resources/produto').success(function(data) {
			    $scope.produtos = data;
		  });
	 }
	 
	 $scope.listar();
	 
	 $scope.load = function (id){
		 $scope.limparForm();
		 $http.get('../resources/produto/'+ id).success(function(data) {
			    $scope.produto = data;
			    console.log(data);
			    $scope.nome = data.nome;
			    $scope.preco = data.preco;
			    $scope.descricao = data.descricao;
			    $scope.id = data.id;
			    $scope.imagem = data.imagens[0];
			    console.log($scope.imagem);
			    
			    $('#img')[0].src = '../resources/imagem/produto/'+ $scope.id + "/" + $scope.imagem.nome;
			    
			    for(var sTagi in data.tags){
			    	
			    	var sTag = data.tags[sTagi];
			    	console.log(sTag);
				    for (var tag in $scope.tags){
					    if(sTag.nome == $scope.tags[tag].nome ){
					    		console.log($scope.tags[tag].nome);
					    		$scope.selectTag($scope.tags[tag]);
					    }
					}
		 		}
		  });
	 }
	 
	
	 
	 $scope.cancel = function (){
		$scope.limparForm();
		$('#messageDiv').hide();
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
		 $scope.id="";
		 $scope.selectedTags=[];
		 $scope.imagem="";
		 $("#img")[0].src = "";
		 tagService.lista().success(function (data){
	    	 $scope.tags = data;
	     });
	 }
	
	
	$scope.send = function() {
		var form = $('#formProduto')[0] ;
		
		console.log($(form).serialize());
		var fd = new FormData(form);
		
		 $http.post('../resources/produto/add',fd, {
			  headers: {
//				  'Content-Type': 'application/x-www-form-urlencoded'
				  'Content-Type': undefined
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

 		$http.post('../resources/produto/edit/'+$scope.id,$(form).serialize(), {
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