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

    @Logger
    private Log log;

    @In
    private EntityManager entityManager;

    @In
    private Identity identity;

    @In
    private Actor actor;

    @In
    private PasswordManager passwordManager;

    @SuppressWarnings("unused")
    @Out(required = false)
    private Golfer currentGolfer;

    @Transactional
    public boolean authenticate() {
        log.info("authenticating #0", identity.getUsername());
        try {
            Member member = (Member) entityManager.createQuery(
                "select distinct m from Member m left join fetch m.roles where m.username = :username")
                //"select m from Member m where m.username = :username")
                .setParameter("username", identity.getUsername())
                .getSingleResult();

            if (!validatePassword(member.getPasswordHash(), identity
                .getPassword())) {
                return false;
            }

            actor.setId(identity.getUsername());

            identity.addRole("member");
            actor.getGroupActorIds().add("member");

            if (member.getRoles() != null) {
                for (Role r : member.getRoles()) {
                    identity.addRole(r.getName());
                    actor.getGroupActorIds().add(r.getName());
                }
            }

            if (member instanceof Golfer) {
                identity.addRole("golfer");
                actor.getGroupActorIds().add("golfer");
                currentGolfer = (Golfer) member;
            }

            return true;
        } catch (NoResultException ex) {
            return false;
        }

    }

    private boolean validatePassword(String passwordHash,
        String plainTextPassword) {
        return passwordManager.hash(plainTextPassword).equals(
            passwordHash);
    }
}
