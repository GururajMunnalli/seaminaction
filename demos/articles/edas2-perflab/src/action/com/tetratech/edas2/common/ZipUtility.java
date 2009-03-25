package com.tetratech.edas2.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtility {
	private static final int BUFFER_SIZE = 1024;

	/**
	 * Utility method to zip a single file
	 * 
	 * @param sourcePath
	 * @param zipPath
	 * @param sourceFileName
	 * @param zipFileName
	 * @throws Exception
	 */
	public static final void createZipFile(String sourcePath, String zipPath, String sourceFileName, String zipFileName)throws Exception{
		ZipOutputStream zipOutStream = null;
		FileInputStream fin = null;
		try{
			//create streams
			zipOutStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath+File.separator+zipFileName)));
			//create a data buffer
			byte data[] = new byte[BUFFER_SIZE];
			//get the file
			File file = new File(sourcePath+ File.separator + sourceFileName);
			//create a new ZIP entry
			ZipEntry entry = new ZipEntry(sourceFileName);
			zipOutStream.putNextEntry(entry);
			fin = new FileInputStream(file);
			int bytesRead;
			while((bytesRead=fin.read(data,0,BUFFER_SIZE))>0){
				zipOutStream.write(data,0,bytesRead);
			}
			//close stream
			fin.close();
			fin = null;
			//close ZIP entry
			zipOutStream.closeEntry();
			//close stream
			zipOutStream.flush();
			zipOutStream.close();
			zipOutStream = null;
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			if(fin!=null){
				fin.close();
				fin = null;
			}           
			if(zipOutStream != null){
				zipOutStream.close();
				zipOutStream = null;
			}   
		}
	}

	/**
	 * Method to zip a bunch of files
	 * 
	 * @param sourceFilePathNames
	 * @param zipPath
	 * @param zipFileName
	 * @throws Exception
	 */
	public static final void createZipFile(String[] sourceFiles,String zipPath,String zipFileName)throws Exception{
		ZipOutputStream zipOutStream = null;
		FileInputStream fin = null;

		try{
			//create stream
			zipOutStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath+File.separator+zipFileName)));
			//zip files
			for(int i=0;i<sourceFiles.length;i++){
				File[] files = FileUtility.getFiles(sourceFiles[i]);
				for(int j=0;j<files.length;j++){
					//create a data buffer
					byte data[] = new byte[BUFFER_SIZE];
					//get the file
					File file = files[j];
					//create a new ZIP entry
					ZipEntry entry = new ZipEntry(file.getName());
					zipOutStream.putNextEntry(entry);
					fin = new FileInputStream(file);
					int bytesRead;
					while((bytesRead=fin.read(data,0,BUFFER_SIZE))>0){
						zipOutStream.write(data,0,bytesRead);
					}
					//close stream
					fin.close();
					fin = null;
					//close ZIP entry
					zipOutStream.closeEntry();
				}
			}
			//close stream
			zipOutStream.flush();
			zipOutStream.close();
			zipOutStream = null;
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			if(fin!=null){
				fin.close();
				fin = null;
			}           
			if(zipOutStream != null){
				zipOutStream.close();
				zipOutStream = null;
			}   
		}
	}
	
}
