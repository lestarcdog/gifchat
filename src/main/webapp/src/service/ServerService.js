app.factory("ServerService", function($http, $location, $q, BaseUrlConst) {
	function all() {
		return $http.get(BaseUrlConst + "/api/messages/all");
	}

	function login(username, password) {
		var cred = new UserCredentialDto(username, password);
		console.log(cred);
		return $http.post(BaseUrlConst + "/api/messages/login", cred);
	}

	function newMessage(message) {
		var data = new UserMessageDto(message);
		return $http.post(BaseUrlConst + "/api/messages/new", data);
	}

	function currentUsers() {
		return $http.get(BaseUrlConst + "/api/messages/currentUsers");
	}

	var url = "ws://" + $location.host() + ":" + $location.port() + BaseUrlConst + "/ws";
	var ws = null;

	function initWebSocketConnection() {
		console.log("connection to url "+url);
		ws = new WebSocket(url);
	}

	function addListeners(newMessageListener) {
		if (ws == null) {
			//initWebSocketConnection();
			console.log("web socket is not initialized yet. exiting");
			return;
		}
		var listener = function onMessageListener(event) {
			console.log("WS received: " + event.data);
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
		currentUsers : currentUsers,
		initWebSocketConnection: initWebSocketConnection,
		addListeners: addListeners

	};
});