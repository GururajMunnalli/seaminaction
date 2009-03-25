/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import com.tetratech.edas2.model.ActivityMedia;
import com.tetratech.edas2.model.ActivityMediaSubdivision;
import com.tetratech.edas2.model.ActivityType;
import com.tetratech.edas2.model.Assemblage;
import com.tetratech.edas2.model.BiologicalIntent;
import com.tetratech.edas2.model.Characteristic;
import com.tetratech.edas2.model.FrequencyClassDescriptor;
import com.tetratech.edas2.model.MeasurementUnit;
import com.tetratech.edas2.model.ResultDetectionCondition;
import com.tetratech.edas2.model.ResultMeasurementQualifier;
import com.tetratech.edas2.model.ResultStatus;
import com.tetratech.edas2.model.ResultType;
import com.tetratech.edas2.model.ResultValueType;
import com.tetratech.edas2.model.SamplingCollectionEquipment;

import com.tetratech.edas2.model.Taxon;
import java.util.List;
import javax.persistence.EntityManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name("lookupValueFactory")
@Scope(ScopeType.STATELESS)
public class LookupValueFactory {
	@Logger private Log log;
	@In private EntityManager entityManager;
	
	@Factory(value = "resultStatusValues", scope = ScopeType.CONVERSATION)
	public List<ResultStatus> getResultStatusValues() {
		return findAll(ResultStatus.class);
	}
	
	@Factory(value = "resultMeasurementQualifierValues", scope = ScopeType.CONVERSATION)
	public List<ResultMeasurementQualifier> getResultMeasureQualifierValues() {
		return findAll(ResultMeasurementQualifier.class);
	}
	
	@Factory(value = "resultValueTypeValues", scope = ScopeType.CONVERSATION)
	public List<ResultValueType> getResultValueTypeValues() {
		return findAll(ResultValueType.class);
	}
	
	@Factory(value = "characteristicValues", scope = ScopeType.CONVERSATION)
	public List<Characteristic> getCharacteristicValues() {
		return findAll(Characteristic.class);
	}
	
	@Factory(value = "resultDetectionConditionValues", scope = ScopeType.CONVERSATION)
	public List<ResultDetectionCondition> getResultDetectionConditionValues() {
		return findAll(ResultDetectionCondition.class);
	}
	
	@Factory(value = "measurementUnitValues", scope = ScopeType.CONVERSATION)
	public List<MeasurementUnit> getMeasurementUnitValues() {
		return findAll(MeasurementUnit.class);
	}
	
	@Factory(value = "assemblageValues", scope = ScopeType.CONVERSATION)
	public List<Assemblage> getAssemblageValues() {
		return findAll(Assemblage.class);
	}
	
	@Factory(value = "sampleCollEquipValues", scope = ScopeType.CONVERSATION)
	public List<SamplingCollectionEquipment> getSampleCollEquipValues() {
		return findAll(SamplingCollectionEquipment.class, "name asc");
	}
	
	@Factory(value = "activityTypeValues", scope = ScopeType.CONVERSATION)
	public List<ActivityType> getActivityTypeValues() {
		return findAll(ActivityType.class, "code asc");
	}
	
	@Factory(value = "activityMediaValues", scope = ScopeType.CONVERSATION)
	public List<ActivityMedia> getActivityMediaValues() {
		return findAll(ActivityMedia.class, "name asc");
	}
	
	@Factory(value = "activityMediaSubdivisionValues", scope = ScopeType.CONVERSATION)
	public List<ActivityMediaSubdivision> getActivityMediaSubdivValues() {
		return findAll(ActivityMediaSubdivision.class, "name asc");
	}
	
	@Factory(value = "resultTypeValues", scope = ScopeType.CONVERSATION)
	public List<ResultType> getResultTypeValues() {
		return findAll(ResultType.class, "name asc");
	}
	
	// FIXME: need to support fetch joins in findAll
	@Factory(value = "lifestageValues", scope = ScopeType.CONVERSATION)
	public List<FrequencyClassDescriptor> getLifestageValues() {
		return entityManager.createQuery("select fc from FrequencyClassDescriptor fc join fetch fc.frequencyClassType where fc.frequencyClassType.uid = 2").getResultList();
	}
	
	@Factory(value = "taxonValues", scope = ScopeType.CONVERSATION)
	public List<Taxon> getTaxonValues() {
		return findAll(Taxon.class, "name asc");
	}
	
	@Factory(value = "biologicalIntentValues", scope = ScopeType.CONVERSATION)
	public List<BiologicalIntent> getBiologicalIntentValues() {
		return findAll(BiologicalIntent.class, "name asc");
	}
	
	private <T> List<T> findAll(Class<T> type) {
		return findAll(type, null);
	}
	
	private <T> List<T> findAll(Class<T> type, String orderBy) {
		StringBuilder query = new StringBuilder();
		query.append("select e from ").append(type.getSimpleName()).append(" e");
		if (orderBy != null) {
			query.append(" order by e.").append(orderBy);
		}
		
		List<T> records = entityManager
			.createQuery(query.toString())
			.setHint("org.hibernate.readOnly", true)
			.getResultList();
		if (records.size() > 25) {
			log.warn("You should be using auto-complete for " + type.getSimpleName() + " rather than a list of values.");
		}
		return records;
	}

}
