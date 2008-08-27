function addToFavorites(entityId, entityName, golferId) {
	loadMessages();
	var favoritesAction = Seam.Component.getInstance("favoritesAction");
	favoritesAction.isFavorite(
		entityId, entityName, function(exists) {
		if (exists) {
			var message = messages.get("favorite.duplicate");
			message = message.replace("{0}", entityName);
			alert(message);
		}
		else {
			var favorite = Seam.Component.newInstance("newFavorite");
			favorite.setEntityId(entityId);
			favorite.setEntityName(entityName);
			// NOTE: only retrieve type by class name if @Name is not on class
			var golfer = Seam.Remoting.createType("org.open18.model.Golfer");
			golfer.setId(golferId);
			favorite.setGolfer(golfer);
			favoritesAction.addFavorite(favorite, notify);
		}
	});
}

function notify(favoriteInstance) {
	if (favoriteInstance == null) {
		alert(messages.get("favorite.addFailed"));
	}
	var message = messages.get("favorite.addSucceeded");
	message = message.replace("{0}", favoriteInstance.getEntityName());
	message = message.replace("{1}", favoriteInstance.getId());
	alert(message);
}
