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

    private static String inputDetails() {
        input.nextLine();
        System.out.print("Product-specific details: ");
        StringBuilder details = new StringBuilder();
        while (true) {
            String read = input.nextLine();
            if (read == null || read.isEmpty())
                break;
            details.append(read).append("\n");
        }
        return details.toString();
    }

    private static void inputProduct(String cId, String cName, boolean inputCategory, boolean addCategory) {
        System.out.println("Enter the following details about the Product you want to add:");
        if (inputCategory) {
            System.out.print("Category ID: ");
            cId = input.nextLine();
            System.out.print("Category Name: ");
            cName = input.nextLine();
        }
        System.out.print("Product ID: ");
        String pId = input.nextLine();
        System.out.print("Product Name: ");
        String pName = input.nextLine();
        System.out.print("Price (in Rs.): ");
        float price = input.nextFloat();
        System.out.print("Quantity to add: ");
        int quantity = input.nextInt();
        if (addCategory)
            Admin.addCategory(flipzon, cId, cName, new Product(pId, pName, price, quantity, inputDetails()));
        else
            Admin.addProduct(flipzon, cId, cName, pId, pName, price, quantity, inputDetails());
    }

    private static HashMap<String, String> selectProduct(String action) {
        HashMap<String, String> details = new HashMap<>();
        System.out.println("Enter the following details about the Product you want to " + action + ":");
        System.out.print("Category ID: ");
        details.put("cId", input.nextLine());
        System.out.print("Category Name: ");
        details.put("cName", input.nextLine());
        System.out.print("Product ID: ");
        details.put("pId", input.nextLine());
        System.out.print("Product Name: ");
        details.put("pName", input.nextLine());
        return details;
    }

    public static boolean handleEmptyCategory(String cId, String cName) {
        System.out.println("A category cannot remain empty!");
        System.out.println("\nPlease select an action:");
        System.out.println("1. Delete the Category " + cName);
        System.out.println("2. Add a Product to the Category " + cName);
        int choice = inputChoice(2);
        if (choice == 1)
            return true;
        else {
            inputProduct(cId, cName, false, false);
            return false;
        }
    }

    private static boolean isNull(Product product, String pId) {
        if (product == null) {
            System.out.println("Product with ID " + pId + " does not exist! Cannot add Deal!");
            return true;
        }
        return false;
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
            System.out.println("\nPlease select an action:");
            System.out.println("1. Add a Category");
            System.out.println("2. Delete a Category");
            System.out.println("3. Add a Product");
            System.out.println("4. Delete a Product");
            System.out.println("5. Set discount on a Product");
            System.out.println("6. Add a Giveaway Deal");
            System.out.println("7. Back");
            int choice = inputChoice(7);
            input.nextLine();

            if (choice == 1) {
                System.out.println("Enter the following details about the Category you want to add:");
                System.out.print("Category ID: ");
                String cId = input.nextLine();
                System.out.print("Category Name: ");
                String cName = input.nextLine();
                if (flipzon.hasCategory(cId))
                    System.out.println("Category " + cName + " already exists!");
                else {
                    System.out.println("A category cannot be empty!");
                    inputProduct(cId, cName, false, true);
                }
            }
            else if (choice == 2) {
                System.out.println("Enter the following details about the Category you want to delete:");
                System.out.print("Category ID: ");
                String cId = input.nextLine();
                System.out.print("Category Name: ");
                String cName = input.nextLine();
                Admin.deleteCategory(flipzon, cId, cName);
            }
            else if (choice == 3) {
                inputProduct("", "", true, false);
            }
            else if (choice == 4) {
                HashMap<String, String> map = selectProduct("delete");
                Admin.deleteProduct(flipzon, map.get("cId"), map.get("cName"), map.get("pId"), map.get("pName"));
            }
            else if (choice == 5) {
                HashMap<String, String> map = selectProduct("add discount to");
                System.out.print("Enter discounts for Elite, Prime, and Normal respectively (in %): ");
                float e = input.nextFloat();
                float p = input.nextFloat();
                float n = input.nextFloat();
                Admin.setDiscounts(
                    flipzon, map.get("cId"), map.get("cName"), map.get("pId"), map.get("pName"), new float[] {e, p, n}
                );
            }
            else if (choice == 6) {
                System.out.println("Enter the following details of the Giveaway Deal: ");
                System.out.print("Product ID 1: ");
                String pId1 = input.nextLine();
                System.out.print("Product ID 2: ");
                String pId2 = input.nextLine();
                Product p1 = flipzon.getProduct(pId1);
                Product p2 = flipzon.getProduct(pId2);
                System.out.print("Combined Giveaway Price for Elite, Prime, and Normal Customers respectively (in Rs.): ");
                float e = input.nextFloat();
                float p = input.nextFloat();
                float n = input.nextFloat();
                if (isNull(p1, pId1) || isNull(p2, pId2))
                    continue;
                Admin.addDeal(flipzon, p1, p2, new float[] {e, p, n});
            }
            else {
                System.out.println("Thanks for using Admin Mode, " + flipzon.getAdmin().getUsername() + "!");
                break;
            }
        }
    }

    private static void customerMenu() {
        System.out.println("Welcome to Customer Mode!");

        while (true) {
            System.out.println("\nPlease select an action:");
            System.out.println("1. Sign-Up as a new Customer");
            System.out.println("2. Log-In as an existing Customer");
            System.out.println("3. Back");
            int choice = inputChoice(3);
            input.nextLine();

            if (choice == 1) {
                System.out.println("Dear customer, please enter the following details:");
                System.out.print("Name: ");
                String name = input.nextLine();
                System.out.print("Password: ");
                String password = input.nextLine();
                flipzon.addCustomer(new Normal(name, password));
                System.out.println("Customer Signed-Up successfully!");
            }
            else if (choice == 2) {
                System.out.println("Dear customer, Enter the following details:");
                System.out.print("Name: ");
                String name = input.nextLine();
                System.out.print("Password: ");
                String password = input.nextLine();
                if (flipzon.hasCustomer(name, password))
                    customerMode(flipzon.getCustomer(name, password));
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
        customer.manageCart(flipzon);

        while (true) {
            System.out.println("\nPlease select an action:");
            System.out.println(" 1. Browse available Products");
            System.out.println(" 2. Browse available Deals");
            System.out.println(" 3. Add a Product to Cart");
            System.out.println(" 4. Add a Deal to Cart");
            System.out.println(" 5. View available Coupons");
            System.out.println(" 6. Check Account Balance");
            System.out.println(" 7. View Cart");
            System.out.println(" 8. Empty Cart");
            System.out.println(" 9. Checkout Cart");
            System.out.println("10. Upgrade/Downgrade Customer Status");
            System.out.println("11. Add an amount to your Wallet");
            System.out.println("12. Back");
            int choice = inputChoice(12);

            if (choice == 1) {
                flipzon.exploreCatalogue();
            }
            else if (choice == 2) {
                flipzon.exploreDeals(customer.getStatus());
            }
            else if (choice == 3) {
                input.nextLine();
                System.out.println("Enter the following details about the Product you want to add to Cart:");
                System.out.print("Enter Product ID: ");
                String pId = input.nextLine();
                System.out.print("Enter Product name: ");
                String pName = input.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = input.nextInt();
                flipzon.addToCart(customer, pId, pName, quantity);
            }
            else if (choice == 4) {
                input.nextLine();
                System.out.println("Enter the following details about the Deal you want to add to cart:");
                System.out.print("Enter Deal ID: ");
                String pId = input.nextLine();
                System.out.print("Enter Deal name: ");
                String pName = input.nextLine();
                flipzon.addToCart(customer, pId, pName, 1);
            }
            else if (choice == 5) {
                customer.viewCoupons();
            }
            else if (choice == 6) {
                System.out.println("Your current Account Balance is: Rs. " + customer.getBalance() + "/-");
            }
            else if (choice == 7) {
                customer.viewCart();
            }
            else if (choice == 8) {
                if (customer.isEmptyCart())
                    System.out.println("Your Cart is already empty!");
                else {
                    System.out.println("Do you really want to empty your cart? This action is IRREVERSIBLE!");
                    System.out.println("Please select an action:");
                    System.out.println("1. Confirm empty Cart");
                    System.out.println("2. Back");
                    int action = inputChoice(2);
                    if (action == 1) {
                        customer.emptyCart();
                        System.out.println("Your Cart has been emptied successfully!");
                    }
                }
            }
            else if (choice == 9) {
                if (customer.isEmptyCart())
                    System.out.println("Cannot checkout empty Cart!");
                else {
                    System.out.println("For confirmation, here is your Cart!");
                    customer.viewCart();
                    if (deleteItemMenu(customer)) {
                        System.out.println("Here is your final Cart!");
                        customer.viewCart();
                        System.out.println("Proceeding to Checkout...");
                        customer.checkOutCart(flipzon);
                    }
                }
            }
            else if (choice == 10) {
                customer = shiftCustomer(customer);
            }
            else if (choice == 11) {
                System.out.print("Please enter the amount to add in your wallet: ");
                float amount = input.nextFloat();
                customer.addBalance(amount);
                System.out.println("Amount added to wallet successfully!");
            }
            else {
                System.out.println("Logging-Out, " + customer.getName() + "!");
                break;
            }
        }
    }

    private static boolean deleteItemMenu(Customer customer) {
        while (true) {
            System.out.println("\nPlease select an action:");
            System.out.println("1. Delete Items from your Cart");
            System.out.println("2. Proceed to Checkout");
            int choice = inputChoice(2);
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter the details of the Product you wish to remove from your Cart!");
                System.out.print("Product ID: ");
                String pId = input.nextLine();
                System.out.print("Product Name: ");
                String pName = input.nextLine();
                System.out.print("Quantity: ");
                int quantity = input.nextInt();
                customer.removeFromCart(pId, pName, quantity);
                if (customer.isEmptyCart()) {
                    System.out.println("Your cart is now Empty!");
                    return false;
                }
            }
            else
                return true;
        }
    }

    public static Customer shiftCustomer(Customer customer) {
        if (customer.getStatus() == 2) {
            System.out.println("Current Status: Normal");
            System.out.println("Choose new Status (the cost of membership is mentioned alongside):");
            System.out.println("1. Prime (Rs. 200/- per Month)");
            System.out.println("2. Elite (Rs. 300/- per Month)");
            if (inputChoice(2) == 1)
                return flipzon.shiftStatus(customer, "Prime", 200.0f);
            else
                return flipzon.shiftStatus(customer, "Elite", 300.0f);
        }
        else if (customer.getStatus() == 1) {
            System.out.println("Current Status: Prime");
            System.out.println("Choose new Status (the cost of membership is mentioned alongside):");
            System.out.println("1. Elite (Rs. 100/- per Month)");
            System.out.println("2. Normal (Non-Refundable)");
            if (inputChoice(2) == 1)
                return flipzon.shiftStatus(customer, "Elite", 100.0f);
            else
                return flipzon.shiftStatus(customer, "Normal", 0.0f);
        }
        else {
            System.out.println("Current Status: Elite");
            System.out.println("Choose new Status (the cost of membership is mentioned alongside):");
            System.out.println("1. Normal (Non-Refundable)");
            System.out.println("2. Prime (Non-Refundable)");
            if (inputChoice(2) == 1)
                return flipzon.shiftStatus(customer, "Normal", 0.0f);
            else
                return flipzon.shiftStatus(customer, "Prime", 0.0f);
        }
    }

    public static void main(String[] args) {
        flipzon = new Flipzon("Flipzon", new Admin("DVGT", "2021529"));
        System.out.println("Welcome to " + flipzon.getName() + ", the market at your doorstep!");

        // Hard-coding a Test-Case

//        Category c1 = new Category("C-1", "Electronics");
//        Category c2 = new Category("C-2", "Appliances");
//        Category c3 = new Category("C-3", "Furniture");
//        Category dx = new Category("Dx0", "Deals");
//
//        Product p1 = new Product("1P-1", "Laptop",       67000,  5,   "OK");
//        Product p2 = new Product("1P-2", "iPhone",       125000, 1,   "OK");
//        Product p3 = new Product("1P-3", "Headphones",   6000,   10,  "OK");
//        Product p4 = new Product("2P-1", "Fridge",       78000,  5,   "OK");
//        Product p5 = new Product("2P-2", "TV",           200000, 20,  "OK");
//        Product p6 = new Product("2P-3", "Microwave",    50500,  10,  "OK");
//        Product p7 = new Product("3P-1", "Sofa Set",     40000,  5,   "OK");
//        Product p8 = new Product("3P-2", "Dining Table", 54000,  10,  "OK");
//        Product p9 = new Product("3P-3", "Curtains",     1200,   100, "OK");
//        Product d1 = new Product(1, p1, p2, new float[] {100000, 110000, 10000});
//        Product d2 = new Product(2, p4, p1, new float[] {10000, 15000, 20000});
//        Product d3 = new Product(3, p7, p8, new float[] {10000, 15000, 20000});
//        p5.setDiscounts(new float[] {20, 15, 10});
//        p6.setDiscounts(new float[] {10, 9, 8});
//        p9.setDiscounts(new float[] {15.5f, 12.4f, 2});
//
//        c1.addProduct(p1); c2.addProduct(p4); c3.addProduct(p7); dx.addProduct(d1);
//        c1.addProduct(p2); c2.addProduct(p5); c3.addProduct(p8); dx.addProduct(d2);
//        c1.addProduct(p3); c2.addProduct(p6); c3.addProduct(p9); dx.addProduct(d3);
//        flipzon.addCategory(c1); flipzon.addCategory(c2); flipzon.addCategory(c3); flipzon.addCategory(dx);

//        Customer n1 = new Normal("Div", "Div123");
//        Customer n2 = new Normal("Asm", "Asm123");
//        flipzon.addCustomer(n1); flipzon.addCustomer(n2);

        while (true) {
            System.out.println("\nPlease select an action:");
            System.out.println("1. Enter as an Admin");
            System.out.println("2. Explore the Product Catalogue");
            System.out.println("3. Show Available Deals");
            System.out.println("4. Enter as a Customer");
            System.out.println("5. Exit the Application");
            int choice = inputChoice(5);

            if (choice == 1) {
                if (adminLogIn()) {
                    System.out.println("\nLogging-In as Admin!");
                    adminMode();
                }
                else
                    System.out.println("Incorrect Credentials! Cannot Log-In as Admin!");
            }
            else if (choice == 2) {
                flipzon.exploreCatalogue();
            }
            else if (choice == 3) {
                flipzon.exploreDeals(3);
            }
            else if (choice == 4) {
                customerMenu();
            }
            else {
                System.out.println("Thanks for using " + flipzon.getName() + "! We hope to see you again!");
                break;
            }
        }

        input.close();
    }
}