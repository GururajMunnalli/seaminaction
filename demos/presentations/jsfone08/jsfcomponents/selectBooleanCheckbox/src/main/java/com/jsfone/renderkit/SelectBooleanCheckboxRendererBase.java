package com.jsfone.renderkit;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.util.SelectUtils;

/**
 * A renderer for an image-based input checkbox. This class extends from
 * {@link HeaderResourcesRendererBase} so that the RichFaces framework can
 * send the JavaScript that manages the hidden form fields and the visible image.
 * The value of the hidden field will be "true" when the input is checked and
 * "false" when the input is not checked. This class converts the string request
 * value into a boolean.
 */
public abstract class SelectBooleanCheckboxRendererBase extends HeaderResourcesRendererBase {

	@Override
	protected void doDecode(FacesContext context, UIComponent component) {
		UIInput selectBooleanCheckbox = (UIInput) component;
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String clientId = selectBooleanCheckbox.getClientId(context);
		selectBooleanCheckbox.setSubmittedValue("true".equals(params.get(clientId)) ? "true" : "false");
	}

	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
		throws ConverterException {
		//return Boolean.valueOf((String) submittedValue);
		return SelectUtils.getConvertedUIInputValue(context, (UIInput) component, (String) submittedValue);
	}
	
}
