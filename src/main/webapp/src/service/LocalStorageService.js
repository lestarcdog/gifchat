app.factory("LocalStorageService", function() {
	
	
	function add(key,value) {
		localStorage.setItem(key, value);
	}
	
	function get(key) {
		return localStorage.getItem(key);
	}
	
	return {
		add:add,
		get:get
	};
});