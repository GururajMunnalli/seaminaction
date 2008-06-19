package org.open18.action;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.open18.auth.PasswordBean;
import org.open18.auth.PasswordManager;
import org.open18.model.Golfer;
import org.open18.validation.GolferValidator;

@Name("registerAction")
public class RegisterAction {

	@Logger	private Log log;

	@In private EntityManager entityManager;

	@In private FacesMessages facesMessages;

	@In private PasswordManager passwordManager;

	@In private Golfer newGolfer;

	@In private PasswordBean passwordBean;

	@In	private GolferValidator golferValidator;

	private String[] proStatusTypes = {};

	private List<String> specialtyTypes = new ArrayList<String>();

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

		if (!golferValidator.validate(newGolfer, passwordBean)) {
			log.info("Invalid registration request");
			facesMessages.addToControls(golferValidator.getInvalidValues());
			return null;
		}

		newGolfer.setPasswordHash(passwordManager.hash(passwordBean.getPassword()));
		entityManager.persist(newGolfer);
		facesMessages.addFromResourceBundle("registration.welcome", newGolfer.getName());
		return "success";
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
}
