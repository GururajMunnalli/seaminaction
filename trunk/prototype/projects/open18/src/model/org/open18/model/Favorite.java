package org.open18.model;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITE")
@Name("newFavorite")
public class Favorite {
	private Long id;

	private Golfer golfer;

	private Long entityId;

	private String entityName;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GOLFER_ID", nullable = false)
	@NotNull
	public Golfer getGolfer() {
		return golfer;
	}

	public void setGolfer(Golfer golfer) {
		this.golfer = golfer;
	}

	@Column(name = "ENTITY_ID", nullable = false)
	@NotNull
	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	@Column(name = "ENTITY_NAME", nullable = false)
	@NotNull
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
