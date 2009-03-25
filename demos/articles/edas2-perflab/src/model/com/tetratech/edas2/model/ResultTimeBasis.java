/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * ResultTimeBasis generated by hbm2java
 */
@Entity
@Table(name = "ED_RESULT_TIME_BASIS")
public class ResultTimeBasis extends AttributableUpdateRetireEntity
		implements
			java.io.Serializable {

	private Long uid;
	private String name;
	private String description;
	private Set<Result> results = new HashSet<Result>(0);

	public ResultTimeBasis() {
	}

	public ResultTimeBasis(Long uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	@Id
	@Column(name = "RTIMB_UID", unique = true, nullable = false, precision = 6, scale = 0)
	@NotNull
	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Column(name = "RTIMB_NAME", nullable = false, length = 12)
	@NotNull
	@Length(max = 12)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RTIMB_DESC", length = 200)
	@Length(max = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "timeBasis")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

}
