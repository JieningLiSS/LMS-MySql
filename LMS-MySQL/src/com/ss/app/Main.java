package com.ss.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import com.ss.controller.AdminController;
import com.ss.controller.ScannerInput;
import com.ss.db.DBConnection;
import com.ss.service.BorrowerService;
import com.ss.service.LibraryBranchService;

public class Main {
	public void show() throws SQLException, ParseException {
		System.out.println("Welcome to LMS. What would you like to do today:");
		System.out.println("1. Admin");
		System.out.println("2. Librarian");
		System.out.println("3. Borrower");
		System.out.println("0. Exit");
		System.out.println("Please enter:");
		Scanner sc = new Scanner(System.in);

		int input = sc.nextInt();
		switch (input) {

		case 0:
			System.out.println("Exiting the system..bye");
			break;
		case 1:
			AdminController adminController = new AdminController();
			adminController.show();
			break;
		case 2:
			LibraryBranchService librarianService = LibraryBranchService.getInstance();
        	        librarianService.librarianMainMenu();
			break;
		case 3:
			BorrowerService borrowerService = BorrowerService.getInstance();
        	        borrowerService.borrowerMainMenu();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}
	}

	public static void main(String[] args) throws SQLException, ParseException {

		Main mainMenu = new Main();
		mainMenu.show();
       Connection connection = DBConnection.getConnection();
       connection.close();
       ScannerInput.closeScanner();
	}
}
