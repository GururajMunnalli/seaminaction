package org.preflight.model;

public enum ItineraryType {
	ONE_WAY("one-way"),
	ROUND_TRIP("round-trip"),
	MULTI_CITY("multi-city");
	
	private String label;
	
	ItineraryType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
