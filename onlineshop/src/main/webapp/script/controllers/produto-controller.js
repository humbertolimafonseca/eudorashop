eudoraShop.controller('produtoCtrl', function ($scope, $http, $rootScope, tagService, marcaService, $routeParams) {
	 $scope.params = $routeParams;
	 
	 //$scope.tags=[];
     $scope.selectedTags=[];
	 
	 $scope.listar = function (){
		 $http.get('../resources/produto').success(function(data) {
			    $scope.produtos = data;
			    console.log("$scope.listar() chamado..");
			    console.log($scope.produtos);
			    
			    $scope.totalItems = $scope.produtos.length;
			    $scope.maxsize = 5;
				$scope.currentPage = 1;
				$scope.start = (($scope.currentPage-1)*$scope.maxsize);
				 
		  });
	 }
	 
//	 $scope.listar();
	 
	 $scope.pageChanged = function(){
		 console.log('Page changed to: ' + $scope.currentPage);
		 $scope.start = (($scope.currentPage-1)*$scope.maxsize);
		 console.log('Page start: ' + $scope.start);
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
	    return mode === 'day' ;
	  }
		  
	  $scope.dateOptions = {
		    dateDisabled: disabled,
		    formatYear: 'yy',
		    maxDate: new Date(2020, 5, 22),
		    minDate: new Date(),
		    startingDay: 1
		  };
	  
	  $scope.load2 = function (id){	
			 $http.get('../resources/produto/'+ id).success(function(data) {
				    $scope.produto = data;
			 });
	  };
	  
	  $scope.alert = function(a){
		  console.log(a);
	  }
	  
	  
	 $scope.load = function (id){
		 if(id){
			 $http.get('../resources/produto/'+ id).success(function(data) {
				    $scope.produto = data;
				    $scope.imagem = data.imagens[0];
				    //$('#img')[0].src = '../resources/imagem/produto/'+ $scope.produto.id + "/" + $scope.imagem.nome;
				    $scope.listaTags($scope.carregaTags);
				    $scope.listaMarcas($scope.carregaMarca);
			  });
		 }else{
			 $scope.listaTags();
			 $scope.listaMarcas();
		 }
	 }
	 
//	 var listeners = function(){
//		  $scope.load($scope.params['produtoId']);
//	  }
	 
	 $scope.carregaTags = function() {
		 for(var sTagi in $scope.produto.tags){
		    	
		    	var sTag = $scope.produto.tags[sTagi];
//		    	console.log("STAG: ");
//		    	console.log(sTag);
			    for (var tag in $scope.tags){
				    if(sTag.nome == $scope.tags[tag].nome ){
//				    		alert("Select: " + sTag.nome );
				    		console.log($scope.tags[tag].nome);
				    		$scope.selectTag($scope.tags[tag]);
				    }
				}
	 		}
		 
	 }
	 
	
	 
	 $scope.listaTags = function(listener){
		 tagService.lista().success(function (data){
	    	 $scope.tags = data;
	    	 console.log("TAGS");
	    	 console.log($scope.tags);
	    	 
	    	 if(listener){
	    		 listener();
	    	 }
	    	 
	    	 
	     });
	 }
	 
	 $scope.carregaMarca = function(){
		 $scope.marca = '' + $scope.produto.marca.id;
	 }
	 
	 
	 $scope.listaMarcas = function(listener){
	     marcaService.lista().success(function (data){
	    	 $scope.marcas = data;
//	    	 console.log("Lista de marcas..");
//	    	 console.log($scope.marcas);
	    	 if(listener){
	    		 listener();
	    	 }
	     });
	 }
	 
	 
	 $scope.cancel = function (){
		$scope.limparForm();
		$rootScope.message=null;
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
			 $rootScope.message = data;
			 $rootScope.messageError = false;
			 $scope.listar();
		 }).error(function(data){
			 $rootScope.message = data;
			 $rootScope.messageError = true;
		 });
		 
	 }
	 
	 $scope.limparForm = function(){
		 $scope.produto="";
		 $scope.selectedTags=[];
		 $scope.imagem="";
		 $("#img")[0].src = "";
		 $("#formProduto")[0].reset();
		 
		 for (tag in $scope.tags)
		 {
			 $scope.tags[tag].checked = false;
		 }
		 
	 }
	
	$scope.send = function(url) {
		var form = $('#formProduto')[0] ;
		
		console.log($(form).serialize());
		var fd = new FormData(form);
		
		 $http.post('../resources/produto/add',fd, {
			  headers: {
//				  'Content-Type': 'application/x-www-form-urlencoded'
				  'Content-Type': undefined
            }
		  }).success(function(data){
			  	$rootScope.message = data;
			  	$scope.messageError = false;
			  	$scope.limparForm();
			  	$scope.listar();
			  	$scope.redirect('#/produto', data);
		  }).error(function(data){
			  $rootScope.message = data;
			  $rootScope.messageError = true;
		  })

     };
     
     function Tag(nome, checked){
         this.nome = nome;
         this.checked = checked;
     }

     $scope.selectTag = function(tag){
    	 
    	 if(tag.checked){
    		 $scope.removerTag(tag)
    	 }else{
    		 tag.checked=true;
    		 console.log("CHECKED TO TRUE");
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
     
     
//     $scope.onload = function() {
//		 
//		 if($scope.params['produtoId']){
//			 $scope.load($scope.params['produtoId']);
//		 }
//		 
//	 }
//	 
//	 $scope.onload();
     
     
     $scope.edit = function() {
 		var form = $('#formProduto')[0] ;
		
		console.log($(form).serialize());
		var fd = new FormData(form);

 		$http.post('../resources/produto/edit/'+$scope.produto.id,fd, {
 			  headers: {
 				  'Content-Type': undefined
             }
 		  }).success(function(data){
 			  	$rootScope.message = data;
		 		$rootScope.messageError = false;
 			  	//$scope.limparForm();
 		  }).error(function(data){
 			 $rootScope.message = data;
 			 $rootScope.messageError = true;
 		  })
      };
     
     
	});