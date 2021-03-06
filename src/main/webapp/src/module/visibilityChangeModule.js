
var visibilityChangeModule = function($rootScope,eventName) {
	
	// Set the name of the hidden property and the change event for visibility
	var hidden="hidden", visibilityChange="visibilitychange"; 
	if (typeof document.hidden !== "undefined") { // Opera 12.10 and Firefox 18 and later support 
	  hidden = "hidden";
	  visibilityChange = "visibilitychange";
	} else if (typeof document.mozHidden !== "undefined") {
	  hidden = "mozHidden";
	  visibilityChange = "mozvisibilitychange";
	} else if (typeof document.msHidden !== "undefined") {
	  hidden = "msHidden";
	  visibilityChange = "msvisibilitychange";
	} else if (typeof document.webkitHidden !== "undefined") {
	  hidden = "webkitHidden";
	  visibilityChange = "webkitvisibilitychange";
	}
	
	var handle = function () {
		$rootScope.$broadcast(eventName,document[hidden]);
	};
	
	 document.addEventListener(visibilityChange, handle, false);

};