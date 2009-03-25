package org.example.tssjs.action

import org.jboss.seam.annotations.*
import org.jboss.seam.log.Log
import org.jboss.seam.security.*
import javax.persistence.*

@Name("authenticator")
class Authenticator
{
    @Logger private Log log

    @In protected Identity identity
    @In protected Credentials credentials
    @In protected EntityManager entityManager

    boolean authenticate()
    {
        log.info("Authenticating {0} with password '{1}'...", credentials.username, credentials.password)

		try {
        	def user = entityManager.createQuery("select u from UserAccount u where u.username = #{identity.username}").singleResult
			// can optionally compare against a hased password in the database
            if (user.password.equals(identity.password)) {
                identity.addRole "member"
        		log.info("Login successful!")
                return true
            }
        	log.info("Invalid password.")
        } catch (NoResultException e) {
        	log.info("An account does not exist with that username.")
		}
		return false
    }

}
