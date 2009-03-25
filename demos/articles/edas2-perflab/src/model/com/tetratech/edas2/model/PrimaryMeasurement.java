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

@Embeddable
public class PrimaryMeasurement extends Measurement {

	private String bias;
	private String confidenceInterval;
	private String lowerConfidenceLimit;
	private String upperConfidenceLimit;
	private String precision;
	private ResultMeasurementQualifier qualifier;

	public PrimaryMeasurement() {
	}

	public PrimaryMeasurement(String value, MeasurementUnit unit) {
		super(value, unit);
	}

	public String getBias() {
		return bias;
	}

	public void setBias(String bias) {
		this.bias = bias;
	}

	public String getConfidenceInterval() {
		return confidenceInterval;
	}

	public void setConfidenceInterval(String confidenceInterval) {
		this.confidenceInterval = confidenceInterval;
	}

	public String getLowerConfidenceLimit() {
		return lowerConfidenceLimit;
	}

	public void setLowerConfidenceLimit(String lowerConfidenceLimit) {
		this.lowerConfidenceLimit = lowerConfidenceLimit;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public ResultMeasurementQualifier getQualifier() {
		return qualifier;
	}

	public void setQualifier(ResultMeasurementQualifier qualifier) {
		this.qualifier = qualifier;
	}

	public String getUpperConfidenceLimit() {
		return upperConfidenceLimit;
	}

	public void setUpperConfidenceLimit(String upperConfidenceLimit) {
		this.upperConfidenceLimit = upperConfidenceLimit;
	}
}
