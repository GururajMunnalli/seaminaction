package org.open18.action;

import org.open18.model.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.framework.EntityNotFoundException;

@Name("facilityHome")
public class FacilityHome extends EntityHome<Facility> {

	private boolean addCourse = true;
	
	public void setAddCourse(boolean addCourse) {
		this.addCourse = addCourse;
	}	
	
	public boolean isAddCourse() {
		return addCourse;
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

}
