package com.peng.ui;

import com.peng.config.Config;
import com.peng.service.Apriori;
import com.peng.util.ReadFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyFrame extends JFrame implements ActionListener{
	String filePath = null;
	static int size= 0;
	JButton open,deal,save,getK;
	JTextField path,cond,sup,minSup,k;
	JTextArea itemsArea,Kitems;
	public static Map<Integer, String> map = new HashMap<Integer, String>();
	public BuyFrame(String name){
		super(name);
//		map = LoginFrame.map;
		Container container = getContentPane();
		JPanel dealData = new JPanel();
		dealData.setLayout(null);
		JLabel address = new JLabel("预处理文件路径");
		address.setFont(new Font("黑体", Font.PLAIN, 15));
		address.setBounds(new Rectangle(20,20,120,30));
		dealData.add(address);
		path = new JTextField();
		path.setBounds(new Rectangle(140,20,300,28));
		dealData.add(path);
		deal = new JButton("预处理数据");
		deal.setBounds(new Rectangle(40,60,100,30));
		dealData.add(deal);
		deal.addActionListener(this);
		
		open=new JButton("选择文件");
		open.setBounds(new Rectangle(480,20,100,28));
		dealData.add(open);
		open.addActionListener(this);
		
		JPanel temp = new JPanel();
		itemsArea = new JTextArea(23,58);
		temp.add(new JScrollPane(itemsArea));
		temp.setBounds(0,100,700,400);
		dealData.add(temp);
		
		save = new JButton("保存txt文件");
		save.setBounds(new Rectangle(40,490,160,30));
		dealData.add(save);
		save.addActionListener(this);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		JLabel confidenceJLabel = new JLabel("置信度");
		confidenceJLabel.setFont(new Font("黑体", Font.PLAIN, 15));
		confidenceJLabel.setBounds(new Rectangle(20,20,60,20));
		mainPanel.add(confidenceJLabel);
		cond = new JTextField("0.5");
		cond.setBounds(new Rectangle(70,20,60,20));
		mainPanel.add(cond);
		
		JLabel supJLabel = new JLabel("支持度");
		supJLabel.setFont(new Font("黑体", Font.PLAIN, 15));
		supJLabel.setBounds(new Rectangle(160,20,80,20));
		mainPanel.add(supJLabel);
		sup = new JTextField("0.6");
		sup.setBounds(new Rectangle(210,20,60,20));
		mainPanel.add(sup);
		
//		JLabel setsupJLabel = new JLabel("设置最小支持度");
//		setsupJLabel.setFont(new Font("黑体", Font.PLAIN, 15));
//		setsupJLabel.setBounds(new Rectangle(300,20,160,20));
//		mainPanel.add(setsupJLabel);
//		minSup = new JTextField("157");
//		minSup.setBounds(new Rectangle(410,20,60,20));
//		mainPanel.add(minSup);
		
		JLabel getKLabel = new JLabel("获取K频繁项集：k = ");
		getKLabel.setFont(new Font("黑体", Font.PLAIN, 15));
		getKLabel.setBounds(new Rectangle(20,70,160,30));
		mainPanel.add(getKLabel);
		k = new JTextField("3");
		k.setBounds(new Rectangle(170,70,60,30));
		mainPanel.add(k);
		
		getK = new JButton("获取");
		getK.setBounds(new Rectangle(260,70,80,30));
		mainPanel.add(getK);
		getK.addActionListener(this);
		
		JLabel adivice = new JLabel("（k = 1.2.3... k = 0时获取最终频繁项集） ");
		adivice.setFont(new Font("黑体", Font.PLAIN, 15));
		adivice.setBounds(new Rectangle(350,70,360,30));
		mainPanel.add(adivice);
		
		JPanel temp1 = new JPanel();
		Kitems = new JTextArea(23,58);
		temp1.add(new JScrollPane(Kitems));
		temp1.setBounds(0,110,700,400);
		mainPanel.add(temp1);
		
		
		JTabbedPane  jp=new JTabbedPane(JTabbedPane.TOP) ;
		jp.add(dealData,"评论获取");
		jp.add(mainPanel,"Apriori算法分析");
		container.add(jp);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {
			// TODO Auto-generated method stub
			JFileChooser jfc=new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
			jfc.showDialog(new JLabel(), "选择");
			File file=jfc.getSelectedFile();
			path.setText(file.getAbsolutePath());
		}
		if (e.getSource() == deal) {
			if (path.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请先选取文件", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List<List<String>> list = new ReadFile().getDatabase(path.getText().trim());
			size = list.size();
			int i = 0;
			for(List<String> keyList:list){
				StringBuffer buffer = new StringBuffer();
				for(String key:keyList){
					buffer.append(key+"         ");
				}
				i++;
				itemsArea.append(i+"    :   "+buffer.toString()+"\n");
			}
		}
		if (e.getSource() == save) {
			if (itemsArea.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请先获取对数据进行预处理", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else {
				JOptionPane.showMessageDialog(null, "文件保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				filePath = Config.writeFileNameString;
			}
		}
		
		if (e.getSource() == getK) {
			if (sup.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请先设定支持度", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
			Apriori.setminSupport((int) (size * Double.parseDouble(sup.getText())));
			int key = Integer.parseInt(k.getText());
			map = Apriori.test();
			if (key == 0 || key > map.size()) {
				key = map.size();
			}
			Kitems.append(map.get(key));
			Kitems.append("\r\n");
		}
	}
}
