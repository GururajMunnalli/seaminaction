package org.open18.action;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.open18.model.Course;

/**
 * can't data model selection be done with s:link?
 */
@Name("courseComparison")
@Scope(ScopeType.CONVERSATION)
public class CourseComparison implements Serializable {
    
    private int maxCourses = 4;
    
    @In
    private EntityManager entityManager;
    
    @In
    private FacesMessages facesMessages;
    
    @Out(scope = ScopeType.EVENT, required = false)
    private Course selectedCourse;
    
    @Out
    private boolean comparisonModeEnabled = false;
    
	@Out
    private List<Course> comparedCourses = new ArrayList<Course>();

    public void unselect(Course course) {
        selectedCourse = null;
        if (comparedCourses.remove(course)) {
            selectedCourse = course;
            facesMessages.add("Removed #{selectedCourse.name} from comparison.");
        }
    }
    
    public void unselect(Long courseId) {
        selectedCourse = null;
        for (Course candidate : comparedCourses) {
            if (candidate.getId().equals(courseId)) {
                selectedCourse = candidate;
                break;
            }
        }
        
        if (selectedCourse != null) {
            comparedCourses.remove(selectedCourse);
            facesMessages.add("Removed #{selectedCourse.name} from comparison.");
        }
    }
    
    public void select(Course course) {
        selectedCourse = null;
        if (course == null) {
            facesMessages.add(FacesMessage.SEVERITY_ERROR, "Invalid course selection");
            return;
        }
        
        if (comparedCourses.size() >= maxCourses) {
            facesMessages.add(FacesMessage.SEVERITY_WARN, "You can only compare a maximum of " + maxCourses + " courses");
            return;
        }
        
        selectedCourse = course;
        if (comparedCourses.contains(selectedCourse)) {
            facesMessages.add(FacesMessage.SEVERITY_WARN, "#{selectedCourse.name} has already been selected.");
        }
        else {
            comparedCourses.add(selectedCourse);
            facesMessages.add("Added #{selectedCourse.name} for comparison.");
        }
    }
    
    @Transactional
    public void select(Long courseId) {
        if (comparedCourses.size() >= maxCourses) {
            facesMessages.add(FacesMessage.SEVERITY_WARN, "You can only compare a maximum of " + maxCourses + " courses");
            return;
        }
        
        selectedCourse = entityManager.find(Course.class, courseId);
        if (selectedCourse == null) {
            facesMessages.add(FacesMessage.SEVERITY_ERROR, "Invalid course selection");
            return;
        }
        else if (comparedCourses.contains(selectedCourse)) {
            facesMessages.add(FacesMessage.SEVERITY_WARN, "#{selectedCourse.name} has already been selected.");
        }
        else {
            comparedCourses.add(selectedCourse);
            facesMessages.add("Added #{selectedCourse.name} for comparison.");
        }
    }
    
    public void enable() {
        comparisonModeEnabled = true;
    }
    
    public void disable() {
        comparisonModeEnabled = false;
    }
    
    public void reset() {
        comparedCourses.clear();
    }
    
    public String validate() {
        return comparisonModeEnabled && comparedCourses.size() > 1 ? "valid" : "invalid";
    }

    public List<Course> getCourses() {
        return comparedCourses;
    }

	public String getCourseNames() {
		String names = "";
		for (int i = 0; i < comparedCourses.size(); i++) {
			if (i > 0) {
				names += ", ";
			}
			names += comparedCourses.get(i).getName();
		}
		return names;
	}
}
