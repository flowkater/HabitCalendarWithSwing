package com.habit.model;

public class Habit {
	String name;
	boolean status;
	long dayId;
	
	Habit(){
		
	}
	
	public Habit(String name){
		this.name = name;
		this.status = false;
	}

	public long getDayId() {
		return dayId;
	}

	public void setDayId(long dayId) {
		this.dayId = dayId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
