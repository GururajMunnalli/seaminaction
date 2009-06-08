if (!window.JSFOne) window.JSFOne = {};
/**
 * This script implements the logic to auto-tab between blocks of a phone
 * number input component.  The code is on loan from http://www.verizon.com
 */
JSFOne.PhoneInput = function(clientId) {
	this.clientId = clientId;
	this.autoTabState = true;
	this.isNN = (navigator.appName.indexOf("Netscape") != -1);

	var input = document.getElementById(clientId);
	input.onfocus = function() { this.select(); };
	input = this.getNextElement(input);
	input.onfocus = function() { this.select(); };
	input = this.getNextElement(input);
	input.onfocus = function() { this.select(); };
};

JSFOne.PhoneInput.instances = {};

JSFOne.PhoneInput.getInstance = function(clientId) {
	var instance = JSFOne.PhoneInput.instances[clientId];
	if (instance != null) {
		return instance;
	}

	instance = new JSFOne.PhoneInput(clientId);
	JSFOne.PhoneInput.instances[clientId] = instance;
	return instance;
}

JSFOne.PhoneInput.prototype = {
	stopAutoTab : function() {
		this.autoTabState = false;
	},

	autoTab : function(input, len, e) {
		if (!this.isNumeric(e)) {
			return false;
		}

		// the line number is the end of the road
		if (len == 4) {
			return true;
		}

		var keyCode = this.isNN ? e.which : e.keyCode; 
		var filter = this.isNN ? [0, 8, 9] : [0, 8, 9, 16, 17, 18, 37, 38, 39, 40, 46];
		var len = this.isNN ? len - 1 : len;

		if (input.value.length > len) {
			input.value = '';
		}
		else if (input.value.length >= len && !this.containsElement(filter, keyCode) && this.autoTabState) {
			this.focusNextElement(input);
		}

		this.autoTabState = true;
		return true;
	},

	isNumeric : function(e) {
		var keyCode = this.isNN ? e.which : e.keyCode; 
		if (this.isNN) {
			if (keyCode == 0) {
				return true;
			}
		}
		if ((keyCode > 47 && keyCode < 58) || (keyCode == 8) || (keyCode == 9)) {
			return true;
		}
		else if (keyCode == 13) {
			// allow default action
		}
		else {	
			this.preventDefault(e);
			return false;
		}	
	},

	preventDefault : function(e) {
		if (e.returnValue) {
			e.returnValue = false;
		}
		else if (e.preventDefault) {
			e.preventDefault();
		}
		else {
			window.event.returnValue = false;
		}
	},

	containsElement : function(arr, ele) {
		var found = false
		var index = 0;
				
		while (!found && index < arr.length) {
			if (arr[index] == ele) {
				found = true;
			}
			else {
				index++;
			}
			return found;
		}
		return false;
	},

	getNextElement : function(input) {
		var f = input.form;
		for (var i = 0, len = f.length; i < len; i++) {
			if (f[i] == input) {
				if (i + 1 < len) {
					return f[i + 1];
				}
				else {
					return null;
				}
			}
		}
		return null;
	},

	focusNextElement : function(input) {
		var nextElement = this.getNextElement(input);
		if (nextElement != null) {
			nextElement.select();
		}
	}
};
