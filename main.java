// Name: Ali Bauyrzhan
// StudentID: 57517720
// Lab Section: T01

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class main {
	public static void main(String[] args) {
	
	String DASH = "-------------------------------------------------------------------------------";
	Scanner input = new Scanner(System.in);
	clearScreen();


	// Login Module
	int attempts = 3;
	String userID;
	String password;
	PI personal_information = new PI();
	personal_information.read_data();

	System.out.println("Welcome to the student management system, please login to your account\n" + DASH);
	while(true){	

		System.out.println("Please enter your userID");
		userID = input.next(); //User ID
	
		System.out.println("Please enter your password:");
		password = input.next(); //password

		// call the login method and get boolean
		if (PI.login(userID, password)){
			break;
		}
		else {
			attempts-=1;
			if (attempts==0){ 
				System.out.println("You have entered wrong account 3 times, the system is closing...");
				System.exit(0);
			}
			System.out.printf("The account you entered does not exist\nThere are %d remaining attempts\n" + DASH+"\n", attempts);
		}
		}
		
		// Go to menu
		new Menu(userID);
	}

	public static void clearScreen() {  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
   	}
	
}