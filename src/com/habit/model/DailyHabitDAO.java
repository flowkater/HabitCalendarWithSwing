package com.habit.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DailyHabitDAO {
	
	public List<DailyHabit> findAllByDayId(Day day){
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<DailyHabit> dailyHabits = new ArrayList<DailyHabit>();
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("select * from daily_habits where day_id=" + day.getId() +";");
				rs = stat.executeQuery();
				System.out.println("select all daily_habits execute");
				while (rs.next()) {
					dailyHabits.add(new DailyHabit(rs.getInt("id"), rs
							.getInt("day_id"), rs.getInt("habit_id"), rs
							.getInt("status")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
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
		return dailyHabits;
	}
	
	public List<DailyHabit> findAllDailyHabits() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<DailyHabit> dailyHabits = new ArrayList<DailyHabit>();
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("select * from daily_habits;");
				rs = stat.executeQuery();
				System.out.println("select all daily_habits execute");
				while (rs.next()) {
					dailyHabits.add(new DailyHabit(rs.getInt("id"), rs
							.getInt("day_id"), rs.getInt("habit_id"), rs
							.getInt("status")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
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
		return dailyHabits;
	}

	public void insertDailyHabit(DailyHabit dailyHabit) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn
						.prepareStatement("insert into daily_habits values (null,"
								+ dailyHabit.getDayId()
								+ ","
								+ dailyHabit.getHabitId()
								+ ","
								+ dailyHabit.getStatus() + ");");
				stat.executeUpdate();
				System.out.println("Add dailyHabit");
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

	public void updateDailyHabit(DailyHabit dailyHabit) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("update daily_habits set status="
						+ dailyHabit.getStatus() + " where id="
						+ dailyHabit.getId() + ";");
				stat.executeUpdate();
				System.out.println("update day " + dailyHabit.getId());
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

	public void deleteDailyHabit(DailyHabit dailyHabit) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn
						.prepareStatement("delete from daily_habits where id = "
								+ dailyHabit.getId() + ";");
				stat.executeUpdate();
				System.out.println("Delete dailyHabit " + dailyHabit.getId());
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

	// main 메서드로 주석을 제거해서 디비코드를 테스트할 수 있습니다. DB Test Code
	public static void main(String[] args) {
		DBConfig dbConfig = new DBConfig();
		dbConfig.dropTable();
		dbConfig.createTable();

		DailyHabitDAO dailyHabitDAO = new DailyHabitDAO();
		dailyHabitDAO.insertDailyHabit(new DailyHabit(1, 1, 1, 0));
		dailyHabitDAO.insertDailyHabit(new DailyHabit(2, 1, 1, 0));
		dailyHabitDAO.insertDailyHabit(new DailyHabit(3, 2, 1, 0));
		dailyHabitDAO.insertDailyHabit(new DailyHabit(4, 1, 1, 0));
		dailyHabitDAO.insertDailyHabit(new DailyHabit(5, 2, 1, 0));

		List<DailyHabit> dailyHabits = new ArrayList<DailyHabit>();

		dailyHabits = dailyHabitDAO.findAllDailyHabits();
		for (DailyHabit dailyHabit : dailyHabits) {
			System.out.println(dailyHabit.getStatus() + " ");
		}

//		dailyHabitDAO.deleteDailyHabit(new DailyHabit(1));
//		dailyHabitDAO.deleteDailyHabit(new DailyHabit(2));
//		dailyHabitDAO.deleteDailyHabit(new DailyHabit(3));
		
		dailyHabitDAO.updateDailyHabit(new DailyHabit(4, 1));
		dailyHabitDAO.updateDailyHabit(new DailyHabit(5, 1));

		dailyHabits = dailyHabitDAO.findAllByDayId(new Day(2));
		for (DailyHabit dailyHabit : dailyHabits) {
			System.out.println(dailyHabit.getStatus() + " ");
		}
	}

}
