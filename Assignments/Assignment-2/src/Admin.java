public class Admin {
    private final String name;
    private final String password;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public boolean matchCredentials(String name, String password) {
        return (this.name.equals(name) && this.password.equals(password));
    }

    public static boolean checkDealPrices(Product p1, Product p2, float[] dealPrices) {
        float priceP1 = p1.getPrice();
        float priceP2 = p2.getPrice();
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
            Product product = flipzon.getProduct(pId);
            flipzon.getProduct(pId).setQuantity(product.getQuantity() + quantity);
            System.out.println(quantity + " more " + pName + "(s) added successfully!");
        }
        else {
            flipzon.addProduct(new Product(pId, pName, price, quantity, details), cId);
            System.out.println("Product " + pName + " added to category " + cName + " successfully!");
        }
    }

    public static void deleteProduct(Flipzon flipzon, String cId, String cName, String pId, String pName) {
        if (!flipzon.hasCategory(cId))
            System.out.println("Category " + cName + " does not exist!");
        else if (!flipzon.hasProduct(pId, cId))
            System.out.println("Product " + pName + " does not exist!");
        else {
            Product product = flipzon.getProduct(pId, cId);
            flipzon.deleteProduct(pId, cId);
            System.out.println("All stock of Product " + pName + " deleted successfully!");
            if (flipzon.getProducts(cId).size() == 0) {
                if (Main.handleEmptyCategory(cId, cName))
                    deleteCategory(flipzon, cId, cName);
            }
            for (Product deal: flipzon.getProducts("Dx0").values()) {
                Product[] dealProducts = deal.getDealProducts();
                if (product.getId().equals(dealProducts[0].getId()) || product.getId().equals(dealProducts[1].getId())) {
                    System.out.println("Deal " + deal.getId() + " is now invalid!");
                    flipzon.deleteProduct(deal.getId(), "Dx0");
                }
            }
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
            flipzon.setCountDeals(flipzon.getCountDeals() + 1);
            int size = flipzon.getCountDeals();
            flipzon.addProduct(new Product(size, p1, p2, dealPrices), "Dx0");
            System.out.println("Deal added successfully!");
        }
    }
}