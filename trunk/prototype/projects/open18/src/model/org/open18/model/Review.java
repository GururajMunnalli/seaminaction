package org.open18.model;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

import javax.persistence.*;
import static javax.persistence.TemporalType.TIMESTAMP;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REVIEW")
public class Review implements Serializable {
	private Long id;
	private Long entityId;
	private String entityName;
	private Date dateCreated;
	private Golfer reviewer;
	private String text;
	private boolean useWikiText;
	private Integer rating;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Temporal(TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVIEWER_ID", nullable = false)
	@NotNull
	public Golfer getReviewer() {
		return reviewer;
	}

	public void setReviewer(Golfer reviewer) {
		this.reviewer = reviewer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "USE_WIKI_TEXT", nullable = false)
	public boolean isUseWikiText() {
		return useWikiText;
	}

	public void setUseWikiText(boolean useWikiText) {
		this.useWikiText = useWikiText;
	}

	@Range(min = 0, max = 5)
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
