
/***************************************************************
 *
 * Bank program in JAVA
 * 
 * @author      Jay Parmar (@jay__s__p)
 * 
 * Original Program is here,
 * https://replit.com/@jaysp303/Online-Bank-System
 * 
 * 
 ***************************************************************/

import java.util.Scanner;

import com.javabank.bank.User;
import com.javabank.bank.Account;
import com.javabank.bank.Captcha;
import com.javabank.bank.Database;
import com.javabank.bank.DatabaseException;

public class App {

    private static User user = null;
    private static Account account = null;
    private static String err = null;
    private static Captcha c = new Captcha();
    private static Database db = new Database();
    private static final Scanner SC = new Scanner(System.in);

    private static byte choice;

    private static final String WELCOME = "\nWelcome to the JAVA Bank \n";
    private static final String LOGIN = " => Login <= ";
    private static final String REGISTER = " => Register <= ";
    private static final String DASHBOARD = " => DashBoard <= ";

    public static void main(String[] args) {

        loginRegister();

        home();

    }

    private static void loginRegister() {

        Boolean success = false;

        while (!success) {
            clrscr();
            println(WELCOME);

            if (err != null) {
                errorMessage(err);
                err = null;
            }

            println("\nPlease choose any option from this :");
            println("1. Login");
            println("2. Register");
            println("3. Exit");
            print("\n\nEnter your choice here : ");

            choice = SC.nextByte();

            clrscr();
            if (choice == 1) {
                success = login();
            } else if (choice == 2) {
                success = register();
            } else if (choice == 3) {
                exit();
            } else {
                err = "Invalid input !";
            }

        }

    }

    private static Boolean login() {
        println(LOGIN);
        print("\nEnter username  : ");
        String username = SC.next();
        print("Enter password  : ");
        String password = SC.next();
        user = db.loginValidate(username, password);
        if (user == null) {
            err = ("Login details are Invalid");
            return false;
        }
        return true;
    }

    private static Boolean register() {

        println(REGISTER);

        String username, password, fName, lName;
        print("\nEnter your first name   : ");
        fName = SC.next();
        print("Enter your last name    : ");
        lName = SC.next();

        do {
            clrscr();
            println(REGISTER);
            if (err != null) {
                errorMessage(err);
                err = null;
            }
            print("\nEnter username          : ");
            username = SC.next();
            if (!db.usernameAvailable(username)) {
                err = "Username already taken!";
                continue;
            }
            break;
        } while (true);

        print("Enter password          : ");
        password = SC.next();

        try {
            user = new User(fName + " " + lName, username, password);
            if (user == null)
                return false;
            else {
                db.addData(user, null);
                return true;
            }
        } catch (DatabaseException e) {
            clrscr();
            errorMessage(e.getMessage());
            System.exit(1);
        }
        return true;
    }

    private static void home() {
        clrscr();
        println(DASHBOARD);

        if (user.getAccountCount() <= 0) {
            registerAccount();
            clrscr();
            println(DASHBOARD);
        }

        account = db.getAccount(user.getAccounts().get(0));

        if (account == null) {
            clrscr();
            errorMessage("account data damaged :(");
            System.exit(1);
        }

        long amount = 0;
        String msg = null;
        while (true) {
            clrscr();
            println(DASHBOARD);
            if (err != null) {
                errorMessage(err);
                err = null;
            }
            if (msg != null) {
                successMessage(msg);
                msg = null;
            }
            println("\nHey " + user.getName() + ", welcome to Java Bank...");
            println("Please choose the operation you would like to perform :-");
            println("1. Check balance");
            println("2. Deposit money");
            println("3. Withdrawal money");
            println("4. Exit");
            print("\nEnter your choice here : ");

            choice = SC.nextByte();

            switch (choice) {
                case 1:
                    amount = account.getBalance();
                    msg = "\nYour balance is " + String.format("%,d", account.getBalance()) + "rs";
                    continue;
                case 2:
                    clrscr();
                    if (!captchaVerification())
                        continue;
                    clrscr();
                    print("\nPlease enter the amount you wish to deposit : ");
                    try {
                        amount = SC.nextLong();
                        account.debitBalance(amount);
                        msg = "\nThe deposit amount has been added to your account SUCCESSFULLY !";
                    } catch (DatabaseException e) {
                        clrscr();
                        errorMessage(e.getMessage());
                        System.exit(1);
                    } catch (Exception e) {
                        SC.nextLine();
                        err = e.getMessage();
                    }
                    continue;
                case 3:
                    clrscr();
                    if (!captchaVerification())
                        continue;
                    clrscr();
                    print("\nPlease enter the amount you wish to withdrawal : ");
                    try {
                        amount = SC.nextLong();
                        account.creditBalance(amount);
                        msg = "\nYour withdrawal of " + amount + "rs has been SUCCESSFUL !";
                    } catch (DatabaseException e) {
                        clrscr();
                        errorMessage(e.getMessage());
                        System.exit(1);
                    } catch (Exception e) {
                        SC.nextLine();
                        err = e.getMessage();
                    }
                    continue;

                case 4:
                    exit();

                default:
                    err = "Invalid input !";
                    continue;
            }
        }

    }

    private static void registerAccount() {

        long money = 0;
        while (true) {

            clrscr();
            println(DASHBOARD);

            if (err != null) {
                errorMessage(err);
                err = null;
            }

            println("\nHey, " + user.getName() + ",\nYou don't have any account...\n");
            println("1. Create a account");
            println("2. Exit");
            print("\nEnter your choice here : ");

            choice = SC.nextByte();

            clrscr();
            println(DASHBOARD);
            if (choice == 1) {
                print("\nPlease fill the following details to create your account...\n\n");
                print("Enter money more than " + Account.MIN_BALANCE + "rs to create account : ");
                money = SC.nextLong();
                if (money <= Account.MIN_BALANCE) {
                    clrscr();
                    println(DASHBOARD);
                    err = "Money must be grater than " + Account.MIN_BALANCE + " to create account !";
                    continue;
                }
                try {
                    account = user.createAccount(money, "saving");
                    return;
                } catch (DatabaseException e) {
                    clrscr();
                    errorMessage(e.getMessage());
                    System.exit(1);
                } catch (Exception e) {
                    err = e.getMessage();
                }
            } else if (choice == 2) {
                exit();
            } else {
                err = "Invalid input !";
            }

        }
    }

    static void clrscr() {
        print("\033[H\033[2J");
    }

    static void print(String s) {
        System.out.print(s);
    }

    static void println(String s) {
        System.out.println(s);
    }

    static void errorMessage(String s) {
        System.out.println("");
        println("ERROR : " + s);
    }

    static void successMessage(String s) {
        System.out.println("");
        println(s);
    }

    static boolean captchaVerification() {
        System.out.println("\nCaptcha :");
        println("-----------------");
        System.out.printf("|     %s     |", c.generateCaptcha());
        println("\n-----------------");

        print("\n\nEnter Captcha : ");
        if (c.validateCaptcha(SC.next())) {
            return true;
        } else {
            err = "Invalid Captcha!!!";
            return false;
        }
    }

    private static void exit() {
        clrscr();
        println("   Thank you for visiting :)   ");
        System.exit(0);
    }
}
