if (!window.JSFOne) window.JSFOne = {};

JSFOne.toggleCheckbox = function(clientId, toState) {
	$(clientId).value = (toState ? 'true' : 'false');
	$(clientId + '_checked').style.display = (toState ? 'inline' : 'none');
	$(clientId + '_unchecked').style.display = (toState ? 'none' : 'inline');
}
