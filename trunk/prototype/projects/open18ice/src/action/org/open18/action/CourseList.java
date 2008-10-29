package org.open18.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.open18.model.Course;

@Name("courseList")
@Scope(ScopeType.PAGE)
public class CourseList extends EntityQuery<Course> {

	private static final String[] RESTRICTIONS = {
			"lower(course.description) like concat(lower(#{courseList.course.description}),'%')",
			"lower(course.designer) like concat(lower(#{courseList.course.designer}),'%')",
			"lower(course.fairways) like concat(lower(#{courseList.course.fairways}),'%')",
			"lower(course.greens) like concat(lower(#{courseList.course.greens}),'%')",
			"lower(course.name) like concat(lower(#{courseList.course.name}),'%')",};

	@In(value = "courseExample", create = true)
	//@Out(value = "courseExample", scope = ScopeType.CONVERSATION, required = false)
	@Out(value = "courseExample", required = false)
	private Course course;

	@In
	private QueryState queryState;
	
	@Override
    public void validate() {
	    super.validate();
	    super.setOrder(queryState.getOrder());
	    super.setFirstResult(queryState.getFirstResult());
	}

	@Override
	public String getEjbql() {
		return "select course from Course course";
	}

	@Override
	public Integer getMaxResults() {
		return 25;
	}

	@Override
	public String getOrder() {
		if (super.getOrder() == null) {
			setOrder("name asc");
		}
		return super.getOrder();
	}

	public Course getCourse() {
		return course;
	}

	public void clearSearch() {
	    super.refresh();
	    course = null;
	    setOrder(null);
	    first();
	}
	
	@End(beforeRedirect = true)
	public void reset() {
	    clearSearch();
	}
	
	@Override
	public List<String> getRestrictions() {
		return Arrays.asList(RESTRICTIONS);
	}

}
