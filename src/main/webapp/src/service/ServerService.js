app.factory("ServerService", function($http, AppConstants) {
	function all() {
		return $http.get("/gifchat/api/messages/all");
	}

	function login(username, password) {
		var cred = {
			"username" : username,
			"password" : password
		};
		return $http.post("/gifchat/api/messages/login", cred);
	}

	function newMessage(message) {
		var data = {
			"message" : message
		};
		return $http.post("/gifchat/api/messages/new", data);
	}

	return {
		all : all,
		login : login,
		newMessage : newMessage

	};
});