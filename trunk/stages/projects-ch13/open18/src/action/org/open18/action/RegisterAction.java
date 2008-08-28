package org.open18.action;

import java.io.IOException;
import org.jboss.seam.annotations.In;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.jboss.seam.ui.graphicImage.Image;
import org.open18.auth.PasswordBean;
import org.open18.auth.PasswordManager;
import org.open18.model.Golfer;
import org.open18.validator.GolferValidator;

@Name("registerAction")
public class RegisterAction {

	@Logger	private Log log;

	@In protected EntityManager entityManager;

	@In protected FacesMessages facesMessages;

	@In protected PasswordManager passwordManager;

	@In protected Golfer newGolfer;

	@In protected PasswordBean passwordBean;
	
	@In protected GolferValidator golferValidator;
	
	protected String[] proStatusTypes = {};

	protected List<String> specialtyTypes = new ArrayList<String>();

	public String[] getProStatusTypes() {
		return this.proStatusTypes;
	}

	public void setProStatusTypes(String[] types) {
		this.proStatusTypes = types;
	}

	public List<String> getSpecialtyTypes() {
		return specialtyTypes;
	}

	public void setSpecialtyTypes(List<String> specialtyTypes) {
		this.specialtyTypes = specialtyTypes;
	}
	
	//@RaiseEvent("golferRegistered") // not nearly as flexible as using the Events API
	public String register() {
		log.info("Registering golfer #{newGolfer.username}");

		if (!golferValidator.validate(newGolfer, passwordBean)) {
			log.info("Invalid registration request");
			facesMessages.addToControls(golferValidator.getInvalidValues());
			return null;
		}

		if (!scaleImage()) {
			return null;
		}
		
		newGolfer.setPasswordHash(passwordManager.hash(passwordBean.getPassword()));
		entityManager.persist(newGolfer);
		if (Events.exists()) {
			Events.instance().raiseTransactionSuccessEvent("golferRegistered", newGolfer);
		}
		facesMessages.addFromResourceBundle("registration.welcome", newGolfer.getName());
		Identity identity = Identity.instance();
		identity.setUsername(newGolfer.getUsername());
		identity.setPassword(passwordBean.getPassword());
		// could also do Events.instance().raiseTransactionSuccessEvent("attemptLogin"); and write an observer
		// quietLogin() doesn't add messages or throw exceptions
		identity.quietLogin();
		return "success";
	}
	
	public boolean isUsernameAvailable(String username) {
        return entityManager.createQuery(
            "select m from Member m where m.username = :username")
            .setParameter("username", username)
            .getResultList().size() == 0;
    }
	
	public boolean isEmailRegistered(String email) {
        return entityManager.createQuery(
            "select m from Member m where m.emailAddress = :email")
            .setParameter("email", email)
            .getResultList().size() > 0;
    }
	
	public void verifyUsernameAvailable(ValueChangeEvent e) {
		String username = (String) e.getNewValue();
		if (!isUsernameAvailable(username)) {
			facesMessages.addToControl(e.getComponent().getId(),
				"Sorry, username already taken");
		}
	}
	
	public void checkEmailRegistered(ValueChangeEvent e) {
		String email = (String) e.getNewValue();
		if (isEmailRegistered(email)) {
			facesMessages.addToControl(e.getComponent().getId(),
				"Sorry, email address is already registered");
		}
	}
	
	protected boolean scaleImage() {
		if (newGolfer.getImage() != null) {
			try {
				Image image = new Image();
				image.setInput(newGolfer.getImage());
				if (image.getBufferedImage() == null) {
					throw new IOException("The profile image data is empty.");
				}
				if (!image.getContentType().getMimeType().matches("image/(png|gif|jpeg)")) {
					facesMessages.addToControl("image",
						"Invalid image type: " + image.getContentType());
				}
				if (image.getHeight() > 64 || image.getWidth() > 64) {
					if (image.getHeight() > image.getWidth()) {
						image.scaleToHeight(64);
					} else {
						image.scaleToWidth(64);
					}
					newGolfer.setImage(image.getImage());
				}
			} catch (IOException e) {
				log.error("An error occurred reading the profile image", e);
				facesMessages.addToControl("image", FacesMessage.SEVERITY_ERROR,
					"An error occurred reading the profile image.");
				newGolfer.setImage(null);
				newGolfer.setImageContentType(null);
				return false;
			}
		}
		return true;
	}

}
