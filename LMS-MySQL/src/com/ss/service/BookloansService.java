package com.ss.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.ss.dao.BookloansDao;
import com.ss.model.Book;
import com.ss.model.BookLoans;
import com.ss.model.Borrower;
import com.ss.model.LibraryBranch;

public class BookloansService {
	private static BookloansService instance = null;

	private BookloansService() {
		// Exists only to defeat instantiation.
	}

	public static BookloansService getInstance() {
		if (instance == null) {
			instance = new BookloansService();
		}
		return instance;
	}

	BookloansDao bookloansDao = BookloansDao.getInstance();

	public void overrideDuedate() throws SQLException, ParseException {
		System.out.println("Please enter book id:");
		Scanner sc = new Scanner(System.in);
		int bookId = sc.nextInt();

		BookService bookService = BookService.getInstance();
		while (!bookService.checkBook(bookId)) {
			System.out.println("Book id doesn't exist! Enter book id again:");
			bookId = sc.nextInt();
		}

		Book book = new Book();
		book.setBookId(bookId);

		System.out.println("Please enter branch id:");
		int branchId = sc.nextInt();

		LibraryBranchService branchService = LibraryBranchService.getInstance();
		while (!branchService.checkBranch(branchId)) {
			System.out.println("Branch id doesn't exist! Enter branch id again:");
			branchId = sc.nextInt();
		}

		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(branchId);

		System.out.println("Please enter borrower card number:");
		int cardNo = sc.nextInt();

		BorrowerService borrowerService = BorrowerService.getInstance();
		while (!borrowerService.checkBorrower(cardNo)) {
			System.out.println("Card number doesn't exist. Enter card number:");
			cardNo = sc.nextInt();
		}

		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);

		BookLoans bookloan = new BookLoans();
		bookloan.setBook(book);
		bookloan.setLibraryBranch(branch);
		bookloan.setBorrower(borrower);

		System.out.println("Please over-ride date: yyyy-mm-dd");
		sc = new Scanner(System.in);
		String input = sc.nextLine();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = dateFormat.parse(input);
		bookloan.setDueDate(parsedDate);

		bookloansDao.overrideDuedate(bookloan);

	}

}
