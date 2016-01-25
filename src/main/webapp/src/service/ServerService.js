app.factory("ServerService", function($http, $location, $q, $rootScope, BaseUrlConst) {

    var error = function(rejection) {
        if (rejection.status == 401) {
            $rootScope.loggedIn = false;
            $rootScope.username = null;
            $location.path("/");
        } else {
            console.log("Error");
            console.log(rejection.data);
            $q.reject(rejection.data);
        }
    };

    function lastMessages() {
        return $http.get(BaseUrlConst + "/api/messages/lastMessages").then(function(response) {
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
        var data = new NewMessageDto(message);
        return $http.post(BaseUrlConst + "/api/messages/new", data).then(function(response) {
            return response.data;
        }, error);
    }

    function currentTopUsers() {
        return $http.get(BaseUrlConst + "/api/messages/currentTopUsers").then(function(response) {
            return response.data;
        }, error);
    }

    function earlierMessages(firstMessage) {
        var ts = firstMessage.sentTime;
        return $http.get(BaseUrlConst + "/api/messages/earlierThan?ts=" + ts).then(function(response) {
            return response.data;
        }, error);
    }

    // because of openshift listens on 8000
    var port = $location.port();
    if (port == 80) {
        port = 8000;
    }
    var url = "ws://" + $location.host() + ":" + port + BaseUrlConst + "/ws";
    var ws = null;

    function initWebSocketConnection() {
        console.log("connection to url " + url);
        ws = new WebSocket(url);
    }

    function addListeners(newMessageListener) {
        if (ws == null) {
            console.log("web socket is not initialized yet. exiting");
            return;
        }
        var listener = function onMessageListener(event) {
            // console.log("WS received: " + event.data);
            var msg = JSON.parse(event.data);
            if (msg._type = ".GifMessageDto") {
                newMessageListener(msg);
            }
        };
        ws.onmessage = listener;
    }
    
    // ===== Sound/speak methods ========
    
    var makeSoundOfMessage = function(id) {
    	return $http.post(BaseUrlConst + "/api/sound/makeSound", {"id" : id}).then(function(response) {
    		return response.data;
    	}, error);
    };
    
    var getSoundOfMessage = function(id) {
    	//return $http.get(BaseUrlConst + "/api/sound/get/"+id).then(function(response) {
    	//}, error);
    	return BaseUrlConst + "/api/sound/"+id;
    };
    

    return {
        lastMessages : lastMessages,
        earlierMessages : earlierMessages,
        login : login,
        newMessage : newMessage,
        currentTopUsers : currentTopUsers,
        initWebSocketConnection : initWebSocketConnection,
        addListeners : addListeners,
        //===== sound ========
        makeSoundOfMessage : makeSoundOfMessage,
        getSoundOfMessage : getSoundOfMessage

    };
});