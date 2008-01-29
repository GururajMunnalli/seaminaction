package org.open18.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.Seam;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

@Name("seamVersion")
@Scope(ScopeType.STATELESS)
public class SeamVersion {
	@Unwrap
	public String getVersion() {
		return Seam.getVersion();
	}
}
