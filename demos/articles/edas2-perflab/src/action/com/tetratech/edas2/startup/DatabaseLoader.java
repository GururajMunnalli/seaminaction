package com.tetratech.edas2.startup;

import com.tetratech.edas2.model.Activity;
import com.tetratech.edas2.model.ActivityMedia;
import com.tetratech.edas2.model.ActivityType;
import com.tetratech.edas2.model.Assemblage;
import com.tetratech.edas2.model.Country;
import com.tetratech.edas2.model.County;
import com.tetratech.edas2.model.FrequencyClassDescriptor;
import com.tetratech.edas2.model.FrequencyClassType;
import com.tetratech.edas2.model.HorizontalCollectionMethod;
import com.tetratech.edas2.model.HorizontalReferenceDatum;
import com.tetratech.edas2.model.Measurement;
import com.tetratech.edas2.model.MeasurementUnit;
import com.tetratech.edas2.model.MonitoringLocation;
import com.tetratech.edas2.model.MonitoringLocationType;
import com.tetratech.edas2.model.Organization;
import com.tetratech.edas2.model.Result;
import com.tetratech.edas2.model.ResultFrequencyClass;
import com.tetratech.edas2.model.ResultStatus;
import com.tetratech.edas2.model.ResultType;
import com.tetratech.edas2.model.State;
import com.tetratech.edas2.model.Taxon;
import com.tetratech.edas2.model.TaxonRank;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.Date;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.contexts.Context;

@Name("databaseLoader")
@Scope(ScopeType.APPLICATION)
@Startup
@Install(value = true, debug = true)
public class DatabaseLoader {
	@In private EntityManager entityManager;
	@In private Context applicationContext;
	
