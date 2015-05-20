package com.zhangpeng.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.util.StringUtils;

import com.zhangpeng.java.Characteristic;
import com.zhangpeng.java.Comment;

//主要展示界面
public class MainFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	static String  fileName = null;
	JTextArea showComment,showWord,countView;
	JButton get,split,pick,key,keyword;
	JTextField id;
	
	public MainFrame(String name){
		super(name);
		Container c=getContentPane();
		
		JLabel ItemId = new JLabel("商品ID");
		id = new JTextField("42800618881",10);
		
	    get = new JButton("获取评论");
	    JPanel panel = new JPanel();
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,15));
		panel.add(ItemId);
		panel.add(id);
		panel.add(get);
		get.addActionListener(this);
		
		JPanel showPanel = new JPanel();
		showPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		showComment = new JTextArea(25,31);
		showWord = new JTextArea(25,30);		
		JPanel splitPanel = new JPanel();
		split = new JButton("分词");
		splitPanel.add(split);
		split.addActionListener(this);
		showPanel.add(new JScrollPane(showComment));
		showPanel.add(split);
		showPanel.add(new JScrollPane(showWord));
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(showPanel);
		mainPanel.add(panel,BorderLayout.NORTH);
		mainPanel.add(showPanel,BorderLayout.CENTER);
		
		JPanel countPanel = new JPanel();
		countPanel.setLayout(new BorderLayout());
		JPanel aJPanel = new JPanel();
		aJPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		pick = new JButton("特征和观点词提取");
		aJPanel.add(pick);
		pick.addActionListener(this);
		countPanel.add(aJPanel,BorderLayout.NORTH);
		countView = new JTextArea();
		countPanel.add(countView,BorderLayout.CENTER);
		JPanel bottonpanel = new JPanel();
		bottonpanel.setLayout(new FlowLayout());
		key = new JButton("产品特征统计图");
		keyword = new JButton("产品观点词统计图");
		key.addActionListener(this);
		keyword.addActionListener(this);
		bottonpanel.add(key);
		bottonpanel.add(keyword);
		bottonpanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));
		countPanel.add(bottonpanel,BorderLayout.SOUTH);
		
		JPanel creditPanel = new JPanel();
		
		
		JTabbedPane  jp=new JTabbedPane(JTabbedPane.TOP) ;
		jp.add(mainPanel,"评论提取");
		jp.add(countPanel,"评论统计");
		c.add(jp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BufferedReader reader;
		if (e.getSource() == get) {
		    if (id.getText().isEmpty()) {
		    	JOptionPane.showMessageDialog(null, "请先输入商品ID", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			fileName = "D:/comment/"+id.getText()+".txt";
			File file = new File(fileName);
			//如果文件不存在 去淘宝抓取
			if ( ! file.exists()) {
				Comment.getCommet(id.getText());
			}
			try {
				int i = 1;
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
				String line = null ;
				while ( (line = reader.readLine())!= null ){
					showComment.append(i+":"+line+"\n");
					i++;
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == split) {
			if (fileName == null) {
				JOptionPane.showMessageDialog(null, "请先获取评论", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int i = 1;
			Map<Integer, Map<String, Integer>> map = new Comment().splitFile(fileName);
			for(Integer keyMap:map.keySet()){
				showWord.append(i+":");
				Characteristic.decide(map.get(keyMap));
				for(String key:map.get(keyMap).keySet()){
					showWord.append(key+"  ");
				}
				showWord.append("\n");
				i++;
			}
			for(String key:Characteristic.decide.keySet()){
				System.out.println(key +"   "+ Characteristic.decide.get(key));
			}
		}
		
		if (e.getSource() == pick) {
			try {
				if (fileName == null) {
					JOptionPane.showMessageDialog(null, "请先获取评论", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
				StringBuffer buffer = new StringBuffer();
				String line = null ;
				while ( (line = reader.readLine())!= null ){
					buffer.append(line);
				}
				int i = 0;
				List<Entry<String, Integer>> list = Comment.sortSegmentResult(Comment.spiltString(buffer.toString()));
				for(Map.Entry<String,Integer> mapping:list){ 
					if (i !=0 && i % 5 == 0) {
						countView.append("\n");
					}
					i++;
					countView.append(mapping.getKey()+"     "+mapping.getValue()+"              ");
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		if (e.getSource() == key) {
			CountFrame.print(0);
		}
		
		if (e.getSource() == keyword) {
			CountFrame.print(1);
		}
	}
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame("DataMining");
		mf.setBounds(100, 100, 800, 600);
		mf.setVisible(true);
		mf.setResizable(false);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Characteristic.positive();
		Characteristic.negative();
	 
	}

}
