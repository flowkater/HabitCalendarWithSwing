package com.habit.model;

public class DailyHabit {
	int id;
	int habitId;
	int dayId;
	int status; // 0 miss, 1 success

	public DailyHabit() {

	}

	public DailyHabit(int id) {
		this.id = id;
	}

//	public DailyHabit(int id, int status) {
//		this.id = id;
//		this.status = status;
//	}
	
	public DailyHabit(int dayId, int habitId) {
		this.dayId = dayId;
		this.habitId = habitId;
		this.status = 0;
	}

	public DailyHabit(int id, int dayId, int habitId, int status) {
		this.id = id;
		this.habitId = habitId;
		this.dayId = dayId;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHabitId() {
		return habitId;
	}

	public void setHabitId(int habitId) {
		this.habitId = habitId;
	}

	public int getDayId() {
		return dayId;
	}

	public void setDayId(int dayId) {
		this.dayId = dayId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
