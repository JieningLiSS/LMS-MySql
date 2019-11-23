package com.ss.test.libraryBranch;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.dao.BookCopiesDao;
import com.ss.dao.LibraryBranchDao;
import com.ss.db.DBConnection;
import com.ss.model.LibraryBranch;
import com.ss.service.LibraryBranchService;

import junit.framework.Assert;

class LibraryBranchServiceTest {
	DBConnection db = new DBConnection();
	LibraryBranchService libraryBranchService = LibraryBranchService.getInstance();
	LibraryBranchDao libraryBranchDao = LibraryBranchDao.getInstance();
	
	@BeforeEach
	void setUp() throws Exception {
		
		db.getConnection();
	}
	
	

	@Test
	void testBranchSize() {
		int size = libraryBranchDao.readLibraryBranchData().size();
		Assert.assertEquals(3, size);
	}
	
	@Test
	void testUpdateBranchName() {
		libraryBranchService.updateBranchName(1, "ChinaTown");
		String actual = libraryBranchDao.selectQuery(1, "branchName");
		Assert.assertEquals("ChinaTown", actual);
	}
	
	@Test
	void testUpdateBranchAddress() {
		String expectedAddress = "34-67 123th avenue,New York, NY-11234";
		libraryBranchService.updateBranchName(1, expectedAddress);
		String actual = libraryBranchDao.selectQuery(1, "branchAddress");
		Assert.assertEquals(expectedAddress, actual);
	}
	
	@Test
	void testIfCopiesAreAddedToBranch(){
		libraryBranchService.updateCopies(12,40, 1, 2);
		BookCopiesDao bookCopiesDao = BookCopiesDao.getInstance();
		int actual = bookCopiesDao.retNoOfCopies(1, 2);
		Assert.assertEquals(40, actual);
	}
	@AfterEach
	public void closeConnection() {
		Connection con = DBConnection.getConnection();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
