package com.habit.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DayDAO {
	
	public Day findByDate(String date){
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Day day = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("select * from days where date='"+ date +"';");
				rs = stat.executeQuery();
				System.out.println("select date day execute");
				if (rs.next()) {
					day = new Day(rs.getInt("id"), rs.getString("diary"), rs
							.getString("date"));
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
		
		return day;
	}

	public List<Day> findAllDays() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<Day> days = new ArrayList<Day>();
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("select * from days;");
				rs = stat.executeQuery();
				System.out.println("select all days execute");
				while (rs.next()) {
					days.add(new Day(rs.getInt("id"), rs.getString("diary"), rs
							.getString("date")));
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
		return days;
	}

	public void insertday(Day day) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("insert into days values (null, null,'"
						+ day.getDate() + "');");
				
				stat.executeUpdate();
				
				System.out.println("Add day " + day.getDate());
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

	public void updateday(Day day) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("update days set diary="
						+ day.getDiary() + " where id=" + day.getId() + ";");
				stat.executeUpdate();
				System.out.println("Delete day " + day.getId() + " "
						+ day.getDiary());
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

	public void deleteday(Day day) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBConfig.getConnection();
			if (conn != null) {
				stat = conn.prepareStatement("delete from days where id = "
						+ day.getId() + ";");
				stat.executeUpdate();
				System.out.println("Delete day " + day.getId());
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
	// public static void main(String[] args) {
	// DBConfig dbConfig = new DBConfig();
	// dbConfig.dropTable();
	// dbConfig.createTable();
	//
	// DayDAO dayDAO = new DayDAO();
	// dayDAO.insertday(new Day("2014-05-12"));
	// dayDAO.insertday(new Day("2014-05-13"));
	// dayDAO.insertday(new Day("2014-05-14"));
	// dayDAO.insertday(new Day("2014-05-15"));
	// dayDAO.insertday(new Day("2014-05-16"));
	//
	// List<Day> days = new ArrayList<Day>();
	// days = dayDAO.findAllDays();
	// for (Day day : days) {
	// System.out.println(day.getDate());
	// }
	//
	// dayDAO.deleteday(new Day(1));
	// dayDAO.deleteday(new Day(2));
	// dayDAO.deleteday(new Day(3));
	//
	// days = dayDAO.findAllDays();
	// for (Day day : days) {
	// System.out.println(day.getDate());
	// }
	// }

}
