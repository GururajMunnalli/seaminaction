package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("designList")
public class DesignList extends EntityQuery<Design> {

	private static final String EJBQL = "select design from Design design";

	private static final String[] RESTRICTIONS = {
			"lower(design.imageContentType) like lower(#{designList.design.imageContentType.concat('%')})",
			"lower(design.title) like lower(#{designList.design.title.concat('%')})",};

	private Design design = new Design();

	public DesignList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Design getDesign() {
		return design;
	}
}
