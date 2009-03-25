package com.tetratech.edas2.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class QueryReportCreator {
	public static final char SPACE = (char)32;
	public static final char BAR = (char)124;
	public static final char COMMA = (char)44;
	public static final char TAB = (char)9;
	private static final char QUOTE = (char)34;
	private static final String EMPTY_STR = "";
	
	public static void createReport(Connection conn,String query, String path,String fileName,char separator)throws Exception{
		
		PreparedStatement ps = null;	
		ResultSet rs = null;
		String[] columns = null;
		PrintWriter pw = null;
		try{
			//create a new file
			pw = new PrintWriter(new BufferedWriter(new FileWriter(path+File.separator + fileName)));
			//execute the query
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			//get the column names
			ResultSetMetaData rsm = rs.getMetaData();
			columns = new String[rsm.getColumnCount()];
			for(int i=0;i<columns.length;i++)
				columns[i]=rsm.getColumnName(i+1);
			pw.println(rowToString(columns,separator));
			//copy the results
			String[] row = new String[columns.length];
			while(rs.next()){
				for(int i=0;i<columns.length;i++){
					row[i]=rs.getString(columns[i]);
				}
				pw.println(rowToString(row,separator));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(pw!=null){
				pw.flush();
				pw.close();
				pw = null;
			}
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(ps!=null){
				ps.close();
				ps=null;
			}
		}
	}
	
	private static String rowToString(String[] row,char separator){
		StringBuffer sb = new StringBuffer();
		if(row.length==0){
			sb.append(EMPTY_STR);
		}else if(row.length==1){
			sb.append(QUOTE);
			sb.append(valueToString(row[0]));
			sb.append(QUOTE);
		}else{
			//append values 1....(n-1)
			int lenMinusOne = row.length-1;
			for(int i=0;i<lenMinusOne;i++){
				sb.append(QUOTE);
				sb.append(valueToString(row[i]));
				sb.append(QUOTE);
				sb.append(separator);
			}
			//append value n
			sb.append(QUOTE);
			sb.append(valueToString(row[lenMinusOne]));
			sb.append(QUOTE);
		}
		return sb.toString();
	}
	
	private static String valueToString(String str){
		return str!=null?str:EMPTY_STR;
	}

}	



