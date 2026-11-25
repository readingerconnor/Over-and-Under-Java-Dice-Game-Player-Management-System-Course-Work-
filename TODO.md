# Assignment 3 to-do list

Due: **Monday, November 2, 2026, at 11:59 PM**. Late deadline: Thursday, November 5, 2026, at 11:59 PM.

Updated after the latest code review on October 25, 2026. Player2, PlayerGroup, Die, and both provided test programs compile. Player2Test and two consecutive PlayerGroupTest runs finish with exit code 0 when responses are entered at each prompt. Their output still does not match the required formatted statistics. Compiling all Java files fails with three errors in Assig3.java, so the main game could not be run.

Tests used a temporary copy of players.txt; your source code and original player data were unchanged. Checked items indicate implemented work; testing claims are stated explicitly.

**Next task: repair Assig3's braces and login flow, restore Player2.toString(), and add the total player count to PlayerGroup.toString().**

## Fix these next

- [ ] Fix the closing braces near Assig3.java:166-173. The goodbye print is currently outside main(), and the final brace is extra. Keep the final summary, saving, and goodbye inside main() after the player loop.
- [ ] Rewrite Assig3.java:25-38 around containsName(). A new name currently goes through two failed recovery calls, leaves player null, and would crash at player.getMoney(). New names should create a Player2 and add it to the group.
- [ ] For existing players, assign the result of password login, then call recovery at most once if needed and assign that result. The current successful-recovery branch creates a duplicate player instead; addPlayer() rejects it, and the game would play with an object outside the group. If recovery fails, return to player entry before accessing player.
- [ ] Restore Player2.toString(). All three test runs printed Player2@... instead of names, balances, debt, and round totals.
- [ ] Add the Total Players count to PlayerGroup.toString(), followed by each player's formatted statistics.
- [ ] Move pg.savePlayers() to after the outer loop and print the group's cumulative statistics there. It currently saves after each player's turn and never prints the final group summary.

## Current status

- [x] Required Java source files, assignment PDF, test input guides, expected outputs, and players.txt are present.
- [x] Fix the two-argument Player2 constructor header and add getFirstName()/getLastName().
- [x] Implement Player2's data parsing, authentication, password reset, and fileString().
- [x] Implement PlayerGroup loading, duplicate-name checks, array resizing, both login methods, size/capacity, and saving.
- [x] Compile Player2, PlayerGroup, Die, Player2Test, and PlayerGroupTest together.
- [x] Execute Player2Test and both PlayerGroupTest runs without an exception, with prompt-by-prompt input and temporary player data. Formatting still needs correction.
- [ ] Get Assig3 compiling and repair registration/recovery before testing gameplay.
- [ ] Complete both classes' formatted output and compare it with the expected output files.
- [ ] Add a completed Assignment Information Sheet.

## 1. Bring over the Assignment 2 foundation

- [x] Bring over the player implementation as Player2.java and rename the class and constructors.
- [x] Bring over Assig3.java and Die.java.
- [x] Preserve the existing dice rolls, round logic, session counters, and multiple-player loop.
- [ ] Review the Assignment 2 betting and borrowing requirements before final gameplay testing.
- [ ] Keep your own backup of the original players.txt for repeatable testing. Review runs used a separate temporary copy.
- [ ] Recheck the main game's initial quit path after fixing compilation. The earlier October 19 result applies only to the old code.

## 2. Finish Player2 before final integration

- [x] Retain player names, money, debt, and round counters; add password, challenge question, and challenge answer fields.
- [x] Parse all nine comma-separated fields in Player2(String playerString), in the required order: firstName,lastName,password,question,answer,money,borrowed,rounds,won.
- [x] Provide Player2(String firstName, String lastName). Its body repeats password entry until matching and loops for positive initial money. Debt and round counters default to zero.
- [x] Preserve getName(), getMoney(), getBorrowed(), and playedARound(); add the individual name getters used by PlayerGroup.
- [x] Implement authenticate() and forgotPassword(), including name checks, challenge verification, and confirmed password replacement.
- [x] Replace saveToFile() with fileString(). The provided Player2Test restored the saved player and produced the expected updated CSV values: password hello, money 200.0, debt 0.0, rounds 2, wins 1.
- [x] Run Player2Test through mismatched password confirmation, two incorrect login passwords, successful login, and successful password recovery without an exception.
- [ ] Restore toString() with the player's name, money, debt when applicable, rounds played, and rounds won. Do not display passwords or challenge details.
- [ ] Remove the unused Scanner field at Player2.java:17. new Scanner("players.txt") scans that literal text rather than opening the file; file loading belongs in PlayerGroup. Remove unused file-related imports if desired.
- [ ] Verify zero/negative starting money, wrong names, wrong challenge answers, and mismatched reset-password confirmation. These additional cases were not all exercised by the supplied Player2Test run.
- [ ] Check Scanner input transitions. Prompt-by-prompt tests worked, but several Scanners share System.in and the constructor leaves the newline after nextDouble(). Check for skipped or buffered responses; do not close System.in.
- [ ] Re-run the unchanged Player2Test after restoring formatted output and compare with Player2Test-out.txt. Use the actual responses in p2testinput.txt, not its explanatory lines.

