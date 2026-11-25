// CMPINF 0401 Fall 2026
// Skeleton of the PlayerGroup class.
// This class represents a collection of players -- a very simple database. 
// See details on the methods and other requirements in the Assignment 3
// document.

// Note the imports below. 
import java.util.*;
import java.io.*;

public class PlayerGroup {
	private Player2[] players; // Variable to store the players
	private int size;            // logical size of the PlayerGroup
	private String file;        // file name to restore and save players

	// Initialize the group from a file.  Note that the file name is a parameter.  You
	// must open the file, read the data, making a new Player2 object for each line and
	// putting them into the array.  Note that this method throws IOException.
	// Because of this, any method that CALLS this method must also either catch
	// IOException or throw IOException.  Note that the main() in PlayerGroupTest.java
	// throws IOException.  Keep this in mind for your main program (Assig3.java).
	// Note that your savePlayers() method will also need throws IOException in its header
	public PlayerGroup(String fName) throws IOException {
	file = fName;
	players = new Player2[3];
	size = 0;

	Scanner fileScan = new Scanner(new File(file));
	while (fileScan.hasNextLine()) {
		String line = fileScan.nextLine();
		Player2 player = new Player2(line);
		addPlayer(player);
	}
	fileScan.close();
	}

	public boolean addPlayer(Player2 newPlayer) {
		if (containsName(newPlayer.getFirstName(), newPlayer.getLastName())) return false;
		if(size == players.length) resize();
		players[size++] = newPlayer;
		return true;
	}
	public boolean containsName(String firstName, String lastName) {
		for (int i = 0; i < size; i++) {
			if (players[i].getFirstName().equals(firstName) && players[i].getLastName().equals(lastName))
			{ return true;
			}
		}
		return false;
	}
	public Player2 login(String firstName, String lastName, String pass) {
		for (int i = 0; i < size; i++) {
			if(players[i].authenticate(firstName, lastName, pass)){
				return players[i];
			}
		}
		return null;
	}
	public Player2 login(String fName, String lName) {
		for (int i = 0; i < size; i++) {
			if (players[i].getFirstName().equals(fName) &&
					players[i].getLastName().equals(lName)) {
				if (players[i].forgotPassword(fName, lName)) {
					return players[i];
				}
			}
		}
		return null;
	}
	public void savePlayers() throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		for (int i = 0; i < size; i++) {
			writer.println(players[i].fileString());
		}
		writer.close();
	}
	private void resize() {
		Player2[] newPlayers = new Player2[players.length*2];
		for (int i = 0; i < size; i++) {
			newPlayers[i] = players[i];
		}
		players = newPlayers;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Total Players: ").append(size).append("\n");
		for (int i = 0; i < size; i++) {
			sb.append(players[i].toString()).append("\n");
		}
		return sb.toString();
	}
	public int size(){
		return size;
	}
	public int capacity(){
		return players.length;
	}

	// Add to PlayerGroup
	public boolean removePlayer(String firstName, String lastName) {
		for (int i = 0; i < size; i++) {
			if (players[i].getFirstName().equals(firstName)
					&& players[i].getLastName().equals(lastName)) {

				// Move every player after this one left by one slot.
				for (int j = i; j < size - 1; j++) {
					players[j] = players[j + 1];
				}

				size--;
				players[size] = null; // Clear the now-unused last slot.
				return true;
			}
		}
		return false; // No matching player.
	}
	Player2 getPlayer(String firstName, String lastName) {
		for (int i = 0; i < size; i++) {
			if (players[i].getFirstName().equals(firstName)
					&& players[i].getLastName().equals(lastName)) {
				return players[i];
			}
		}
		return null;
	}
}



	// You must provide the remaining methods for this class so that it works
	// with PlayerGroupTest.java and with your main program Assig3.java