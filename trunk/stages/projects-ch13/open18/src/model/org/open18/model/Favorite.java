package org.open18.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

@Entity
@Table(name = "FAVORITE")
@Name("newFavorite")
public class Favorite implements Serializable {
    private Long id;
    private Long entityId;
    private String entityName;
    private Golfer golfer;

	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

}
