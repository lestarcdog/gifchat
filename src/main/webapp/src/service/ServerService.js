app.factory("ServerService", function($http,BaseUrlConst) {
	function all() {
		return $http.get(BaseUrlConst + "/api/messages/all");
	}

	function login(username, password) {
		var cred = {
			"username" : username,
			"password" : password
		};
		return $http.post(BaseUrlConst + "/api/messages/login", cred);
	}

	function newMessage(message) {
		var data = {
			"message" : message
		};
		return $http.post(BaseUrlConst + "/api/messages/new", data);
	}
	
	function currentUsers() {
		return $http.get(BaseUrlConst + "/api/messages/currentUsers");
	}

	return {
		all : all,
		login : login,
		newMessage : newMessage,
		currentUsers : currentUsers

	};
});