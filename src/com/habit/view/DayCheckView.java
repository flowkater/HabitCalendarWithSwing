package com.habit.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twitter4j.TwitterException;

import com.habit.lib.TweetPost;
import com.habit.model.DailyHabit;
import com.habit.model.DailyHabitDAO;
import com.habit.model.Day;
import com.habit.model.DayDAO;
import com.habit.model.Habit;
import com.habit.model.HabitDAO;

public class DayCheckView extends JFrame {
	List<DailyHabit> dailyHabits = new ArrayList<DailyHabit>();
	private JPanel jPTop;
	private JLabel titleLabel;
	private JPanel jPCenter;
	private JPanel jPBottom;
	private Container c;
	private JScrollPane scrollbar;
	private JTextArea tweetBox;
	private DayDAO dayDAO;
	private DailyHabitDAO dailyHabitDAO;
	private HabitDAO habitDAO;
	private String date;
	private Day day;
	private TweetPost tweetPost = new TweetPost();

	public void startView(String date) {
		this.date = date;
		dayDAO = new DayDAO();
		dailyHabitDAO = new DailyHabitDAO();
		habitDAO = new HabitDAO();
		day = dayDAO.findByDate(date);
		if (day != null) {
			System.out.println(day.getDate());
			dailyHabits = dailyHabitDAO.findAllByDayId(day);
		}
		initView();
	}

	private JPanel topPanel() {
		jPTop = new JPanel();

		titleLabel = new JLabel();
		titleLabel.setText(date);

		jPTop.add(titleLabel);

		return jPTop;
	}

	private JPanel centerPanel() {
		System.out.println("center Panel");
		jPCenter = new JPanel();
		GridLayout grid = new GridLayout(0, 1);

		jPCenter.setLayout(grid);

		JList jList = new JList();
		// jList.setModel(arg0);

		for (DailyHabit dailyHabit : dailyHabits) {
			Habit habit = habitDAO.findByDailyHabit(dailyHabit);
			System.out.println(habit.getName());
			System.out.println(dailyHabit.getStatus());
			jPCenter.add(itemPanel(habit, dailyHabit));
		}

		return jPCenter;
	}

	private JPanel itemPanel(Habit habit, DailyHabit dailyHabit) {

		JPanel itemPanel = new JPanel();

		JLabel habitText = new JLabel();
		habitText.setText(habit.getName());

		if (dailyHabit.getStatus() == 0) {
			System.out.println("ITALIC");
			habitText.setFont(new Font(null, Font.ITALIC, 14));
		} else if (dailyHabit.getStatus() == 1) {
			habitText.setFont(new Font(null, Font.BOLD, 14));
		}

		JButton missBtn = new JButton();
		missBtn.setText("Miss");
		missBtn.addActionListener(new missBtnAction(dailyHabit));

		JButton successBtn = new JButton();
		successBtn.setText("Success");
		successBtn.addActionListener(new successBtnAction(dailyHabit));

		itemPanel.add(habitText);
		itemPanel.add(missBtn);
		itemPanel.add(successBtn);

		return itemPanel;
	}

	private JPanel bottomPanel() {
		jPBottom = new JPanel();
		jPBottom.setLayout(new BorderLayout());

		JButton tweetBtn = new JButton();
		tweetBtn.setText("Tweet");
		tweetBtn.addActionListener(new tweetBtnAction());

		jPBottom.add(tweetBtn, BorderLayout.SOUTH);

		return jPBottom;
	}

	class tweetBtnAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				tweetPost.tweetMyHabit(date, dailyHabits);
				new TweetDialog(new JFrame(), "tweet", "Tweet Success");
				dispose();
			} catch (TwitterException e1) {
				e1.printStackTrace();
			}
			initView();
		}
	}

	class missBtnAction implements ActionListener {

		DailyHabit dailyHabit;

		missBtnAction(DailyHabit dailyHabit) {
			this.dailyHabit = dailyHabit;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dailyHabit.setStatus(0);
			dailyHabitDAO.updateDailyHabit(dailyHabit);
			initView();
		}
	}

	class successBtnAction implements ActionListener {
		DailyHabit dailyHabit;

		successBtnAction(DailyHabit dailyHabit) {
			this.dailyHabit = dailyHabit;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dailyHabit.setStatus(1);
			dailyHabitDAO.updateDailyHabit(dailyHabit);
			initView();
		}
	}

	private void initView() {
		c = getContentPane();

		scrollbar = new JScrollPane();
		scrollbar.setViewportView(centerPanel());

		c.add(topPanel(), BorderLayout.NORTH);
		c.add(scrollbar, BorderLayout.CENTER);
		c.add(bottomPanel(), BorderLayout.SOUTH);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scSize = tk.getScreenSize();
		setBounds(scSize.width / 2 - 295, scSize.height / 2 - 220, 300, 300);

		setVisible(true);
	}
}
