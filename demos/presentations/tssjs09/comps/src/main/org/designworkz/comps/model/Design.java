package org.designworkz.comps.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.security.permission.Permission;
import org.jboss.seam.annotations.security.permission.Permissions;

/**
 * Entity implementation class for Entity: Design
 */
@Entity
@Table(name = "design")
@Permissions( { @Permission(action = "view"), @Permission(action = "comment") })
public class Design implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String description;
	private String imageContentType;
	private byte[] imageData;
	private String owner;
	private Date dateCreated;
	private Company company;

	public Design() {
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

	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Lob
	@Column(name = "image_data")
	@Basic(fetch = LAZY)
	public byte[] getImageData() {
		return this.imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	@Column(name = "image_content_type")
	public String getImageContentType() {
		return imageContentType;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@NotNull
	public String getOwner() {
		return owner;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Column(name = "created")
	@Temporal(TIMESTAMP)
	@NotNull
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(fetch = LAZY)
	@NotNull
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
