package com.jsfone.renderkit;

import com.jsfone.component.UIOutputBooleanCheckbox;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.util.SelectUtils;

public abstract class OutputBooleanCheckboxRendererBase extends RendererBase {
	// FIXME: Not playing nice by considering converters
	// NOTE: if this is all we were doing, we could just make this isSelected() on UIOutputBooleanCheckbox
	protected boolean getValueAsBoolean(FacesContext context, UIComponent component) {
		UIOutputBooleanCheckbox outputBooleanCheckbox = (UIOutputBooleanCheckbox) component;
		Object value = outputBooleanCheckbox.getValue();
		if (value == null) {
			return false;
		}
		else if (value instanceof Boolean) {
			return (Boolean) value;
		}
		else {
			String stringValue;
			//Converter userConverter = outputBooleanCheckbox.getConverter();
			Converter userConverter = SelectUtils.getConverterForProperty(context, outputBooleanCheckbox, "value");
			if (userConverter != null) {
				stringValue = userConverter.getAsString(context, component, value);
			}
			else {
				stringValue = value.toString();
			}
			return Boolean.valueOf(stringValue);
		}
	}
	
	protected boolean getVisible(FacesContext context, UIComponent component) {
		String visibleStates = (String) component.getAttributes().get("visibleStates");
		if (visibleStates == null) {
			visibleStates = "both";
		}
		
		if (visibleStates.equals("both")) {
			return true;
		}
		else if (visibleStates.equals("true")) {
			return getValueAsBoolean(context, component);
		}
		else if (visibleStates.equals("false")) {
			return !getValueAsBoolean(context, component);
		}
		else {
			return false;
		}
	}
}
