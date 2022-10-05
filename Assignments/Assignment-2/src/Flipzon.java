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

    public HashMap<String, Category> getCategories() {
        return this.categories;
    }

    public HashMap<String, Product> getProducts(String cId) {
        return this.categories.get(cId).getProducts();
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
        if (this.categories.size() == 0) {
            System.out.println("There are no Products available in " + this.name + " currently!");
            return;
        }
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
        Collection<Product> deals = this.categories.get("Dx0").getProductList();
        if (deals.size() == 0) {
            System.out.println("There are no Deals available in " + this.name + " currently!");
            System.out.println("Please keep checking for regular exciting Deals!");
            return;
        }
        System.out.println("The following Deals are available in " + this.name + ":");
        for (Product product: this.categories.get("Dx0").getProductList())
            System.out.println(product);
    }

    public void addToCart(Customer customer, String pId, String pName, int quantity) {
        Product product = this.getProduct(pId);
        if (product == null)
            System.out.println("Item " + pName + " does not exist!");
        else {
            if (customer.hasInCart(product.getId())) {
                customer.incQuantity(pId, quantity);
                System.out.println(quantity + " more " + pName + "(s) added to Cart!");
            }
            else {
                customer.setQuantity(pId, quantity);
                customer.addToCart(product);
                System.out.println(quantity + " " + pName + "(s) added to Cart!");
            }
        }
    }

    public void placeOrder(Customer customer, float total) {
        if (customer.affords(total)) {
            customer.deductBalance(total);
            System.out.println("Your order has been placed!");
            System.out.println("Your order will be delivered in " + customer.getDeliveryTime() + " days!");
            for (Product product: customer.getCart())
                product.resetQuantity();
            customer.emptyCart();
            if (total > 5000)
                customer.addCoupons();
            customer.getFreeProduct(this);
        }
        else {
            System.out.println("You do not have enough balance in your Wallet!");
            System.out.println("Cannot place order! Your items will remain in your Cart!");
        }
    }
}