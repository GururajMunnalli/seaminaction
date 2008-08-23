package org.open18.comparator;

import java.util.Comparator;
import org.open18.model.Hole;

public class HoleComparator implements Comparator<Hole> {

	public int compare(Hole a, Hole b) {
		return Integer.valueOf(a.getNumber()).compareTo(Integer.valueOf(b.getNumber()));
	}
}
