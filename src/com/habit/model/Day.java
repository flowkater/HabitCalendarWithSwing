package com.habit.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Day {
	private int id;
	private List<DailyHabit> dailyHabits = new ArrayList<DailyHabit>();
	private String diary;
	private String date;

	public Day() {

	}

	public Day(String date) {
		this.date = date;
	}

	public Day(int id) {
		this.id = id;
	}

	public Day(int id, String diary, String date) {
		this.id = id;
		this.diary = diary;
		this.date = date;
	}

	public int getDay() {
		String[] array = date.split("-");
		return Integer.parseInt(array[2]);
	}

	public int getMonth() {
		String[] array = date.split("-");
		return Integer.parseInt(array[1]) - 1;
	}

	public int getYear() {
		String[] array = date.split("-");
		return Integer.parseInt(array[0]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DailyHabit> getDailyHabits() {
		return dailyHabits;
	}

	public void setDailyHabits(ArrayList<DailyHabit> dailyHabits) {
		this.dailyHabits = dailyHabits;
	}

	public String getDiary() {
		return diary;
	}

	public void setDiary(String diary) {
		this.diary = diary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/*
	 * getColor() 색깔 가져오기 하나라도 status 실패이면 Red, 모두 success 이면 Green
	 */
	public Color getColor() {
		DailyHabitDAO dailyHabitDAO = new DailyHabitDAO();
		dailyHabits = dailyHabitDAO.findAllByDayId(this);
		for (DailyHabit dailyHabit : dailyHabits)
			if (dailyHabit.getStatus() == 0)
				return new Color(255, 51, 51); // Red
		return new Color(0, 255, 0); // Green
	}
}