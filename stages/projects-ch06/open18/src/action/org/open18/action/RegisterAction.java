package org.open18.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.open18.auth.PasswordBean;
import org.open18.auth.PasswordManager;
import org.open18.model.Golfer;
import org.open18.validation.GolferValidator;

import javax.persistence.EntityManager;

@Name("registerAction")
public class RegisterAction {

	@Logger	private Log log;
	
	@In	protected FacesMessages facesMessages;
	
	@In	protected EntityManager entityManager;
	
	@In protected PasswordManager passwordManager;
	
	@In protected Golfer newGolfer;
	
	@In	protected PasswordBean passwordBean;
	
	@In	protected GolferValidator golferValidator;

	public String register() {
		log.info("registerAction.register() action called");
		log.info("Registering golfer #{newGolfer.username}");
		
		if (!golferValidator.validate(newGolfer, passwordBean)) {
			log.info("Invalid registration request");
			facesMessages.addToControls(golferValidator.getInvalidValues());
			return null;
		}

		newGolfer.setPasswordHash(passwordManager
			.hash(passwordBean.getPassword()));
		
		entityManager.persist(newGolfer);
		facesMessages
			.add("Welcome to the club, #{newGolfer.name}!");
		return "/home.xhtml";
	}
	
	public boolean isUsernameAvailable(String username) {
        return entityManager.createQuery(
            "from Golfer where username = :username")
            .setParameter("username", username)
            .getResultList().size() == 0;
    }
	
	public boolean isEmailRegistered(String email) {
        return entityManager.createQuery(
            "from Golfer where emailAddress = :email")
            .setParameter("email", email)
            .getResultList().size() > 0;
    }
}
