package com.inspur.util;

import java.io.FileWriter;
import java.io.IOException;

public class Save_Result {
	public void SaveResult(String content,String file_path){
		 FileWriter fwriter = null;
		 try {
		  fwriter = new FileWriter(file_path);
		  fwriter.write(content);
		 } catch (IOException ex) {
		  ex.printStackTrace();
		 } finally {
		  try {
		   fwriter.flush();
		   fwriter.close();
		  } catch (IOException ex) {
		   ex.printStackTrace();
		  }
		 }
	}
}
