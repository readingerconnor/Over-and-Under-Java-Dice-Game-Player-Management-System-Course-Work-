// CMPINF 0401 Fall 2026
// Test Program for Player2 class. This program must run without error using your
// Player2 class and the output must match that shown in Player2Test-out.txt.
// Note that there are aspects of this program that are interactive with the user.
// Look at PlayerTest-out.txt and use the same input used there - your execution
// should then produce the same output.
//
// See more comments below and in the Assignment 3 document explaining in detail what
// each of the Player2 methods are expected to accomplish.
// Note: Some of the Player2 methods are identical to those from the Player class in
// Assignment 2. For details on those methods see Assignment 2.

import java.util.*;

public class Player2Test
{
	public static void main(String [] args)
	{
		Player2 p1;
		
		Scanner inScan = new Scanner(System.in);
		
		System.out.print("What is your first name? ");
		String fName = inScan.next(); inScan.nextLine();
		System.out.print("What is your last name? ");
		String lName = inScan.next(); inScan.nextLine();
		
		p1 = new Player2(fName, lName);		
		System.out.println("Here is a formatted output String:");
		System.out.println(p1.toString());
		// Note what is shown in toString() and what is not shown. In particular,
		// the password and challenge question and answer are NOT shown. However,
		// all of the data fields are contained in the fileString() (called below)
		// which is used when saving the Player2 information to a file. See how
		// fileString() will be used in the PlayerGroupTest.java program.
		System.out.println("Here is a comma separated String:");
		System.out.println(p1.fileString());
		System.out.println();
	
		double bet = p1.getMoney(); // The getMoney() method returns the amount
					// of money that the player has (without going into debt). In
					// this test we will bet it all.
		System.out.print("Player " + p1.getName() + " is betting " + bet);
		System.out.println(" and won with even odds");
		p1.playedARound(true, bet, 1); // playedARound is identical to that in
					// Assignment 2.
		System.out.println(p1.toString());
		
		bet = p1.getMoney() / 2;
		System.out.print("Player " + p1.getName() + " is betting " + bet);
		System.out.println(" and lost with even odds");
		p1.playedARound(false, bet, 1);
		
		System.out.println(p1.toString());
		
		System.out.println("Here is a comma separated String:");
		System.out.println(p1.fileString());	
		System.out.println();
		
		System.out.println("Demonstrating authenticate...");
		// Demonstration of the authenticate method. authenticate() will return
		// true if all of the first name, last name and password fields match those
		// in the Player2 object. If any does not match it will return false.
		boolean good = false;
		do
		{
			System.out.print("Enter the password: ");
			String pass = inScan.next(); inScan.nextLine();
			good = p1.authenticate(fName, lName, pass);
			if (!good)
				System.out.println("Your password does not match!");
		}
		while (!good);
		System.out.println("You have been authenticated!");
		System.out.println();
		
		// Now test forgotPassword(). Be sure to see how this process works in
		// the Assignment 3 document and in the output file Player2Test-out.txt
		// forgotPassword() will check the first Name and last Name against the
		// Player2 and if they match, will give the option to reset the password
		// if the challenge question is answered successfully. See the output
		// for an example of how this is done. Note that the I/O for this is done
		// within the forgotPassword() method using a Scanner.
		System.out.println("Now testing the forgotPassword() method...");
		boolean check = p1.forgotPassword(fName, lName);
		if (check)
			System.out.println("The password for " + p1.getName() + " has been updated");
		else
			System.out.println("The password for " + p1.getName() + " is unchanged");
			
		String saveString = p1.fileString();
		System.out.println("Here is the updated data...");
		System.out.println(saveString);
		
		// Make a new Player2 object using the saved String
		Player2 p2 = new Player2(saveString);
		System.out.println("Here is the new Player2 object: ");
		System.out.println(p2.toString());
	}
}