package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("companyHome")
public class CompanyHome extends EntityHome<Company> {

	public void setCompanyId(Long id) {
		setId(id);
	}

	public Long getCompanyId() {
		return (Long) getId();
	}

	@Override
	protected Company createInstance() {
		Company company = new Company();
		return company;
	}

	public void load() {
		getDefinedInstance();
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Company getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Design> getDesigns() {
		return getInstance() == null ? null : new ArrayList<Design>(
				getInstance().getDesigns());
	}
	
	public List<Company> getClients() {
		return getInstance() == null ? null : new ArrayList<Company>(getInstance().getClients());
	}
	
	public void setClients(List<Company> clients) {
		if (getInstance() != null) {
			getInstance().setClients(new HashSet<Company>(clients));
		}
	}

}
