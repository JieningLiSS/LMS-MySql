package com.ss.controller;

public class BorrowerController {

	private static BorrowerController instance = null;
	
	private BorrowerController() {
		// Exists only to defeat instantiation.
	}

	public static BorrowerController getInstance() {
		if (instance == null) {
			instance = new BorrowerController();
		}
		return instance;
	}

	public int menuOne() {
		System.out.println("1. Enter your card number.\n2. Quit to main menu.");
		int choice = ScannerInput.scanNextInt();
		while(choice<0 || choice>3) {
			System.out.println("Invalid input. Please try again: ");
		}
		return choice;
	}
	public int getCardNumber() {
		System.out.println("Enter the your Card Number: ");
		return  ScannerInput.scanNextInt();
			
	}
	
	public int menuTwo() {
		System.out.println("\n1. Checkout a book\n"
				+ "2. Return a book\n"
				+ "3. Quit to previous menu.");
		int choice = ScannerInput.scanNextInt();
		while(choice<0 || choice>4) {
			System.out.println("Invalid Option. Please try again.\n\n");
	   		choice= ScannerInput.scanNextInt();
		}
		return choice;
			
	}
}
