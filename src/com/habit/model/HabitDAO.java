package com.habit.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HabitDAO {
	/*
	 * @findAllHabits() 모든 등록된 습관을 가져옵니다.
	 */
	public List<Habit> findAllHabits() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<Habit> habits = new ArrayList<Habit>();
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("select * from habits;");
				rs = stat.executeQuery();
				System.out.println("select all habits execute");
				while (rs.next()) {
					habits.add(new Habit(rs.getInt("id"), rs.getString("name")));
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
		return habits;
	}

	public Habit findByDailyHabit(DailyHabit dailyHabit) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Habit habit = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("select * from habits where id='"
						+ dailyHabit.getHabitId() + "';");
				rs = stat.executeQuery();
				System.out.println("select findbyDailyHabit habits execute");
				if (rs.next()) {
					habit = new Habit(rs.getInt("id"), rs.getString("name"));
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

		return habit;
	}

	/*
	 * @insertHabit(String name) name : 습관 이름 습관을 추가합니다.
	 */
	public void insertHabit(String name) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn
						.prepareStatement("insert into habits values (null, '"
								+ name + "');");
				stat.executeUpdate();
				System.out.println("Add Habit " + name);
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
	 * @updateHabit(int id, String name) id : 습관 아이디, name : 습관 이름 습관을 수정합니다.
	 */
	public void updateHabit(int id, String name) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("update habits set name = '"
						+ name + "' where id=" + id + ";");
				stat.executeUpdate();
				System.out.println("update Habit " + id);
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
	 * @deleteHabit(int id) id : 습관 아이디 습관을 삭제합니다.
	 */
	public void deleteHabit(int id) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("delete from habits where id = "
						+ id + ";");
				stat.executeUpdate();
				System.out.println("Delete Habit " + id);
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
	// // public static void main(String[] args) {
	// // DBConfig dbConfig = new DBConfig();
	// // dbConfig.createTable();
	// // HabitDAO habitDAO = new HabitDAO();
	// // habitDAO.insertHabit("nansd");
	// // habitDAO.insertHabit("shjw");
	// // habitDAO.insertHabit("=powerwer");
	// // habitDAO.insertHabit("nasdljzcx");
	// // habitDAO.insertHabit("nansd");
	// //
	// // habitDAO.updateHabit(1, "운동하기");
	// //
	// // List<Habit> habits = new ArrayList<Habit>();
	// // habits = habitDAO.findAllHabits();
	// // for (Habit habit : habits) {
	// // System.out.println(habit.getName());
	// // }
	// //
	// // habitDAO.deleteHabit(1);
	// // habitDAO.deleteHabit(2);
	// // habitDAO.deleteHabit(3);
	// //
	// // habits = habitDAO.findAllHabits();
	// // for (Habit habit : habits) {
	// // System.out.println(habit.getName());
	// // }
	// }

}
