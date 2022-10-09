import javax.sound.sampled.Port;

public class Admin implements User {
    private final String username;
    private final String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return this.username;
    }

    public boolean matchCredentials(String name, String password) {
        return (this.username.equals(name) && this.password.equals(password));
    }

    public static boolean checkDealPrices(Product p1, Product p2, float[] dealPrices) {
        float priceP1 = p1.getPrice(3);
        float priceP2 = p2.getPrice(3);
        boolean cond0 = dealPrices[0] > (priceP1 - p1.getDiscount(0)/100.0f * priceP1) + (priceP2 - p2.getDiscount(0)/100.0f * priceP2);
        boolean cond1 = dealPrices[1] > (priceP1 - p1.getDiscount(1)/100.0f * priceP1) + (priceP2 - p2.getDiscount(1)/100.0f * priceP2);
        boolean cond2 = dealPrices[2] > (priceP1 - p1.getDiscount(2)/100.0f * priceP1) + (priceP2 - p2.getDiscount(2)/100.0f * priceP2);
        return (cond0 || cond1 || cond2);
    }

    public static void addCategory(Flipzon flipzon, String cId, String cName, Product product) {
        flipzon.addCategory(new Category(cId, cName));
        System.out.println("Category " + cName + " added successfully!");
        flipzon.addProduct(product, cId);
        System.out.println("Product " + product.getName() + " added to category " + cName + " successfully!");
    }

    public static void deleteCategory(Flipzon flipzon, String cId, String cName) {
        if (!flipzon.hasCategory(cId))
            System.out.println("Category " + cName + " does not exist!");
        else {
            flipzon.deleteCategory(cId);
            System.out.println("Category " + cName + " deleted successfully!");
        }
    }

    public static void addProduct(
        Flipzon flipzon, String cId, String cName, String pId, String pName, float price, int quantity, String details
    ) {
        if (!flipzon.hasCategory(cId)) {
            System.out.println("Category " + cName + " does not exist! Creating the category...");
            addCategory(flipzon, cId, cName, new Product(pId, pName, price, quantity, details));
        }
        else if (flipzon.hasProduct(pId, cId)) {
            Product product = (Product) flipzon.getProduct(pId);
            flipzon.getProduct(pId).setQuantity(product.getQuantity() + quantity);
            System.out.println(quantity + " more " + pName + "(s) added successfully!");
        }
        else {
            flipzon.addProduct(new Product(pId, pName, price, quantity, details), cId);
            System.out.println("Product " + pName + " added to Category " + cName + " successfully!");
        }
    }

    public static void deleteProduct(Flipzon flipzon, String cId, String cName, String pId, String pName) {
        if (!flipzon.hasCategory(cId))
            System.out.println("Category " + cName + " does not exist!");
        else if (!flipzon.hasProduct(pId, cId))
            System.out.println("Product " + pName + " does not exist in Category " + cName + "!");
        else {
            flipzon.deleteProduct(pId, cId);
            System.out.println("All stock of Product " + pName + " deleted successfully!");
            flipzon.manageCategories();
            flipzon.manageDeals();
        }
    }

    public static void setDiscounts(
            Flipzon flipzon, String cId, String cName, String pId, String pName, float[] discounts
    ) {
        if (!flipzon.hasCategory(cId)) {
            System.out.println("Category " + cName + " does not exist!");
            return;
        }
        if (!flipzon.hasProduct(pId, cId))
            System.out.println("Product " + pName + " does not exist!");
        else {
            flipzon.setDiscounts(pId, cId, discounts);
            System.out.println("The given discounts have been applied to " + pName + " successfully!");
        }
    }

    public static void addDeal(Flipzon flipzon, Product p1, Product p2, float[] dealPrices) {
        if (!flipzon.hasCategory("Dx0"))
            flipzon.addCategory(new Category("Dx0", "Deals"));
        if (checkDealPrices(p1, p2, dealPrices))
            System.out.println("To form a Deal, the combined price must be lower than the sum of individual prices (after discount)");
        else {
            int num = flipzon.getCountDeals();
            for (int i=0; i <= num; i++) {
                if (flipzon.hasProduct("D-"+num, "Dx0")) {
                    Deal deal = (Deal) flipzon.getProduct("D-"+num);
                    Product[] products = deal.getProducts();
                    if (
                        (p1.getId().equals(products[0].getId()) && p2.getId().equals(products[1].getId())) ||
                        (p2.getId().equals(products[0].getId()) && p1.getId().equals(products[1].getId()))
                    ) {
                        System.out.println("A Deal on " + p1.getName() + " and " + p2.getName() + " already exists!");
                        return;
                    }
                }
            }
            flipzon.setCountDeals(flipzon.getCountDeals() + 1);
            flipzon.addProduct(new Deal(flipzon.getCountDeals(), p1, p2, dealPrices), "Dx0");
            System.out.println("Deal added successfully!");
        }
    }
}