package com.ss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.ss.db.DBConnection;
import com.ss.model.LibraryBranch;

public class LibraryBranchDao {

	private static LibraryBranchDao instance = null;

	private LibraryBranchDao() {
		// Exists only to defeat instantiation.
	}

	public static LibraryBranchDao getInstance() {
		if (instance == null) {
			instance = new LibraryBranchDao();
		}
		return instance;
	}

	public List<Integer> findAll() throws SQLException {

		List<Integer> branchList = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		String query = "SELECT * FROM tbl_library_branch";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			branchList.add(rs.getInt("branchId"));
		}
		st.close();

		return branchList;

	}

	public void addLibraryBranches(LibraryBranch libraryBranch) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = DBConnection.getConnection();
			String query = " insert into tbl_library_branch (branchId, branchName, branchAddress)" + " values (?,?,?)";
			preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, libraryBranch.getBranchId());
			preparedStmt.setString(2, libraryBranch.getBranchName());
			preparedStmt.setString(3, libraryBranch.getBranchAddress());
			preparedStmt.execute();
			System.out.println("Add library branch completed");

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e3) {
//				}
//			}
		}

	}

	public void updateLibraryBranches(LibraryBranch libraryBranch) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = DBConnection.getConnection();
			String query = "update tbl_library_branch set branchName=?,branchAddress=? where branchId=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			preparedStmt.setString(1, libraryBranch.getBranchName());
			preparedStmt.setString(2, libraryBranch.getBranchAddress());
			preparedStmt.setInt(3, libraryBranch.getBranchId());
			preparedStmt.execute();
			stmt.executeUpdate();
			System.out.println("Update library branch completed");

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e3) {
//				}
//			}
		}
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) {
		// TODO Auto-generated method stub

		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			String sqlDeleteBookLoan = "delete from tbl_book_loans where branchId=?";
			PreparedStatement stmtDeleteBookLoan = conn.prepareStatement(sqlDeleteBookLoan);
			stmtDeleteBookLoan.setInt(1, libraryBranch.getBranchId());
			stmtDeleteBookLoan.executeUpdate();

			String sqlDeleteBookCopy = "delete from tbl_book_copies where branchId=?";
			PreparedStatement stmtDeleteBookCopy = conn.prepareStatement(sqlDeleteBookCopy);
			stmtDeleteBookCopy.setInt(1, libraryBranch.getBranchId());
			stmtDeleteBookCopy.executeUpdate();

			String sql = "delete from tbl_library_branch where branchId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, libraryBranch.getBranchId());
			stmt.executeUpdate();
			System.out.println("Delete library branch completed");

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e3) {
//				}
//			}
		}

	}
	
	public HashMap<Integer,LibraryBranch> libraryBranchArray = new HashMap<>();
	DBConnection db = new DBConnection();
	
	//the following method calls super.readData() method which returns
	//him a general information of the table contents and assigns them to the
	//pojo class objects. and returns a hashmap of list of objects.
	
	public HashMap<Integer,LibraryBranch> readLibraryBranchData(){
		
		
		HashMap<Integer,List<String>> data = db.readData("tbl_library_branch"); 
		
		for(Entry<Integer, List<String>> entry : data.entrySet()) {
			LibraryBranch libraryBranch =new LibraryBranch();
			
			libraryBranch.setBranchId(Integer.parseInt(entry.getValue().get(0)));
			libraryBranch.setBranchName(entry.getValue().get(1));
			libraryBranch.setBranchAddress(entry.getValue().get(2));
			libraryBranchArray.put(entry.getKey(), libraryBranch);
		}
		
		return libraryBranchArray;
	}
	
	
	public void updateLibraryName(int id,String newBranchName) {
		libraryBranchArray.get(id).setBranchName(newBranchName);
		db.updateTable("tbl_library_branch", "branchName", id, newBranchName);
	}
	public void updateLibraryAddress(int id,String newBranchAddress) {
		libraryBranchArray.get(id).setBranchAddress(newBranchAddress);
		db.updateTable("tbl_library_branch", "branchAddress", id, newBranchAddress);
	}
	public String selectQuery(int branchId, String columnName) {
		Connection connection = DBConnection.getConnection();
		StringBuilder sb = new StringBuilder();
		try {
			Statement st = connection.createStatement();
			String query = "select "+columnName+" from tbl_library_branch where branchId="+branchId+";";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				sb.append(rs.getString(1));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		String retString = new String(sb);
		return retString;
	}

}
