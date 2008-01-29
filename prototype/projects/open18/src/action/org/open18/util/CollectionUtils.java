package org.open18.util;

import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Name("collections")
public class CollectionUtils {
	public List toList(Collection c) {
		return new ArrayList(c);
	}
}
