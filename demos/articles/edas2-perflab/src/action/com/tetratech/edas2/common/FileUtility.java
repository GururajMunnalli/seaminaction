package com.tetratech.edas2.common;

import java.io.File;

public class FileUtility {
	
	/**
	 * This method deletes the files and sub-directories
	 * of a directory as given by the path
	 * 
	 * @param path
	 */
	public static void cleanDirectory(String path){
		File[] files = getFiles(path);
		if(files!=null)
			for(int i=0;i<files.length;i++)
				files[i].delete();
	}
	

	/**
	 * This method returns a list of files
	 * of a directory
	 * 
	 * @param path
	 * @param filter
	 * @return
	 */
	public static File[] getFiles(String path){
		File[] files = null;
		File directory = new File(path);
		if(directory!=null && directory.isDirectory()){
			files = directory.listFiles();
		}
		return files;
	}
}
