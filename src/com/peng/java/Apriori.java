package com.peng.java;
 

import java.util.List ;
import java.util.Map ;
import java.util.ArrayList ;
import java.util.HashMap;
import java.util.Set;


import com.peng.config.Config;
 
/**
 * 实现Aprior算法
 * @author MZhang
 * @since 2015-4-8
 */
public class Apriori {
	
    private static List<String> prunnedItemset = new ArrayList<String>();           //被剪枝的项目集
    private static int MIN_SUPPORT = 157;     //最小主持度

    public static void setminSupport(int minSupport){
		MIN_SUPPORT = minSupport;
	}

    //读取文件
    public static List<List<String>> getDataSet (){
        return new ReadFile().getDatabase(Config.readFileName);
    }

    //获取频繁1-项集
    public static Map<String, Integer> getFirstFrequent (List<List<String>> record){
        //候选1-项集
		Map<String, Integer> fisrtCItemSet =  new HashMap<String, Integer>() ;
		//频繁1-项集
        for(List<String> string:record){
			for(String key:string){
				if(fisrtCItemSet.get(key) == null){
                	fisrtCItemSet.put(key, 1);
                }else{
                	fisrtCItemSet.put(key, fisrtCItemSet.get(key)+1);
                }
            }
        }
        //项集剪枝
        return pruning(fisrtCItemSet) ;
    }


	/**
	 * @param k
	 * @param cK_1ItemSet
	 * @param record
	 * @return
	 */
    public static Map<String, Integer> getKCandidate (int k,Map<String, Integer> cK_1ItemSet,List<List<String>> record){
    	String exsitItem  = "";
    	Set<String> set = cK_1ItemSet.keySet(); 
    	Map<String, Integer> cKItemSet = new HashMap<>();
		for (String str : set) {
			for(String item:str.split(" ")){
				if( ! exsitItem.contains(item)){
					 exsitItem = exsitItem + item + " ";
				}
			}
		}
		
		//获取所有的可能出现的候选集
		String[] items = exsitItem.trim().split(" ");
		boolean needPruning = false;
		ArrayList<String> groups = new ArrayList<>();
		int nCnt = items.length;
		int nBit = (0xFFFFFFFF >>> (32 - nCnt));
		for (int i = 1; i <= nBit; i++) {
			StringBuffer item = new StringBuffer();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					item.append(items[j]+" ");
				}
			}
			//对生成的项集进行剪枝生成候选集
			//控制是否剪枝 比较性能
			if(item.toString().trim().split(" ").length == k && prunnedItemset.size() != 0){
				for(String key:prunnedItemset){
					needPruning = true;
					//TODO bug T10 包含T1 如果剪枝结合中有T1 则T10也会被剪掉
					//解决方案  生成事物集时用ABCD...代替T1 T2...
					for(String keyItem:key.split(" ")){
						if( ! item.toString().trim().contains(keyItem)){
							needPruning = false;
						}
					}
					//如果被剪枝的是该项目集的子集 就不加入到候选集中
					if(needPruning){
						break;
					}
				}
				//不需要被剪枝  加入候选集计算支持度
				if( ! needPruning){
					groups.add(item.toString());
				}else{
					//剪枝 加入到剪枝的集合中
					prunnedItemset.add(item.toString());
				}
			}
		}
		
		//计算候选集支持度
		for (int i = 0; i < record.size(); i++) {
			String itemSet = "";
			//遍历事物集 生成字符串进行操作
			for (int j = 0; j < record.get(i).size(); j++) {
				itemSet += record.get(i).get(j);
			}
			//对每一个候选集计算支持度
			for (int m = 0; m < groups.size(); m++) {
				//判断候选集是否已经出现过
				boolean flag = true;
				for(String key:groups.get(m).split(" ")){
					if( ! itemSet.contains(key)){
						flag = false;
					}
				}
				if(flag){
					//如果不存在 加入 支持度为1  存在支持度加1
					if(cKItemSet.get(groups.get(m)) == null){
						cKItemSet.put(groups.get(m),1);
					}else{
						cKItemSet.put(groups.get(m),cKItemSet.get(groups.get(m))+1);
					}
				}
			}
		}
		return cKItemSet;
    }

	/**
	 * 获取K-频繁项集
	 * @param k
	 * @param cK_1ItemSet
	 * @param record
	 * @return
	 */
    public static Map<String,Integer>getKFrequent (int k,Map<String,Integer>cK_1ItemSet,List<List<String>> record){
		//频繁k-项集
		return pruning(getKCandidate(k, cK_1ItemSet,record));
    }
    
    /**
     * 剪枝 把支持度小于最小支持度的候选项目集去掉
     * @param cKItemSet Map<String,Integer> 候选项集
     * @return Map<String,Integer> 频繁k-项集
     */
    public static  Map<String, Integer> pruning(Map<String, Integer> cKItemSet) {
    	Map<String, Integer> fKItemSet = new HashMap<String, Integer>();
    	//遍历map集合 把支持度小于最小支持度的项目集去掉
    	Set<String> ItemSet = cKItemSet.keySet();
		for (String key :ItemSet) {
			if(cKItemSet.get(key) >= MIN_SUPPORT){
				fKItemSet.put(key, cKItemSet.get(key));
			}else{
				prunnedItemset.add(key);
			}
		}
		return fKItemSet;
	}
    
    public static Map<Integer, String> test(){
    	List<List<String>> record = getDataSet();    //获取数据库记录 生成事物集
    	Map<String,Integer> fk_1Itemset;   //k-1频繁项集
        Map<Integer,Map<String,Integer>> fKItemMap = new HashMap<>();  //k频繁项集
        int k = 2;
        Map<String, Integer> fkItemset = getFirstFrequent(record);
        while(fkItemset.size() > 0){
        	 fk_1Itemset = fkItemset;
        	 //存储生成的频繁项集
        	 fKItemMap.put(k-1, fk_1Itemset);
        	 fkItemset = getKFrequent(k, fk_1Itemset, record);
        	 //计算生成项集的时间
        	 k++;
        }
        Map<Integer,String> map = new HashMap<Integer, String>();
        for(int key:fKItemMap.keySet()){
        	StringBuffer buffer = new StringBuffer();
        	Set<String> s = fKItemMap.get(key).keySet();//获取KEY集合
        	for (String str : s) {
    	 		buffer.append(str+"        "+fKItemMap.get(key).get(str)+"\r\n");
    		}
        	map.put(key, buffer.toString());
        }
        return map;
    }

    public static void main(String[] args) {

		System.out.println(Apriori.test().get(2));
	}

}
