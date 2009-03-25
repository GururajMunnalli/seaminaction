package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("designHome")
public class DesignHome extends EntityHome<Design> {

	@In(create = true)
	CompanyHome companyHome;

	public void setDesignId(Long id) {
		setId(id);
	}

	public Long getDesignId() {
		return (Long) getId();
	}

	@Override
	protected Design createInstance() {
		Design design = new Design();
		return design;
	}

	public void load() {
		getDefinedInstance();
	}

	public void wire() {
		getInstance();
		Company company = companyHome.getDefinedInstance();
		if (company != null) {
			getInstance().setCompany(company);
		}
	}

	public boolean isWired() {
		if (getInstance().getCompany() == null)
			return false;
		return true;
	}

	public Design getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
