import javax.swing.text.html.CSS;
import java.util.*;


public abstract class Customer {
    private final String name;
    private final String password;
    protected float balance = 1000.0f;
    protected HashMap<String, Product> cart = new HashMap<>();
    protected ArrayList<Float> coupons = new ArrayList<>();
    protected float discount;
    protected float extraDeliveryCharge;

    public abstract float getCartPrice();
    public abstract int getDeliveryTime();
    public abstract void addCoupons();
    public abstract int getStatus();
    public void getFreeProduct(Flipzon flipzon) {}

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public float getExtraDeliveryCharge() {
        return this.extraDeliveryCharge;
    }

    public float getBalance() {
        return this.balance;
    }

    public HashMap<String, Product> getCart() {
        return this.cart;
    }

    public boolean isEmptyCart() {
        return (this.cart.size() == 0);
    }

    public boolean hasInCart(String pId) {
        return this.cart.containsKey(pId);
    }

    public void setQuantity(String pId, int quantity) {
        this.cart.get(pId).setQuantity(quantity);
    }

    public void incQuantity(String pId, int quantity) {
        this.cart.get(pId).incQuantity(quantity);
    }

    public float getMaxCoupon() {
        if (this.coupons.size() == 0)
            return 0.0f;
        else
            return Collections.max(this.coupons);
    }

    public boolean matchCredentials(String name, String password) {
        return (this.name.equals(name) && this.password.equals(password));
    }

    public void addBalance(float amount) {
        this.balance += amount;
    }

    public boolean affords(float amount) {
        return (this.balance >= amount);
    }

    public void deductBalance(float price) {
        this.balance -= price;
    }

    public void addToCart(Product product) {
        this.cart.put(product.getId(), product);
    }

    public void viewCoupons() {
        if (this.coupons.size() == 0)
            System.out.println("You do not have any Coupons!");
        else {
            for (int i=0; i < this.coupons.size(); i++)
                System.out.println("Coupon-" + (i+1) + ": " + this.coupons.get(i));
        }
    }

    public void viewCart() {
        if (this.isEmptyCart())
            System.out.println("Your cart is empty!");
        else {
            int products = 0;
            int deals = 0;
            for (Product product: this.cart.values()) {
                String type = (product.getId().contains("D-") ? "Deal" : "Product");
                if (type.equals("Deal"))
                    deals++;
                else
                    products++;
                System.out.println(type + (product.getId().contains("D-") ? deals : products) + ":");
                System.out.println(product);
            }
        }
    }

    public void emptyCart() {
        this.cart.clear();
    }

    public void removeFromCart(String pId, String pName) {
        if (!this.hasInCart(pId))
            System.out.println("Item " + pName + " does not exist in your Cart");
        else {
            this.cart.remove(pId);
            System.out.println("Item " + pName + " removed from your Cart successfully!");
        }
    }

    public void checkOutCart(Flipzon flipzon) {
        float extraDeliveryCharge = this.getExtraDeliveryCharge();
        float coupon = this.getMaxCoupon();
        float price = this.getCartPrice();
        float deliveryCharge = 100 + (extraDeliveryCharge / 100 * price);
        float discount = (coupon / 100 * price);
        float finalTotal = price + deliveryCharge - discount;

        System.out.println("Total Cost of Items: Rs. " + price + "/-");
        System.out.println(
            "Delivery Charges: Rs. 100 + " + extraDeliveryCharge + "% of " + price + " = Rs. " + deliveryCharge + "/-"
        );
        if (coupon != 0)
            System.out.println("Discount: " + coupon + "% of " + price + " = Rs. " + discount + "/-");
        System.out.println("Final Amount: Rs. " + finalTotal);
        flipzon.placeOrder(this, finalTotal);
        if (coupon != 0)
            this.coupons.remove(coupon);
    }
}


class Normal extends Customer {
    public Normal(String name, String password) {
        super(name, password);
        this.discount = 0.0f;
        this.extraDeliveryCharge = 5.0f;
    }

    public Normal(Customer customer) {
        super(customer.getName(), customer.getPassword());
        this.balance = customer.balance;
        this.cart = customer.cart;
        this.coupons = customer.coupons;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public float getCartPrice() {
        float total = 0.0f;
        for (Product product: this.cart.values()) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    @Override
    public int getDeliveryTime() {
        return ((int) (Math.random()*(11-7+1) + 7));
    }

    @Override
    public void addCoupons() {}
}


class Prime extends Customer {
    public Prime(String name, String password) {
        super(name, password);
        this.discount = 5.0f;
        this.extraDeliveryCharge = 2.0f;
    }

    public Prime(Customer customer) {
        super(customer.getName(), customer.getPassword());
        this.balance = customer.balance;
        this.cart = customer.cart;
        this.coupons = customer.coupons;
    }

    @Override
    public int getStatus() {
        return 1;
    }

    @Override
    public float getCartPrice() {
        float total = 0.0f;
        for (Product product: this.cart.values()) {
            total += (product.getPrice() - (5.0f / 100.0f * product.getPrice())) * product.getQuantity();
        }
        return total;
    }

    @Override
    public int getDeliveryTime() {
        return ((int) (Math.random()*(7-3+1) + 3));
    }

    @Override
    public void addCoupons() {
        int newCoupons = (int) (Math.random()*(3-1+1) + 1);
        for (int i=0; i < newCoupons; i++) {
            float newCoupon = Math.round(2 * (Math.random()*(11-5+1) + 5)) / 2.0f;
            this.coupons.add(newCoupon);
            System.out.println("You have won a Coupon of " + newCoupon + "%!");
        }
    }
}


class Elite extends Customer {
    public Elite(String name, String password) {
        super(name, password);
        this.discount = 10.0f;
        this.extraDeliveryCharge = 0.0f;
    }

    public Elite(Customer customer) {
        super(customer.getName(), customer.getPassword());
        this.balance = customer.balance;
        this.cart = customer.cart;
        this.coupons = customer.coupons;
    }

    @Override
    public int getStatus() {
        return 2;
    }

    @Override
    public float getCartPrice() {
        float total = 0.0f;
        for (Product product: this.cart.values())
            total += (product.getPrice() - (10.0f/100.0f * product.getPrice())) * product.getQuantity();
        return total;
    }

    @Override
    public int getDeliveryTime() {
        return 2;
    }

    @Override
    public void addCoupons() {
        int newCoupons = (int) (Math.random()*(5-3+1) + 3);
        for (int i=0; i < newCoupons; i++) {
            float newCoupon = Math.round(2 * (Math.random()*(11-5+1) + 5)) / 2.0f;
            this.coupons.add(newCoupon);
            System.out.println("You have won a Coupon of " + newCoupon + "%!");
        }
    }

    @Override
    public void getFreeProduct(Flipzon flipzon) {
        if (Math.random() >= 0.5f) {
            List<Category> categoryList = new ArrayList<>(flipzon.getCategories().values());
            int luckyIndex = new Random().nextInt(categoryList.size());
            Category category = categoryList.get(luckyIndex);

            List<Product> productList = new ArrayList<>(category.getProductList());
            luckyIndex = new Random().nextInt(productList.size());
            Product product = productList.get(luckyIndex);

            System.out.println("You have won the following free Product from " + flipzon.getName() + "!");
            System.out.println(product);
        }
    }
}