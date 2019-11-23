package com.ss.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import com.ss.app.Main;
import com.ss.model.Author;
import com.ss.model.Book;
import com.ss.model.Borrower;
import com.ss.model.LibraryBranch;
import com.ss.model.Publisher;
import com.ss.service.AuthorService;
import com.ss.service.BookService;
import com.ss.service.BookloansService;
import com.ss.service.BorrowerService;
import com.ss.service.LibraryBranchService;
import com.ss.service.PublisherService;

public class AdminController {

	public static void gobackUpper() throws SQLException, ParseException {
		System.out.println("Do you want to go back to upper level menu? Y/N");
		Scanner sc = new Scanner(System.in);
		String jump = sc.nextLine();
		if (jump.equalsIgnoreCase("Y")) {
			AdminController admin = new AdminController();
			admin.show();
		} else {
			System.out.println("Exiting the system...bye");
			System.exit(0);
		}
	}

	public static void gobackMain() throws SQLException, ParseException {
		System.out.println("Do you want to go back to main level menu? Y/N");
		Scanner sc = new Scanner(System.in);
		String jump = sc.nextLine();
		if (jump.equalsIgnoreCase("Y")) {
			Main mainMenu = new Main();
			mainMenu.show();
		} else {
			System.out.println("Exiting the system...bye");
			System.exit(0);
		}
	}

	public void audAuthor() throws SQLException, ParseException {
		AuthorService authorService = AuthorService.getInstance();
		String string = "Would you like to:" + "\n1. Add a author" + "\n2. Update a author" + "\n3. Delete a author"
				+ "\n0. Exit";
		System.out.println(string);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		switch (input) {
		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			authorService.addAuthor();
			break;
		case 2:
			authorService.updateAuthor();
			break;
		case 3:
			authorService.deleteAuthor();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}
		gobackUpper();

	}

	public void audPublisher() throws SQLException, ParseException {
		PublisherService publisherService = PublisherService.getInstance();
		String string = "Would you like to: " + "\n1. Add a publisher" + "\n2. Update a publisher"
				+ "\n3. Delete a publisher" + "\n0. Exit";
		System.out.println(string);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		switch (input) {
		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			publisherService.addPublisher();
			break;
		case 2:
			publisherService.updatePublisher();
			break;
		case 3:
			publisherService.deletePublisher();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}

		gobackUpper();
	}

	public void audBook() throws SQLException, ParseException {
		BookService bookService = BookService.getInstance();
		String string = "Would you like to: " + "\n1. Add a book" + "\n2. Update a book" + "\n3. Delete a book"
				+ "\n0. Exit";
		System.out.println(string);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		switch (input) {
		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			bookService.addBook();
			break;
		case 2:
			bookService.updateBook();
			break;
		case 3:
			bookService.deleteBook();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}
		gobackUpper();
	}

	public void audLibraryBranch() throws SQLException, ParseException {
		LibraryBranchService libraryBranchService = LibraryBranchService.getInstance();
		String string = "Would you like to: " + "\n1. Add a library branch" + "\n2. Update a library branch"
				+ "\n3. Delete a library branch" + "\n0. Exit";
		System.out.println(string);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		switch (input) {
		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			libraryBranchService.addLibraryBranch();
			break;

		case 2:
			libraryBranchService.updateLibraryBranch();
			break;
		case 3:
			libraryBranchService.deleteLibraryBranch();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}
		gobackUpper();
	}

	public void audBorrower() throws SQLException, ParseException {
		String string = "Would you like to: " + "\n1. Add a borrower" + "\n2. Update a borrower"
				+ "\n3. Delete a borrower" + "\n0. Exit";
		System.out.println(string);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		BorrowerService borrowerService = BorrowerService.getInstance();
		switch (input) {
		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			borrowerService.addBorrower();
			break;
		case 2:
			borrowerService.updateBorrower();
			break;
		case 3:
			borrowerService.deleteBorrower();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}
		gobackUpper();
	}

	public void overideDuedate() throws SQLException, ParseException {

		BookloansService bookloansService = BookloansService.getInstance();
		bookloansService.overrideDuedate();
		gobackUpper();
	}

	public void show() throws SQLException, ParseException {
		String string = "Would you like to: " + "\n1. Add/Update/Delete Author" + "\n2. Add/Update/Delete Publisher"
				+ "\n3. Add/Update/Delete Book" + "\n4. Add/Update/Delete Library Branch"
				+ "\n5. Add/Update/Delete Borrower" + "\n6. Over-ride Due Date for a Book Loan "
				+ "\n7. Go back to main menu" + "\n0. Exit";
		System.out.println(string);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		switch (input) {
		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			audAuthor();
			break;
		case 2:
			audPublisher();
			break;
		case 3:
			audBook();
			break;
		case 4:
			audLibraryBranch();
			break;
		case 5:
			audBorrower();
			break;
		case 6:
			overideDuedate();
			break;
		case 7:
			gobackMain();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}

	}

}
