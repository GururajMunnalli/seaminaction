package org.designworkz.comps.model;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

/**
 * Entity implementation class for Entity: Company
 */
@Entity
@Table(name = "company", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Set<Company> clients = new HashSet<Company>();
	private Set<Design> designs = new HashSet<Design>();

	public Company() {
		super();
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClients(Set<Company> clients) {
		this.clients = clients;
	}

	@ManyToMany(cascade = { PERSIST, REMOVE })
	@JoinTable(joinColumns = @JoinColumn(name = "company_id"), inverseJoinColumns = @JoinColumn(name = "client_id"), name = "company_client")
	public Set<Company> getClients() {
		return clients;
	}

	public boolean isClientOf(Company candidate) {
		if (candidate == null || candidate.getClients() == null) {
			return false;
		}
		
		for (Company c : candidate.getClients()) {
			if (getId().equals(c.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasMember(MemberAccount member) {
		if (member == null || member.getCompany() == null) {
			return false;
		}
		
		return member.getCompany().getId().equals(getId());
	}
	
	public void setDesigns(Set<Design> designs) {
		this.designs = designs;
	}

	@OneToMany(cascade = { PERSIST, REMOVE }, mappedBy = "company")
	public Set<Design> getDesigns() {
		return designs;
	}

}
