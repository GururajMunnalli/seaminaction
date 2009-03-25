/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Embeddable
public class Measurement {
	private String value;
	private MeasurementUnit unit;

	public Measurement() {
	}

	public Measurement(String value, MeasurementUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	//@NotNull
	public MeasurementUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasurementUnit unit) {
		this.unit = unit;
	}

	//@NotNull
	@Length(max = 12)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Transient
	public String getValueWithUnits() {
		if (this.value == null) {
			return null;
		}
		StringBuilder valueWithUnits = new StringBuilder(this.value);
		if (this.unit != null) {
			valueWithUnits.append(" ").append(this.unit.getCode());
		}
		return valueWithUnits.toString();
	}
}
