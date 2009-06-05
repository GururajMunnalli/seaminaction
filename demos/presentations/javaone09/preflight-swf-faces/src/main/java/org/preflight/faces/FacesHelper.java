package org.preflight.faces;

import javax.faces.context.FacesContext;

public class FacesHelper {
	
	@SuppressWarnings("unchecked")
	public static <T> T resolveContextVariable(String name, Class<T> expectedType) {
		return (T) facesContext().getApplication().getELResolver().getValue(facesContext().getELContext(), null, name);
	}
	
	public static FacesContext facesContext() {
		return FacesContext.getCurrentInstance();
	}
}
