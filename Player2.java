import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Player2 {
    // Store the player's identity, money, debt, and historical record.
    private String firstName;
    private String lastName;
    private double availableMoney;
    private double borrowedMoney;
    private int roundsPlayed;
    private int roundsWon;
    private String password;
    private String challengeQuestion;
    private String challengeAnswer;
    Scanner Sc = new Scanner(System.in);

public Player2(String playerString){
    String[] players = playerString.split(",");
    firstName = players[0];
    lastName = players[1];
    password = players[2];
    challengeQuestion = players[3];
    challengeAnswer = players[4];
    availableMoney = Double.parseDouble(players[5]);
    borrowedMoney = Double.parseDouble(players[6]);
    roundsPlayed = Integer.parseInt(players[7]);
    roundsWon = Integer.parseInt(players[8]);
}


public boolean authenticate( String fName, String lName, String pass){
    return fName.equals(firstName) && lName.equals(lastName) && password.equals(pass);
}

public boolean forgotPassword(String fName, String lName){

    if (!fName.equals(firstName) || !lName.equals(lastName)) return false;


    System.out.println(challengeQuestion);
    String answer = Sc.nextLine();
    if (!answer.equals(challengeAnswer)) return false;

    String newPass1, newPass2;
    do{
        System.out.print("Enter new password: ");
        newPass1 = Sc.nextLine();
        System.out.print("Confirm new password: ");
        newPass2 = Sc.nextLine();
    } while (!newPass1.equals(newPass2));
password = newPass1;
return true;
}

public String fileString(){
    StringBuilder builder = new StringBuilder();
    builder.append(firstName+","+lastName+","+password+","+challengeQuestion+","+challengeAnswer+","+availableMoney+","+borrowedMoney+"," +roundsPlayed+","+roundsWon);
    return builder.toString();
}

public Player2(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;

    Scanner Sc = new Scanner(System.in);
    String pass1, pass2;
    do{
        System.out.print("Enter password: ");
        pass1 = Sc.nextLine();
        System.out.print("Confirm password: ");
        pass2 = Sc.nextLine();
    }
    while (!pass1.equals(pass2));
    password = pass1;

    System.out.println("Enter challenge question: ");
    challengeQuestion = Sc.nextLine();
    System.out.println("Enter challenge answer: ");
    challengeAnswer = Sc.nextLine();

    double money;
    do{
        System.out.println("Enter intital money (>0): ");
        money = Sc.nextDouble();
    }
    while (money <= 0 );
    availableMoney = money;

}


    public String getName() {

        return firstName + " " + lastName;
    }



    public double getMoney() {
        return availableMoney;
    }



    public double getBorrowed() {
        return borrowedMoney;
    }



    public void playedARound(boolean sucess, double bet, int odds) {
        roundsPlayed++;
        if (sucess) {
            roundsWon++;
            double winnings = bet * odds;

            // Pay existing debt before adding winnings to available money.
            if (borrowedMoney > 0) {
                if (winnings >= borrowedMoney) {
                    winnings -= borrowedMoney;
                    borrowedMoney = 0;
                    availableMoney += winnings;
                } else {
                    borrowedMoney -= winnings;

                }


            } else {
                availableMoney += winnings;
            }

        } else {
            // Any loss beyond the available money becomes debt.
            if (bet <= availableMoney) {
                availableMoney -= bet;
            } else {
                borrowedMoney += bet - availableMoney;
                availableMoney = 0;
            }
        }
        // Apply ten percent interest once when debt remains after a round.
        if (borrowedMoney > 0) {
            borrowedMoney =
                    Math.round(borrowedMoney * 1.1 * 100.0) / 100.0;
        }
    }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tName: ").append(getName()).append("\n");
        sb.append("\tMoney left: $").append(String.format("%.2f", availableMoney)).append("\n");
        if (borrowedMoney > 0) {
            sb.append("\tMoney owed: $").append(String.format("%.2f", borrowedMoney)).append("\n");
        }
        sb.append("\tTotal Rounds Played: ").append(roundsPlayed).append("\n");
        sb.append("\tTotal Rounds Won: ").append(roundsWon).append("\n");
        return sb.toString();
    }
    public boolean setAvailableMoney(double amount) {
        if (!Double.isFinite(amount) || amount < 0) return false;
        availableMoney = amount;
        return true;
    }



}
