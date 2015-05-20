package com.zhangpeng.util;

import java.util.ArrayList;


/**
 * 全组合算法
 * @author zhangpeng
 * @date 2015-04-8 
 */
public class SortUtil {
	//将NUM设置为待排列数组的长度即实现全排列
	private static int NUM = 2;

	/**
	 * 递归算法：将数据分为两部分，递归将数据从左侧移右侧实现全排列
	 *
	 * @param datas
	 * @param target
	 */


	public static void main(String[] args) {
		ArrayList<String> groups = new ArrayList<String>();
		String str[] = { "A", "B", "C", "D", "E","F" };
		int nCnt = str.length;

		int nBit = (0xFFFFFFFF >>> (32 - nCnt));
		for (int i = 1; i <= nBit; i++) {
			StringBuffer item = new StringBuffer();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					item.append(str[j]+" ");
				}
			}
			if(item.toString().trim().split(" ").length == NUM){
				groups.add(item.toString());
			}
			
			for(String key:groups){
				System.out.println(key);
			}
		}
	}
}


