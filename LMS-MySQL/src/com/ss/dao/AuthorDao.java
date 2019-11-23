package com.ss.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.ss.db.DBConnection;
import com.ss.model.Author;
import com.ss.service.AuthorService;

public class AuthorDao {

	private static AuthorDao instance = null;
    private DBConnection ds;
	private AuthorDao() {
		// Exists only to defeat instantiation.
	}

	public AuthorDao(DBConnection ds) {
		this.ds = ds;
		// TODO Auto-generated constructor stub
	}

	public static AuthorDao getInstance() {
		if (instance == null) {
			instance = new AuthorDao();
		}
		return instance;
	}

	public List<Integer> findAll() throws SQLException {

		List<Integer> authorList = new ArrayList<>();

		Connection conn = DBConnection.getConnection();

		String query = "SELECT * FROM tbl_author";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			authorList.add(rs.getInt("authorId"));
		}
		st.close();

		return authorList;

	}

	public void addAuthors(Author author) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			conn = ds.getConnection();
			String query = " insert into tbl_author (authorId, authorName)" + " values (?,?)";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, author.getAuthorId());
			preparedStmt.setString(2, author.getAuthorName());
			preparedStmt.execute();
			System.out.println("Add author completed");

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

	public void updateAuthors(Author author) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			String sql = "update tbl_author set authorName=? where authorId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, author.getAuthorName());
			stmt.setInt(2, author.getAuthorId());
			stmt.executeUpdate();
			System.out.println("Update author completed");

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

	public void deleteAuthor(Author author) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			String sqlDeleteBook = "delete from tbl_book where authId=?";
			PreparedStatement stmtDeleteBook = conn.prepareStatement(sqlDeleteBook);
			stmtDeleteBook.setInt(1, author.getAuthorId());
			stmtDeleteBook.executeUpdate();

			String sql = "delete from tbl_author where authorId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, author.getAuthorId());
			stmt.executeUpdate();
			System.out.println("Delete author completed");

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

}
