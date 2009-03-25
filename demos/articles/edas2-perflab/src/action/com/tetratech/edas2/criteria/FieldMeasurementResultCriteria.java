/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.criteria;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.tetratech.edas2.model.ResultDetectionCondition;
import com.tetratech.edas2.model.ResultMeasurementQualifier;
import com.tetratech.edas2.model.ResultValueType;

@Name("fieldMeasResultSearchCriteria")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class FieldMeasurementResultCriteria extends ResultCriteria {

	private ResultValueType valueType = null;
	private ResultDetectionCondition detectionCondition = null;
	private ResultMeasurementQualifier measurementQualifier = null;
	private String characteristicName = null;

	public FieldMeasurementResultCriteria() {
	}

	public ResultValueType getValueType() {
		return valueType;
	}

	public void setValueType(ResultValueType valueType) {
		this.valueType = valueType;
	}

	public ResultDetectionCondition getDetectionCondition() {
		return detectionCondition;
	}

	public void setDetectionCondition(ResultDetectionCondition detectionCondition) {
		this.detectionCondition = detectionCondition;
	}

	public ResultMeasurementQualifier getMeasurementQualifier() {
		return measurementQualifier;
	}

	public void setMeasurementQualifier(ResultMeasurementQualifier measurementQualifier) {
		this.measurementQualifier = measurementQualifier;
	}

	public String getCharacteristicName() {
		return characteristicName;
	}

	public void setCharacteristicName(String characteristicName) {
		this.characteristicName = characteristicName;
	}

	@Override
	public void reset() {
		super.reset();
		valueType = null;
		detectionCondition = null;
		measurementQualifier = null;
		characteristicName = "";
	}
}
