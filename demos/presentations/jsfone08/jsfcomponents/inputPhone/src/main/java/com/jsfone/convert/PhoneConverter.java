package com.jsfone.convert;

import com.jsfone.model.Phone;
import java.text.ParseException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class PhoneConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		} else if (value.length() != 10 || !isNumeric(value)) {
			throw new ConverterException(
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be 10 digits", "Value must be 10 digits"));
		} else {
			return new Phone(
				value.substring(0, 3),
				value.substring(3, 6),
				value.substring(6, 10));
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Phone phone = (Phone) value;

		if (isBlank(phone.getAreaCode()) && isBlank(phone.getPrefix()) && isBlank(phone.getLine())) {
			return null;
		}

		try {
			if (!isPartValid(phone.getAreaCode(), 3)) {
				throw new ParseException("Invalid area code", 0);
			}

			if (!isPartValid(phone.getPrefix(), 3)) {
				throw new ParseException("Invalid prefix", 3);
			}

			if (!isPartValid(phone.getLine(), 4)) {
				throw new ParseException("Invalid line number", 6);
			}
		} catch (ParseException e) {
			throw new ConverterException(
				new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		return phone.toString();
	}

	private boolean isPartValid(String part, int expectedLength) {
		return !(isBlank(part) || part.length() != expectedLength || !isNumeric(part));
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().length() == 0;
	}

	private boolean isNumeric(String value) {
		for (char c : value.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
}
