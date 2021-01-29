package com.MyUtils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

public class ThreaFile implements Runnable{
	
	private String outFilePath(String name){
		String path = "C:\\Users\\ljb\\Desktop\\TestIo\\";
		return path+name+".txt";
	}
	@Override
	  public void run(){
		  String path = "C:\\Users\\ljb\\Desktop\\TestIo\\新建文本文档.txt";
		  try {
			InputStream is = new FileInputStream(new File(path));
			OutputStream os = new FileOutputStream(new File(outFilePath(Thread.currentThread().getName())));
			byte[] buf = new byte[1024];
			int n =0;
			while((n = is.read(buf))!=-1){
				os.write(buf, 0, n);
			}
			
		  } catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  public static void main(String[] args){
		  ThreaFile t = new ThreaFile();
		  	
		       new Thread(t).start();
		        new Thread(t).start();
		        new Thread(t).start();
		        new Thread(t).start();
		    }
	}
