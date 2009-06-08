package com.jsfone.renderkit;

import com.jsfone.component.UIInputPhone;
import com.jsfone.convert.PhoneConverter;
import com.jsfone.model.Phone;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.util.SelectUtils;

public abstract class InputPhoneRendererBase extends HeaderResourcesRendererBase {

	private static final String AREA_CODE_CLIENT_ID_SUFFIX = "";
	
	private static final String PREFIX_CLIENT_ID_SUFFIX = "_prefix";
	
	private static final String LINE_CLIENT_ID_SUFFIX = "_line";
	
	@Override
	protected void doDecode(FacesContext context, UIComponent component) {
		UIInputPhone inputPhone = (UIInputPhone) component;
		Map params = context.getExternalContext().getRequestParameterMap();
		String clientId = inputPhone.getClientId(context);
		inputPhone.setSubmittedValue(
			(String) params.get(clientId + AREA_CODE_CLIENT_ID_SUFFIX),
			(String) params.get(clientId + PREFIX_CLIENT_ID_SUFFIX),
			(String) params.get(clientId + LINE_CLIENT_ID_SUFFIX)
		);
	}
	
	protected Phone getModel(FacesContext context, UIComponent component) {
		UIInputPhone inputPhone = (UIInputPhone) component;
		
		Phone model = inputPhone.getSubmittedValue();
		if (model == null) {
			if (inputPhone.isModelResolved()) {
				model = inputPhone.getModel();
			}
			else {	
				Object value = inputPhone.getValue();
				if (value != null) {
					String phoneAsString;
					//Converter userConverter = inputPhone.getConverter();
					Converter userConverter = SelectUtils.getConverterForProperty(context, inputPhone, "value");
					if (userConverter != null) {
						phoneAsString = userConverter.getAsString(context, component, value);
					}
					else {
						phoneAsString = value.toString();
					}
				
					model = (Phone) getPhoneConverter().getAsObject(context, component, phoneAsString);
				}
				inputPhone.setModel(model);
			}
		}
		
		return model;
	}
	
	protected String getAreaCode(FacesContext context, UIComponent component) {
		Phone model = getModel(context, component);
		return model != null ? model.getAreaCode() : null;
	}
	
	protected String getPrefix(FacesContext context, UIComponent component) {
		Phone model = getModel(context, component);
		return model != null ? model.getPrefix() : null;
	}
	
	protected String getLine(FacesContext context, UIComponent component) {
		Phone model = getModel(context, component);
		return model != null ? model.getLine() : null;
	}

	/**
	 * Convert the internal representation of the value to a string, then let the converter
	 * registered on this component convert it to an object, or simply return a string if
	 * there is no registered converter.
	 */
	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
		throws ConverterException {
		// first convert our internal representation to a string to make this look like a simple input field
		String phoneAsString = getPhoneConverter().getAsString(context, component, submittedValue);
		UIInputPhone inputPhone = (UIInputPhone) component;
		// FIXME: should lookup converter by property's type
//		Converter userConverter = inputPhone.getConverter();
//		if (userConverter == null) {
//			return phoneAsString;
//		}
//		else {
//			return userConverter.getAsObject(context, component, phoneAsString);
//		}
		return SelectUtils.getConvertedUIInputValue(context, inputPhone, phoneAsString);
	}
	
	private Converter getPhoneConverter() {
		return new PhoneConverter();
	}
}
