package com.peng.java;


import java.io.BufferedReader ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList ;
import java.util.List ;

import com.peng.config.Config;

public class ReadFile{
	public static BufferedReader reader;
	public static  FileOutputStream outputStream;
	static List<List<String>> dataList ;
	
	public static List<List<String>> getDatabase(String fileName){
		try{
			dataList = new ArrayList<List<String>>();
			File file = new File(fileName);
			outputStream = new FileOutputStream(Config.writeFileNameString);
			if (file.isFile() && file.exists()){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))) ;
				String line = null ;
				while ( (line = reader.readLine())!= null ){
					List<String> item = new ArrayList<String>();
				    String[] strToknizer = line.replaceAll("\\s{1,}", " ").split(" ") ;
				    for (int i = 0; i < strToknizer.length; i++) {
						if( ! strToknizer[i].isEmpty()){
							switch (strToknizer[i]) {
							case "天猫":
								item.add("A");
								break;
							case "seagate旗舰店":
								item.add("B");
								break;
							case "developed":
								item.add("C");
								break;
							case "short":
								item.add("D");
								break;
							case "famous":
								item.add("E");
								break;
							case "high":
								item.add("F");
								break;
							case "USB3.0":
								item.add("G");
								break;
							case "2.5英寸":
								item.add("H");
								break;
							case "无":
								item.add("I");
								break;
							case "average":
								item.add("J");
								break;
							default:
								break;
							}
						}
					}
				    for(String key:item){
						outputStream.write((key+" ").getBytes());
					}
				    outputStream.write("\r\n".getBytes());
				    dataList.add(item);
				}
				outputStream.close();
				reader.close();
			}else{
			    throw new FileNotFoundException();
		    }
		}catch (Exception e){
	        e.printStackTrace();
		}
        return dataList ;
    }
	
	
	public static void main(String[] args) {
		System.out.println(Apriori.test().get(3));
		ReadFile.getDatabase(Config.readFileName);
	}
}
