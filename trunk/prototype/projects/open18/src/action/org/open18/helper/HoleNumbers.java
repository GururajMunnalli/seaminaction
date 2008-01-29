package org.open18.helper;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Name("holeNumbers")
public class HoleNumbers {

	private Map<String, List<Integer>> holeNumbers = new HashMap<String, List<Integer>>();

	@Create
	public void onCreate() {
		holeNumbers.put("out", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		holeNumbers.put("in", Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18));
		holeNumbers.put("all", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18));
	}

	@Unwrap
	public Map<String, List<Integer>> getHoleNumbers() {
		return holeNumbers;
	}
}
