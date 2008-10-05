package org.open18.action;

import java.io.Serializable;
//import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

//import org.hibernate.validator.InvalidValue;
//import org.jboss.seam.Component;
import org.jboss.seam.ui.validator.ModelValidator;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.open18.auth.PasswordBean;
import org.open18.auth.PasswordManager;
import org.open18.model.Gender;
import org.open18.model.Golfer;
//import org.open18.validation.GolferValidator;
import org.jboss.seam.ScopeType;

import com.icesoft.faces.application.D2DViewHandler;
import com.icesoft.faces.component.datapaginator.DataPaginator;
import com.icesoft.faces.component.ext.HtmlInputSecret;
@Name("registerAction")
@Scope(ScopeType.PAGE)
public class RegisterAction implements Serializable{

	@Logger	private Log log;

	@In private EntityManager entityManager;

	@In private FacesMessages facesMessages;

	@In private PasswordManager passwordManager;

	@In(create=true)
	private Golfer newGolfer;

	@In private PasswordBean passwordBean;

// 	@In	private GolferValidator golferValidator;
//	private boolean errors=true;
	
	private boolean emailValid=false;
	private boolean passwordValid=false;
	private boolean usernameValid=false;

	private String[] proStatusTypes = {};

	private List<String> specialtyTypes = new ArrayList<String>();

	@Create
	public void init(){
		log.info("created");
		//next line just for development so don't get errors resulting from stale objects
		//with hot deploy
		this.entityManager.flush();
		this.newGolfer.setGender(Gender.MALE);	
	}
	
	@BypassInterceptors
	public String[] getProStatusTypes() {
		return this.proStatusTypes;
	}

	public void setProStatusTypes(String[] types) {
		this.proStatusTypes = types;
	}

	@BypassInterceptors
	public List<String> getSpecialtyTypes() {
		return specialtyTypes;
	}

	public void setSpecialtyTypes(List<String> specialtyTypes) {
		this.specialtyTypes = specialtyTypes;
	}
	
	@RaiseEvent("golferRegistered")
	public String register() {
		log.info("Registering golfer #{newGolfer.username}");
//don't need to use golferValidator since we did it all with ajax!!
//		if (!golferValidator.validate(newGolfer, passwordBean)) {
//			log.info("Invalid registration request");
//			facesMessages.addToControls(golferValidator.getInvalidValues());
//			return null;
//		}
		if (this.isRegistrationValid()){
			newGolfer.setPasswordHash(passwordManager.hash(passwordBean.getPassword()));
			try{
//				entityManager.merge(newGolfer);
				entityManager.persist(newGolfer);			
				facesMessages.addFromResourceBundle("registration.welcome", newGolfer.getName());
				return "success";
			}catch (Exception e){
				facesMessages.add("problems persisting new golfer");
				e.printStackTrace();
				return "unsuccessful";
			}
		}else{
			if (!this.emailValid)facesMessages.add("Problems with Registration:- email address already in database");
			if (!this.passwordValid)facesMessages.add("Problems with Registration:- password confirmation failed");
			if (!this.usernameValid)facesMessages.add("Problems with Registration:- username taken");
			return "unsuccessful";
		}
	}
	
	public boolean isUsernameAvailable(String username) {
		return entityManager.createQuery(
			"select m from Member m where m.username = ?1")
			.setParameter(1, username).getResultList().size() == 0;
	}

	public boolean isEmailRegistered(String email) {
		return entityManager.createQuery(
			"select m from Member m where m.emailAddress = ?1")
			.setParameter(1, email).getResultList().size() > 0;
	}
	
	public void emailNotTaken(FacesContext context, UIComponent validate, Object value){
		//since we have our own validator, need to check for hibernate annotation validations
		checkHibernateAnnotations(context, validate, value);		
		String email = (String)value;
		if (isEmailRegistered(email)){
 			FacesMessage msg = new FacesMessage("ice email already taken");
 			context.addMessage(validate.getClientId(context), msg);
 			emailValid=false;
		}else emailValid=true;
	}

	private void checkHibernateAnnotations(FacesContext context,
			UIComponent validate, Object value) {
		ModelValidator mv = new ModelValidator();
		mv.validate(context, validate, value);
	}
	public void validateUserName(FacesContext context, UIComponent validate, Object value){
		//since we have our own validator, need to check for hibernate annotation validations
		checkHibernateAnnotations(context, validate, value);				
		String username=(String)value;
		if (!isUsernameAvailable(username)) {
			FacesMessage msg = new FacesMessage("ice username already taken");
			context.addMessage(validate.getClientId(context), msg);
			usernameValid=false;
		}else usernameValid=true;
	}
	public void confirmPassword(FacesContext context, UIComponent confirmInputText, Object value){
		//since we have our own validator, need to check for hibernate annotation validations
		checkHibernateAnnotations(context, confirmInputText, value);
		UIComponent passwordInputText = findComponent(":registerActionForm:passwordField:password");
		if (passwordInputText!=null){
			EditableValueHolder password = (EditableValueHolder)passwordInputText;
			EditableValueHolder confirmed= (EditableValueHolder)confirmInputText;			
//			log.info("submittedValue for password ="+password.getSubmittedValue()+" for confirmed="+confirmed.getSubmittedValue());	
//			log.info("value for password ="+password.getValue()+" for confirmed="+confirmed.getValue());		
			String passwordString = (String)(password.getSubmittedValue()!=null ? password.getSubmittedValue(): password.getValue());
			String confirmString = (String)(confirmed.getSubmittedValue()!=null ? confirmed.getSubmittedValue(): confirmed.getValue());
			if (passwordString.equals(confirmString)){
				passwordValid=true;
			}
			else{
				FacesMessage msg = new FacesMessage("ice password not same as original");
				context.addMessage(confirmInputText.getClientId(context), msg);
				passwordValid=false;
			}
		} else {
			passwordValid=false;
		}
/*		
		String confirm=(String)value; 
 		log.info("confirm="+confirm);
 		if (passwordBean!=null)log.info("passwordBean.password="+passwordBean.getPassword());
		if (!confirm.equals(passwordBean.getPassword())){
				FacesMessage msg = new FacesMessage("ice password not same as original");
				context.addMessage(confirmInputText.getClientId(context), msg);
				passwordValid=false;
		}else passwordValid=true; */
	}
	public void destroy(){
		log.info("destroyed");
	}
	
	public boolean isRegistrationValid(){return (emailValid && usernameValid && passwordValid);}

	private UIComponent findComponent(String compLookup){
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		D2DViewHandler dvh = (D2DViewHandler) FacesContext.getCurrentInstance().getApplication().getViewHandler();
		UIComponent uicomp = (UIComponent)(dvh.findComponent(compLookup,viewRoot));
		return uicomp;
	}
	

}
