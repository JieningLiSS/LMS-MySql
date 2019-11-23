package com.ss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ss.db.DBConnection;
import com.ss.model.Book;
import com.ss.model.BookCopies;
import com.ss.model.LibraryBranch;


public class BookCopiesDao{
	
	LibraryBranchDao libraryBranchDaoObj = LibraryBranchDao.getInstance();
	BookDao bookDaoObj = BookDao.getInstance();
	
	private static BookCopiesDao instance = null;

	private BookCopiesDao() {
		// Exists only to defeat instantiation.
	}

	public static BookCopiesDao getInstance() {
		if (instance == null) {
			instance = new BookCopiesDao();
		}
		return instance;
	}
	
	//this method reads all the data in bookCopies table and stores and returns the result in a list
	public List<BookCopies> readBookCopiesTable() {
		
		Connection connection = DBConnection.getConnection();
		List<BookCopies> bookCopiesArray = new ArrayList<>();
		
	    HashMap<Integer,LibraryBranch> libraryBranchArray = libraryBranchDaoObj.readLibraryBranchData();
	    
	    HashMap<Integer,Book> bookList = bookDaoObj.getBookList();
		
		try {
			Statement st = connection.createStatement();
			String query = "select * from tbl_book_copies";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				BookCopies bookCopiesObj = new BookCopies();
				
				bookCopiesObj.setBook(bookList.get(Integer.parseInt(rs.getString("bookId")) ));
				bookCopiesObj.setLibraryBranch(libraryBranchArray.get(Integer.parseInt(rs.getString("branchId")) ));
				bookCopiesObj.setNoOfCopies(Integer.parseInt(rs.getString("noOfCopies")));
				bookCopiesArray.add(bookCopiesObj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookCopiesArray;
	}
	
	
	//this method does join between tbl_library_branch, tbl_book_copies and
	//tbl_book and returns bookId,title and no of books.
	public HashMap<Integer,List<String>> getBookDescription(int branchId) {
		
		Connection connection = DBConnection.getConnection();
		HashMap<Integer,List<String>> retBookList = new HashMap<>();
		
		try {
			Statement st = connection.createStatement();
			String query = "select tbl_book.bookId, title, ifnull(noOfCopies, 0)as noOfCopies\n" + 
					"from tbl_book\n" + 
					"left join (select bookId, noOfCopies from tbl_book_copies where branchId = "+Integer.toString(branchId)+"    ) b\n" + 
					"on tbl_book.bookId=b.bookId";
			ResultSet rs = st.executeQuery(query);
			int Key=0;
			
			while(rs.next()) {
				List<String> tempList = new ArrayList<>();
				tempList.add(rs.getString("bookId"));
				tempList.add(rs.getString("title"));
				tempList.add(rs.getString("noOfCopies"));
				retBookList.put(++Key, tempList);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retBookList;
	}
	
	public HashMap<Integer,List<String>> getBookForBorrower(int branchId) {
		Connection connection = DBConnection.getConnection();
		HashMap<Integer,List<String>> retBookList = new HashMap<>();
		try {
			Statement st = connection.createStatement();
			String query = "select b.bookId,b.title,bc.noOfCopies from tbl_book b join tbl_book_copies bc\n" + 
					"on b.bookId=bc.bookId join\n" + 
					"tbl_library_branch lb on bc.branchId=lb.branchId\n" + 
					"where lb.branchId="+Integer.toString(branchId)+" and bc.noOfCopies>=1;";
			ResultSet rs = st.executeQuery(query);
			int Key=0;
			while(rs.next()) {
				List<String> tempList = new ArrayList<>();
				tempList.add(rs.getString("bookId"));
				tempList.add(rs.getString("title"));
				tempList.add(rs.getString("noOfCopies"));
				retBookList.put(++Key, tempList);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return retBookList;
	}
	
	
	//this method updates the number of copies of a book in a branch.
	
	public void updateNoOfCopies(int newValue, int branchId, int bookId) {
		Connection connection = DBConnection.getConnection();
		try {
			Statement st = connection.createStatement();
			String query = "update tbl_book_copies set noOfCopies="+Integer.toString(newValue)+" where \n" + 
					"bookId="+Integer.toString(bookId)+" and branchId="+Integer.toString(branchId)+";";
			st.executeUpdate(query);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public void insertNoOfCopies(int newValue, int branchId, int bookId) {
		Connection connection = DBConnection.getConnection();
		try {
			Statement st = connection.createStatement();
			String query = "insert into tbl_book_copies values("
			+bookId+","
			+branchId+","
			+newValue+");";
			st.executeUpdate(query);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public int retNoOfCopies(int bookId, int branchId) {
		Connection connection = DBConnection.getConnection();
		int numberOfCopies=0;
		try {
			Statement st = connection.createStatement();
			String query = "select bc.noOfCopies from tbl_book_copies bc join\n" + 
					"tbl_book b on bc.bookId=b.bookId join\n" + 
					"tbl_library_branch lb on bc.branchId=lb.branchId\n" + 
					"where b.bookId="+bookId+" and lb.branchId="+branchId+";";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				numberOfCopies = Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfCopies;
	}
	
}
