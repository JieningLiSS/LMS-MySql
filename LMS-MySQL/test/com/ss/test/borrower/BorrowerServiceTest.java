package com.ss.test.borrower;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.dao.BookloansDao;
import com.ss.db.DBConnection;

import junit.framework.Assert;

class BorrowerServiceTest {
	DBConnection db = new DBConnection();
	BookloansDao bookLoanDao = BookloansDao.getInstance();
	@BeforeEach
	void setUp() throws Exception {
		db.getConnection();
	}

	@Test
	void wasInsertToBookLoanTableSuccessful() {
		bookLoanDao.insertRow(1, 3, 1003);
		 int actual = db.readData("tbl_book_loans").size();
		Assert.assertEquals(7, actual);
	}
	
	@Test
	void checkIfUserAlreadyCheckedOutBook() {
		boolean actual = bookLoanDao.checkIfAlreadyCheckedOut(1, 3, 1003);
		Assert.assertEquals(true, actual);
	}
	
	@Test
	void deleteFromBookLoansAfterReturn() {
		bookLoanDao.deleteBookLoanInformation(1, 3, 1003);
		 int actual = db.readData("tbl_book_loans").size();
			Assert.assertEquals(6, actual);
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
