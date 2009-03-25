package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("companyList")
public class CompanyList extends EntityQuery<Company> {

	private static final String EJBQL = "select company from Company company";

	private static final String[] RESTRICTIONS = {"lower(company.name) like lower(#{companyList.company.name.concat('%')})",};

	private Company company = new Company();

	public CompanyList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Company getCompany() {
		return company;
	}
}
