package com.peng.ui;

import com.peng.config.Config;
import com.peng.service.Characteristic;
import com.peng.service.Comment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//主要展示界面
public class CommentFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	static Map<Integer, Map<String, Integer>> map = null;
	static Map<Integer,String> comment = new HashMap<Integer, String>();
	static String  fileName = null;
	JTextArea showComment,showWord,goodArea,badArea,getgoodArea,getbadArea;
	JButton get,split,pickFrenqWord,pickopinionword,num,getclass,starimage,typeimage;
	JTextField id;
	JTextField starField[],typeField[];
	public CommentFrame(String name){
		super(name);
		Characteristic.loadedVocabulary();
		Container c= getContentPane();
		
		JPanel getPanel = new JPanel();
		JLabel ItemId = new JLabel("商品ID");
		id = new JTextField("19260270260",10);
		get = new JButton("获取评论");
		getPanel.setLayout(null);
		ItemId.setBounds(new Rectangle(30, 10, 40, 25));
		getPanel.add(ItemId);
		id.setBounds(new Rectangle(80,10,90,25));
		getPanel.add(id);
		get.setBounds(new Rectangle(180,10,90,25));
		getPanel.add(get);
		get.addActionListener(this);
		JLabel getgoodJLabel = new JLabel("好评");
		getgoodJLabel.setBounds(new Rectangle(30, 40, 50, 40));
		getPanel.add(getgoodJLabel);
		JPanel getgoodJPanel = new JPanel();
		getgoodArea = new JTextArea(10,60);
		getgoodJPanel.setBounds(new Rectangle(30, 80,700,200));
		getgoodJPanel.add(new JScrollPane(getgoodArea));
		getPanel.add(getgoodJPanel);
		JLabel getbadJLabel = new JLabel("差评");
		getbadJLabel.setBounds(new Rectangle(30, 280, 50, 40));
		getPanel.add(getbadJLabel);
		JPanel getbadJPanel = new JPanel();
		getbadArea = new JTextArea(10,60);
		getbadJPanel.setBounds(new Rectangle(new Rectangle(30,320,700,200)));
		getbadJPanel.add(new JScrollPane(getbadArea));
		getPanel.add(getbadJPanel);
			
		
	    JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,15));
	
		
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
		
		JPanel commentJPanel = new JPanel();
		commentJPanel.setLayout(null);
		pickFrenqWord = new JButton("提取高频特征词");
		pickFrenqWord.setBounds(new Rectangle(30,30,150,30));
		commentJPanel.add(pickFrenqWord);
		pickFrenqWord.addActionListener(this);
		
		pickopinionword = new JButton("提取观点词词");
		pickopinionword.setBounds(new Rectangle(300,30,150,30));
		commentJPanel.add(pickopinionword);
		pickopinionword.addActionListener(this);
		num = new JButton("分析评论");
		num.setBounds(30,100,150,30);
		num.addActionListener(this);
		commentJPanel.add(num);
		
		JLabel starnum = new JLabel("评论星级分布");
		starnum.setFont(new Font("黑体", Font.PLAIN, 15));
		starnum.setBounds(new Rectangle(30,160,150,30));
		commentJPanel.add(starnum);
		JPanel star = new JPanel();
		star.setLayout(new FlowLayout(FlowLayout.LEFT));
		starField = new JTextField[5];
		for (int i = 0; i < 5; i++) {
			star.add(new JLabel((i+1)+"颗星"));
			starField[i] = new JTextField(5);
			star.add(starField[i]);
			star.add(new JLabel("        "));
		}
		star.setBounds(new Rectangle(30,190,700,60));
		commentJPanel.add(star);
		
		JLabel typenum = new JLabel("评论极性分布");
		typenum.setFont(new Font("黑体", Font.PLAIN, 15));
		typenum.setBounds(new Rectangle(30,260,150,30));
		commentJPanel.add(typenum);
		JPanel type = new JPanel();
		typeField= new JTextField[3];
		String typename[] = {"好评","中评","差评"};
		type.setLayout(new FlowLayout(FlowLayout.LEFT));
		for (int i = 0; i < 3; i++) {
			type.add(new JLabel(typename[i]));
			typeField[i] = new JTextField(5);
			type.add(typeField[i]);
			type.add(new JLabel("        "));
		}
		type.setBounds(new Rectangle(30,290,700,60));
		commentJPanel.add(type);
		starimage = new JButton("评论星级分布图");
		starimage.setBounds(new Rectangle(30,380,150,30));
		starimage.addActionListener(this);
		commentJPanel.add(starimage);
		
		typeimage = new JButton("评论极性分布图");
		typeimage.setBounds(new Rectangle(300,380,150,30));
		typeimage.addActionListener(this);
		commentJPanel.add(typeimage);
		
		JPanel resultpJPanel = new JPanel();
		resultpJPanel.setLayout(null);
		getclass = new JButton("评论分类");
		getclass.setBounds(new Rectangle(60, 10, 120, 25));
		resultpJPanel.add(getclass);
		getclass.addActionListener(this);
		JLabel goodJLabel = new JLabel("好评");
		goodJLabel.setBounds(new Rectangle(30, 40, 50, 40));
		resultpJPanel.add(goodJLabel);
		JPanel goodJPanel = new JPanel();
		goodArea = new JTextArea(10,60);
		goodJPanel.setBounds(new Rectangle(30, 80,700,200));
		goodJPanel.add(new JScrollPane(goodArea));
		resultpJPanel.add(goodJPanel);
		
		JLabel badJLabel = new JLabel("差评");
		badJLabel.setBounds(new Rectangle(30, 280, 50, 40));
		resultpJPanel.add(badJLabel);
		JPanel badJPanel = new JPanel();
		badArea = new JTextArea(10,60);
		badJPanel.setBounds(new Rectangle(new Rectangle(30,320,700,200)));
		badJPanel.add(new JScrollPane(badArea));
		resultpJPanel.add(badJPanel);
		

		JTabbedPane  jp=new JTabbedPane(JTabbedPane.TOP) ;
		jp.add(getPanel,"评论获取");
		jp.add(mainPanel,"评论分词");
		jp.add(commentJPanel,"评论统计");
		jp.add(resultpJPanel,"分析结果");
		c.add(jp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BufferedReader reader;
		Boolean flag = false;
		if (e.getSource() == get) {
		    if (id.getText().isEmpty()) {
		    	JOptionPane.showMessageDialog(null, "请先输入商品ID", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			fileName = Config.FILEPATH +id.getText()+".txt";
			File file = new File(fileName);
			//如果文件不存在或者存在但是没记录 就去淘宝抓取
			if ( ! file.exists() || (file.exists() && file.length() < Config.MIN_FILE_LENGTH)) {
				Comment.getComment(id.getText(), 1, Comment.getUserId(id.getText()));
			}

			try {
				int i = 0;
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line = null ;
				while ( (line = reader.readLine())!=null){
					if (line.contains("stop")) {
						if (i == 0){
							JOptionPane.showMessageDialog(null, "该商品没有评论", "提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						flag = true;
						continue;
					}
					if ( ! flag) {
						getgoodArea.append(i+":"+line+"\n");
					}else {
						getbadArea.append(i+":"+line+"\n");
					}
					showComment.append(i+":"+line+"\n");
					comment.put(i,line);
					i++;
				}
				reader.close();
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
			map = new Comment().splitFile(fileName);
			for(Integer keyMap:map.keySet()){
				showWord.append(keyMap+":");
				for(String key:map.get(keyMap).keySet()){
					showWord.append(key+"  ");
				}
				showWord.append("\n");
			}
		}
		
		if (e.getSource() == num) {
			if (map == null) {
				JOptionPane.showMessageDialog(null, "请先对评论进行分词操作", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//统计好评 中评 差评数量
			Map<String, Map<String, Integer>> commmetMap = Characteristic.saveDicides(map) ;
			if (commmetMap.get("final") != null) {
				typeField[0].setText(String.valueOf(commmetMap.get("final").get("good")));
				typeField[1].setText(String.valueOf(commmetMap.get("final").get("normal")));
				typeField[2].setText(String.valueOf(commmetMap.get("final").get("bad")));
			}
			for(int key:Characteristic.gradeMap.keySet())
				starField[key-1].setText(String.valueOf(Characteristic.gradeMap.get(key)));
		}
		
		if (e.getSource() == getclass) {
			if (comment.size() == 0) {
				JOptionPane.showMessageDialog(null, "请先分析评论", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (Characteristic.good.size() != 0) {
				for(String i:Characteristic.good){
					String[] strings = i.split("_");
					goodArea.append(strings[0]+":"+comment.get(Integer.parseInt(strings[0]))+strings[1]+"\n");
				}
			}
			if (Characteristic.bad.size() != 0) {
				for(String i:Characteristic.bad){
					String[] strings = i.split("_");
					badArea.append(strings[0]+":"+comment.get(Integer.parseInt(strings[0]))+strings[1]+"\n");
				}
			}
		}
		if (e.getSource() == starimage) {
			if (starField[0].getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请先分析评论", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List<String> dataList = new ArrayList<String>();
			dataList.add("一星_"+starField[0].getText());
			dataList.add("二星_"+starField[1].getText());
			dataList.add("三星_"+starField[2].getText());
			dataList.add("四星_"+starField[3].getText());
			dataList.add("五星_"+starField[4].getText());
			CountFrame.print(2, dataList);
			
		}
		
		if (e.getSource() == typeimage) {
			if (typeField[0].getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请先分析评论", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List<String> dataList = new ArrayList<String>();
			dataList.add("好评_"+typeField[0].getText());
			dataList.add("中评_"+typeField[1].getText());
			dataList.add("差评_"+typeField[2].getText());
			CountFrame.print(3, dataList);
		}
		
		if (e.getSource() == pickFrenqWord) {
			List<String>  freqword = new ArrayList<String>();
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
				List<Entry<String, Integer>> list = Comment.sortSegmentResult(Comment.spiltString(buffer.toString()));
				for(Entry<String,Integer> mapping:list){
					if (Characteristic.characteristic.contains(mapping.getKey())) {
						freqword.add(mapping.getKey()+"_"+mapping.getValue());
					}
				}
				reader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			CountFrame.print(0,freqword);
		}

		if (e.getSource() == pickopinionword) {
			List<String> opinionword  = new ArrayList<String>();
			opinionword = new ArrayList<String>();
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
				List<Entry<String, Integer>> list = Comment.sortSegmentResult(Comment.spiltString(buffer.toString()));
				for(Entry<String,Integer> mapping:list){
					if (Characteristic.opinion.contains(mapping.getKey())) {
						opinionword.add(mapping.getKey()+"_"+mapping.getValue());
					}
				}
				reader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			CountFrame.print(1,opinionword);
		}
	}
}
