package com.ss.dao;

import java.sql.Connection;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

import com.ss.db.DBConnection;
import com.ss.model.BookLoans;

public class BookloansDao {

	private static BookloansDao instance = null;

	private BookloansDao() {
		// Exists only to defeat instantiation.
	}

	public static BookloansDao getInstance() {
		if (instance == null) {
			instance = new BookloansDao();
		}
		return instance;
	}

	public void overrideDuedate(BookLoans bookloan) {
		// TODO Auto-generated method stub

		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			String sql = "update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setTimestamp(1, new Timestamp(bookloan.getDueDate().getTime()));
			stmt.setInt(2, bookloan.getBook().getBookId());
			stmt.setInt(3, bookloan.getLibraryBranch().getBranchId());
			stmt.setInt(4, bookloan.getBorrower().getCardNo());
			stmt.executeUpdate();
			System.out.println("Over-ride due date completed");

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
	
	public boolean checkIfAlreadyCheckedOut(int bookId,int branchId, int cardNumber) {
		Connection connection = DBConnection.getConnection();
		boolean retBool = false;
		try {
			Statement st = connection.createStatement();
			String query = "select * from tbl_book_loans;";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				int tableBookId = Integer.parseInt(rs.getString("bookId"));
				int tableBranchId = Integer.parseInt(rs.getString("branchId"));
				int tablecardNumber = Integer.parseInt(rs.getString("cardNo"));
				if(bookId==tableBookId && branchId==tableBranchId && cardNumber==tablecardNumber) {
					retBool=true;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return retBool;
	}
	
	//this method inserts a row in book loans table when a borrower checks out a book.
	public void insertRow(int bookId, int branchId,int cardNumber) {
    	Connection connection = DBConnection.getConnection();
    	Calendar c = Calendar.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date checkOutDate = new Date();  
	    c.add(Calendar.DATE, 7);
	    Date dueDate = c.getTime();
	    
    	try {
			Statement st = connection.createStatement();
			String query = "insert into tbl_book_loans values("+Integer.toString(bookId)
			+","+Integer.toString(branchId)
			+","+ Integer.toString(cardNumber)
			+",'"+formatter.format(checkOutDate) 
			+"','"+formatter.format(dueDate)+"');";
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
	
	//this method returns list of branch id and book taken out from that branch by a specific borrower.
	public HashMap<Integer,List<String>> returnBookLoanList(int cardNumber){
		Connection connection = DBConnection.getConnection();
		HashMap<Integer,List<String>> retHashMap = new HashMap<>();
		try {
			Statement st = connection.createStatement();
			String query = "select b.bookId,b.title,lb.branchId,lb.branchName from tbl_book_loans bl join\n" + 
					" tbl_book b on bl.bookId=b.bookId join\n" + 
					"tbl_library_branch lb on bl.branchId=lb.branchId\n" + 
					"where bl.cardNo="+cardNumber+";";
			ResultSet rs = st.executeQuery(query);
			int Key=0;
			while(rs.next()) {
				List<String> tempList = new ArrayList<>();
				tempList.add(rs.getString("bookId"));
				tempList.add(rs.getString("title"));
				tempList.add(rs.getString("branchId"));
				tempList.add(rs.getString("branchName"));
				retHashMap.put(++Key, tempList);
			}
            } catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retHashMap;
	}
	
	public void deleteBookLoanInformation(int bookId, int branchId,int cardNumber) {
		Connection connection = DBConnection.getConnection();
		try {
			Statement st = connection.createStatement();
			String query = "delete from tbl_book_loans where bookId="+bookId
					+ " and branchId="+branchId
					+" and cardNo="+cardNumber;
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
