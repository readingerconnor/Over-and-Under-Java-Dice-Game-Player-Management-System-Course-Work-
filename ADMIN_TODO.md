# Extra-credit administrator: updated checklist

Updated October 29, 2026 against the current Admin.java, PlayerGroup.java, and Player2.java.

All Java files compile. Running java Admin currently creates the Admin object and exits without displaying anything. The next step is to connect the existing methods through main(). This guide now follows your instance-method design and your actual method names; you do not need to rewrite the class using static helpers or introduce a username.

Checked items mean implemented and reviewed unless a runtime test is explicitly noted. The earlier deletion tests passed; the complete admin interface is not yet implemented or tested.

## What you have finished

- [x] Create Admin.java with a separate administrator password.
- [x] Add main() and create the object with `Admin admin = new Admin();`.
- [x] Write Options() to display choices 1-4 and repeat for out-of-range numeric choices.
- [x] Consume the newline after sc.nextInt() in Options().
- [x] Fix SignIn() so a retry updates the attempted password rather than the stored password.
- [x] Add ViewPlayers(group), which returns the formatted group string.
- [x] Add DeletePlayer(group), which reads both names, calls removePlayer(), and reports success or a missing player.
- [x] Implement PlayerGroup.removePlayer() with array shifting, size reduction, and clearing the unused slot. Earlier tests covered first/middle/last/only/missing/empty cases and persistence.
- [x] Implement PlayerGroup.getPlayer() to return the matching stored player or null. Its package access works with Admin in your current default package.
- [x] Correct Player2.setAvailableMoney(): use its argument, accept zero, reject negative/non-finite values, and leave other fields unchanged.
- [x] Keep the underlying player array private; the old getPlayers() array accessor is gone.
- [x] Compile the full project successfully on this review.

## Where to work next

These line numbers refer to the files reviewed on October 29. Use the method names as anchors after inserting code, since line numbers will shift.

| File and current lines | Work |
| --- | --- |
| Admin.java:1-4 | Remove unused compiler/Swing imports; add java.io.IOException for loading/saving. |
| Admin.java:8 | Optionally make the stored password private final. |
| Admin.java:9-11 | Expand the existing main(): prompt, authenticate, load group, run menu, save/exit. |
| Admin.java:30-43 | Fix SignIn() so all three intended attempts are checked. |
| Admin.java:45-47 | Reuse ViewPlayers(); print the returned String from main(). |
| Admin.java:48-64 | Add lookup/display/confirmation before the removal at current line 55. |
| Admin.java:65-68 | Add the missing balance-editing helper, before the class's closing brace at line 69. |
| PlayerGroup.java:100-125 | Reuse removePlayer() and getPlayer(); no new search helper is necessary. |
| Player2.java:164-168 | Reuse the corrected setter; input prompts belong in Admin. |
| InfoSheet.txt | List Admin.java and describe how to run/test the extra credit. |

## 1. Fix administrator password retries

**Location: the existing SignIn(String password), Admin.java:30.**

- [ ] Replace the current retry control flow. `attempts < 2` currently checks only two passwords, then reads a third password that is never checked.
- [ ] Count the argument supplied by main() as attempt 1.
- [ ] Check each attempt before deciding whether to prompt for another.
- [ ] After the third incorrect attempt, return false without prompting again.

One pattern that preserves your existing parameter and scanner is:

```java
for (int attempt = 1; attempt <= 3; attempt++) {
    if (password.equals(this.password)) {
        return true;
    }
    if (attempt < 3) {
        System.out.print("Wrong password. Try again: ");
        password = sc.nextLine();
    }
}
return false;
```

- [ ] Optionally change the field at current line 8 to `private final String password = "Admin_123";` so the stored password cannot be reassigned.

Do not use `this.password = sc.nextLine()` for a login attempt. That would replace the credential instead of checking it.

## 2. Connect login and loading in main()

**Location: expand the main() you already wrote, Admin.java:9-11. Do not add a second main().**

- [ ] Remove `import com.sun.tools.javac.Main;`. Your own main method does not need this compiler-specific import.
- [ ] Remove the unused Swing import and add `import java.io.IOException;`.
- [ ] Add `throws IOException` to main() because PlayerGroup loading and saving use file I/O.
- [ ] Keep your existing Admin object, prompt through its scanner, and call its SignIn() method.
- [ ] Return immediately if authentication fails.
- [ ] Load the group only after successful authentication.

Use this structure as you expand the current method:

```java
public static void main(String[] args) throws IOException {
    Admin admin = new Admin();

    System.out.print("Enter administrator password: ");
    String attemptedPassword = admin.sc.nextLine();
    if (!admin.SignIn(attemptedPassword)) {
        System.out.println("Access denied.");
        return;
    }

    PlayerGroup group = new PlayerGroup("players.txt");
    // Put the menu loop from the next section here.
}
```

Your instance methods belong to the admin object, so call `admin.Options()` and `admin.DeletePlayer(group)` from static main(). You do not need to make those methods static.

## 3. Connect the menu choices

**Location: inside main(), after the PlayerGroup is loaded.**

- [ ] Add a loop that calls Options() each time so the menu repeats.
- [ ] Use integer cases because your Options() returns Integer, not String.
- [ ] Print the String returned by ViewPlayers().
- [ ] Route option 2 to your existing DeletePlayer().
- [ ] Route option 3 to the ChangeBalance() helper you will add below.
- [ ] Save and exit only when option 4 is selected.

