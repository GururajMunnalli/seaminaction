package org.open18.option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.open18.model.enums.GrassType;

@Name("grassOptions")
@Scope(ScopeType.PAGE)
@BypassInterceptors
public class GrassOptions {

	private List<String> greens;
	private List<String> fairways;

	@Create
	public void initOptions() {
		greens = new ArrayList<String>();
		for (GrassType type : GrassType.values()) {
			if (type.isValidForGreens()) {
				greens.add(type.name());
			}
		}
		Collections.sort(greens);

		fairways = new ArrayList<String>();
		for (GrassType type : GrassType.values()) {
			if (type.isValidForFairways()) {
				fairways.add(type.name());
			}
		}
		Collections.sort(fairways);
	}

	public List<String> getGreens() {
		return greens;
	}

	public List<String> getFairways() {
		return fairways;
	}
}
