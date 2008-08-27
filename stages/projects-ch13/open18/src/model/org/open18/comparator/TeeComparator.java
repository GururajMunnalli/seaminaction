package org.open18.comparator;

import java.util.Comparator;
import org.open18.model.Tee;

public class TeeComparator implements Comparator<Tee> {

	public int compare(Tee a, Tee b) {
		return Integer.valueOf(a.getHole().getNumber()).compareTo(Integer.valueOf(b.getHole().getNumber()));
	}
}
