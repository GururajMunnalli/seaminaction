package org.open18.action;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.open18.auth.PasswordBean;
import org.open18.auth.PasswordManager;
import org.open18.model.Golfer;

@Name("registerAction")
public class RegisterAction {

    @Logger
    private Log log;
    
    @In
    FacesMessages facesMessages;

    public String register() {
        log.info("registerAction.register() action called");
        log.info("Registering golfer #{newGolfer.username}");
        Context eventContext = Contexts.getEventContext();
        PasswordBean passwordBean =
            (PasswordBean) eventContext.get("passwordBean");
        if (!passwordBean.verify()) {
			facesMessages
				.addToControl("confirm",
					"Confirmation password does not match");
            return null;
        }

        Golfer newGolfer = (Golfer) Contexts
            .lookupInStatefulContexts("newGolfer");
        PasswordManager passwordManager = (PasswordManager) Component
            .getInstance(PasswordManager.class);
        newGolfer.setPasswordHash(passwordManager
            .hash(passwordBean.getPassword()));
        EntityManager entityManager =
            (EntityManager) Component.getInstance("entityManager");
        entityManager.persist(newGolfer);
        facesMessages
            .add("Welcome to the community, #{newGolfer.name}!");
        return "/home.xhtml";
    }
}
