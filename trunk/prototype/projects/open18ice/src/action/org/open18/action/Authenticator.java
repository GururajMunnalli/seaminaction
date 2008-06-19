package org.open18.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.open18.auth.PasswordManager;
import org.open18.model.Golfer;
import org.open18.model.Member;
import org.open18.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Name("authenticator")
public class Authenticator {

    @Logger private Log log;

    @In private EntityManager entityManager;

    @In private Identity identity;

    @In private PasswordManager passwordManager;

    @SuppressWarnings("unused")
    @Out(required = false)
    private Golfer currentGolfer;

    @Transactional
    public boolean authenticate() {
        log.info("authenticating #0", identity.getUsername());
        try {
            Member m = (Member) entityManager.createQuery(
                "select distinct m from Member m left join fetch m.roles where m.username = ?1")
                .setParameter(1, identity.getUsername()).getSingleResult();

            if (!validatePassword(identity.getPassword(), m)) {
                return false;
            }

			// don't worry about duplicates; they get weeded out
            identity.addRole("member");

            if (m.getRoles() != null) {
                for (Role r : m.getRoles()) {
                    identity.addRole(r.getName());
                }
            }

            if (m instanceof Golfer) {
                identity.addRole("golfer");
                currentGolfer = (Golfer) m;
            }

            return true;
        } catch (NoResultException ex) {
            return false;
        }

    }

    private boolean validatePassword(String password, Member m) {
        return passwordManager.hash(password).equals(m.getPasswordHash());
    }
}
