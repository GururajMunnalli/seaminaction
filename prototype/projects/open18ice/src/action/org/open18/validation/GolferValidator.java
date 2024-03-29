package org.open18.validation;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.open18.action.RegisterAction;
import org.open18.auth.PasswordBean;
import org.open18.model.Golfer;

import java.util.ArrayList;
import java.util.List;

@Name("golferValidator")
@AutoCreate
public class GolferValidator {
	
	@In protected RegisterAction registerAction;
	
	List<InvalidValue> invalidValues = new ArrayList<InvalidValue>();

	public boolean validate(Golfer newGolfer, PasswordBean passwordBean) {
		
		if (!passwordBean.verify()) {
			addInvalidValue("confirm", PasswordBean.class,
				"Confirmation password does not match");
		}
		
		if (!registerAction.isUsernameAvailable(newGolfer.getUsername())) {
			addInvalidValue("username", Golfer.class,
				"Username is already taken");
		}
		
		if (registerAction.isEmailRegistered(newGolfer.getEmailAddress())) {
			addInvalidValue("emailAddress", Golfer.class,
				"E-mail address is already registered");
		}
		
		return !hasInvalidValues();
	}
	
	public InvalidValue[] getInvalidValues() {
		return invalidValues.toArray(new InvalidValue[invalidValues.size()]);
	}
	
	public boolean hasInvalidValues() {
		return invalidValues.size() > 0;
	}
	
	public void reset() {
		invalidValues = new ArrayList<InvalidValue>();
	}
	
	protected void addInvalidValue(String property, Class beanClass, String message) {
		invalidValues.add(new InvalidValue(message, beanClass, property, null, null));
	}
	
}