	// NOTE: need cascade = CascadeType.PERSIST on @ManyToOne associations to avoid
	// the multiple calls to EntityManager#persist()
	@Create
	@Transactional
	public void populate(Component component) {
		if (!isLocalDatabase()) {
			discard(component);
			return;
		}
		
		Organization org = new Organization();
		org.setName("National Sediment Quality Survey");
		org.setId("NSQS");
		entityManager.persist(org);
		
		Country us = new Country(1l, "US", "United States");
		entityManager.persist(us);
		State md = new State(1l, us, "MD", "Maryland");
		entityManager.persist(md);
		County aa = new County(1l, md, "Anne Arundel", "AA");
		entityManager.persist(aa);
		HorizontalReferenceDatum hrefDatum = new HorizontalReferenceDatum();
		hrefDatum.setUid(1l);
		hrefDatum.setName("D1");
		hrefDatum.setDescription("Datum 1");
		entityManager.persist(hrefDatum);
		HorizontalCollectionMethod hmeth = new HorizontalCollectionMethod();
		hmeth.setUid(1l);
		hmeth.setName("M1");
		hmeth.setDescription("Horiz. Collection Method 1");
		entityManager.persist(hmeth);
		MonitoringLocationType type = new MonitoringLocationType();
		type.setUid(1l);
		type.setName("TYPE1");
		type.setDescription("Monitoring Location Type 1");
		entityManager.persist(type);
		MonitoringLocation mloc = new MonitoringLocation();
		mloc.setId("MLOC1");
		mloc.setName("Monitoring Location 1");
		mloc.setDescription("An example monitoring location");
		mloc.setHuc8("02060006");
		mloc.setLatitude(new BigDecimal(39.115f, new MathContext(5)));
		mloc.setLongitude(new BigDecimal(-76.799f, new MathContext(5)));
		mloc.setType(type);
		mloc.setState(md);
		mloc.setCounty(aa);
		mloc.setCountry(us);
		mloc.setOrganization(org);
		mloc.setHorizontalReferenceDatum(hrefDatum);
		mloc.setHorizontalCollectionMethod(hmeth);
		entityManager.persist(mloc);
		
		ResultType benthicResultType = new ResultType();
		benthicResultType.setUid(2l);
		benthicResultType.setName("Benthic");
		benthicResultType.setDescription("Benthic Measurement");
		entityManager.persist(benthicResultType);
		
		Assemblage asmblg = new Assemblage(1l, "Fish");
		entityManager.persist(asmblg);
		
		ActivityMedia actMedia = new ActivityMedia(1l, "Water");
		entityManager.persist(actMedia);
		
		ActivityType actType = new ActivityType();
		actType.setUid(1l);
		actType.setCode("T1");
		actType.setAnalyticalMethodRequired(true);
		actType.setMonitoringLocationRequired(true);
		entityManager.persist(actType);
		
		Activity act = new Activity();
		act.setId("TRIP1");
		act.setStartDate(new Date());
		act.setMonitoringLocation(mloc);
		act.setOrganization(org);
		act.setMedia(actMedia);
		act.setType(actType);
		act.setResultType(benthicResultType);
		act.setAssemblage(asmblg);
		
		entityManager.persist(act);
		
		TaxonRank family = new TaxonRank(1l, "FAMILY");
		entityManager.persist(family);
		String[] familyTaxaNames = new String[]{"Chrysomelidae", "Curculionidae", "Dryopidae", "Dytiscidae", "Elmidae", "Gyrinidae", "Haliplidae", "Hydraenidae", "Hydrophilidae", "Lutrochidae", "Noteridae", "Psephenidae", "Ptilodactylidae", "Scirtidae", "Staphylinidae"};
		Taxon[] familyTaxa = new Taxon[familyTaxaNames.length];

		for (int i = 0, len = familyTaxaNames.length; i < len; i++) {
			familyTaxa[i] = new Taxon((long) (i + 1), family, familyTaxaNames[i]);
			entityManager.persist(familyTaxa[i]);
		}

		TaxonRank genus = new TaxonRank(2l, "GENUS");
		entityManager.persist(genus);

		String[] genusTaxaNames = new String[]{"Agasicles", "Auleutes", "Hyperodes", "Listronotus", "Lixus", "Onychylis", "Stenopelmus", "Tanysphyrus", "Ampumixis", "Ancyronyx", "Dubiraphia", "Gonielmis", "Macronychus", "Microcylloepus", "Optioservus", "Oulimnius", "Promoresia", "Stenelmis"};
		Taxon[] genusTaxa = new Taxon[genusTaxaNames.length];

		for (int i = 0, len = genusTaxaNames.length; i < len; i++) {
			genusTaxa[i] = new Taxon((long) (i + 1 + familyTaxaNames.length), genus, genusTaxaNames[i]);
			entityManager.persist(genusTaxa[i]);
		}
		
		FrequencyClassType fct = new FrequencyClassType(1l, "Larva", false, false);
		entityManager.persist(fct);
		FrequencyClassDescriptor fcd = new FrequencyClassDescriptor(null, fct, "Larva");
		entityManager.persist(fcd);
		
		ResultStatus status = new ResultStatus(1l, "Final");
		entityManager.persist(status);
		
		MeasurementUnit count = new MeasurementUnit(1l, "count");
		entityManager.persist(count);
		
		for (int i = 1; i < 500; i++) {
			Result result = new Result();
			result.setOrganization(org);
			result.setActivity(act);
			result.setType(benthicResultType);
			int taxonIdx = (i - 1) % (familyTaxa.length + genusTaxa.length);
			if (taxonIdx < familyTaxa.length) {
				result.setTaxon(familyTaxa[taxonIdx]);
			}
			else {
				result.setTaxon(genusTaxa[taxonIdx - familyTaxa.length]);
			}
			result.setStatus(status);
			ResultFrequencyClass rfc = new ResultFrequencyClass(null, fcd, org);
			rfc.setResult(result);
			result.setResultFrequencyClass(rfc);
			result.setPrimaryMeasurement(new Measurement(String.valueOf(i), count));
			result.setComments("Sampling #" + i);
			entityManager.persist(result);
			entityManager.persist(rfc);
		}
		
		discard(component);
	}
	
	protected void discard(Component component) {
		applicationContext.remove(component.getName());
	}
	
	protected boolean isLocalDatabase() {
		String vendor = null;
		Session hibernateSession = (Session) entityManager.getDelegate();
		try {
			vendor = hibernateSession.connection().getMetaData().getDatabaseProductName();
		} catch (SQLException e) {}
		return "H2".equals(vendor);
	}
}
