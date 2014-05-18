package com.habit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.habit.model.Habit;

public class MyHabitView extends JFrame {

	ArrayList<Habit> habits = new ArrayList<Habit>();
	private JPanel jPTop;
	private JLabel titleLabel;
	private JButton addBtn;
	private JPanel jPCenter;
	private Container c;
	private JScrollPane scrollbar;

	MyHabitView() {
		setData();
		initView();
	}

	private void setData() {
		habits.add(new Habit("�����ϱ�"));
		habits.add(new Habit("��ħ�� 6�� �Ͼ��"));
		habits.add(new Habit("���ῡ ���� �ڱ�"));
		habits.add(new Habit("�ｺ�� ����"));
		habits.add(new Habit("�ϱ� ����"));
		habits.add(new Habit("�����ϱ�"));
	}

	private JPanel topPanel() {
		jPTop = new JPanel();

		titleLabel = new JLabel();
		titleLabel.setText("My Habit");

		addBtn = new JButton();
		addBtn.setText("Add");
		addBtn.addActionListener(new addBtnAction());

		jPTop.add(titleLabel);
		jPTop.add(addBtn);

		return jPTop;
	}

	private JPanel centerPanel() {
		jPCenter = new JPanel();
		GridLayout grid = new GridLayout(0, 1);

		jPCenter.setLayout(grid);

		for (Habit habit : habits) {
			jPCenter.add(itemPanel(habit.getName()));
		}

		return jPCenter;
	}
	
	private JPanel itemPanel(String name){
		JPanel itemPanel = new JPanel();

		JLabel habitText = new JLabel();
		habitText.setText(name);
		
		JButton editBtn = new JButton();
		editBtn.setText("Edit");
		editBtn.addActionListener(new editBtnAction(name));

		JButton deleteBtn = new JButton();
		deleteBtn.setText("Delete");

		itemPanel.add(habitText);
		itemPanel.add(editBtn);
		itemPanel.add(deleteBtn);
		
		return itemPanel;
	}
	
	class addBtnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog(getContentPane(), "���� �߰��� ������ �����ΰ���?",null);
			
			if(name != null){
				habits.add(new Habit(name));
				////// db insert
			}else{
				JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּž� �մϴ�.");
			}
		}
	}
	
	class editBtnAction implements ActionListener{
		String name;
		
		editBtnAction(String name){
			this.name = name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String editname = JOptionPane.showInputDialog(getContentPane(), "������ �̸��� �ٲ��ּ���.",name);
			///// db update
		}
	}
	
	class deleteBtnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			///// db delete
		}
	}

	private void initView() {
		c = getContentPane();

		scrollbar = new JScrollPane();
		scrollbar.setViewportView(centerPanel());

		c.add(topPanel(), BorderLayout.NORTH);
		c.add(scrollbar, BorderLayout.CENTER);		

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scSize = tk.getScreenSize();
		setBounds(scSize.width / 2 - 295, scSize.height / 2 - 220, 300, 800);
		
		setVisible(true);
		setBackground(Color.white);

	}
}
