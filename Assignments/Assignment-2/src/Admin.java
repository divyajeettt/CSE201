class Admin {
    private final String username;
    private final String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean matchCredentials(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    }

    public static void addCategory(Flipzon flipzon, String cId, String cName) {
        if (flipzon.hasCategory(cId))
            System.out.println("Category " + cName + " already exists!");
        else {
            flipzon.addCategory(new Category(cId, cName));
            System.out.println("Category " + cName + " added successfully!");
        }
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
        Flipzon flipzon, String cId, String cName, String pId, String pName, String details, float price, int quantity
    ) {
        if (!flipzon.hasCategory(cId)) {
            System.out.println("Category " + cName + " does not exist! Creating the category...");
            addCategory(flipzon, cId, cName);
        }
        if (flipzon.hasProduct(pId, cId))
            System.out.println("Product " + pName + " already exists in Category " + cName + "!");
        else {
            flipzon.addProduct(new Product(pId, pName, details, price, quantity), cName);
            System.out.println("Product " + pName + " added successfully!");
        }
    }

    public static void deleteProduct(Flipzon flipzon, String cId, String cName, String pId, String pName) {
        if (!flipzon.hasCategory(cId))
            System.out.println("Category " + cName + " does not exist!");
        else if (!flipzon.hasProduct(pId, cId))
            System.out.println("Product " + pName + " does not exist!");
        else {
            flipzon.deleteProduct(pId, cId);
            System.out.println("Product " + pName + " deleted successfully!");
        }
    }

    public void setDiscount(Flipzon flipzon, String cId, String cName, String pId, String pName, float[] discounts) {
        if (!flipzon.hasCategory(cId)) {
            System.out.println("Category " + cName + " does not exist!");
            return;
        }
        if (!flipzon.hasProduct(pId, cId))
            System.out.println("Product " + pName + " does not exist!");
        else {
            flipzon.setDiscount(pId, cId, discounts);
            System.out.println("Se");
        }
    }

    public void addDeal(Flipzon flipzon, Product product1, Product product2, float price) {
        if (!flipzon.hasCategory("Dx0"))
            flipzon.addCategory(new Category("Dx0", "Deals"));
        if (price > product1.getPrice() + product2.getPrice())
            System.out.println("To form a deal, the combined price must be lower than the sum of individual prices!");
        else {
            int size = flipzon.getProducts("Dx0").size() + 1;
            String details = "This deal is an offer on the Products: " + product1.getName() + " and " + product2.getName();
            flipzon.addProduct(new Product("D-"+size, "Deal-"+size, details, price, 1), "Dx0");
            System.out.println("Deal added successfully!");
        }
    }
}