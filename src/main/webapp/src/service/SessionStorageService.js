app.factory("SessionStorageService", function(SessionStorageConst) {
	
	function addCurrentUser(username) {
		localStorage.setItem(SessionStorageConst.usernameKey, username);
	}
	
	function getCurrentUser() {
		return localStorage.getItem(SessionStorageConst.usernameKey);
	}
	
	function add(key,value) {
		localStorage.setItem(key, value);
	}
	
	function get(key) {
		return localStorage.getItem(key);
	}
	
	return {
		addCurrentUser : addCurrentUser,
		getCurrentUser : getCurrentUser,
		add:add,
		get:get
	}
});