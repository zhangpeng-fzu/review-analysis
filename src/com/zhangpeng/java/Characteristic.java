package com.zhangpeng.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.zhangpeng.config.Config;

public class Characteristic {
	public static List<String> positive = new ArrayList<String>();  //正面词
	public static List<String> negative = new ArrayList<String>();  //负面词
	
	public static Map<String, Integer> decide = new HashMap<String, Integer>(){
		{put("good",0);put("normal",0);put("bad",0);}};
	
	public static List<String> positive(){
		String fileName = Config.BASEPATH +"/com/zhangpeng/config/正面词.txt";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			String line = null;
			while((line = reader.readLine())!= null){
				positive.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return positive;
		
	}
	
	public static List<String> negative(){
		String fileName = Config.BASEPATH +"/com/zhangpeng/config/负面词.txt";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			String line = null;
			while((line = reader.readLine())!= null){
				negative.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return negative;
		
	}
	
	public static Map<String, Integer> decide(Map<String , Integer> map){
		
		int grade = 0;
		for(String key:map.keySet()){
			if (positive.contains(key)) {
				grade += map.get(key);
			}else if (negative.contains(key)) {
				grade -= map.get(key);
			} 
		}
		if (grade > 0) {
			decide.put("good", decide.get("good")+1);
		}else if (grade == 0) {
			decide.put("normal", decide.get("normal")+1);
		}else {
			decide.put("bad", decide.get("bad")+1);
		}
		return decide;
	}
	public static void main(String[] args) {
		
	}
}
