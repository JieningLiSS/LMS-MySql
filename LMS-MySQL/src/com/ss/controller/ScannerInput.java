package com.ss.controller;

import java.util.Scanner;

public class ScannerInput {
	private static Scanner sc = new Scanner(System.in);
	  public static String scanNextLine() {
		  return sc.nextLine();
	  }
	  public static int scanNextInt() {
		  int input;
		  while(true) {
			  try {
				  input = Integer.parseInt(sc.nextLine());
				  if(input<0) {
					  System.out.println("No negative number please. Please enter a number greater that -1.");
				  }else {
					  break;
				  }
				  
			  }catch(NumberFormatException e) {
				  System.out.println("Invalid format. Please enter a Number.");
			  }
		  }
		  return input;
	  }
	  public static void closeScanner() {
		  sc.close();
	  }
}
