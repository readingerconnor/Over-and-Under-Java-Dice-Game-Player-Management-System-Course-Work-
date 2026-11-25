# Over and Under

A Java console dice game featuring player authentication, persistent balances and statistics, borrowing with interest, and a separate administrator interface.

## About

Developed for CMPINF 0401: Intermediate Programming at the University of Pittsburgh, this project extends an earlier Over and Under game with password authentication, security-question password recovery, and a shared player database. An extra-credit administrator program adds player viewing, cash-balance editing, and player removal.

The project practices object-oriented programming, encapsulation, array manipulation, file I/O, and input validation. Player records are managed with a manually resized Java array.

## Features

- Roll two six-sided dice and bet on a total below, above, or exactly seven.
- Register new players or authenticate returning players by name and password.
- Reset a forgotten password by answering the player's security question.
- Track available cash, outstanding debt, rounds played, and rounds won.
- Borrow to cover a bet, with interest applied to remaining debt after each round.
- Save accounts and cumulative statistics between sessions.
- Manage player records through a separately authenticated administrator program.

## Requirements

- A Java Development Kit (developed and tested with JDK 25).
- A terminal or Java IDE.
- The included `players.txt` file in the program's working directory.

The application uses the Java standard library and does not require external dependencies.

## Compile and run

Download or clone the repository, then open a terminal in the directory containing the `.java` files and `players.txt`.

Compile all source files:

```bash
javac *.java
```

Start the game:

```bash
java Assig3
```

1. Enter your first and last names. Leave the first name blank to exit.
2. For an existing account, enter its password. An incorrect password leads to security-question recovery.
3. For a new account, choose and confirm a password, supply a security question and answer, and enter a positive starting balance.
4. Answer `yes` to play a round, enter your bet, and select `under`, `over`, or `seven`.
5. Answer `no` to finish the current player's turn. Another player can then log in.
6. At the next first-name prompt, press Enter without typing a name to display final player statistics, save all records, and exit.

## Game rules

| Prediction | Winning total | Profit on a winning bet |
| --- | --- | --- |
| `under` | 2 through 6 | Equal to the bet |
| `seven` | 7 | Four times the bet |
| `over` | 8 through 12 | Equal to the bet |

A losing player loses the bet amount. Bets cannot be negative. If a bet exceeds available cash, the game asks for confirmation to borrow the difference.

Winnings first repay outstanding debt. After each round, any remaining debt increases by 10%. Player statistics and the session's win/loss record and net gain or loss are displayed during play.

## Administrator program

Run the administrator separately:

```bash
java Admin
```

The classroom demonstration password is **`Admin_123`**. Three incorrect attempts deny access.

| Option | Action |
| --- | --- |
| `1` | View all players and their statistics. |
| `2` | Find a player by first and last name, then type `DELETE` to confirm removal. |
| `3` | Find a player and replace their available cash balance; type `yes` to confirm. |
| `4` | Save changes to `players.txt` and exit. |

Cash editing accepts zero or a positive finite amount and leaves the player's debt, history, and credentials unchanged. Deletion and cash changes can be canceled before they are applied.

**Choose option 4 to save admin changes.** Run the game and administrator one at a time: both load and later overwrite the same player file. Back up `players.txt` before experimenting with deletion or balance edits.

## Project structure

| File | Purpose |
| --- | --- |
| `Assig3.java` | Main game, player entry, betting, and session summaries. |
| `Player2.java` | Player data, authentication, password recovery, finances, and statistics. |
| `PlayerGroup.java` | Array-backed player storage, searching, resizing, deletion, and file persistence. |
| `Die.java` | Random rolls from 1 through 6. |
| `Admin.java` | Administrator login and player-management menu. |
| `players.txt` | Shared player records and sample accounts. |
| `Player2Test.java` | Course-provided test program for player behavior. |
| `PlayerGroupTest.java` | Course-provided test program for group behavior and persistence. |

`PlayerGroup` starts with capacity 3 and doubles its array when necessary. Deleting a player shifts later entries left and reduces the logical size while preserving capacity.

## Player data format

Each line in `players.txt` contains one record in this order:

```text
firstName,lastName,password,question,answer,money,borrowed,rounds,won
```

The coursework implementation stores passwords and challenge answers as plain text and uses commas as field separators. Use demonstration credentials and avoid commas inside field values.

## Testing

After compiling, run the supplied tests interactively:

```bash
java Player2Test
java PlayerGroupTest
```

- Follow the responses in `p2testinput.txt` for the player test and compare the displayed statistics with `Player2Test-out.txt`.
- Start the group test with the original sample `players.txt`, follow `pgtestinput.txt`, and compare results with `PlayerGroupTest-out.txt`.
- Run the group test a second time using the first run's saved file and the second-run responses. This checks that additions and updates persist.
- Enter responses when prompted. The input guides include explanatory text and are not intended to be redirected wholesale into the programs.

For the administrator, check successful and failed logins, invalid menu choices, canceled and confirmed deletions, invalid cash amounts, and saving/reopening the database. Launch the game afterward to verify that edited accounts retain their credentials and updated balances.

## Course context

Created by Connor Readinger for Intermediate Programming at the University of Pittsburgh. The test programs and sample test materials were supplied as part of the course assignment.
