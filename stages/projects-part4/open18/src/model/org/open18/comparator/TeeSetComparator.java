package org.open18.comparator;

import java.util.Comparator;
import org.open18.model.TeeSet;

public class TeeSetComparator implements Comparator<TeeSet> {

	public int compare(TeeSet a, TeeSet b) {
		return a.getPosition() == null ||
			b.getPosition() == null ? 0 : a.getPosition().compareTo(b.getPosition());
	}
}
