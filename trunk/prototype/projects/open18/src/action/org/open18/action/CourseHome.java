package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.framework.EntityNotFoundException;
import org.open18.model.Course;
import org.open18.model.Facility;
import org.open18.model.Hole;
import org.open18.model.TeeSet;

import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Name("courseHome")
public class CourseHome extends EntityHome<Course> {

	@In(create = true)
	FacilityHome facilityHome;

	public void setCourseId(Long id) {
		setId(id);
	}

	public Long getCourseId() {
		return (Long) getId();
	}

	@Override
	protected Course createInstance() {
		Course course = new Course();
		return course;
	}

	public void wire() {
		Facility facility = facilityHome.getDefinedInstance();
		if (facility != null) {
			getInstance().setFacility(facility);
		}
	}

	public boolean isWired() {
		if (getInstance().getFacility() == null)
			return false;
		return true;
	}

	public Course getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	@Factory(value = "course", scope = ScopeType.EVENT)
	@Override
	public Course getInstance() {
		return super.getInstance();
	}

//	@Override
//	public void create() {
//		setCreatedMessage("You have successfully added #{course.name}. "
//			+ "Thanks for contributing!");
//		setUpdatedMessage("You have successfully updated #{course.name}. "
//			+ "Your careful eye is appreciated!");
//		setDeletedMessage("#{course.name} has been removed. "
//			+ "I never liked it anyway.");
//	}

	/**
	 * <p>Order the holes according to the value of the number property.</p>
	 * <p>Note that this could also be done by adding an @OrderBy("number asc") above
	 * the getHoles() method on the Course entity so that the collection is sorted
	 * coming out of the database.</p>
	 */
	public List<Hole> getHoles() {
		if (getInstance() == null) {
			return null;
		}

		List<Hole> holes =
				new ArrayList<Hole>(getInstance().getHoles());
		Collections.sort(holes, new Comparator<Hole>() {

			public int compare(Hole a, Hole b) {
				return Integer.valueOf(a.getNumber()).compareTo(Integer.valueOf(b.getNumber()));
			}
		});

		return holes;
	}

	/**
	 * <p>Order the tee sets according to the value of the position property.</p>
	 * <p>Note that this could also be done by adding an @OrderBy("position asc") above
	 * the getTeeSets() method on the Course entity so that the collection is sorted
	 * coming out of the database.</p>
	 */
	public List<TeeSet> getTeeSets() {
		if (getInstance() == null) {
			return null;
		}

		List<TeeSet> teeSets =
				new ArrayList<TeeSet>(getInstance().getTeeSets());
		Collections.sort(teeSets, new Comparator<TeeSet>() {

			public int compare(TeeSet a, TeeSet b) {
				return a.getPosition() == null ||
						b.getPosition() == null ? 0 : a.getPosition().compareTo(b.getPosition());
			}
		});

		return teeSets;
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
			Long courseId = Long.parseLong(resourceId);
			setCourseId(courseId);
			return validateEntityFound().equals("valid") ? action : "invalid";
		} catch (Exception e) {
			return "invalid";
		}
	}

}
