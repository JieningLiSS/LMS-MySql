package com.ss.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

import com.ss.dao.BorrowerDao;
import com.ss.model.Borrower;
import com.ss.app.Main;
import com.ss.controller.BorrowerController;
import com.ss.controller.LibrarianController;
import com.ss.dao.BookCopiesDao;
import com.ss.dao.BookloansDao;
import com.ss.dao.LibraryBranchDao;
import com.ss.model.LibraryBranch;

public class BorrowerService {

	private static BorrowerService instance = null;

	private BorrowerService() {
		// Exists only to defeat instantiation.
	}

	public static BorrowerService getInstance() {
		if (instance == null) {
			instance = new BorrowerService();
		}
		return instance;
	}

	BorrowerDao borrowerDao = BorrowerDao.getInstance();
	
        BorrowerController borrowerController = BorrowerController.getInstance();
        LibraryBranchDao libraryBranchDao = LibraryBranchDao.getInstance();
        LibrarianController librarianController = new LibrarianController();
        BookCopiesDao bookCopiesDao = BookCopiesDao.getInstance();
        BookloansDao bookLoanDao = BookloansDao.getInstance();

	public boolean checkBorrower(int cardNo) {

		List<Integer> borrowerList = null;
		try {
			borrowerList = borrowerDao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (borrowerList.contains(cardNo)) {
			return true;
		}
		return false;

	}

	public void addBorrower() {
		// TODO Auto-generated method stub

		System.out.println("Please enter borrower card number:");
		Scanner sc = new Scanner(System.in);
		int cardNo = sc.nextInt();

		while (checkBorrower(cardNo)) {
			System.out.println("Card number already exist. Enter a new card number:");
			cardNo = sc.nextInt();
		}

		System.out.println("Please enter borrower name:");
		sc = new Scanner(System.in);
		String name = sc.nextLine();

		System.out.println("Please enter borrower address:");
		sc = new Scanner(System.in);
		String address = sc.nextLine();

		System.out.println("Please enter borrower phone number:");
		sc = new Scanner(System.in);
		String phone = sc.nextLine();

		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		borrower.setName(name);
		borrower.setAddress(address);
		borrower.setPhone(phone);

		borrowerDao.addBorrowers(borrower);
	}

	public void updateBorrower() {
		// TODO Auto-generated method stub

		System.out.println("Please enter borrower card number:");
		Scanner sc = new Scanner(System.in);
		int cardNo = sc.nextInt();

		while (checkBorrower(cardNo)) {
			System.out.println("Card number doesn't exist. Enter a new card number:");
			cardNo = sc.nextInt();
		}

		System.out.println("Please enter borrower new name:");
		sc = new Scanner(System.in);
		String updateName = sc.nextLine();

		System.out.println("Please enter borrower new address:");
		sc = new Scanner(System.in);
		String updateAddress = sc.nextLine();

		System.out.println("Please enter borrower new phone number:");
		sc = new Scanner(System.in);
		String updatePhone = sc.nextLine();

		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		borrower.setName(updateName);
		borrower.setAddress(updateAddress);
		borrower.setPhone(updatePhone);

		borrowerDao.updateBorrowers(borrower);

	}

	public void deleteBorrower() {
		// TODO Auto-generated method stub

		System.out.println("Please enter borrower card number you want to delete:");
		Scanner sc = new Scanner(System.in);
		int cardNo = sc.nextInt();

		while (checkBorrower(cardNo)) {
			System.out.println("Card number doesn't exist. Enter a new card number:");
			cardNo = sc.nextInt();
		}

		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		borrowerDao.deleteBorrowers(borrower);
	}
	
	public int borrowerMenuOne() {
  	  int cardNumber;
  	  while(true) {
  		  cardNumber =  borrowerController.getCardNumber();
  		  if(!checkBorrower(cardNumber)) {
  			  System.out.println("Invalid card Number.");
  		  }
  		  else {
  			  break;
  		  }  
  	  }
  	  return cardNumber;
    }
	
	public void borrowerMenuTwo(int cardNumber) {
  	  int choice = borrowerController.menuTwo();
		
  	  switch(choice) {
			case 1:
				checkOutBook(cardNumber);
				break;
			case 2:
				returnBook(cardNumber);
				break;
			case 3:
				borrowerMainMenu();
				break;
			
			}
    }
	
	public void borrowerMainMenu() {
	 int choice = borrowerController.menuOne();
	 if(choice ==1) {
		 int cardNumber =  borrowerMenuOne();
		   	borrowerMenuTwo(cardNumber);
	 }else {
		 Main mainMenu = new Main();
		 try {
			mainMenu.show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
   	 	
     }

	private void returnBook(int cardNumber) {
		HashMap<Integer,List<String>> bookLoanHistory = bookLoanDao.returnBookLoanList(cardNumber);
		System.out.println("\n   Title     Branch\n");
		bookLoanHistory.forEach((k,v) -> System.out.println(k+".  "+v.get(1)+"   "+v.get(3)));
		System.out.println((bookLoanHistory.size()+1)+".Go back to previous menu");
		
		int hashMapIndex = librarianController.menuTwo((bookLoanHistory.size()+1)); //take in the hashmap index or branch
		if(hashMapIndex == (bookLoanHistory.size()+1)) {
			borrowerMenuTwo(cardNumber);
			System.exit(0);
		}else {
			
			System.out.println("\nSelect which book you want to checkout: ");
			
			int bookId = Integer.parseInt(bookLoanHistory.get(hashMapIndex).get(0));
			int branchId = Integer.parseInt(bookLoanHistory.get(hashMapIndex).get(2));
			bookLoanDao.deleteBookLoanInformation(bookId, branchId, cardNumber);
			int newBookCopy = bookCopiesDao.retNoOfCopies(bookId, branchId) +1;
			bookCopiesDao.updateNoOfCopies(newBookCopy, branchId, bookId);
			System.out.println("Return successful.");
			borrowerMenuTwo(cardNumber);
			
		}
	}
	
	
	//this method lets a librarian checkout a book.

	private void checkOutBook(int cardNumber) {
		
	  HashMap<Integer,LibraryBranch> libraryBranchList = libraryBranchDao.readLibraryBranchData();
	  System.out.println("Select the branch to checkout from: \n   Branch Name\n");
	  
  	  libraryBranchList.forEach((k,v) ->System.out.println(k+".  "+v.getBranchName())); //print list of branches
  	  
  	  System.out.println((libraryBranchList.size()+1)+".Go back to previous menu");
	
  	int hashMapIndex = librarianController.menuTwo((libraryBranchList.size()+1)); //take in the hashmap index or branch
  	
    if(hashMapIndex == (libraryBranchList.size()+1)) { //check if the borrower wants to go back to prev menu
    	borrowerMenuTwo(cardNumber);
    	System.exit(0);
    }
    else {
    	int branchId = libraryBranchList.get(hashMapIndex).getBranchId(); //reusing method from librarian
          //controller and getting the hashmap index which returns the bookId.
	 HashMap<Integer,List<String>> bookList = bookCopiesDao.getBookForBorrower(branchId); //print books that have at least one copy
	                                                                            //in the branch borrower selected.
	 System.out.println("\nBooks available for checkout at this branch: \n");
    
	 System.out.println("  Book Title\n");
	 bookList.forEach((k,v) -> System.out.println(k+".   "+v.get(1)));
	 System.out.println("\nEnter which book you wish to checkout: ");
	 
	 int bookIndex = librarianController.menuTwo((bookList.size()+1));  
	 int bookId =Integer.parseInt(bookList.get(bookIndex).get(0));     //get id of the book borrower wants to checkout. 
	 
	 if(!(bookLoanDao.checkIfAlreadyCheckedOut(bookId,branchId, cardNumber))) {  //this function checks if the borrower already
		                                                       //checked out that book from that branch.
		  bookLoanDao.insertRow(bookId, branchId, cardNumber);  //this inserts a new row in book loans table.
		  int newBookCopy =Integer.parseInt(bookList.get(bookIndex).get(2)) -1; 
		  bookCopiesDao.updateNoOfCopies(newBookCopy, branchId, bookId); //this decrements number of book copies in book copies table.
		  System.out.println("Book Successfully checked out.");
		  borrowerMenuTwo(cardNumber); //go back to prev menu.
	  }else {
		  System.out.println("\nYou have already checked out this book.\n\n"
		  		+ "Try checking out of another branch\n");
		  checkOutBook(cardNumber);
	  }
	 
	}
	}
	

}
