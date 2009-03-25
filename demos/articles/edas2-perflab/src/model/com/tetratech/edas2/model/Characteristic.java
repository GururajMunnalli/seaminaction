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
import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Characteristic generated by hbm2java
 */
@Entity
@Table(name = "ED_CHARACTERISTIC")
public class Characteristic extends AttributableUpdateRetireEntity
		implements
			java.io.Serializable {

	private Long uid;
	private String name;
	private Long srsId;
	private Long storetId;
	private Boolean sampleFractionRequired;
	private Boolean pickList;
	private String description;
	private Set<CharacteristicPickListValue> characteristicPickListValues = new HashSet<CharacteristicPickListValue>(
			0);
	private Set<Result> results = new HashSet<Result>(0);

	public Characteristic() {
	}

	public Characteristic(Long uid, String name,
			Boolean sampleFractionRequired, Boolean pickList) {
		this.uid = uid;
		this.name = name;
		this.sampleFractionRequired = sampleFractionRequired;
		this.pickList = pickList;
	}

	@Id
	@Column(name = "CHR_UID", unique = true, nullable = false, precision = 6, scale = 0)
	@NotNull
	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Column(name = "CHR_NAME", nullable = false, length = 120)
	@NotNull
	@Length(max = 120)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CHR_SRS_ID", precision = 12, scale = 0)
	public Long getSrsId() {
		return this.srsId;
	}

	public void setSrsId(Long srsId) {
		this.srsId = srsId;
	}

	@Column(name = "CHR_STORET_ID", precision = 12, scale = 0)
	public Long getStoretId() {
		return this.storetId;
	}

	public void setStoretId(Long storetId) {
		this.storetId = storetId;
	}

	@Column(name = "CHR_SAMPLE_FRACTION_REQ_YN", nullable = false, length = 1)
	@Type(type = "yes_no")
	@NotNull
	public Boolean getSampleFractionRequired() {
		return this.sampleFractionRequired;
	}

	public void setSampleFractionRequired(Boolean sampleFractionRequired) {
		this.sampleFractionRequired = sampleFractionRequired;
	}

	@Column(name = "CHR_PICK_LIST_YN", nullable = false, length = 1)
	@Type(type = "yes_no")
	@NotNull
	public Boolean getPickList() {
		return this.pickList;
	}

	public void setPickList(Boolean pickList) {
		this.pickList = pickList;
	}

	@Column(name = "CHR_DESC", length = 300)
	@Length(max = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "characteristic")
	public Set<CharacteristicPickListValue> getCharacteristicPickListValues() {
		return this.characteristicPickListValues;
	}

	public void setCharacteristicPickListValues(
			Set<CharacteristicPickListValue> characteristicPickListValues) {
		this.characteristicPickListValues = characteristicPickListValues;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "characteristic")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

}