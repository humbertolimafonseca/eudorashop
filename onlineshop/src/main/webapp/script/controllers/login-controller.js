eudoraShop.controller('loginCtrl', function($scope, $http, $rootScope) {

	$scope.getLogged = function() {
		$http.get('../resources/usuario/logged').success(function(data) {
			$rootScope.usuario = data;

		}).error(function(data) {
			$rootScope.usuario.nome = "";
			$rootScope.usuario.email = "";
		})
	};

	$scope.getLogged();

	$scope.reload = function() {
		location.reload();
	}

	$scope.cancel = function() {
		$scope.limparForm();
	}

	$scope.limparForm = function() {
		$scope.login = "";
		$scope.senha = "";
	}

	$scope.logout = function() {

		$http.get('../resources/usuario/logout').success(function(data) {
			$rootScope.usuario="";

		}).error(function(data) {
			$rootScope.message = data;
			$rootScope.messageError = true;
		})
	};
	
	$scope.novoUsuario = function(){
		$http.post('../resources/usuario/add', $(form).serialize(), {
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).then(
				function(data) {
					$rootScope.message = data.data;
					$rootScope.messageError = false;
				}, 
				function(data) {
					$rootScope.message = data;
					$rootScope.messageError = true;
				}
		);
	}

	$scope.logar = function() {
		var form = $('#loginForm')[0];
		// alert(form.elements[0].value);
		$http.post('../resources/usuario/login', $(form).serialize(), {
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).then(function(data) {
			$rootScope.usuario = data.data;
			console.log("USUARIO");
			console.log(data);
			$rootScope.message = "Login realizado com sucesso!";
			$rootScope.messageError = false;
			$scope.login = "";
			$scope.senha = "";

		}, function(data) {
			$rootScope.usuario.nome = "";
			$rootScope.usuario.email = "";
			$rootScope.message = data;
			$rootScope.messageError = true;
		})
	};

});