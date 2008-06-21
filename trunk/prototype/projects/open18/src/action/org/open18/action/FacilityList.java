package org.open18.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.open18.model.Facility;

@Name("facilityList")
@Scope(ScopeType.PAGE)
public class FacilityList extends EntityQuery {

	private static final String[] RESTRICTIONS = {
			"lower(facility.address) like concat(lower(#{facilityList.facility.address}),'%')",
			"lower(facility.city) like concat(lower(#{facilityList.facility.city}),'%')",
			"lower(facility.country) like concat(lower(#{facilityList.facility.country}),'%')",
			"lower(facility.county) like concat(lower(#{facilityList.facility.county}),'%')",
			"lower(facility.description) like concat(lower(#{facilityList.facility.description}),'%')",
			"lower(facility.name) like concat(lower(#{facilityList.facility.name}),'%')",
			"lower(facility.phone) like concat(lower(#{facilityList.facility.phone}),'%')",
			"lower(facility.state) like concat(lower(#{facilityList.facility.state}),'%')",
			"lower(facility.type) like concat(lower(#{facilityList.facility.type}),'%')",
			"lower(facility.uri) like concat(lower(#{facilityList.facility.uri}),'%')",
			"lower(facility.zip) like concat(lower(#{facilityList.facility.zip}),'%')",
			"facility.priceRange <= #{facilityList.facility.priceRange gt 0 ? facilityList.facility.priceRange : null}"};

	@In("facilityExample")
	private Facility facility;

	@Override
	public String getEjbql() {
		return "select facility from Facility facility";
	}

	@Override
	public Integer getMaxResults() {
		return 25;
	}

	@Override
	public String getOrder() {
		if (super.getOrder() == null) {
			setOrder("name asc");
		}
		return super.getOrder();
	}

	public Facility getFacility() {
		return facility;
	}

	@Override
	public List<String> getRestrictions() {
		return Arrays.asList(RESTRICTIONS);
	}
	
	@Factory(value = "facilityExample", autoCreate = true, scope = ScopeType.CONVERSATION)
	public Facility createFacilityExample() {
	    return new Facility();
	}
}
