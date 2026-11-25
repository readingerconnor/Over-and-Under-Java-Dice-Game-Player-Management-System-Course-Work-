import java.util.*;
import java.io.*;

public class Assig3 {
    public static void main(String[] args) throws IOException {

        Scanner inScan = new Scanner(System.in);

        // Use two separate dice for every round.
        Die die1 = new Die();
        Die die2 = new Die();
        PlayerGroup pg = new PlayerGroup("players.txt");


        boolean moreplayers = true;
        boolean playing;
        // Keep accepting players until an empty last name is entered.
        while (moreplayers) {
            System.out.print("Welcome to Over and Under");
            System.out.print("What is you first name or empty to quit: ");
            String firstname = inScan.nextLine();
            if(firstname.isEmpty()) break;
            System.out.print(" Please enter your last name: ");
            String lastname = inScan.nextLine();
            Player2 player;
            if (pg.containsName(firstname, lastname)) {
                System.out.print("Please enter your password: ");
                String password = inScan.nextLine();
                player = pg.login(firstname, lastname, password);
                if (player == null) {
                    System.out.println("Incorrect password. Try password recovery.");
                    player = pg.login(firstname, lastname);
                }
                if (player == null) {
                    System.out.println("Login failed. Please enter a player name again.");
                    continue;
                }
            } else {
                player = new Player2(firstname, lastname);
                pg.addPlayer(player);
            }

            System.out.println("Here is your information");
                System.out.println(player);

                playing = true;
                int wins = 0;
                int losses = 0;
                int rounds = 0;
                double netMoney = 0;
                // Include both available money and debt in the starting value.
                double startingNet = player.getMoney() - player.getBorrowed();
                while (playing) {
                    boolean validAns = false;
                    System.out.print("would you like to play a round(yes or no)");
                    String answer = inScan.nextLine();
                    while (validAns == false) {
                        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
                            validAns = true;
                        } else {
                            System.out.println("Invalid , try again");
                            answer = inScan.nextLine();
                        }

                    }
                    double bet = 0;
                    if (answer.equalsIgnoreCase("yes")) {

                        boolean nonNeg = false;
                        // Require a nonnegative bet and consent to any borrowing.
                        while (nonNeg == false) {
                            System.out.println("How much would you like to bet?");
                            bet = inScan.nextDouble();
                            inScan.nextLine(); // Consume the newline after the number.

                            if (bet < 0) {
                                System.out.println("Invalid, try again.");
                            } else if (bet > player.getMoney()) {
                                double needed = bet - player.getMoney();
                                System.out.printf(
                                        "You need to borrow $%.2f. Continue? (yes or no): ",
                                        needed
                                );

                                String borrow = inScan.nextLine();
                                while (!borrow.equalsIgnoreCase("yes")
                                        && !borrow.equalsIgnoreCase("no")) {
                                    System.out.print("Please enter yes or no: ");
                                    borrow = inScan.nextLine();
                                }

                                if (borrow.equalsIgnoreCase("yes")) {
                                    nonNeg = true;
                                } else {
                                    System.out.println("Please enter a different bet.");
                                }
                            } else {
                                nonNeg = true;
                            }
                        }

                        System.out.println("Do you think you will roll a seven, under, or over");
                        String decision = inScan.nextLine();

                        validAns = false;
                        // Repeat until the prediction is valid.
                        while (validAns == false) {
                            if (decision.equalsIgnoreCase("over") || decision.equalsIgnoreCase("under") || decision.equalsIgnoreCase("seven")) {
                                validAns = true;
                            } else {
                                System.out.println("Invalid , try again");
                                decision = inScan.nextLine();
                            }
                        }
                        decision = decision.toLowerCase();
                        int roll1 = die1.roll();
                        int roll2 = die2.roll();
                        int result = roll1 + roll2;

                        System.out.println("Die 1: " + roll1);
                        System.out.println("Die 2: " + roll2);
                        System.out.println("Total: " + result);

                        boolean win = false;
                        int odds = 0;
                        if (decision.equals("seven") && result == 7) {
                            System.out.println("The result is seven");
                            win = true;
                            odds = 4;
                        } else if (decision.equals("under") && result < 7) {
                            System.out.println("The result is under");
                            win = true;
                            odds = 1;
                        } else if (decision.equals("over") && result > 7) {
                            System.out.println("The result is over");
                            win = true;
                            odds = 1;
                        } else {
                            String rollResult;

                            if (result < 7) {
                                rollResult = "under";
                            } else if (result == 7) {
                                rollResult = "seven";
                            } else {
                                rollResult = "over";
                            }
                            System.out.println("The result is " + rollResult);
                        }
                        if (win) {
                            System.out.println("You won!");
                        } else {
                            System.out.println("You lost!");
                        }

                        // Let Player update all money, debt, and historical totals.
                        player.playedARound(win, bet, odds);
                        System.out.println(player);
                        // These counters apply only to the current session.
                        if (win) {
                            wins++;
                            rounds++;
                        } else {
                            losses++;
                            rounds++;
                        }

                    } else {
                        playing = false;
                        System.out.println("Thank you for playing");

                    }

                }
                // Calculate how much the player gained or lost this session.
                double endingNet = player.getMoney() - player.getBorrowed();
                netMoney = endingNet - startingNet;
                System.out.println("Game Results");
                System.out.println("Rounds played: " + rounds);
                System.out.println("Rounds won: " + wins);
                System.out.println("Rounds lost: " + losses);
                System.out.println("Net gain/loss: $" + String.format("%.2f", netMoney));
                System.out.println(player);
                // Save the updated historical information for the next login.

            }
        System.out.println("Final player statistics:");
        System.out.println(pg);
        pg.savePlayers();
        System.out.println("No more players. Goodbye for now");
        }


    }






