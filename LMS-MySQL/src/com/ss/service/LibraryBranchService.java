package com.ss.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import com.ss.dao.LibraryBranchDao;
import com.ss.model.LibraryBranch;
import com.ss.app.Main;
import com.ss.controller.LibrarianController;
import com.ss.controller.ScannerInput;
import com.ss.dao.BookCopiesDao;

public class LibraryBranchService {

	private static LibraryBranchService instance = null;

	private LibraryBranchService() {
		// Exists only to defeat instantiation.
	}

	public static LibraryBranchService getInstance() {
		if (instance == null) {
			instance = new LibraryBranchService();
		}
		return instance;
	}

	LibraryBranchDao libraryBranchDao = LibraryBranchDao.getInstance();

	public boolean checkBranch(int bookId) {

		List<Integer> branchList = null;
		try {
			branchList = libraryBranchDao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (branchList.contains(bookId)) {
			return true;
		}
		return false;
	}

	public void addLibraryBranch() {
		// TODO Auto-generated method stub

		System.out.println("Please enter branch id:");
		Scanner sc = new Scanner(System.in);
		int branchId = sc.nextInt();

		while (checkBranch(branchId)) {
			System.out.println("Branch id already exist! Enter a new branch id again:");
			branchId = sc.nextInt();
		}

		System.out.println("Please enter branch name:");
		sc = new Scanner(System.in);
		String name = sc.nextLine();

		System.out.println("Please enter branch address:");
		sc = new Scanner(System.in);
		String address = sc.nextLine();

		LibraryBranch libraryBcanch = new LibraryBranch();
		libraryBcanch.setBranchId(branchId);
		libraryBcanch.setBranchName(name);
		libraryBcanch.setBranchAddress(address);

		libraryBranchDao.addLibraryBranches(libraryBcanch);
	}

	public void updateLibraryBranch() {
		// TODO Auto-generated method stub
		System.out.println("Please enter branch id:");
		Scanner sc = new Scanner(System.in);
		int branchId = sc.nextInt();

		while (!checkBranch(branchId)) {
			System.out.println("Branch id doesn't exist. Enter branch id again:");
			branchId = sc.nextInt();
		}

		System.out.println("Please enter branch new name:");
		sc = new Scanner(System.in);
		String updateName = sc.nextLine();

		System.out.println("Please enter branch new address:");
		sc = new Scanner(System.in);
		String updateAddress = sc.nextLine();

		LibraryBranch libraryBcanch = new LibraryBranch();
		libraryBcanch.setBranchId(branchId);
		libraryBcanch.setBranchName(updateName);
		libraryBcanch.setBranchAddress(updateAddress);

		libraryBranchDao.updateLibraryBranches(libraryBcanch);
	}

	public void deleteLibraryBranch() {
		// TODO Auto-generated method stub

		System.out.println("Please enter branch id:");
		Scanner sc = new Scanner(System.in);
		int branchId = sc.nextInt();

		while (!checkBranch(branchId)) {
			System.out.println("Branch id doesn't exist. Enter branch id again:");
			branchId = sc.nextInt();
		}

		LibraryBranch libraryBcanch = new LibraryBranch();
		libraryBcanch.setBranchId(branchId);

		libraryBranchDao.deleteLibraryBranch(libraryBcanch);
	}
	
	LibrarianController libConObj = new LibrarianController();
	BookCopiesDao bookCopiesDao = BookCopiesDao.getInstance();
	
     public void librarianMainMenu() {
		
		int choice = libConObj.menuOne();
		switch(choice) {
		case 1:
			getBranchList();
			break;
		case 2:
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
			break;

		}
		
	}
	
	//the following method gets the branch list from dao
	//and shows it to the user. Then takes branch input from
	//the user.
	
	public void getBranchList(){
   	 
   	 HashMap<Integer,LibraryBranch> libraryBranchMap = libraryBranchDao.readLibraryBranchData();
   	 
   	 System.out.println("  Branch Name"+"         Address\n");
   	 libraryBranchMap.forEach((k,v)-> System.out.println(k+". "
   	 +v.getBranchName()+"       "+v.getBranchAddress()));
   	 System.out.println((libraryBranchMap.size()+1)+". Go back to previous menu.");
   	 
    int choice = libConObj.menuTwo((libraryBranchMap.size()+1));
    
    if(choice==(libraryBranchMap.size()+1)) {
    	librarianMainMenu();
    }else {
    	makeChangesToBranch(choice,libraryBranchMap.get(choice));
    }
    
        
    }
	

	//this method asks the librarian how they want to change the branches and calls methods accordingly.
	
	public void makeChangesToBranch(int hashMapKey, LibraryBranch libraryBranch){
		
		int choice = libConObj.menuThree();
		switch(choice) {
		case 1:
			updateLibraryInformation(hashMapKey,libraryBranch);
			break;
		case 2:
			updateCopiesOfBranch(hashMapKey,libraryBranch);
			break;
		case 3:
			getBranchList();
			break;
		}
		
	}

	//librarian can add book copies to existing branch.
	public void updateCopiesOfBranch(int branchHashMapKey, LibraryBranch libraryBranch){
		
	 HashMap<Integer,List<String>> bookList = bookCopiesDao.getBookDescription(libraryBranch.getBranchId()); 
   	 System.out.println("Pick the Book you want to add copies of, to your branch: \n");
   	 System.out.println("   Title\n");
   	 bookList.forEach((k,v)->System.out.println(k+".  "+v.get(1)));
   	 System.out.println((bookList.size()+1)+". Goback to previous menu.");
   	 
		int bookHashMapKey = libConObj.menuTwo((bookList.size()+1)); //this returns the index in hashmap.
		
		if(bookHashMapKey == (bookList.size()+1)) {
    		makeChangesToBranch(branchHashMapKey,libraryBranch); //go back to previous menu.
    		System.exit(0);
    	}else {
    		int bookId = Integer.parseInt(bookList.get(bookHashMapKey).get(0));
    		int existingNumberOfCopies =Integer.parseInt(bookList.get(bookHashMapKey).get(2)) ;
    		
    		System.out.println("Existing Number of Copies: "+existingNumberOfCopies); //shows the number of copies 
    		
        	System.out.println("Enter new number of copies of that book in the branch: ");
        	int newCopiesAmount = ScannerInput.scanNextInt();
        	
        	updateCopies(existingNumberOfCopies,newCopiesAmount, libraryBranch.getBranchId(), bookId);
        	
        	makeChangesToBranch(branchHashMapKey,libraryBranch);
    	}
	}

	public void updateCopies(int existingNumberOfCopies, int newCopiesAmount, int branchId, int bookId) {
		if(existingNumberOfCopies==0) {
			bookCopiesDao.insertNoOfCopies(newCopiesAmount, branchId, bookId);
		}else {
			bookCopiesDao.updateNoOfCopies(newCopiesAmount, branchId, bookId);
	    	
		}
		System.out.println("Successfully added copies.");
		
	}

	public void updateLibraryInformation(int branchHashMapKey, LibraryBranch libraryBranch){
		
		System.out.println("\nYou have chosen to update the Branch with \nBranch Id: "
		+libraryBranch.getBranchId()+" and \nBranch Name: "+libraryBranch.getBranchName()
		+". \nEnter ‘quit’ at any prompt to cancel operation.");
		String branchName = libConObj.updateBranchName();
		
		if("quit".equalsIgnoreCase(branchName)) {
			makeChangesToBranch(branchHashMapKey,libraryBranch);
			System.exit(0);
		}
		String branchAddress = libConObj.updateBranchAddress();
		
		if("quit".equalsIgnoreCase(branchAddress)) {
			makeChangesToBranch(branchHashMapKey,libraryBranch);
			System.exit(0);
		}
		
		if(!"N/A".equalsIgnoreCase(branchName)) {
			updateBranchName(libraryBranch.getBranchId(),branchName);
   	 }
       if(!"N/A".equalsIgnoreCase(branchAddress)) {
       	updateBranchAddress(libraryBranch.getBranchId(),branchAddress);
   	 }
       makeChangesToBranch(branchHashMapKey,libraryBranch);
		
	}
	public void updateBranchName(int id,String newBranchName) {
		libraryBranchDao.updateLibraryName(id, newBranchName);
  		 System.out.println("Branch Name successfully updated.");
	}
	
	public void updateBranchAddress(int id,String newBranchAddress) {
		libraryBranchDao.updateLibraryAddress(id, newBranchAddress);
       	System.out.println("Branch Address successfully updated.");
	}

}
