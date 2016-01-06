app.factory("ServerService", function($http, $location, $q, BaseUrlConst) {
	
	var error = function(rejection) {
		if(rejection.status == 401) {
			$location.path("/");
		} else {
			console.log("Error");
			console.log(rejection.data);
			$q.reject(data);
		}
	}
	
	function all() {
		return $http.get(BaseUrlConst + "/api/messages/all").then(function (response) {
			return response.data;
		}, error);
	}

	function login(username, password) {
		var cred = new UserCredentialDto(username, password);
		return $http.post(BaseUrlConst + "/api/messages/login", cred).then(function(response) {
			return response.data;
		}, error);
	}

	function newMessage(message) {
		var data = new UserMessageDto(message);
		return $http.post(BaseUrlConst + "/api/messages/new", data).then(function(response) {
			return response.data;
		}, error);
	}

	function currentTopUsers() {
		return $http.get(BaseUrlConst + "/api/messages/currentTopUsers").then(function(response) {
			return response.data;
		}, error);
	}

	//because of openshift listens on 8000
	var port = $location.port();
	if(port == 80) {
		port = 8000;
	}
	var url = "ws://" + $location.host() + ":" + port + BaseUrlConst + "/ws";
	var ws = null;

	function initWebSocketConnection() {
		console.log("connection to url "+url);
		ws = new WebSocket(url);
	}

	function addListeners(newMessageListener) {
		if (ws == null) {
			console.log("web socket is not initialized yet. exiting");
			return;
		}
		var listener = function onMessageListener(event) {
			//console.log("WS received: " + event.data);
			var msg = JSON.parse(event.data);
			if (msg._type = ".GifMessageDto") {
				newMessageListener(msg);
			}
		}
		ws.onmessage = listener;
	}

	return {
		all : all,
		login : login,
		newMessage : newMessage,
		currentTopUsers : currentTopUsers,
		initWebSocketConnection: initWebSocketConnection,
		addListeners: addListeners

	};
});