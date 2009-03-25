package com.tetratech.edas2.test;

import com.tetratech.edas2.model.Country;
import com.tetratech.edas2.model.County;
import com.tetratech.edas2.model.HorizontalCollectionMethod;
import com.tetratech.edas2.model.HorizontalReferenceDatum;
import com.tetratech.edas2.model.Measurement;
import com.tetratech.edas2.model.MonitoringLocation;
import com.tetratech.edas2.model.MonitoringLocationType;
import com.tetratech.edas2.model.Organization;
import com.tetratech.edas2.model.Result;
import com.tetratech.edas2.model.State;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import org.jboss.seam.Component;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class EntityMappingDevelopmentTest extends SeamTest {

	@Test
	public void saveMeasurement() throws Exception {
		new FacesRequest() {

			@Override
			protected void invokeApplication() throws Exception {
				EntityManager em = getEntityManager();
				Result result = em.find(Result.class, 31232321l);
				result.setPrimaryMeasurement(new Measurement());
				em.flush();
				assertNotNull(result.getPrimaryMeasurement());
				//result.setComments(new Date().toString());
				result.setPrimaryMeasurement(new Measurement());
				em.flush();
				assertNotNull(result.getPrimaryMeasurement());
			}
			
		}.run();
	}
	
	@Test(enabled = false)
	public void retrieveOrganization19() throws Exception {
		new NonFacesRequest() {

			@Override
			protected void renderResponse() throws Exception {
				EntityManager em = (EntityManager) getValue("#{entityManager}");
				Organization org = em.find(Organization.class, 19l);
				assertEquals("National Sediment Quality Survey", org.getName());
			}
			
		}.run();
	}	
	
	@Test(enabled = false)
	public void persistMonitoringLocation() throws Exception {
		new NonFacesRequest() {

			@Override
			protected void renderResponse() throws Exception {
				EntityManager em = (EntityManager) getValue("#{entityManager}");
				MonitoringLocation mloc = new MonitoringLocation();
				mloc.setName("Test Monitoring Location " + new Date());
				mloc.setId("Sample " + new Date());
				mloc.setType(em.find(MonitoringLocationType.class, 1L));
				mloc.setOrganization(em.find(Organization.class, 19l));
				mloc.setCountry((Country) em.createQuery("select c from Country c where c.abbreviation = 'US'").getSingleResult());
				mloc.setState((State) em.createQuery("select s from State s where s.abbreviation = 'MD'").getSingleResult());
				mloc.setCounty((County) em.createQuery("select c from County c where c.id = 1212").getSingleResult());
				mloc.setLatitude(new BigDecimal(45));
				mloc.setLongitude(new BigDecimal(45));
				mloc.setHorizontalCollectionMethod(em.find(HorizontalCollectionMethod.class, 1l));
				mloc.setHorizontalReferenceDatum(em.find(HorizontalReferenceDatum.class, 1l));
				em.persist(mloc);
			}
			
		}.run();
	}
	
	private EntityManager getEntityManager() {
		return (EntityManager) Component.getInstance("entityManager");
	}
		
}
