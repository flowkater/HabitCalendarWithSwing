package com.habit.view;

/*Contents of CalendarProgran.class */

//Import packages
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.habit.lib.Lib;
import com.habit.model.DBConfig;
import com.habit.model.DailyHabit;
import com.habit.model.DailyHabitDAO;
import com.habit.model.Day;
import com.habit.model.DayDAO;
import com.habit.model.Habit;
import com.habit.model.HabitDAO;

public class MainCalendar {
	JLabel lblMonth, lblYear;
	JButton btnPrev, btnNext, btnHabit;
	JTable tblCalendar;
	JComboBox cmbYear;
	JFrame frmMain;
	Container pane;
	DefaultTableModel mtblCalendar; // Table model
	JScrollPane stblCalendar; // The scrollpane
	JPanel pnlCalendar;
	int realYear, realMonth, realDay, currentYear, currentMonth;
	private DayDAO dayDAO = new DayDAO();
	private HabitDAO habitDAO = new HabitDAO();
	private DailyHabitDAO dailyHabitDAO = new DailyHabitDAO();
	List<Day> days = new ArrayList<Day>();
	MyHabitView myHabitView;

	public static void main(String args[]) {
		new MainCalendar();
	}

	/*
	 * 오늘 Day 가 없으면 생성
	 */
	private void createToday(Date d) {
		String date = Lib.df.format(d);
		System.out.println(date);
		List<Habit> habits = new ArrayList<Habit>();

		if (dayDAO.findByDate(date) == null) {
			dayDAO.insertday(new Day(date));
			habits = habitDAO.findAllHabits();
			Day day = dayDAO.findByDate(date);

			for (Habit habit : habits)
				dailyHabitDAO.insertDailyHabit(new DailyHabit(day.getId(),
						habit.getId()));

		} else {
			System.out.println("Today Day exists");
		}
	}

	MainCalendar() {
		DBConfig dbConfig = new DBConfig();
		dbConfig.dropTable();
		dbConfig.createTable();
		dbConfig.example();

		days = dayDAO.findAllDays();

		// Look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		// Prepare frame
		frmMain = new JFrame("My Habit Manager"); // Create frame
		frmMain.setSize(900, 640); // Set size to 400x400 pixels
		pane = frmMain.getContentPane(); // Get content pane
		pane.setLayout(null); // Apply null layout
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close when X
																// is clicked

		// Create controls
		lblMonth = new JLabel("January");
		lblYear = new JLabel("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton("<<");
		btnNext = new JButton(">>");
		btnHabit = new JButton("My Habit");
		mtblCalendar = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);

		// Set border
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));

		// Register action listeners
		btnHabit.addActionListener(new btnHabitAction());
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());

		// Add controls to pane
		pane.add(pnlCalendar);
		pnlCalendar.add(lblMonth);
		pnlCalendar.add(lblYear);
		pnlCalendar.add(cmbYear);
		pnlCalendar.add(btnPrev);
		pnlCalendar.add(btnNext);
		pnlCalendar.add(btnHabit);
		pnlCalendar.add(stblCalendar);

		// Set bounds
		pnlCalendar.setBounds(0, 0, 900, 640);
		lblMonth.setBounds(400, 45, 800, 60);
		lblMonth.setFont(new Font(null, Font.BOLD, 25));
		lblYear.setBounds(10, 20, 100, 20);
		cmbYear.setBounds(100, 20, 100, 20);
		btnHabit.setBounds(790, 20, 100, 50);
		btnPrev.setBounds(100, 55, 80, 30);
		btnNext.setBounds(690, 55, 80, 30);
		stblCalendar.setBounds(10, 100, 880, 500);

		// Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);

		// Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar

		// createToday Call
		createToday(cal.getTime());
		//

		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		realMonth = cal.get(GregorianCalendar.MONTH); // Get month
		realYear = cal.get(GregorianCalendar.YEAR); // Get year
		currentMonth = realMonth; // Match month and year
		currentYear = realYear;

		// Add headers
		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }; // All
																				// headers
		for (int i = 0; i < 7; i++) {
			mtblCalendar.addColumn(headers[i]);
		}

		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); // Set
																			// background

		// No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		// Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set row/column count
		tblCalendar.setRowHeight(70);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);

		// Populate table
		for (int i = realYear - 100; i <= realYear + 100; i++) {
			cmbYear.addItem(String.valueOf(i));
		}

		tblCalendar.addMouseListener(new MouseAdapter() {
			private DayCheckView dayCheckView;

			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				Object value = table.getModel().getValueAt(row, column);
				if (value != null) {
					String date = null;
					String month = null;
					String day = null;
					if (currentMonth < 9) {
						int cm = currentMonth + 1;
						month = "0" + cm;
					} else {
						month = currentMonth + "";
					}

					if (Integer.parseInt(value.toString()) < 10) {
						day = "0" + value.toString();
					} else {
						day = value.toString();
					}

					date = currentYear + "-" + month + "-" + day;
					System.out.println(date);

					dayCheckView = new DayCheckView();
					dayCheckView.startView(date);
				}
			}
		});

		// Refresh calendar
		refreshCalendar(realMonth, realYear); // Refresh calendar
	}

	public void refreshCalendar(int month, int year) {
		// Variables
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		int nod, som; // Number Of Days, Start Of Month

		// Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear - 10) {
			btnPrev.setEnabled(false);
		} // Too early
		if (month == 11 && year >= realYear + 100) {
			btnNext.setEnabled(false);
		} // Too late
		lblMonth.setText(months[month]); // Refresh the month label (at the top)
		lblMonth.setBounds(400, 45, 800, 60); // Re-align label with calendar
		cmbYear.setSelectedItem(String.valueOf(year)); // Select the correct
														// year in the combo box

		// Clear table
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				mtblCalendar.setValueAt(null, i, j);
			}
		}

		// Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Draw calendar
		for (int i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			mtblCalendar.setValueAt(i, row, column);
		}

		// Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0),
				new tblCalendarRenderer());
	}

	class tblCalendarRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean selected, boolean focused, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, selected,
					focused, row, column);
			setBackground(new Color(205, 201, 201));

			if (value != null) {
				if (isDateCheck(value, realYear, realMonth, realDay)) { // Today
					setBackground(new Color(220, 220, 255));
				}
				/*
				 * row color initialize
				 */
				for (Day day : days) {
					if (isDateCheck(value, day.getYear(), day.getMonth(),
							day.getDay())) {
						setBackground(day.getColor());
					}
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}

	public boolean isDateCheck(Object value, int year, int month, int day) {
		if (Integer.parseInt(value.toString()) == day && currentMonth == month
				&& currentYear == year)
			return true;
		return false;
	}

	class btnHabitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (myHabitView == null) {
				myHabitView = new MyHabitView();
				myHabitView.startView();
			}
			myHabitView.startView();
		}
	}

	class btnPrev_Action implements ActionListener {
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

	class btnNext_Action implements ActionListener {
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

	class cmbYear_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cmbYear.getSelectedItem() != null) {
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}
