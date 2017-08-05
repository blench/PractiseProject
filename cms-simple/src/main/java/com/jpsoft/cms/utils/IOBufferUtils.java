package com.jpsoft.cms.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.fileupload.util.Streams;

public class IOBufferUtils {
	public static void copyByBuffered(InputStream input,OutputStream output,boolean bClose){
		BufferedInputStream bis = new BufferedInputStream(input);
		BufferedOutputStream bos = new BufferedOutputStream(output);
		
		byte[] buffer = new byte[4096];
		
		int count=0;
		
		try {
			while((count=bis.read(buffer))>0){
				bos.write(buffer, 0, count);
			}
			
			bos.flush();
			
			input.close();
			
			if(bClose){
				output.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String readToEnd(String fileName){
		StringBuilder sb = new StringBuilder();
		
		try{
			InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName),"GBK");
			BufferedReader br = new BufferedReader(reader);
			
			String str = null;
			
			while((str=br.readLine())!=null){
				sb.append(str);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String srcFileName = "F:\\荆鹏合影.jpg";
		String destFileName = "F:\\荆鹏合影2.jpg";
		
		try{
			FileInputStream input = new FileInputStream(srcFileName);
			FileOutputStream output = new FileOutputStream(destFileName);
			
			long start = System.currentTimeMillis();
			
			//加了buffered更快
			//IOUtils.copyByBuffered(input, output); 
			Streams.copy(input, output, true);
			
			//input.close();
			//output.close();
			
			System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
