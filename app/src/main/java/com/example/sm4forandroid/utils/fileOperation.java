package com.example.sm4forandroid.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class fileOperation {
	public static boolean exist(String file){
		return (new java.io.File(file)).exists();
	}
	
	public static int writeString(String file, String str, int mode){
		//mode:0:create
		//1:overwrite
		java.io.File outputFile=new java.io.File(file);
		if(outputFile.exists() && mode==0){
			return -1;
		}
	    java.io.PrintWriter output;
		try {
			output = new java.io.PrintWriter(outputFile);
		} catch (FileNotFoundException e) {
			return -2;
		}
		output.print(str);
		output.close();
		return 0;
	}
	
	public static String readString(String file){
		String inputStr="";
		java.io.File inputFile=new java.io.File(file);
		java.util.Scanner input;
        try {
			input=new java.util.Scanner(inputFile);
			while(input.hasNext()){
				inputStr+=input.next();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		input.close();
		return inputStr;
	}
	
	public static int writeBytes(String file, byte[] bytes){
		java.io.ObjectOutputStream out;
		try {
			out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream(file));
			out.writeObject(bytes);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
		
		return 0;
	}
	
	public static byte[] readBytes(String file){
		byte[] r = null;
		java.io.ObjectInputStream in;
		try {
			in = new java.io.ObjectInputStream(
					new java.io.FileInputStream(file));
			r=(byte[]) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
}
