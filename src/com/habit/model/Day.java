package com.habit.model;

import java.awt.Color;
import java.util.ArrayList;

public class Day {
	ArrayList<Habit> habits;
	String diary;

	public ArrayList<Habit> getHabits() {
		return habits;
	}

	public void setHabits(ArrayList<Habit> habits) {
		this.habits = habits;
	}

	public String getDiary() {
		return diary;
	}

	public void setDiary(String diary) {
		this.diary = diary;
	}
	
	public Color getColor(){
		for (Habit habit : habits) {
			if(!habit.isStatus())
				return Color.red;
		}
		return Color.green;
	}
}