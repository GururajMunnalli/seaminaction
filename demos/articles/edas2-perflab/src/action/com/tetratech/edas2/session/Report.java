package com.tetratech.edas2.session;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectMany;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import com.tetratech.edas2.common.FileUtility;
import com.tetratech.edas2.common.QueryReportCreator;
import com.tetratech.edas2.common.ZipUtility;
import com.tetratech.edas2.criteria.ReportCriteria;
import com.tetratech.edas2.model.Characteristic;
import com.tetratech.edas2.model.Measurement;
import com.tetratech.edas2.model.MeasurementUnit;
import com.tetratech.edas2.model.MonitoringLocation;
import com.tetratech.edas2.model.ResultStatus;

@Name("report")
@Scope(ScopeType.CONVERSATION)
public class Report {

	private static final int FIELD_MEASUREMENT_REPORT = 0;
	private static final int BENTHIC_MEASUREMENT_REPORT = 1;
	private static final int BENTHIC_METRICS_REPORT = 2;
	

	@Logger
	Log log;

	@In	
	protected EntityManager entityManager;

	@In	
	protected FacesMessages facesMessages;

	@In("reportSearchCriteria") 
	protected ReportCriteria searchCriteria;

	private String monLocName;
	private List<MonitoringLocation> selectedMonLocs;

	private String charName;
	private List<Characteristic> selectedChars;

	Integer reportType;

	String reportName;

	@RequestParameter
	Integer type;
	
	private String tmpFolder;
	private String reportFolder;
	private String datasource;

	@Create
	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public void init(){
		reportType = type;
		if(reportType==FIELD_MEASUREMENT_REPORT)
			reportName = "Field Measurement Report";
		else if(reportType==BENTHIC_MEASUREMENT_REPORT)
			reportName = "Benthic Macro Invertebrate Data Report";
		else if(reportType==BENTHIC_METRICS_REPORT)
			reportName = "Benthic Macro Intertebrate Metrics Report";
		log.info(reportName);
	}

