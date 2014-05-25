package com.habit.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfig {
	/*
	 * @getConnection() DB 연결 객체를 가져옵니다.
	 */
	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			// System.out.println("db get connection");
			return DriverManager.getConnection("jdbc:sqlite:habit.db");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @createTable() 테이블을 만듭니다. habits, daily_habits, days
	 */
	public void createTable() {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				stat.executeUpdate("create table if not exists habits (id integer primary key, name text);");
				stat.executeUpdate("create table if not exists daily_habits (id integer primary key, day_id integer references days(id), habit_id integer references habits(id), status integer);");
				stat.executeUpdate("create table if not exists days (id integer primary key, diary text, date text);");
				System.out.println("create table");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
	}

	/*
	 * @dropTable() 테이블을 삭제합니다. habits, daily_habits, days
	 */
	public void dropTable() {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				stat.executeUpdate("drop table if exists habits;");
				stat.executeUpdate("drop table if exists daily_habits;");
				stat.executeUpdate("drop table if exists days;");
				System.out.println("drop table");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
	}

	public void example() {
		HabitDAO habitDAO = new HabitDAO();
		DailyHabitDAO dailyHabitDAO = new DailyHabitDAO();
		DayDAO dayDAO = new DayDAO();

		habitDAO.insertHabit("아침 6시 일어나기");
		habitDAO.insertHabit("운동하기");
		habitDAO.insertHabit("물 많이 마시기");
		habitDAO.insertHabit("영어 단어 외우기");
		habitDAO.insertHabit("자바 공부");

		for (int i = 1; i < 25; i++) {
			if (i < 10) {
				dayDAO.insertday(new Day("2014-05-0" + i));
			} else {
				dayDAO.insertday(new Day("2014-05-" + i));
			}
		}
		
		for (int i = 1; i < 25; i++) {
			if(i % 2 == 0){
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 1, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 2, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 3, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 4, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 5, 1));
			}else{
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 1, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 2, 0));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 3, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 4, 1));
				dailyHabitDAO.insertDailyHabit(new DailyHabit(1, i, 5, 0));
			}
			
		}
	}

}
