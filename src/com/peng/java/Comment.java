package com.peng.java;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.peng.config.Config;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.peng.util.HttpClientUtil;

/**
 * 获取评论和分词操作
 * @author MZhang
 * @since 2015-5-7
 *
 */
public class Comment {
	static Vector<String> html;
	private BufferedReader reader;
	public static void getAllComment(String itemId){
		//使用商品命名
		File file = new File(Config.FILEPATH+itemId+".txt");
		
		//获取好评 
		try {
			if (!file.exists()) {
					file.createNewFile();
				}else {
					file.delete();
					file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file,true);
			getComment(out,itemId, file, 1);
			out.write("stop".getBytes());
			out.write("\r\n".getBytes());
			getComment(out,itemId, file, 0);
			getComment(out,itemId, file, -1);
			out.close();
			System.out.println("success");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取评论
	public static void  getComment(FileOutputStream out,String itemId,File file,int type) throws IOException{
		int maxPage = 0;
		int currentPage = 0;
		boolean flag = false;
		String url = "http://rate.taobao.com/feedRateList.htm?_ksTS=1431442217626_1472&callback=jsonp_reviews_list&userNumId="+getUserId(itemId)+"&auctionNumId="+itemId+"&siteID=7&currentPageNum=1&rateType="+type+"&orderType=sort_weight&showContent=1&attribute=&ua=";
		for (int i = 1; i < 100; i++) {
		HttpResponse httpRespons = HttpClientUtil.sendGet(url);
		url = url.replace("currentPageNum="+i, "currentPageNum="+(i+1));
		//获取每一行的数据分析
			for(String key:httpRespons.getContentCollection().get(1).toString().split(",")){
				if ( ! flag) {
					if (key.contains("maxPage")) {
						maxPage = Integer.parseInt(key.split(":")[1]);
					}
					if (key.contains("currentPageNum")) {
						currentPage = Integer.parseInt(key.split(":")[1]);
					}	
					if (maxPage !=0 && maxPage == currentPage) {
						flag = true;
					}
				}
				if (key.contains("content")) {
					String cont = key.replace("\"content\":", "").replace("\"appendList\":[{", "").replace("\"append\":{", "").replace("\"reply\":{", "").replace("<br/>\\n", "");
					out.write(cont.getBytes());
					out.write("\r\n".getBytes());
				}
			}
			if (flag) {
				break;
			}		
		}
	}
	
	/**
	 * 获取用户Id
	 * @param itemId
	 * @return
	 */
	public  static String getUserId(String itemId){
		String userId = null;
		String url = "http://item.taobao.com/item.htm?spm=a219r.lm895.14.22.hQJzlA&id="+itemId+"&ns=1&abbucket=6";
		try {
			boolean flag = false;
			HttpResponse response = HttpClientUtil.sendGet(url);
			html = response.getContentCollection();
			Pattern pa = Pattern.compile("userid=[0-9]*");
			for(String line:html){
				Matcher ma = pa.matcher(line);
				while (ma.find()) {
			         userId = ma.group().replace("userid=", "");
			         flag = true;
			         break;
			     }
				if (flag) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	/**
	 * 对文件进行分词
	 * @param fileName
	 * @return
	 */
	public Map<Integer, Map<String, Integer>> splitFile(String fileName){
		//分词操作
		Map<Integer, Map<String, Integer>> splitCommentMap = new HashMap<Integer, Map<String,Integer>>();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			String line = null ;
			int i = 0;
			//对每一行进行分词 
			while ( (line = reader.readLine())!= null ){
				if (line.contains("stop")) {
					continue;
				}
				splitCommentMap.put(i, spiltString(line.toString()));
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return splitCommentMap;
		
	}
	
	/**
	 * 对字符串进行分词
	 * @param content
	 * @return
	 */
	public static Map<String, Integer> spiltString(String content){
		//分词操作
		Map<String, Integer> wordsFrenMaps = new HashMap<String, Integer>();
		try {
			IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(content), true);
	        Lexeme lexeme;
	        while ((lexeme = ikSegmenter.next()) != null) {
	            if(lexeme.getLexemeText().length()>1){
	                if(wordsFrenMaps.containsKey(lexeme.getLexemeText())){
	                	wordsFrenMaps.put(lexeme.getLexemeText(),wordsFrenMaps.get(lexeme.getLexemeText())+1);
	                }else {
	                	wordsFrenMaps.put(lexeme.getLexemeText(),1);
	                }
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wordsFrenMaps;
		
	}
	
	/**
	 * 进行词频统计排序
	 * @param wordsFrenMaps
	 * @return
	 */
	public static  List<Entry<String, Integer>> sortSegmentResult(Map<String,Integer> wordsFrenMaps){
      //这里将map.entrySet()转换成list
        List<Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(wordsFrenMaps.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
        });
		return list;
    }
	
}
