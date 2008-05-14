package org.open18.golftips.lookup;

import java.util.List;
import java.util.ArrayList;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

@Name("tipCategories")
public class TipCategoryList {

	private List<String> categories;

	@Unwrap
	public List<String> lookup() {
		if (categories == null) {
			categories = new ArrayList<String>();
			categories.add("The Swing");
			categories.add("Putting");
			categories.add("Attitude");
		}

		return categories;
	}

}
