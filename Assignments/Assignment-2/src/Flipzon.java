import java.util.*;


public class Flipzon {
    private String name;
    private Admin admin;
    private HashMap<String, Category> categories;
    private ArrayList<Customer> customers;

    public Flipzon(String name, Admin admin) {
        this.name = name;
        this.admin = admin;
        this.categories = new HashMap<>();
        this.customers = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public Admin getAdmin() {
        return this.admin;
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

    public void addProduct(Product product, String cName) {
        this.categories.get(cName).addProduct(product);
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
}