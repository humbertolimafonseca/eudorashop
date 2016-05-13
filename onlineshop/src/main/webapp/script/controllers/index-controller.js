eudoraShop.controller('indexCtrl', function($scope, $http, tagService,
		marcaService, produtoService) {

	$scope.rate = 4;

	tagService.lista().success(function(data) {
		$scope.tags = data;
	});

	produtoService.lista().success(function(data) {
		$scope.produtos = data;
	});

	marcaService.lista().success(function(data) {
		$scope.marcas = data;
	});

	$scope.selectTag = function(tag) {
		tag.checked = !tag.checked;
	}

});