import java.util.*;


public class Flipzon {
    private String name;
    private Admin admin;
    private HashMap<String, Category> categories = new HashMap<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private int countDeals = 0;

    public Flipzon(String name, Admin admin) {
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return this.name;
    }

    public Admin getAdmin() {
        return this.admin;
    }

    public int getCountDeals() {
        return this.countDeals;
    }

    public void setCountDeals(int count) {
        this.countDeals = count;
    }

    public ArrayList<Customer> getCustomers() {
        return this.customers;
    }

    public HashMap<String, Category> getCategories() {
        return this.categories;
    }

    public HashMap<String, Product> getProducts(String cId) {
        return this.categories.get(cId).getProducts();
    }

    public Category getCategory(String cId) {
        return this.categories.get(cId);
    }

    public Product getProduct(String pId) {
        for (Category category: this.getCategories().values()) {
            for (Product product: category.getProductList()) {
                if (product.getId().equals(pId))
                    return product;
            }
        }
        return null;
    }

    public boolean hasCategory(String cId) {
        return this.categories.containsKey(cId);
    }

    public void addCategory(Category category) {
        this.categories.put(category.getId(), category);
    }

    public void deleteCategory(String cId) {
        this.categories.remove(cId);
    }

    public boolean hasProduct(String pId, String cId) {
        return this.categories.get(cId).hasProduct(pId);
    }

    public void addProduct(Product product, String cId) {
        this.categories.get(cId).addProduct(product);
    }

    public void deleteProduct(String pId, String cId) {
        this.categories.get(cId).deleteProduct(pId);
    }

    public void setDiscount(String pId, String cId, float[] discounts) {
        this.categories.get(cId).getProducts().get(pId).setDiscounts(discounts);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public boolean hasCustomer(String name, String email, String password) {
        for (Customer customer: this.customers) {
            if (customer.matchCredentials(name, email, password))
                return true;
        }
        return false;
    }

    public Customer getCustomer(String name, String email, String password) {
        for (Customer customer: this.customers) {
            if (customer.matchCredentials(name, email, password))
                return customer;
        }
        return null;
    }

    public void incQuantity(String pId, String cId, int quantity) {
        this.categories.get(cId).getProduct(pId).incQuantity(quantity);
    }

    public void decQuantity(String pId, String cId, int quantity) {
        this.categories.get(cId).getProduct(pId).incQuantity(-1 * quantity);
    }

    public void exploreCatalogue() {
        System.out.println("The following Products are available in " + this.name + ":");
        int i = 1;
        for (Category category: this.categories.values()) {
            if (category.getId().equals("Dx0"))
                continue;
            System.out.println("Category " + i + ": " + category.getName());
            int j = 1;
            for (Product product: category.getProductList()) {
                System.out.println("Product " + j + ":");
                System.out.println(product);
                j++;
            }
            i++;
        }
    }
    public void exploreDeals() {
        System.out.println("The following Deals are available in " + this.name + ":");
        for (Product product: this.categories.get("Dx0").getProductList())
            System.out.println(product);
    }

    public void addToCart(Customer customer, String pId, String pName, boolean isDeal) {
        String type = (isDeal ? "Deal " : "Product ");
        Product product = this.getProduct(pId);
        if (product == null)
            System.out.println(type + pName + " does not exist!");
        else {
            customer.addToCart(product);
            System.out.println(type + pName + " added to Cart!");
        }
    }
}