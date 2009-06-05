package org.preflight.model;

import static javax.persistence.CascadeType.ALL;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "aircraft", uniqueConstraints =
	@UniqueConstraint(columnNames = {"manufacturer", "model"})
)
public class Aircraft implements Serializable {

	private static final long serialVersionUID = -2647203051940675358L;

	private long id;
	private String manufacturer;
	private String model;
	private String comment;
	private Set<Seat> seats;

	public Aircraft() {
		super();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@OneToMany(cascade = ALL, mappedBy = "aircraft")
	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

}
