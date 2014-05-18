package com.habit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MainView extends JFrame {

	private JComboBox yearCmb;
	private int realDay;
	private int realMonth;
	private int realYear;
	private int currentMonth;
	private int currentYear;
	private JButton monthLeftBtn;
	private JButton monthRightBtn;
	private JLabel monthLabel;
	private DefaultTableModel defaultTable;
	private JTable calendarTable;
	String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	String[] months = { "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" };

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

	private JPanel TopController() {
		JPanel JPtop = new JPanel();
		GridLayout topGrid = new GridLayout(0, 3);

		JPtop.setLayout(topGrid);

		JPanel JPtopLeft = new JPanel();
		JPanel JPtopCenter = new JPanel();
		JPanel JPtopRight = new JPanel();

		yearCmb = new JComboBox();

		for (int i = realYear - 100; i <= realYear + 100; i++)
			yearCmb.addItem(String.valueOf(i));

		JPtopLeft.add(yearCmb);

		monthLabel = new JLabel("May");
		monthLabel.setFont(new Font("", Font.BOLD + Font.ITALIC, 20));

		monthLeftBtn = new JButton("<");
		monthLeftBtn.setPreferredSize(new Dimension(60, 20));

		monthRightBtn = new JButton(">");
		monthRightBtn.setPreferredSize(new Dimension(60, 20));

		yearCmb.addActionListener(new yearCmbAction());
		monthLeftBtn.addActionListener(new monthLeftBtnAction());
		monthRightBtn.addActionListener(new monthRightBtnAction());

		JPtopCenter.add(monthLeftBtn);
		JPtopCenter.add(monthLabel);
		JPtopCenter.add(monthRightBtn);

		JButton myHabitBtn = new JButton("My Habbit");
		myHabitBtn.addActionListener(new myHabitBtnAction());
		JPtopRight.add(myHabitBtn);

		JPtop.add(JPtopLeft);
		JPtop.add(JPtopCenter);
		JPtop.add(JPtopRight);

		JPtop.setPreferredSize(new Dimension(900, 50));

		return JPtop;
	}

	private JTable calendarTable() {
		defaultTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		for (int i = 0; i < 7; i++)
			defaultTable.addColumn(headers[i]);

		calendarTable = new JTable(defaultTable);
		calendarTable.setPreferredSize(new Dimension(880, 500));
		calendarTable.getParent();

		// No resize/reorder
		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		// Single Cell selection
		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set row/column count
		calendarTable.setRowHeight(85);
		defaultTable.setColumnCount(7);
		defaultTable.setRowCount(6);

		return calendarTable;
	}

	private void initView() {
		Container c = getContentPane();

		JPanel JPBottom = new JPanel();
		JPBottom.add(calendarTable());

		// c.add(TopController(), BorderLayout.NORTH);
		c.add(JPBottom, BorderLayout.SOUTH);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scSize = tk.getScreenSize();
		setBounds(scSize.width / 2 - 395, scSize.height / 2 - 320, 900, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setBackground(Color.white);

		// refreshCalendar(currentMonth, currentYear);
	}

	private void refreshCalendar(int month, int year) {
		int numberOfdays, startOfMonth;

		monthLeftBtn.setEnabled(true);
		monthRightBtn.setEnabled(true);

		if (month == 0 && year <= realYear - 10)
			monthLeftBtn.setEnabled(false);
		if (month == 11 && year >= realYear + 100)
			monthRightBtn.setEnabled(false);

		monthLabel.setText(months[month]);
		yearCmb.setSelectedItem(String.valueOf(year));

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				defaultTable.setValueAt(null, i, j);

		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numberOfdays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Draw Calendar
		for (int i = 1; i <= numberOfdays; i++) {
			int row = new Integer((i + startOfMonth - 2) / 7);
			int column = (i + startOfMonth - 2) % 7;
			defaultTable.setValueAt(i, row, column);
		}

		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0),
				new calendarTableRenderer());
	}

	private class calendarTableRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			setBackground(new Color(240, 248, 255));
			return this;
		}

	}

	private class monthLeftBtnAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 0) { // Back one year
				currentMonth = 11;
				currentYear -= 1;
			} else { // Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	private class monthRightBtnAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 11) { // Foward one year
				currentMonth = 0;
				currentYear += 1;
			} else { // Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	private class yearCmbAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (yearCmb.getSelectedItem() != null) {
				String b = yearCmb.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}

	private class myHabitBtnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame myHabitView = new MyHabitView();
		}
	}

}
