package org.open18.action;

import org.open18.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.List;
import java.util.Arrays;

@Name("courseList")
public class CourseList extends EntityQuery {

	private static final String[] RESTRICTIONS = {
			"lower(course.description) like concat(lower(#{courseList.course.description}),'%')",
			"lower(course.designer) like concat(lower(#{courseList.course.designer}),'%')",
			"lower(course.fairways) like concat(lower(#{courseList.course.fairways}),'%')",
			"lower(course.greens) like concat(lower(#{courseList.course.greens}),'%')",
			"lower(course.name) like concat(lower(#{courseList.course.name}),'%')",};

	private Course course = new Course();

	@Override
	public String getEjbql() {
		return "select course from Course course";
	}

	@Override
	public Integer getMaxResults() {
		return 25;
	}

	public Course getCourse() {
		return course;
	}

	@Override
	public List<String> getRestrictions() {
		return Arrays.asList(RESTRICTIONS);
	}

}
