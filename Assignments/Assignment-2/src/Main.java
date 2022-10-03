import java.util.*;


public class Main {
    private static Flipzon flipzon;
    private static final Scanner input = new Scanner(System.in);

    private static int inputChoice(int upper) {
        System.out.print(">>> ");
        int choice = input.nextInt();
        while (!(1 <= choice && choice <= upper)) {
            System.out.println("Please enter a number between 1 - " + upper);
            System.out.print(">>> ");
            choice = input.nextInt();
        }
        return choice;
    }

    private static boolean adminLogIn() {
        System.out.println("Dear Admin, please enter your credentials!");
        System.out.print("Username: ");
        input.nextLine();
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        return flipzon.getAdmin().matchCredentials(username, password);
    }

    private static void adminMode() {
        System.out.println("Welcome " + flipzon.getAdmin().getUsername() + "!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Add a Category");
            System.out.println("2. Delete a Category");
            System.out.println("3. Add a Product");
            System.out.println("4. Delete a Product");
            System.out.println("5. Set discount on a Product");
            System.out.println("6. Add a Giveaway Deal");
            System.out.println("7. Back");
            int choice = inputChoice(7);

            if (choice == 1) {}
            else if (choice == 2) {}
            else if (choice == 3) {}
            else if (choice == 4) {}
            else if (choice == 5) {}
            else if (choice == 6) {}
            else {
                System.out.println("Thanks for using Admin Mode, " + flipzon.getAdmin().getUsername() + "!");
                break;
            }
        }
    }

    private static void customerMenu() {
        System.out.println("Welcome to Customer Mode!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Sign-Up as a new Customer");
            System.out.println("2. Log-In as an existing Customer");
            System.out.println("3. Back");
            int choice = inputChoice(3);

            if (choice == 1) {
                input.nextLine();
                System.out.println("Dear customer, Enter the following details:");

                System.out.print("Name: ");
                String name = input.nextLine();
                System.out.print("Password: ");
                String password = input.nextLine();
                System.out.print("E-Mail ID: ");
                String email = input.nextLine();
                System.out.print("Phone Number: ");
                String phoneNumber = input.nextLine();
                System.out.print("Age: ");
                int age = input.nextInt();

            } else if (choice == 2) {
            } else {
                System.out.println("Thanks for using Customer Mode!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        flipzon = new Flipzon("Flipzon", new Admin("DVGT", "2021529"));
        System.out.println("Welcome to " + flipzon.getName() + ", the market at your doorstep!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Enter as an Admin");
            System.out.println("2. Explore the Product Catalogue");
            System.out.println("3. Show Available Deals");
            System.out.println("4. Enter as a Customer");
            System.out.println("5. Exit the Application");
            int choice = inputChoice(5);

            if (choice == 1) {
                if (adminLogIn()) {
                    System.out.println("Logging-In as Admin!");
                    adminMode();
                } else
                    System.out.println("Incorrect Credentials! Cannot Log-In as Admin!");
            }
            else if (choice == 2) {}
            else if (choice == 3) {}
            else if (choice == 4)
                customerMenu();
            else {
                System.out.println("Thanks for using " + flipzon.getName() + "! We hope to see you again!");
                break;
            }
        }

        input.close();
    }
}