package org.open18;

import javax.servlet.http.HttpServletResponse;
import org.jboss.seam.annotations.ApplicationException;
import org.jboss.seam.annotations.exception.HttpError;

@ApplicationException(rollback = true)
@HttpError(errorCode = HttpServletResponse.SC_NOT_FOUND)
public class ProfileNotFoundException extends Exception {
	public ProfileNotFoundException(Long id) {
		super("The record for the requested profile does not exist: " + id);
	}
}