	public void addMonitoringLocationToSearch(){
		log.info("adding mon loc "+monLocName);
		MonitoringLocation ml = findMonitoringLocation(monLocName);
		if(ml!=null){
			if(!searchCriteria.containsMonitoringLocation(ml)){
				searchCriteria.addMonitoringLocation(ml);
				monLocName = "";
			}else{
				facesMessages.add(FacesMessage.SEVERITY_WARN,
						"A monitoring location with {0} name has been already added to the list!",monLocName);
			}
		}else{
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"A monitoring location with {0} name could not be found. Please try again!",monLocName);
		}
	}

	//	public void removeMonitoringLocationFromSearch(){
	//		System.out.println("selections: " + selectedMonLocs.size());
	//		FacesContext context = FacesContext.getCurrentInstance();
	//		UIViewRoot view = context.getViewRoot();
	//		UIComponent UI = view.findComponent("report:monlocs");
	//		UISelectMany sm = (UISelectMany)UI;
	//		for (UIComponent c : sm.getChildren()) {
	//			if (c instanceof UISelectItems) {
	//				UISelectItems itemsHolder = (UISelectItems) c;
	//				List<SelectItem> items = (List<SelectItem>) itemsHolder.getValue();
	//				for (Iterator<SelectItem> iter = items.iterator(); iter.hasNext();) {
	//					MonitoringLocation candidate = (MonitoringLocation) iter.next().getValue();
	//					if (selectedMonLocs.contains(candidate)) {
	//						iter.remove();
	//					}
	//				}
	//			}
	//		}
	//		//		if(selectedMonLocs!=null)
	//		//			for(int i=0;i<selectedMonLocs.size();i++)
	//		//				searchCriteria.removeMonitoringLocation(selectedMonLocs.get(i));
	//
	//	}

	public void clearMonitoringLocationSearch(){
		searchCriteria.clearMonitoringLocations();
	}


	public void addCharacteristicToSearch(){
		log.info("adding mon loc");
		Characteristic c = findCharacteristic(charName);
		if(c!=null){
			if(!searchCriteria.containsCharacteristic(c)){
				searchCriteria.addCharacteristic(c);
				charName = "";
			}else{
				facesMessages.add(FacesMessage.SEVERITY_WARN,
						"A characteristic with {0} name has been already added to the list!",monLocName);
			}
		}else{
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"A characteristic with {0} name could not be found. Please try again!",monLocName);
		}
	}

	//	public void removeCharacteristicFromSearch(){
	//		if(selectedChars!=null)
	//			for(int i=0;i<selectedChars.size();i++)
	//				searchCriteria.removeCharacteristic(selectedChars.get(i));
	//
	//	}

	public void clearCharacteristicSearch(){
		searchCriteria.clearCharacteristics();
	}

	public List<MonitoringLocation> filterMonitoringLocation(Object event) {
		List<MonitoringLocation> matches = null;
		try {
			String pref = event.toString();
			matches = entityManager
			.createQuery("select m from MonitoringLocation m " +
					"where " +
			"lower(m.name) like concat(concat('%',lower(:name)),'%') ")
			.setParameter("name", pref)
			.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		return matches;
	}

	public List<MeasurementUnit> filterCharacteristic(Object event) {
		List<MeasurementUnit> matches = null;
		try {
			String pref = event.toString();
			matches = entityManager
			.createQuery("select c from Characteristic c " +
					"where " +
			"lower(c.name) like concat(concat('%',lower(:name)),'%') ")
			.setParameter("name", pref)
			.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		return matches;
	}

	private MonitoringLocation findMonitoringLocation(String name){
		if(StringUtils.isEmpty(name))
			return null;

		List<MonitoringLocation> m = entityManager.createQuery("select m from MonitoringLocation m where m.name = :name")
		.setParameter("name",name)
		.getResultList();

		return m.size()!=0?m.get(0):null;
	}

	private Characteristic findCharacteristic(String name){
		if(StringUtils.isEmpty(name))
			return null;

		List<Characteristic> c = entityManager.createQuery("select c from Characteristic c where c.name = :name")
		.setParameter("name", name)
		.getResultList();

		return c.size()!=0?c.get(0):null;
	}

	public String getMonLocName() {
		return monLocName;
	}

	public void setMonLocName(String monLocName) {
		this.monLocName = monLocName;
	}

	public List<MonitoringLocation> getSelectedMonLocs() {
		return selectedMonLocs;
	}

	public void setSelectedMonLocs(List<MonitoringLocation> selectedMonLocs) {
		this.selectedMonLocs = selectedMonLocs;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public List<Characteristic> getSelectedChars() {
		return selectedChars;
	}

	public void setSelectedChars(List<Characteristic> selectedChars) {
		this.selectedChars = selectedChars;
	}	

	public void resetFilters(){
		searchCriteria.reset();
	}

	private String createQuery(String query, int type){
		StringBuffer str = new StringBuffer(query);
		if (searchCriteria.getStartDate()!=null){
			str.append(" AND a.act_start_date >= TO_DATE('");
			str.append((new SimpleDateFormat("MM/dd/yyyy")).format(searchCriteria.getStartDate()));
			str.append("','MM/dd/yyyy') ");
		}
		if (searchCriteria.getEndDate()!=null){
			str.append(" AND a.act_end_date <= TO_DATE('");
			str.append((new SimpleDateFormat("MM/dd/yyyy")).format(searchCriteria.getEndDate()));
			str.append("','MM/dd/yyyy') ");
		}
		if (searchCriteria.getResultStatuses().size()!=0){
			str.append(" AND r.RESSTA_UID IN(");
			List<ResultStatus> list = searchCriteria.getResultStatuses();
			int length = list.size(); 
			str.append(list.get(0).getUid().toString());
			for(int i=1;i<length;i++){
				str.append(",");
				str.append(list.get(i).getUid().toString());
			}
			str.append(") ");
		}
		if (!StringUtils.isEmpty(searchCriteria.getExclude())){
			str.append(" AND " );
			if(searchCriteria.getExclude().equals("Y"))
				str.append(" r.exclude_yn = 'Y' ");
			else if(searchCriteria.getExclude().equals("N"))
				str.append(" r.exclude_yn = 'N' ");
		}
		if(searchCriteria.getMonitoringLocations().size()!=0){
			str.append(" AND a.mloc_uid IN(");
			List<MonitoringLocation> list = searchCriteria.getMonitoringLocations();
			int length = list.size();
			str.append(list.get(0).getUid().toString());
			for(int i=1;i<length;i++){
				str.append(",");
				str.append(list.get(i).getUid().toString());
			}
			str.append(") ");
		}
		if(searchCriteria.getCharacteristics().size()!=0){
			str.append(" AND r.CHR_UID IN(");
			List<Characteristic> list = searchCriteria.getCharacteristics();
			int length = list.size();
			str.append(list.get(0).getUid());
			for(int i=1;i<length;i++){
				str.append(",");
				str.append(list.get(i).getUid());
			}
			str.append(") ");
		}
		if(type == FIELD_MEASUREMENT_REPORT || type == BENTHIC_MEASUREMENT_REPORT)
			str.append(" ORDER BY o.org_name,a.act_id,r.res_uid");
		else if(type == BENTHIC_METRICS_REPORT)
			str.append("GROUP BY ML.MLOC_ID, ML.MLOC_NAME, A.ACT_ID, A.ACT_START_DATE, A.ACT_END_DATE )"); 
		log.info("GENERATED QUERY: "+str.toString());
		return str.toString();
	}

	private static final String GET_MEASUREMENTS =
		"SELECT * " + 
		"FROM " +
		"ED_ORGANIZATION o," +
		"ED_MONITORING_LOCATION l, " +
		"ED_ACTIVITY a, " +
		"ED_RESULT r " +
		"WHERE " +
		"  o.ORG_UID = l.ORG_UID " + 
		"AND l.MLOC_UID = a.MLOC_UID " + 
		"AND a.ACT_UID = r.ACT_UID ";

	private static final String GET_METRICS =
		"SELECT MLOC_ID,MLOC_NAME,ACT_ID, " + 
		"   TO_CHAR(ACT_START_DATE,'MM/DD/YYYY') AS ACT_START_DATE," +
	    "   TO_CHAR(ACT_END_DATE,'MM/DD/YYYY') AS ACT_END_DATE, " +
		"	   taxa_no, indiv_no, ephem_taxa_no, " + 
		"	   trim(CASE WHEN indiv_no = 0 THEN NULL  " + 
		"       ELSE TO_CHAR( ephem_taxa_no / indiv_no * 100, '990.00' ) " + 
		"       END) AS perc_ephem     " + 
		"FROM ( " + 
		"SELECT  " + 
		"ML.MLOC_ID, ML.MLOC_NAME, A.ACT_ID,  " + 
		"ACT_START_DATE,ACT_END_DATE, " + 
		"COUNT (DISTINCT DECODE (r.exclude_yn,'Y', NULL,t.tax_uid)) AS taxa_no,  " + 
		"SUM (r.res_measure) AS indiv_no, " + 
		"COUNT(DISTINCT CASE WHEN r.exclude_yn = 'N'  " + 
		"AND t.tax_uid IN (SELECT  tax_uid " + 
		"FROM ED_TAXON " + 
		"START WITH UPPER (tax_name) = 'EPHEMEROPTERA' " + 
		"CONNECT BY PRIOR tax_uid = parent_tax_uid) " + 
		"THEN t.tax_uid ELSE NULL END) AS ephem_taxa_no " + 
		"FROM ED_RESULT r, ED_TAXON t, ED_ACTIVITY a, ED_MONITORING_LOCATION ML " + 
		"WHERE " + 
		"	  A.RES_TYPE_UID = 2 " + 
		"AND  r.tax_uid = t.tax_uid  " + 
		"AND  a.ACT_UID = r.ACT_UID " + 
		"AND  A.MLOC_UID = ML.MLOC_UID ";

	public void createReport(){
		Connection conn = null;
		try{
			//check that the user has selected monitoring locations
			if(searchCriteria.getMonitoringLocations().size()==0){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,"Please find at least one monitoring location and add it to the list.");
				return;
			}
			//get connection
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(datasource);
			conn = ds.getConnection();
			//generate report
			String name = "";
			if(reportType==FIELD_MEASUREMENT_REPORT)
				name =  generateFieldMeasurementReport(conn);
			else if(reportType==BENTHIC_MEASUREMENT_REPORT)
				name = generateBenthicMeasurementReport(conn);
			else if(reportType==BENTHIC_METRICS_REPORT)
				name = generateBenthicMetricsReport(conn);
			//stream report
			streamReport(reportFolder,name);
			//clear directories
			FileUtility.cleanDirectory(tmpFolder);
			FileUtility.cleanDirectory(reportFolder);
		}catch(Exception e){
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while generating your report. Please try again!");
		}finally{
			try {
				if(conn!=null){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String generateFieldMeasurementReport(Connection conn) throws Exception{
		//create report
		QueryReportCreator.createReport(conn,createQuery(GET_MEASUREMENTS,FIELD_MEASUREMENT_REPORT),tmpFolder,"fieldMeasurements.csv",QueryReportCreator.COMMA);
		ZipUtility.createZipFile(new String[]{tmpFolder}, reportFolder, "fieldMeasurementsReport.zip");
		return "fieldMeasurementsReport.zip";
	}

	private String generateBenthicMeasurementReport(Connection conn) throws Exception{
		//create report
		QueryReportCreator.createReport(conn,createQuery(GET_MEASUREMENTS,BENTHIC_MEASUREMENT_REPORT),tmpFolder,"benthicMeasurements.csv",QueryReportCreator.COMMA);
		ZipUtility.createZipFile(new String[]{tmpFolder}, reportFolder, "benthicMeasurementsReport.zip");
		return "benthicMeasurementsReport.zip";
	}

	private String generateBenthicMetricsReport(Connection conn) throws Exception{
		//create report
		QueryReportCreator.createReport(conn,createQuery(GET_METRICS,BENTHIC_METRICS_REPORT),tmpFolder,"benthicMetrics.csv",QueryReportCreator.COMMA);
		ZipUtility.createZipFile(new String[]{tmpFolder}, reportFolder, "benthicMetricsReport.zip");
		return "benthicMetricsReport.zip";
	}
	
	private void streamReport(String path,String fileName) throws Exception{
		OutputStream out = null;
		FileInputStream fis = null;
		try{
			//get the http request
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse resp = (HttpServletResponse)fc.getExternalContext().getResponse();
			//create input stream
			File file = new File(path+ File.separator + fileName);
			fis = new FileInputStream(file);
			//set the response type
			resp.setContentType("application/zip");
			resp.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
			//write out the binary data
			out = resp.getOutputStream();
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length=fis.read(buffer,0,1024))> 0){
				out.write(buffer,0,length);
			}
			fc.responseComplete();
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			if( out!=null){
				try{
					out.flush();
					out.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if (fis!=null){
				try{
					fis.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
