import java.util.*;


public class Flipzon {
    private final String name;
    private final User admin;
    private HashMap<String, Category> categories = new HashMap<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private int countDeals = 0;

    public Flipzon(String name, User admin) {
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return this.name;
    }

    public User getAdmin() {
        return this.admin;
    }

    public int getCountDeals() {
        return this.countDeals;
    }

    public void setCountDeals(int count) {
        this.countDeals = count;
    }

    public Collection<Category> getCategoryList() {
        return this.categories.values();
    }

    public Item getProduct(String pId) {
        for (Category category: this.getCategoryList()) {
            if (category.hasProduct(pId))
                return category.getProducts().get(pId);
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
        if (this.categories.get(cId) == null)
            return false;
        else
            return this.categories.get(cId).hasProduct(pId);
    }

    public void addProduct(Item product, String cId) {
        this.categories.get(cId).addProduct(product);
    }

    public void deleteProduct(String pId, String cId) {
        this.categories.get(cId).deleteProduct(pId);
    }

    public void deleteProduct(String pId) {
        for (Category category: this.categories.values()) {
            if (category.hasProduct(pId)) {
                this.deleteProduct(pId, category.getId());
                break;
            }
        }
    }

    public void setDiscounts(String pId, String cId, float[] discounts) {
        Product product = (Product) this.categories.get(cId).getProducts().get(pId);
        product.setDiscounts(discounts);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public User getCustomer(String name, String password) {
        for (Customer customer: this.customers) {
            if (customer.matchCredentials(name, password))
                return customer;
        }
        return null;
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
            for (Item item: category.getProductList()) {
                System.out.println("Product " + i + "." +  j + ":");
                System.out.println(item.print(3));
                j++;
            }
            i++;
        }
    }

    public void exploreDeals(int status) {
        Category deals = this.categories.get("Dx0");
        if (deals == null || deals.getProductList().isEmpty()) {
            if (status == 3)
                System.out.println("No Deals have been added in " + this.name +"!");
            else {
                System.out.println("There are no Deals available in " + this.name + " currently!");
                System.out.println("Please keep checking for regular exciting Deals!");
            }
        }
        else {
            System.out.println("The following Deals are available in " + this.name + ":");
            for (Item item: this.categories.get("Dx0").getProductList())
                System.out.println(item.print(status) + "\n");
        }
    }

    public void addToCart(Customer customer, String pId, String pName, int quantity) {
        Item product = this.getProduct(pId);
        if (product == null)
            System.out.println("Item " + pName + " does not exist!");
        else if (customer.hasInCart(product.getId())) {
            if (product.isDeal()) {
                System.out.println("Deal already added to Cart!");
                return;
            }
            int inCart = customer.getCart().get(pId).getQuantity();
            if (inCart + quantity > product.getQuantity())
                System.out.println("Only " + (product.getQuantity()-inCart) + " " + pName + "(s) left in stock!");
            else {
                customer.incQuantity(pId, quantity);
                System.out.println(quantity + " more " + pName + "(s) added to Cart!");
            }
        }
        else {
            if (quantity > product.getQuantity())
                System.out.println("Only " + product.getQuantity() + " " + pName + "(s) are available!");
            else {
                if (product.isDeal())
                    customer.addToCart(new Deal((Deal) product, quantity));
                else
                    customer.addToCart(new Product((Product) product, quantity));
                System.out.println(quantity + " " + pName + "(s) added to Cart!");
            }
        }
    }

    public void placeOrder(Customer customer, float total) {
        if (!customer.affords(total)) {
            System.out.println("You do not have enough balance in your Wallet!");
            System.out.println("Cannot place order! Your items will remain in your Cart!");
            return;
        }

        customer.deductBalance(total);
        System.out.println("Your order has been placed!");
        System.out.println(
            "An amount of Rs. " + total + "/- has been deducted from your Wallet! "
            + "Current Balance: Rs. " + customer.getBalance() + "/-"
        );
        System.out.println("Your order will be delivered in " + customer.getDeliveryTime() + " days!");
        this.manageProducts(customer);
        this.manageCategories();
        this.manageDeals();
        customer.emptyCart();
        if (total > 5000.0f)
            customer.addCoupons(customer.getNumCoupons());
        customer.getFreeProduct(this);
    }

    public void manageDeals() {
        if (!this.hasCategory("Dx0"))
            return;
        ArrayList<String> toDelete = new ArrayList<>();
        for (Item item: this.categories.get("Dx0").getProductList()) {
            Deal deal = (Deal) item;
            Product p1 = deal.getProducts()[0];
            Product p2 = deal.getProducts()[1];
            if (p1.getQuantity() <= 0 || p2.getQuantity() <= 0) {
                Product zero = ((p1.getQuantity() <= 0) ? p1 : p2);
                System.out.println(
                    "Deal " + deal.getId() + " is now Invalid! Product " + zero.getName() + " is no longer available!"
                );
                toDelete.add(deal.getId());
            }
        }
        for (String dId: toDelete)
            this.deleteProduct(dId);
    }

    public void manageCategories() {
        for (Category category: this.categories.values()) {
            if (!category.getName().equals("Dx0") && category.getProducts().isEmpty()) {
                if (Main.handleEmptyCategory(category.getId(), category.getName()))
                    this.deleteCategory(category.getId());
            }
        }
    }

    public void manageProducts(Customer customer) {
        for (String pId: customer.getCart().keySet()) {
            Item item = this.getProduct(pId);
            if (item.isDeal()) {
                Deal deal = (Deal) item;
                for (Product dealProduct: deal.getProducts()) {
                    Item product = this.getProduct(dealProduct.getId());
                    if (product == null)
                        continue;
                    product.setQuantity(dealProduct.getQuantity() - 1);
                    if (product.getQuantity() <= 0) {
                        this.deleteProduct(product.getId());
                        System.out.println("Product " + product.getName() + " is no longer available! All Sold Out!");
                    }
                }
            }
            else {
                Product product = (Product) item;
                product.setQuantity(product.getQuantity() - customer.getCart().get(pId).getQuantity());
                if (product.getQuantity() <= 0) {
                    this.deleteProduct(pId);
                    System.out.println("Product " + product.getName() + " is no longer available! All Sold Out!");
                }
            }
        }
    }

    public Customer shiftStatus(Customer customer, String status, float price) {
        if (!customer.affords(price)) {
            System.out.println("You don't have enough balance in your Wallet!");
            return customer;
        }

        Customer shifted;
        if (status.equals("Normal"))
            shifted = new Normal(customer);
        else if (status.equals("Prime"))
            shifted = new Prime(customer);
        else
            shifted = new Elite(customer);
        shifted.deductBalance(price);
        this.customers.remove(customer);
        this.addCustomer(shifted);
        System.out.println(
            "Dear " + customer.getName() + ", your status has been successfully changed to " + status + "!"
        );
        if (price != 0)
            System.out.println(
                "An amount of Rs. " + price + "/- has been deducted from your Wallet! "
                + "Current Balance: Rs. " + shifted.getBalance() + "/-"
            );
        return shifted;
    }
}