package com.peng.ui;


import com.peng.util.BarChart;
import com.peng.util.CartogramUtil;

import javax.swing.*;
import java.util.List;


public class CountFrame {
	public static void print(int type,List<String> data){  
	    JFrame frame=new JFrame("Java数据统计图");  
	    switch (type) {
		case 0:
			frame.add(new CartogramUtil(type,data).getChartPanel());
			break;
		case 1:
			frame.add(new CartogramUtil(type,data).getChartPanel());
			break;
		case 2:
			frame.add(new BarChart(type,data).getChartPanel());
			break;
		case 3:
			frame.add(new BarChart(type,data).getChartPanel());
			break;
		default:
			break;
		}
	    frame.setBounds(50, 50, 800, 600);  
	    frame.setVisible(true); 
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}  
}
