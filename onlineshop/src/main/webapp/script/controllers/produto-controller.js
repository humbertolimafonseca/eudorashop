eudoraShop.controller('produtoCtrl', function ($scope, $http, tagService, marcaService) {
	
	 $scope.listar = function (){
		 $http.get('../resources/produto').success(function(data) {
			    $scope.produtos = data;
			    console.log("$scope.listar() chamado..");
			    console.log($scope.produtos);
		  });
	 }
	 
	 $scope.popup = {
	    opened: false
	  };
	 
	 $scope.popup2 = {
			    opened: false
	  };
	 
	 $scope.open = function() {
		    $scope.popup.opened = true;
		  };
		  
	  $scope.open2 = function() {
		    $scope.popup2.opened = true;
		  };
		  
	  function disabled(data) {
	    var date = data.date,
	      mode = data.mode;
	    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
		  
	  $scope.dateOptions = {
		    dateDisabled: disabled,
		    formatYear: 'yy',
		    maxDate: new Date(2020, 5, 22),
		    minDate: new Date(),
		    startingDay: 1
		  };
	 
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
			    $scope.marca = data.marca;
			    $scope.codigo = data.codigo;
			    $scope.inicio = new Date( data.inicio );
			    $scope.fim = new Date( data.fim );
			    console.log($scope.inicio);
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
		 $scope.marca="";
		 $scope.selectedTags=[];
		 $scope.imagem="";
		 $("#img")[0].src = "";
		 $("#formProduto")[0].reset();
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
     
     marcaService.lista().success(function (data){
    	 $scope.marcas = data;
    	 console.log($scope.marcas);
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
		
		console.log($(form).serialize());
		var fd = new FormData(form);

 		$http.post('../resources/produto/edit/'+$scope.id,fd, {
 			  headers: {
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
     
     
	});