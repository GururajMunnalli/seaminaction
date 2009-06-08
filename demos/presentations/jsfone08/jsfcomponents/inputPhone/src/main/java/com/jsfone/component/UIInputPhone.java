package com.jsfone.component;

import com.jsfone.model.Phone;
import javax.faces.component.UIInput;

/**
 * JSF component class
 */
public abstract class UIInputPhone extends UIInput {
	
	public static final String COMPONENT_TYPE = "com.jsfone.InputPhone";
	
	public static final String COMPONENT_FAMILY = "com.jsfone.InputPhone";
	
	private Phone model;
	
	private boolean modelResolved;
	
	public void setSubmittedValue(String areaCode, String prefix, String line) {
		setSubmittedValue(new Phone(areaCode, prefix, line));
	}
	
	@Override
	public Phone getSubmittedValue() {
		return (Phone) super.getSubmittedValue();
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		modelResolved = false;
		model = null;
	}
	
	public boolean isModelResolved() {
		return modelResolved;
	}
	
	public void setModel(Phone model) {
		this.modelResolved = true;
		this.model = model;
	}
	
	public Phone getModel() {
		return model;
	}
	
}
