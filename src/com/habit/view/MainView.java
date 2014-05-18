package com.habit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class MainView extends JFrame {

	private JComboBox yearCmb;
	private int realDay;
	private int realMonth;
	private int realYear;
	private int currentMonth;
	private int currentYear;

	public static void main(String[] args) {
		new MainView();
	}

	MainView() {
		setRealDate();
		initView();
	}

	private void setRealDate() {
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
		realMonth = cal.get(GregorianCalendar.MONTH);
		realYear = cal.get(GregorianCalendar.YEAR);
		currentMonth = realMonth;
		currentYear = realYear;
	}

	private JTable calendarTable() {
		DefaultTableModel defaultTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		JTable calendarTable = new JTable(defaultTable);

		// set header
		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

		for (String header : headers)
			defaultTable.addColumn(header);

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(50);
		defaultTable.setColumnCount(7);
		defaultTable.setRowCount(6);

		return calendarTable;
	}

	private void initView() {
		Container c = getContentPane();

		JPanel JPtopLeft = new JPanel();
		JPanel JPtopCenter = new JPanel();
		JPanel JPtopRight = new JPanel();
		JPanel JPBottom = new JPanel();

		yearCmb = new JComboBox();

		for (int i = realYear - 100; i <= realYear + 100; i++) {
			yearCmb.addItem(String.valueOf(i));
		}

		JPtopLeft.add(yearCmb);

		JLabel monthLabel = new JLabel("            May            ");
		monthLabel.setFont(new Font("", Font.BOLD + Font.ITALIC, 20));

		JButton monthLeftBtn = new JButton("<");
		monthLeftBtn.setPreferredSize(new Dimension(60, 20));

		JButton monthRightBtn = new JButton(">");
		monthRightBtn.setPreferredSize(new Dimension(60, 20));

		JPtopCenter.add(monthLeftBtn);
		JPtopCenter.add(monthLabel);
		JPtopCenter.add(monthRightBtn);

		JButton myHabitBtn = new JButton("My Habbit");
		myHabitBtn.addActionListener(new myHabitBtnAction());
		JPtopRight.add(myHabitBtn);

		JPBottom.add(calendarTable());

		c.add(JPtopLeft, BorderLayout.WEST);
		c.add(JPtopCenter, BorderLayout.CENTER);
		c.add(JPtopRight, BorderLayout.EAST);
		c.add(JPBottom, BorderLayout.SOUTH);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scSize = tk.getScreenSize();
		setBounds(scSize.width / 2 - 395, scSize.height / 2 - 320, 900, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setBackground(Color.white);
	}
	
	

	class myHabitBtnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame myHabitView = new MyHabitView();
		}
	}

}
