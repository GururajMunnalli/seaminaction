package org.open18.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.ui.graphicImage.Image;
import org.open18.auth.PasswordBean;
import org.open18.auth.PasswordManager;
import org.open18.model.Golfer;
import org.open18.validation.GolferValidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import java.io.IOException;

@Name("registerAction")
public class RegisterAction {

	@Logger
	private Log log;

	@In
	protected FacesMessages facesMessages;

	@In
	protected EntityManager entityManager;

	@In
	protected PasswordManager passwordManager;

	@In
	protected Golfer newGolfer;

	@In
	protected PasswordBean passwordBean;

	@In
	protected GolferValidator golferValidator;

	@In
	protected RegistrationMailer registrationMailer;

	private String[] proStatusTypes = {};

	private String[] specialtyTypes = {};

	private int maxImageDimension = 64;

	public String[] getProStatusTypes() {
		return this.proStatusTypes;
	}

	public void setProStatusTypes(String[] types) {
		this.proStatusTypes = types;
	}

	public String[] getSpecialtyTypes() {
		return specialtyTypes;
	}

	public void setSpecialtyTypes(String[] specialtyTypes) {
		this.specialtyTypes = specialtyTypes;
	}

	@RaiseEvent("golferRegistered")
	public String register() {
		log.info("registerAction.register() action called");
		log.info("Registering golfer #{newGolfer.username}");

		if (!golferValidator.validate(newGolfer, passwordBean)) {
			log.info("Invalid registration request");
			facesMessages.addToControls(golferValidator.getInvalidValues());
			return null;
		}

		if (newGolfer.getImage() != null) {
			try {
				Image image = new Image();
				image.setInput(newGolfer.getImage());
				if (image.getBufferedImage() == null) {
					throw new IOException("An image could not be read from the file data");
				}

				if (!image.getContentType().getMimeType().matches("image/(png|gif|jpeg)")) {
					facesMessages.addToControl(
						"image",
						"Invalid image type: " + image.getContentType()
					);
				}
				if (image.getHeight() > maxImageDimension || image.getWidth() > maxImageDimension) {
					if (image.getHeight() > image.getWidth()) {
						image.scaleToHeight(maxImageDimension);
					}
					else {
						image.scaleToWidth(maxImageDimension);
					}

					newGolfer.setImage(image.getImage());
				}
			} catch (IOException ioe) {
				log.error("An error occurred while processing the profile image for #{newGolfer.username}", ioe);
				facesMessages.addToControl(
					"image",
					FacesMessage.SEVERITY_ERROR,
					"An error occurred while processing the profile image.");
				newGolfer.setImage(null);
				newGolfer.setImageContentType(null);
				return null;
			}
		}

		newGolfer.setPasswordHash(passwordManager
			.hash(passwordBean.getPassword()));

		entityManager.persist(newGolfer);
		facesMessages
			.add("Welcome to the community, #{newGolfer.name}!");
		registrationMailer.sendWelcomeEmail();
		return "/home.xhtml";
	}

	// this version requires the model to be updated
	/*
	public void verifyUsernameAvailable() {
		if (newGolfer.getUsername() != null && !isUsernameAvailable(newGolfer.getUsername())) {
			facesMessages.addToControl("username", "Username is already taken");
		}
	}*/

	// this version does not require the model to be updated (bypassUpdates="true")
	public void verifyUsernameAvailable(ActionEvent e) {
		String username = (String) ((UIInput) e.getComponent().getParent()).getValue();
		if (username != null && !isUsernameAvailable(username)) {
			facesMessages.addToControl("username", "Username is already taken");
		}
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
