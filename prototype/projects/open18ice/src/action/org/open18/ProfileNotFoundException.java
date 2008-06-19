package org.open18;

import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.annotations.ApplicationException;
import org.jboss.seam.annotations.exception.HttpError;

@HttpError(errorCode = HttpServletResponse.SC_NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException {
	public ProfileNotFoundException(Long id) {
		super(id == null ? "No profile was requested" :
			"The requested profile does not exist");
	}

	public ProfileNotFoundException(String username) {
		super(username == null ? "No profile was requested" :
			"The requested profile does not exist");
	}
}