```java
boolean running = true;
while (running) {
    int choice = admin.Options();
    switch (choice) {
        case 1:
            System.out.println(ViewPlayers(group));
            break;
        case 2:
            admin.DeletePlayer(group);
            break;
        case 3:
            admin.ChangeBalance(group);
            break;
        case 4:
            group.savePlayers();
            System.out.println("Changes saved.");
            running = false;
            break;
    }
}
```

This fragment requires the ChangeBalance() method from section 5 before it will compile. ViewPlayers() is already static, so calling it directly inside main() is appropriate.

- [ ] Explain in the menu that modifications remain in memory until Save and exit.
- [ ] Print "Changes saved" only after savePlayers() returns successfully.

Hint: `break` leaves the switch. Setting running to false ends the surrounding loop. Run Admin and Assig3 one at a time because both load and later save the same database.

## 4. Add confirmation to your existing deletion helper

**Location: inside DeletePlayer(), after reading lName and before group.removePlayer() at current line 55.**

Your deletion algorithm in PlayerGroup is already implemented. Keep it there.

- [ ] Look up the target with your existing getPlayer() method.
- [ ] If it returns null, report that no player was found and return false.
- [ ] Display the target's statistics and ask the administrator to type DELETE.
- [ ] If the response is anything else, cancel and return false without calling removePlayer().

```java
Player2 target = group.getPlayer(fName, lName);
if (target == null) {
    System.out.println("Player not found.");
    return false;
}
System.out.println(target);
System.out.print("Type DELETE to confirm: ");
if (!sc.nextLine().equals("DELETE")) {
    System.out.println("Deletion canceled.");
    return false;
}
```

Then let your existing removePlayer() call and success reporting run. This method returns boolean, so use `return false;`, not a bare `return;`.

## 5. Add the balance-editing helper

**Location: Admin.java, current blank lines 65-68, after DeletePlayer() and before the final class brace.**

- [ ] Add an instance helper with this signature:

```java
private void ChangeBalance(PlayerGroup group) {
    // Read names, find the player, get a valid amount, confirm, update.
}
```

- [ ] Read first and last names using the existing sc field.
- [ ] Find the actual stored player:

```java
Player2 target = group.getPlayer(fName, lName);
if (target == null) {
    System.out.println("Player not found.");
    return;
}
```

- [ ] Display the current player. Explain that this operation replaces available cash only; debt and round history stay unchanged.
- [ ] Read a nonnegative finite amount. Using nextLine() for this helper avoids introducing another leftover numeric-input newline:

```java
double amount;
while (true) {
    System.out.print("New cash balance (0 or greater): ");
    try {
        amount = Double.parseDouble(sc.nextLine());
        if (Double.isFinite(amount) && amount >= 0) {
            break;
        }
    } catch (NumberFormatException ex) {
        // Retry below if the input is not a number.
    }
    System.out.println("Enter a finite number that is 0 or greater.");
}
```

- [ ] Display the proposed value with two decimal places and request confirmation.
- [ ] On cancellation, return without changing the player.
- [ ] Use the corrected setter and display its result:

```java
if (target.setAvailableMoney(amount)) {
    System.out.println("Cash balance updated.");
    System.out.println(target);
} else {
    System.out.println("Invalid cash balance; nothing changed.");
}
```

The object returned by getPlayer() belongs to group, so its changed cash will be included in group.savePlayers(). Do not create a replacement Player2 or add the same player again.

## 6. Verify the completed interface

Back up players.txt or test in a separate directory with a copy. Previous data-method checks do not replace testing the full menu once it is connected.

- [ ] Compile all Java files with `javac *.java`.
- [ ] Run `java Admin`: it should now prompt instead of exiting silently.
- [ ] Correct password on attempt 1, 2, and 3: each reaches the menu.
- [ ] Three wrong attempts: access denied, no fourth prompt, no player data displayed or modified.
- [ ] Enter 0 or 5 at the menu: repeat until a valid selection is entered. Optionally handle letters too; nextInt() currently expects numeric input.
- [ ] View players and return to the menu; verify counts, balances, debt, and statistics are displayed without player passwords.
- [ ] Delete a missing player: no change. Cancel a deletion: no change.
- [ ] Confirm first/middle/last/only-player deletion and deletion from an empty group. Earlier direct method checks passed; verify the menu and confirmations too.
- [ ] Change cash to 0 and 125.50, verifying debt, history, and credentials remain unchanged.
- [ ] Reject negative/non-finite amounts and nonnumeric text in the balance helper without changing the original balance.
- [ ] Cancel a cash edit: no change.
- [ ] Perform several different menu actions in one session; check that name prompts are not skipped.
- [ ] Save and exit, then reopen Admin: changes persist.
- [ ] Open Assig3: the edited player retains their login and new cash amount; a deleted name is treated as a new registration.
- [ ] Re-run the provided class tests using restored sample data. Keep both consecutive PlayerGroupTest runs together since the second uses the first run's saved changes.
- [ ] Restore the intended submission players.txt after testing.

## 7. Finish documentation and submission

- [ ] Add brief comments for the retry limit, cash-only update, and menu actions.
- [ ] Include Admin.java in the additional-files list in InfoSheet.txt.
- [ ] Fill in the extra-credit section: separate admin login, viewing, confirmed deletion, cash editing, and persistent saving.
- [ ] Document `java Admin`, the actual demo password, and option 4 as Save and exit. Do not claim the complete interface works until the tests above pass.
- [ ] Keep the original required Java files, players.txt, and the completed information sheet in the submission ZIP, along with Admin.java.
- [ ] Exclude .class files, backups, IDE folders, review scripts, and checklist documents from the ZIP.

**Next action: fix SignIn()'s attempt handling, then expand the main() you already have. The setter, lookup, and array deletion are ready to reuse.**
