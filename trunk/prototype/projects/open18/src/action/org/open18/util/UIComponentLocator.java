package org.open18.util;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.Iterator;

@Name("uiComponentLocator")
public class UIComponentLocator {

	@In
	FacesContext facesContext;

	public UIComponent findComponent(String id) {
		return facesContext.getViewRoot().findComponent(resolveClientId(id));
	}

	public String resolveClientId(String id) {
		return resolveClientId(facesContext.getViewRoot(), id, facesContext);
	}

	private String resolveClientId(UIComponent component, String id, FacesContext facesContext) {
		String componentId = component.getId();
		if (componentId != null && componentId.equals(id)) {
			return component.getClientId(facesContext);
		} else {
			Iterator iter = component.getFacetsAndChildren();
			while (iter.hasNext()) {
				UIComponent child = (UIComponent) iter.next();
				String clientId = resolveClientId(child, id, facesContext);
				if (clientId != null) {
					return clientId;
				}
			}
			return null;
		}
	}

	public static UIComponentLocator instance() {
		return (UIComponentLocator) Component.getInstance(UIComponentLocator.class);
	}

}
