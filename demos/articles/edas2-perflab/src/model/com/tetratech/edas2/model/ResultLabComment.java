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
 * ResultLabComment generated by hbm2java
 */
@Entity
@Table(name = "ED_RESULT_LAB_COMMENT")
public class ResultLabComment extends AttributableUpdateRetireEntity
		implements
			java.io.Serializable {

	private Long uid;
	private String code;
	private String description;
	private Set<Result> results = new HashSet<Result>(0);

	public ResultLabComment() {
	}

	public ResultLabComment(Long uid, String code) {
		this.uid = uid;
		this.code = code;
	}

	@Id
	@Column(name = "RLCOM_UID", unique = true, nullable = false, precision = 6, scale = 0)
	@NotNull
	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Column(name = "RLCOM_CD", nullable = false, length = 3)
	@NotNull
	@Length(max = 3)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "RLCOM_DESC", length = 100)
	@Length(max = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "labComment")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

}
