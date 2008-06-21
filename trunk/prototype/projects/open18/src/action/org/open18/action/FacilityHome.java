package org.open18.action;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.framework.EntityNotFoundException;
import org.open18.model.Course;
import org.open18.model.Facility;
import org.open18.util.UIComponentLocator;

import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.core.Conversation;

@Name("facilityHome")
public class FacilityHome extends EntityHome<Facility> {

	private byte[] newLogo;

	private String newLogoContentType;

	public void setFacilityId(Long id) {
		setId(id);
	}

	public Long getFacilityId() {
		return (Long) getId();
	}

	@Override
	protected Facility createInstance() {
		Facility facility = new Facility();
		return facility;
	}

	public void setInstance(Facility facility) {
		super.setInstance(facility);
		newLogo = null;
		newLogoContentType = null;
	}

	public void wire() {
	}

	public boolean isWired() {
		return true;
	}

	public Facility getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Course> getCourses() {
		return getInstance() == null ? null : new ArrayList<Course>(
				getInstance().getCourses());
	}

	public String update() {
		if (getNewLogo() != null && getNewLogoContentType() != null) {
			getInstance().setLogo(getNewLogo());
			getInstance().setLogoContentType(getNewLogoContentType());
			newLogo = null;
			newLogoContentType = null;
		}

		return super.update();
	}

	public void updateCityAndState(ValueChangeEvent e) {
		String zipCode = (String) e.getNewValue();
		//EditableValueHolder city = (EditableValueHolder) e.getComponent().findComponent(":facility:cityDecoration:city");
		//EditableValueHolder state = (EditableValueHolder) e.getComponent().findComponent(":facility:stateDecoration:state");
		EditableValueHolder city = (EditableValueHolder) UIComponentLocator.instance().findComponent("city");
		EditableValueHolder state = (EditableValueHolder) UIComponentLocator.instance().findComponent("state");
		if ("20724".equals(zipCode)) {
			city.setValue("Laurel");
			state.setValue("MD");
		}
	}
	
	public String validateEntityFound() {
		try {
			this.getInstance();
		} catch (EntityNotFoundException e) {
			return "invalid";
		}

		return this.isManaged() ? "valid" : "invalid";
	}
	
	public String parseRestfulUrl() {
		String viewId =
			FacesContext.getCurrentInstance().getViewRoot().getViewId();
		try {
			String[] segments = viewId.split("/");
			assert segments.length >= 2;
			String action = segments[segments.length - 2];
			String resourceId = segments[segments.length - 1];
			if (resourceId.contains(".")) {
				resourceId = resourceId.substring(0, resourceId.lastIndexOf('.'));
			}
			Long facilityId = Long.parseLong(resourceId);
			setFacilityId(facilityId);
			return validateEntityFound().equals("valid") ? action : "invalid";
		} catch (Exception e) {
			return "invalid";
		}
	}
	
	// FIXME: won't work with pageflow
//	@Observer("org.jboss.seam.afterTransactionSuccess.Facility")
//	public void restoreParentView() {
//		Conversation.instance().redirect();
//	}

	public byte[] getNewLogo() {
		return newLogo;
	}

	public void setNewLogo(byte[] newLogo) {
		this.newLogo = newLogo;
	}

	public String getNewLogoContentType() {
		return newLogoContentType;
	}

	public void setNewLogoContentType(String newLogoContentType) {
		this.newLogoContentType = newLogoContentType;
	}
}
