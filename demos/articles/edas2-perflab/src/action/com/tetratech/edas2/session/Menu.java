/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;


@Name("menu")
public class Menu {
	@Logger 
	Log log;
	
	public String forwardToMonitoringLocations(){
		log.info("forwarding to monitoring locations");
		return "MonitoringLocations";
	}
	
	public String forwardToActivities(){
		log.info("forwarding to activities");
		return "Activities";
	}
	
	public String forwardToFieldMeasurementReport(){
		return "FieldMeasReport";
	}
	
	public String forwardToBenthicMeasurementReport(){
		return "BenthicMeasReport";
	}
	
	public String forwardToBenthicMetricsReport(){
		return "BenthicMetricsReport";
	}
	
}
