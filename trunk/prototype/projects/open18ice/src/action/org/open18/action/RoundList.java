package org.open18.action;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

@Name("roundList")
public class RoundList extends EntityQuery {

	private String countEjbql;

	// FIXME: overriding this causes the restrictions to fail
	@Override
	public String getCountEjbql() {
		return countEjbql != null ? countEjbql : super.getCountEjbql();
	}

	public void setCountEjbql(String ejbql) {
		this.countEjbql = ejbql;
	}

}
