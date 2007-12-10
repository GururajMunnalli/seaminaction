package org.open18.action;

import org.open18.model.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

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

}
