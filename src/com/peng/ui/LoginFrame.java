package com.peng.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.peng.java.Apriori;

public class LoginFrame extends JFrame implements ActionListener {
	
	JButton comment,buy;
	public static Map<Integer, String> map = new HashMap<Integer, String>();
	public LoginFrame(String s){
		super(s);
		map = Apriori.test();
		Container container = getContentPane();
		container.setLayout(new GridLayout(3, 1));
		JPanel bJPanel = new JPanel();
		bJPanel.setLayout(new BorderLayout());
		JLabel name = new JLabel("                        电商用户行为分析");
		name.setFont(new Font("Dialog", 1, 35));
		bJPanel.add(name,BorderLayout.CENTER);
		
		JPanel aJPanel = new JPanel();
		aJPanel.setLayout(null);
		comment = new JButton("评论行为");
		comment.setFont(new Font("Dialog", 0, 20));
		comment.setBounds (265, 30, 240, 40);
		aJPanel.add(comment);
		comment.addActionListener(this);
		
		JPanel cJPanel = new JPanel();
		cJPanel.setLayout(null);
		buy = new JButton("购买行为");
		buy.setFont(new Font("Dialog", 0, 20));
		buy.setBounds (265, 30, 240, 40);
		cJPanel.add(buy);
		buy.addActionListener(this);
		
		container.add(bJPanel);
		container.add(aJPanel);
		container.add(cJPanel);
	}
	
	public static void main(String[] args) {
		
		LoginFrame lf = new LoginFrame("电商用户行为分析");
		lf.setBounds(200, 100, 800, 600);
		lf.setVisible(true);
		lf.setResizable(false);
		lf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comment) {
			CommentFrame mf = new CommentFrame("电商用户行为分析");
			mf.setBounds(200, 100, 800, 600);
			mf.setVisible(true);
			mf.setResizable(false);
			mf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		if (e.getSource() == buy) {
			BuyFrame bf = new BuyFrame("购买行为分析");
			bf.setBounds(200, 100, 800, 600);
			bf.setVisible(true);
			bf.setResizable(false);
			bf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
}
