package com.habit.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.habit.model.Habit;
import com.habit.model.HabitDAO;

public class MyHabitView extends JFrame {
	List<Habit> habits = new ArrayList<Habit>();
	private JPanel jPTop;
	private JLabel titleLabel;
	private JButton addBtn;
	private JPanel jPCenter;
	private Container c;
	private JScrollPane scrollbar;
	HabitDAO habitDAO = new HabitDAO();
	
	public void startView(){
		getContentPane().removeAll();
		setData();
		initView();
	}

	private void setData() {
		habits = habitDAO.findAllHabits();
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
			jPCenter.add(itemPanel(habit));
		}

		return jPCenter;
	}

	private JPanel itemPanel(Habit habit) {
		JPanel itemPanel = new JPanel();

		JLabel habitText = new JLabel();
		habitText.setText(habit.getName());

		JButton editBtn = new JButton();
		editBtn.setText("Edit");
		editBtn.addActionListener(new editBtnAction(habit));

		JButton deleteBtn = new JButton();
		deleteBtn.setText("Delete");
		deleteBtn.addActionListener(new deleteBtnAction(habit));

		itemPanel.add(habitText);
		itemPanel.add(editBtn);
		itemPanel.add(deleteBtn);

		return itemPanel;
	}

	class addBtnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog(getContentPane(),
					"새로 추가할 습관은 무엇인가요?", null);

			if (name != null) {
				habitDAO.insertHabit(name);
			} else {
				JOptionPane.showMessageDialog(null, "문자를 입력해주셔야 합니다.");
			}
			startView();
		}
	}

	class editBtnAction implements ActionListener {
		Habit habit;

		editBtnAction(Habit habit) {
			this.habit = habit;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String editname = JOptionPane.showInputDialog(getContentPane(),
					"습관의 이름을 바꿔주세요.", habit.getName());
			habitDAO.updateHabit(habit.getId(), editname);
			startView();
		}
	}

	class deleteBtnAction implements ActionListener {
		Habit habit;

		deleteBtnAction(Habit habit) {
			this.habit = habit;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			habitDAO.deleteHabit(habit.getId());
			startView();
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
		setBounds(scSize.width / 2 - 295, scSize.height / 2 - 220, 300, 300);

		setVisible(true);
		// setBackground(Color.white);

	}
}
