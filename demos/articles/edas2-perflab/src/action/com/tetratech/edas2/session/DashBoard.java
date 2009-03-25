/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import org.richfaces.component.UIToggleControl;


import com.tetratech.edas2.beans.CategoryValuePair;

@Name("dashBoard")
public class DashBoard {
	@Logger 
	Log log;
	
	@In
	EntityManager entityManager;
	
	private static final String GET_MON_LOC_COUNT = "select count(*) from ed_monitoring_location";
	
	public int getMonitoringLocationCount(){
		return ((BigDecimal)entityManager.createNativeQuery(GET_MON_LOC_COUNT).getSingleResult()).intValue();
	}
	
	private static final String GET_MON_LOC_BY_ID = "select case when c.CNTY_NAME is null then 'Undefined' else c.CNTY_NAME end, count(*) from ed_monitoring_location ml, ed_county c where ml.CNTY_UID = c.CNTY_UID (+)  group by c.CNTY_NAME";
	
	public List<CategoryValuePair> getMonitoringLocationData(){
		List<CategoryValuePair> data = new ArrayList<CategoryValuePair>();
		Iterator results = entityManager.createNativeQuery(GET_MON_LOC_BY_ID).getResultList().iterator();
		while(results.hasNext()){
			Object[] r = (Object[])results.next();
			log.info((String)r[0]);
			log.info(((BigDecimal)r[1]).intValue());
			data.add(new CategoryValuePair((String)r[0],((BigDecimal)r[1]).intValue()));
		}
		return data;
	}
	
	private static String GET_ACTIVITY_COUNT = 
		"select count(*) from ed_activity";
	
	public int getActivityCount(){
		return ((BigDecimal)entityManager.createNativeQuery(GET_ACTIVITY_COUNT).getSingleResult()).intValue();
	}
	
	private static String GET_ACTIVITY_DATA = 
		"select b.ACTYP_CD, count(*) " +
		"from ed_activity a, ed_activity_type b " +
		"where " +
		"	 a.ACTYP_UID = b.ACTYP_UID " +
		"group by b.ACTYP_CD";
		
		public List<CategoryValuePair> getActivityData(){
			List<CategoryValuePair> data = new ArrayList<CategoryValuePair>();
			Iterator results = entityManager.createNativeQuery(GET_ACTIVITY_DATA).getResultList().iterator();
			while(results.hasNext()){
				Object[] r = (Object[])results.next();
				log.info((String)r[0]);
				log.info(((BigDecimal)r[1]).intValue());
				data.add(new CategoryValuePair((String)r[0],((BigDecimal)r[1]).intValue()));
			}
			return data;
		}
	
	private static String GET_RESULT_COUNT = 
			"select count(*) from ed_result";
	
	public int getResultCount(){
		return ((BigDecimal)entityManager.createNativeQuery(GET_RESULT_COUNT).getSingleResult()).intValue();
	}
	
	private static String GET_RESULT_DATA =
		"select rs.RESSTA_NAME, count(*) from " +
		"ed_result r,ed_result_status rs " +
		"where " +
		"	  r.RESSTA_UID = rs.RESSTA_UID " +
		"group by rs.RESSTA_NAME";
		
	public List<CategoryValuePair> getResultData(){
			List<CategoryValuePair> data = new ArrayList<CategoryValuePair>();
			Iterator results = entityManager.createNativeQuery(GET_RESULT_DATA).getResultList().iterator();
			while(results.hasNext()){
				Object[] r = (Object[])results.next();
				log.info((String)r[0]);
				log.info(((BigDecimal)r[1]).intValue());
				data.add(new CategoryValuePair((String)r[0],((BigDecimal)r[1]).intValue()));
			}
			return data;
		}
	
	public void toggleSwitch(ActionEvent event){
		log.info("working*******");
		UIComponent comp = event.getComponent();
		UIToggleControl tc = (UIToggleControl)comp;
		tc.setValue("Foo");
	}
	
}
