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

import com.tetratech.edas2.model.FrequencyClassDescriptor;

@Name("benthicMeasResultSearchCriteria")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class BenthicMeasurementResultCriteria extends ResultCriteria{

	private String taxonomyName;
	private FrequencyClassDescriptor freqClassDesc;
	
	public BenthicMeasurementResultCriteria(){
		
	}

	public String getTaxonomyName() {
		return taxonomyName;
	}

	public void setTaxonomyName(String taxonomyName) {
		this.taxonomyName = taxonomyName;
	}

	
	public FrequencyClassDescriptor getFreqClassDesc() {
		return freqClassDesc;
	}

	public void setFreqClassDesc(FrequencyClassDescriptor freqClassDesc) {
		this.freqClassDesc = freqClassDesc;
	}

	@Override
	public void reset() {
		super.reset();
		taxonomyName = "";
		freqClassDesc = null;
	}
}
