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
import com.ss.model.Author;
import com.ss.model.Book;
import com.ss.model.Publisher;

public class BookDao {

	private static BookDao instance = null;

	private BookDao() {
		// Exists only to defeat instantiation.
	}

	public static BookDao getInstance() {
		if (instance == null) {
			instance = new BookDao();
		}
		return instance;
	}

	public List<Integer> findAll() throws SQLException {

		List<Integer> bookList = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		String query = "SELECT * FROM tbl_book";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			bookList.add(rs.getInt("bookId"));
		}
		st.close();

		return bookList;

	}

	public void addBooks(Book book) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = DBConnection.getConnection();
			String query = " insert into tbl_book (bookId, title, authId, pubId)" + " values (?,?,?,?)";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, book.getBookId());
			preparedStmt.setString(2, book.getTitle());
			preparedStmt.setInt(3, book.getAuthor().getAuthorId());
			preparedStmt.setInt(4, book.getPublisher().getPublisherId());
			preparedStmt.execute();
			System.out.println("Add book completed");

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

	public void updateBooks(Book book) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = DBConnection.getConnection();
			String query = "update tbl_book set title=?,authId=?,pubId=? where bookId=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, book.getTitle());
			stmt.setInt(2, book.getAuthor().getAuthorId());
			stmt.setInt(3, book.getPublisher().getPublisherId());
			stmt.setInt(4, book.getBookId());
			stmt.executeUpdate();
			System.out.println("Update book completed");

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

	public void deleteBook(Book book) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = DBConnection.getConnection();

			String sqlDeleteBookLoan = "delete from tbl_book_loans where bookId=?";
			PreparedStatement stmtDeleteBookLoan = conn.prepareStatement(sqlDeleteBookLoan);
			stmtDeleteBookLoan.setInt(1, book.getBookId());
			stmtDeleteBookLoan.executeUpdate();

			String sqlDeleteBookCopy = "delete from tbl_book_copies where bookId=?";
			PreparedStatement stmtDeleteBookCopy = conn.prepareStatement(sqlDeleteBookCopy);
			stmtDeleteBookCopy.setInt(1, book.getBookId());
			stmtDeleteBookCopy.executeUpdate();

			String query = " delete from tbl_book where bookId=?";
			PreparedStatement stmtDeleteBook = conn.prepareStatement(query);
			stmtDeleteBook.setInt(1, book.getBookId());
			stmtDeleteBook.executeUpdate();
			System.out.println("Delete book completed");

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
	
	//this method returns all the books in the table
	public HashMap<Integer,Book> getBookList(){
		DBConnection db = new DBConnection();
		HashMap<Integer,List<String>> dataFromDB = db.readData("tbl_book");
		HashMap<Integer,Book> bookList = new HashMap<>();
		
		for(Entry<Integer, List<String>> data : dataFromDB.entrySet()) {
			
			Author authorObj = new Author();
			Publisher publisherObj = new Publisher();
			Book bookObj = new Book();
			
			bookObj.setBookId(Integer.parseInt(data.getValue().get(0)));
			bookObj.setTitle(data.getValue().get(1));
			bookObj.setAuthor(authorObj);
			bookObj.setPublisher(publisherObj);
			bookList.put(data.getKey(), bookObj);
		}
		return bookList;
	}

}
