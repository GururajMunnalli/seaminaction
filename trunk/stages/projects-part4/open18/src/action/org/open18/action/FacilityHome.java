package org.open18.action;

import org.open18.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.framework.EntityNotFoundException;

@Name("facilityHome")
public class FacilityHome extends EntityHome<Facility> {

	@In private Conversation conversation;

	@In	Map<String, UIComponent> uiComponent;
	
	private boolean enterCourse = true;
	
	private String lastStateChange;
	
    private byte[] newLogo;

    private String newLogoContentType;
	
	public String getLastStateChange() {
		return this.lastStateChange;
	}
	
	public void setEnterCourse(boolean enterCourse) {
		this.enterCourse = enterCourse;
	}	
	
	public boolean isEnterCourse() {
		return enterCourse;
	}

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

	public void wire() {
		getInstance();
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
	
	public String validateEntityFound() {
		try {
			this.getInstance();
		} catch (EntityNotFoundException e) {
			return "invalid";
		}

		return this.isManaged() ? "valid" : "invalid";
	}
	
	@Override
	public String persist() {
		if (getNewLogo() != null && getNewLogoContentType() != null) {
			getInstance().setLogo(getNewLogo());
			getInstance().setLogoContentType(getNewLogoContentType());
			newLogo = null;
			newLogoContentType = null;
		}
		
		lastStateChange = super.persist();
		return lastStateChange;
	}

	@Restrict("#{s:hasPermission('facilityHome', 'remove', facilityHome.instance)}")
	@Override
	public String remove() {
		lastStateChange = super.remove();
		return lastStateChange;
		}

	@Restrict("#{s:hasPermission('facilityHome', 'update', facilityHome.instance)}")
	@Override
	public String update() {
		if (getNewLogo() != null && getNewLogoContentType() != null) {
			getInstance().setLogo(getNewLogo());
			getInstance().setLogoContentType(getNewLogoContentType());
			newLogo = null;
			newLogoContentType = null;
		}

		lastStateChange = super.update();
		if (conversation.isNested()) {
			conversation.endAndRedirect();
		}
		return lastStateChange;
	}
	
	public void updateCityAndState(ValueChangeEvent e) {
		String zipCode = (String) e.getNewValue();
		UIComponent city = e.getComponent().findComponent(":facility:cityField:city");
		UIComponent state = uiComponent.get("facility:stateField:state");
		if ("20724".equals(zipCode)) {
			((EditableValueHolder) city).setSubmittedValue("Laurel");
			((EditableValueHolder) state).setSubmittedValue("MD");
		}
	}

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
