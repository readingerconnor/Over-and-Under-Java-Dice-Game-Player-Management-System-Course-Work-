// CMPINF 0401 Fall 2026
// Program to test PlayerGroup class (note: this program also requires class
// Player2 to be implemented correctly). Note the sample output shown for this
// program during which it is run 2 times - in the first run some new players 
// are added but the second time none are added since the updated list was saved
// back to the file.

// Your Player2 class is a modification of the Player class from Assignment 2.
// For details on the changes in Player2 see test file Player2Test.java and also
// see the Assignment 2 document.

// Your PlayerGroup class can be implemented in various ways and you are free to add
// additional methods to those used below. However, as stated in the Assignment 3
// document, the underlying data for your PlayerGroup class must be a simple Java
// array - it CANNOT be any predefined Java collection such as ArrayList, LinkedList
// or anything else. As a starting point use the provided skeleton from Canvas in
// Files/Assignments/Assignment3/PlayerGroup.java

// The methods used below are required and must have the effect discussed / demonstrated
// in this program.  After completing your updated Player2 class and your new PlayerGroup
// class, this program should compile and execute and produce the same results as shown
// in PlayerGroupTest-out.txt.

// For some help with these files you should look at Lab 6 and, in particular the
// Movie class and the MovieDB class.  You should also look at the SimpleAList.java
// handout and class lecture notes.

import java.util.*;
import java.io.*;

