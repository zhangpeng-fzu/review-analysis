package com.peng.config;

public class Config {
	public final  int MIN_SUPPORT = 200;
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

	//目前cookie无法做到自动更新
	public final static String COOKIE = "cna=N0pLD4yyHwICAW5XPGHJWJA7; thw=cn; uc2=wuf=http%3A%2F%2Fpub.alimama.com%2F; uc3=nk2=pIEpLGV1UPakf0g9eha8wf4%3D&id2=UoCLH%2BAF%2B4O37A%3D%3D&vt3=F8dAScn%2FSIr0W0cur0k%3D&lg2=W5iHLLyFOGW7aA%3D%3D; lgc=%5Cu9752%5Cu6625%5Cu4E36%5Cu4F9D%5Cu65E7%5Cu5815%5Cu843D520; tracknick=%5Cu9752%5Cu6625%5Cu4E36%5Cu4F9D%5Cu65E7%5Cu5815%5Cu843D520; _cc_=VT5L2FSpdA%3D%3D; tg=0; mt=ci=0_1; v=0; cookie2=1ea456372e67107e240a3105e5f68201; t=62e8ea10c0a74de94ab59a9f103726d7; uc1=cookie14=UoWyiPlEX0fxUg%3D%3D; _tb_token_=e573baa3b70a8; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; l=AmpqwhL03nJ4aXFpWruQY8WXOtoMz-41";
}
