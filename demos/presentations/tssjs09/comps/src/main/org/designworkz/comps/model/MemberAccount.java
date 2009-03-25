package org.designworkz.comps.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.security.management.UserEnabled;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;
import javax.persistence.ManyToOne;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"), name = "member_account")
public class MemberAccount implements Serializable {
	private static final long serialVersionUID = 6368734442192368866L;

	private Long id;
	private String username;
	private String passwordHash;
	private boolean enabled;
	private Company company;

	private Set<MemberRole> roles;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@UserPrincipal
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@UserPassword(hash = "SHA")
	@Column(name = "password_hash")
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@UserEnabled
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@UserRoles
	@ManyToMany
	@JoinTable(name = "member_account_role", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "member_of_role"))
	public Set<MemberRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<MemberRole> roles) {
		this.roles = roles;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne
	public Company getCompany() {
		return company;
	}
	
	@Transient
	public Company getCompanyNoProxy() {
		if (company == null) {
			return null;
		}
		
		Company c = new Company();
		c.setId(company.getId());
		c.setName(company.getName());
		return c;
	}
}
