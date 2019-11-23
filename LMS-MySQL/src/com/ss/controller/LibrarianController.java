package com.ss.controller;

public class LibrarianController {

	//the following function shows menu one of librarian role. and keeps taking input until
	//a valid one is entered.
	
	public int menuOne() {
		
	 System.out.println("Enter 1 to see library branches\nEnter 2 to quit to main menu"); 
   	 int choice= ScannerInput.scanNextInt();
   	 
   	  while(choice<0 || choice>2) {
   		  System.out.println("Invalid Option. Please try again.\n\n");
   		choice= ScannerInput.scanNextInt();
   		
   	  }
   	  return choice;
	}
	
	
	
	
	//the following method takes the branch they want to work on. It also makes sure that librarian input 
	//goes through bound check.
	
	public int menuTwo(int size) {
		
  	  int choice = ScannerInput.scanNextInt();
  	  if(choice<0 || choice>size) {
  		  System.out.println("Invalid option. Please try again.");
  		  menuTwo(size);
  	  }
  	  
  	  return choice;
    }
	
	//this method asks the user how they want to change the branch.
	
	public int menuThree() {
		
  	  System.out.println("\n1. Update the details of the Library \n" + 
  	  		"2. Update copies of Book on that Branch\n" + 
  	  		"3. Quit to previous");
  	  int choice = ScannerInput.scanNextInt();
  	  if(choice<0 || choice>3) {
  		  System.out.println("Invalid Option. Please try again.");
  		  menuThree();
  	  }
  	  return choice;
    }
	
	
	public String updateBranchName() {
  	  
  	  System.out.println("\nPlease enter new branch name or enter N/A for no change:");  
     return ScannerInput.scanNextLine();
    }
	
	public String updateBranchAddress() {
		System.out.println("\nPlease enter new branch address or enter N/A for no change:");
		return ScannerInput.scanNextLine();
	}

}
