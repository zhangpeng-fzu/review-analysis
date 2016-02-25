package com.peng.service;

import com.peng.config.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Characteristic {
	public static List<String> positive = new ArrayList<String>();  //正面词
	public static List<String> negative = new ArrayList<String>();  //负面词
	public static List<String> characteristic = new ArrayList<String>();  //正面词
	public static List<String> opinion = new ArrayList<String>();  //负面词
	public static List<String> good = new ArrayList<String>();
	public static List<String> bad = new ArrayList<String>();
	public static Map<Integer, Integer> gradeMap = new HashMap<Integer, Integer>(){
		{put(1,0);put(2,0);put(3,0);put(4,0);put(5,0);}};

	/**
	 * 加载正面词 负面词 特征词 观点词
	 */
	public static void loadedVocabulary(){
		if ( ! loadWord(Config.POSITIVE_PATH,positive)){
			System.out.println("加载正面词失败");
			return;
		}
		if ( ! loadWord(Config.NEGATIVE_PATH,negative)){
			System.out.println("加载负面词失败");
			return;
		}
		if ( ! loadWord(Config.CHARACTERISTIC_PATH,characteristic)){
			System.out.println("加载特征词失败");
			return;
		}
		if ( ! loadWord(Config.OPINION_PATH, opinion)){
			System.out.println("加载观点词失败");
			return;
		}
		System.out.println("加载所有词汇完成");
	}

	private static boolean loadWord(String path,List<String> word){
		BufferedReader reader = null;
		try {
			File file = new File(path);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while((line = reader.readLine())!= null){
				word.add(line.trim());
			}
			reader.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Map<String, Integer> decide(int start,int end,Map<Integer,Map<String, Integer>> commentMap){
		Map<String, Integer> decide = new HashMap<String, Integer>(){
			{put("good",0);put("normal",0);put("bad",0);}};
			for (int i = start; i <= end; i++) {
				if(commentMap != null){
					Map<String, Integer> map = commentMap.get(i);
					int grade = 0;
					if (map != null) {
						for(String key:map.keySet()){
							if (positive.contains(key)) {
								grade += map.get(key);
							}else if (negative.contains(key)) {
								grade -= map.get(key);
							} 
						}
						if (grade > 3) {
							decide.put("good", decide.get("good")+1);
							good.add(i+"_"+grade);
							gradeMap.put(5, gradeMap.get(5)+1);
						}else if (grade > 0 && grade <=3) {
							decide.put("good", decide.get("good")+1);
							good.add(i+"_"+grade);
							gradeMap.put(4, gradeMap.get(4)+1);
						}else if (grade == 0) {
							decide.put("normal", decide.get("normal")+1);
							gradeMap.put(3, gradeMap.get(3)+1);
						}else if (grade < 0 && grade >= -3) {
							decide.put("bad", decide.get("bad")+1);
							bad.add(i+"_"+grade);
							gradeMap.put(2, gradeMap.get(2)+1);
						}else {
							decide.put("bad", decide.get("bad")+1);
							bad.add(i+"_"+grade);
							gradeMap.put(1, gradeMap.get(1)+1);
						}
					}
				}
			}
		return decide;
	}
	
	public static Map<String,Map<String, Integer>> saveDicides(Map<Integer,Map<String, Integer>> commentMap) {
		Map<String,Map<String, Integer>> decides = new HashMap<String,Map<String, Integer>>();
		Map<String, Integer> decide1 = decide(1, commentMap.size(), commentMap);

		decides.put("final", decide1);
		
		return decides;
		
	}
}
