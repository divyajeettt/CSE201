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

    public static boolean handleEmptyCategory(String cId, String cName) {
        System.out.println("A category cannot remain empty!");

        System.out.println("Please select an action:");
        System.out.println("1. Delete the Category " + cName);
        System.out.println("2. Add a Product to the Category " + cName);
        int choice = inputChoice(2);

        if (choice == 1)
            return true;
        else {
            // Take inputs to add a product
            // Admin.addProduct(flipzon, cId, cName, pId, pName, details, price, quantity);
            return false;
        }
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
                flipzon.addCustomer(new Normal(name, password, email, phoneNumber, age));
                System.out.println("Customer Signed-Up successfully!");
            }
            else if (choice == 2) {
                System.out.println("Dear customer, Enter the following details:");
                System.out.print("Name: ");
                String name = input.nextLine();
                System.out.print("E-Mail ID: ");
                String email = input.nextLine();
                System.out.print("Password: ");
                String password = input.nextLine();
                if (flipzon.hasCustomer(name, email, password))
                    customerMode(flipzon.getCustomer(name, email, password));
                else
                    System.out.println("Invalid Credentials! Cannot Log-In as " + name + "!");
            }
            else {
                System.out.println("Thanks for using Customer Mode!");
                break;
            }
        }
    }

    private static void customerMode(Customer customer) {
        System.out.println("Welcome to " + flipzon.getName() + ", " + customer.getName() + "!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Browse available Products");
            System.out.println("2. Browse available Deals");
            System.out.println("3. Add a Product to Cart");
            System.out.println("4. Add a Deal to Cart");
            System.out.println("5. View available Coupons");
            System.out.println("6. Check Account Balance");
            System.out.println("7. View Cart");
            System.out.println("8. Empty Cart");
            System.out.println("9. Checkout Cart");
            System.out.println("10. Upgrade Customer Status");
            System.out.println("11. Add an amount to your Wallet");
            System.out.println("12. Back");
            int choice = inputChoice(12);

            if (choice == 1) {}
            else if (choice == 2) {}
            else if (choice == 3) {}
            else if (choice == 4) {}
            else if (choice == 5) {}
            else if (choice == 6) {
                System.out.println("Your current Account Balance is: Rs. " + customer.getBalance());
            }
            else if (choice == 7) {}
            else if (choice == 8) {}
            else if (choice == 9) {}
            else if (choice == 10) {}
            else if (choice == 11) {
                System.out.print("Please enter the amount to add in your wallet: ");
                float amount = input.nextFloat();
                customer.addBalance(amount);
                System.out.println("Amount added to wallet successfully!");
            }
            else {
                System.out.println("Logging Out, " + customer.getName() + "!");
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
            } else if (choice == 2) {
                flipzon.exploreCatalogue();
            } else if (choice == 3) {
                flipzon.exploreDeals();
            }
            else if (choice == 4) {
                customerMenu();
            } else {
                System.out.println("Thanks for using " + flipzon.getName() + "! We hope to see you again!");
                break;
            }
        }

        input.close();
    }
}

// HANDLE EDGE CASE: CANNOT PAY FOR EMPTY CART