public class PlayerGroupTest
{
	public static void main(String [] args) throws IOException
	{
		System.out.println("Testing PlayerGroup and Player2 classes");
		
		// Create a PlayerGroup from a file of players.  Note the format of the file
		// and note that after this constructor all of the player information is stored
		// within the allPlayers object.  Note also the effect of the toString() method.
		// Parse the output carefully in order to figure out what it entails.  To help with
		// the grading of this project, you MUST:
		// 1) Initialize the your array within your PlayerGroup constructor to size 3
		// 2) Resize your array when necessary to double its previous size, as discussed in
		//    in lecture.
		// 3) Implement a capacity() method, which indicates the current size of the array
		//    within your PlayerList object
		PlayerGroup allPlayers = new PlayerGroup("players.txt");
		
		// Note the format and information returned by the toString() method.  Your
		// output should contain the same, well formatted information.  Note that this
		// call shows ALL of the Player2s in the PlayerGroup -- this is because the 
		// toString() method in the PlayerGroup class will iterate through all of the
		// Player2 objects and call the toString() method for each, appending them all 
		// into one (very big) StringBuilder. It will then return the single string 
		// representing the entire group of Player2s.
		System.out.println("Here are the player stats: ");
		System.out.println(allPlayers.toString());
		
		System.out.println("Current list size is: " + allPlayers.size());  // logical size
		System.out.println("Current array capacity is: " + allPlayers.capacity());
																			// physical size
		System.out.println();
		
		// Some names and passwords are hard coded here just to test the classes.  Initially
		// Marge and Indiana will NOT be in the PlayerGroup, and they will be added.
		String [] fnames = {"Marge", "Fezzik", "Indiana", "Inigo", "Vizzini"};
		String [] lnames = {"Simpson", "TheGiant", "Jones", "Montoya", "Vizzini"};
		String [] passes = {"IHeartCMPINF401", "Sportsmanlike", "Junior", "Prepare", "Genius"};
		System.out.println("Checking for names in the PlayerGroup");
		for (int i = 0; i < fnames.length; i++)
		{
			String fName = fnames[i];
			String lName = lnames[i];
			String pass = passes[i];
			String name = fName + " " + lName;
			// containsName will return true if the Player2's name is within the PlayerList
			// and false otherwise.  This will be useful in your main program.
			boolean found = allPlayers.containsName(fName, lName);
			if (found)
			{
				System.out.println(name + " is a name in the PlayerGroup\n");
			}
			else
			{
				System.out.println(name + " is not in the group -- will be added:");
				// Create a new player using the Player2 constructor. This should prompt
				// the user to initialize some fields and should set some others to 0.
				// See more information in Player2Test.java.
				Player2 onePlayer = new Player2(fName, lName);
				
				// Show player info using the toString() method.
				System.out.println("\tNew player info: ");
				System.out.println(onePlayer.toString());
				// Add the player to the PlayerGroup.  Note that addPlayer() should not add
				// a new Player2 whose name matches one already found in the list. If this
				// is tried the method should return false. Due to the test above we know
				// that all of the adds here should work.
				if (allPlayers.addPlayer(onePlayer))
					System.out.println("has been added to the PlayerGroup\n");
				else
					System.out.println("was already in the group -- NOT added!");
			}
		}
		
		System.out.println("Checking for valid Player2s in the PlayerGroup");
		for (int i = 0; i < fnames.length; i++)
		{
			String fName = fnames[i]; // Try each name / password combo from the arrays
			String lName = lnames[i];
			String pass = passes[i];  // above to see which are valid.
			
			// There are two versions of the login() method within PlayerGroup. The first
			// login() method takes 3 String arguments as shown below and matches them
			// against Player2 objects in the PlayerGroup using the authenticate() method.
			// See authenticate() in the details for the Player2 class and in the
			// Player2Test.java program. If authenticate() is true for a Player2 return
			// that Player2 object - otherwise return null. The idea is that when a user 
			// wants to login to play the game they will be asked to enter their firstName,
			// lastName and password and if those authenticate to a Player2 in the
			// PlayerGroup then that Player2 will be returned and can play the game.
			
			// The second version of the login() method takes 2 String arguments (the first
			// name and last name but not a password) and calls the forgotPassword() method
			// for Player2s in the PlayerGroup. The forgotPassword() method will allow the
			// Player2 to reset their password and will return true if the fName and lName
			// arguments match and they answer the challenge question correctly. See more
			// information on forgotPassword() in the Player2Test.java file. The idea of the
			// second version of login() is that it will allow a user who has forgotten their
			// password to reset it and log in as long as they can answer the challenge
			// question.
			
			// Try both versions of login in sequence. If both return null then the user
			// is not logged in. Considering the data above, Marge, Fezzik and Indiana 
			// should succeed with the first login while Inigo and Vizzini will fail.
			// In the second login the success depends on user input. In my first test run I
			// answer the challenge question successfully for Inigo but not for Vizzini. In
			// the second run I answer it successfully for Vizzini.s
			Player2 play = allPlayers.login(fName, lName, pass); 
			if (play == null)
				play = allPlayers.login(fName, lName);
			if (play != null)
			{
				System.out.println(play.toString() + " has logged in and can play!");
				// The methods below can be used to update a GamePlayer during the game (after each
				// completed round).  These are the same as they were for Assignment 2.
				System.out.println("\t\tSimulating a win and a loss for " + play.getName());
				System.out.println();
				play.playedARound(true, 100.0, 2);
				play.playedARound(false, 50, 4);
				System.out.println(play.toString());
				System.out.println();
			}
			else 
			{
				System.out.println(fName + " " + lName + " could not log in...\n");
			}
		}
		// Once again show all of the Player2s in the PlayerGroup. Note which have been
		// added and which have been updated.
		System.out.println("Here are the updated player stats: ");
		System.out.println(allPlayers.toString());
		
		System.out.println("Current list size is: " + allPlayers.size());
		System.out.println("Current array capacity is: " + allPlayers.capacity());
		
		// Write the players back to a text file so that they can be retrieved later.
		// Clearly to write the Player2 information to the file, the PlayerGroup class must
		// be able to get the information from each of the Player2 objects. The idea of this
		// method is as follows:  Iterate through the list of players, writing the information 
		// for each player back to the file. The information for each Player2 will be obtained
		// via the fileString() method. Thus, the method
		//     public String fileString() {}
		// in class Player2 will return all of the information from the Player2 appended
		// together as a single comma separated String. Note that this is NOT the same as 
		// toString(), since toString() has formatting in it.  fileString() is simply the raw
		// data in the format necessary for the file. Also note that toString does NOT have
		// the password or challenge question / answer in it (since we would not want to
		// display that) but fileString() DOES have those things since they are needed to
		// restore the Player2 later.
		allPlayers.savePlayers();
		
		// To see the effect of this program and its output, see the file playertest-out.txt. Note that
		// the contents of this file shows two runs of the program and also the contents of the data
		// files before and after the runs.
	}		
}