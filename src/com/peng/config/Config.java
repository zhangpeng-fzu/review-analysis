package com.peng.config;

public class Config {
	public final  int MIN_SUPPORT = 200;
	//文件基础路径
	public final static String BASEPATH = "./src";
	//生成的事物集合文件路径
	public final static String readFileName = "./lib/data.txt";
	//生成的事物集路径
	public final static String writeFileNameString = "./lib/events.txt";
	//获取到的评论文件位置（相对路径）
	public final static String FILEPATH = "./file/";
	//正向词汇文件路径
	public final static String POSITIVE_PATH = "./src/com/peng/config/positive.txt";
	//负向词汇文件路径
	public final static String NEGATIVE_PATH = "./src/com/peng/config/negative.txt";
	//特征词汇文件路径
	public final static String CHARACTERISTIC_PATH = "./src/com/peng/config/characteristic.txt";
	//观点词汇文件路径
	public final static String OPINION_PATH = "./src/com/peng/config/opinionWord.txt";
	//文件最小大小 至少有"stop"
	public final static int MIN_FILE_LENGTH = 6;
}