## 3. Finish PlayerGroup

- [x] Initialize the remembered filename, a regular Player2 array of capacity 3, and logical size zero; load each line into a Player2.
- [x] Fix addPlayer(p) to addPlayer(player) in the constructor.
- [x] Implement containsName() using both first and last names.
- [x] Implement addPlayer() with duplicate-name rejection, resizing when full, insertion, and size tracking.
- [x] Double array capacity and copy existing player references when resizing. Do not use ArrayList or another collection.
- [x] Implement password login using authenticate(), returning the stored Player2 or null.
- [x] Implement recovery login using forgotPassword(), returning the stored Player2 or null.
- [x] Implement size() and capacity(). The first supplied test run started at 5 players/capacity 6 and ended at 7/capacity 12; the second remained at 7/capacity 12.
- [x] Implement savePlayers() using each player's fileString() and the remembered filename. The second test run loaded the saved additions and password changes.
- [x] Execute both provided PlayerGroupTest runs. Inigo's recovery succeeded in run 1; Vizzini's failed in run 1 and succeeded in run 2.
- [ ] Complete toString(): add the total player count and use the restored Player2.toString() for each player. Current output contains object identifiers and omits the total-player heading.
- [ ] Close fileScan after loading, or use try-with-resources. The current constructor leaves the file Scanner open.
- [ ] Explicitly test addPlayer() rejecting a duplicate without changing size/capacity, and test absent-name and incorrect-password login returning null. The supplied program does not cover every failure case.
- [ ] Re-run both unchanged PlayerGroupTest executions and compare formatted output with PlayerGroupTest-out.txt. Restore the original players.txt before run 1; preserve the saved result for run 2.

## 4. Connect the game in Assig3

- [x] Load one PlayerGroup from players.txt before the player loop.
- [x] Ask for both first and last names and use Player2 as the current player type.
- [x] Replace the removed Player2.saveToFile() call with pg.savePlayers(). Its location still needs correction below.
- [ ] Fix the misplaced closing braces so all Java files compile.
- [ ] Use containsName() to separate new-player registration from existing-player login. Register only genuinely new names, using the two-argument constructor and addPlayer().
- [ ] For an existing name, try password login and then recovery if needed. Store each result; do not call recovery twice and do not construct a replacement for a successfully recovered player.
- [ ] If both login methods fail, skip gameplay and return to player entry. Never call methods on a null player or let an unregistered duplicate play.
- [x] Call playedARound() after each round and retain the end-of-turn session summary. Correct password login currently returns the stored player reference.
- [ ] Verify every successful path uses the group's stored Player2 so its changed money and statistics are saved.
- [ ] Restore Player2.toString() so per-round and end-of-turn prints show cumulative statistics.
- [ ] Confirm bets exceeding available money before rolling, as in the assignment's sample flow. The current code does not ask for borrowing consent.
- [ ] After the outer loop, display the cumulative statistics of all players and save the group to players.txt. Keep the goodbye message inside main().
- [x] Declare IOException in main().
- [ ] Test existing-player login, new registration, successful recovery, failed recovery, multiple players, multiple rounds, borrowing, and quitting. Assig3 could not be executed during this review because of its syntax errors.

## 5. Verify and submit

- [x] Compile the two player classes, Die, and both supplied test programs into a temporary build directory.
- [x] Run the supplied class tests with prompt-by-prompt input and a temporary players.txt copy. All three executions ended normally; formatted-output requirements remain unmet.
- [ ] Compile the entire project from the command line with javac *.java after fixing Assig3. The latest full compile reports three errors at lines 170 and 173.
- [ ] Compare class-test output with the supplied expected files after correcting formatting. Keep both test programs unchanged.
- [ ] Exercise the remaining input/error cases listed above, plus wins, losses, and Assignment 2 borrowing behavior.
- [ ] Restart the main game and verify new registrations, password resets, balances, and statistics persist. The group tests verified saving/loading in isolation; the game integration remains untested.
- [ ] Comment the new methods, tidy indentation/braces, and complete the Assignment Information Sheet.
- [ ] Create a ZIP containing at least Assig3.java, PlayerGroup.java, Player2.java, Player2Test.java, PlayerGroupTest.java, Die.java, players.txt, and the information sheet. Include other source/data files only if required by your implementation.
- [ ] Exclude .class files, IDE/project files, project subdirectories, review scripts/logs, this checklist/PDF, and the assignment PDF. Submit only Java source, text files, and the information sheet.
- [ ] Submit to Canvas by the deadline and be ready to demonstrate the program if selected.

Optional after the required work: add a separately authenticated administrator program or obtain instructor approval for new nontrivial game functionality. Extra credit is capped at 10 points and cannot reuse Assignment 2 extra credit.
