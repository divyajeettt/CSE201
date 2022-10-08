import java.util.*;


public abstract class Customer {
    private final String name;
    private final String password;
    protected float balance = 1000.0f;
    protected HashMap<String, Product> cart = new HashMap<>();
    protected ArrayList<Float> coupons = new ArrayList<>();
    protected float discount;
    protected float extraDeliveryCharge;

    public abstract int getDeliveryTime();
    public abstract int getStatus();
    public abstract float getDiscount(Product product);
    public abstract int getNumCoupons();
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

    public void manageCart(Flipzon flipzon) {
        boolean isUpdated = false;
        for (String pId: this.cart.keySet()) {
            Product product = this.cart.get(pId);
            int available = flipzon.getProduct(product.getId()).getQuantity();
            int inCart = product.getQuantity();
            if (available < inCart) {
                System.out.println("Sorry, we are currently short on Stock of " + product.getName() + "(s)!");
                this.cart.get(pId).setQuantity(inCart - available);
                isUpdated = true;
            }
        }
        if (isUpdated) {
            System.out.println("Your Cart is been modified! Here is your updated Cart!");
            this.viewCart();
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

    public void addCoupons(int numCoupons) {
        int newCoupons = (int) (Math.random()*(5-3+1) + 3);
        for (int i=0; i < numCoupons; i++) {
            float newCoupon = Math.round(2 * (Math.random()*(16-5+1) + 5)) / 2.0f;
            this.coupons.add(newCoupon);
            System.out.println("You have won a Coupon of " + newCoupon + "%!");
        }
    }

    public float getCartPrice(float coupon) {
        float total = 0.0f;
        boolean usedCoupon = false;
        for (Product product: this.cart.values()) {
            float price = product.getPrice() * product.getQuantity();
            float maxDiscount = Math.max(this.discount, Math.max(coupon, this.getDiscount(product)));
            if (product.getId().contains("D-"))
                total += price;
            else
                total += price - maxDiscount/100.0f * price;
            if (!usedCoupon && maxDiscount == coupon && !product.getId().contains("D-"))
                usedCoupon = true;
        }
        if (coupon != 0 && usedCoupon)
            this.coupons.remove(coupon);
        return total;
    }

    public void checkOutCart(Flipzon flipzon) {
        float price = this.getCartPrice(this.getMaxCoupon());
        float extraDeliveryCharge = this.getExtraDeliveryCharge();
        float deliveryCharge = 100.0f + (extraDeliveryCharge/100.0f * price);
        float finalTotal = price + deliveryCharge;

        System.out.println("Total Cost of Items: Rs. " + price + "/-");
        if (extraDeliveryCharge != 0)
            System.out.println(
                "Delivery Charges: Rs. 100 + " + extraDeliveryCharge + "% of " + price + " = Rs. " + deliveryCharge + "/-"
            );
        else
            System.out.println("Delivery Charges: Rs. 100/-");
        System.out.println("Final Amount: Rs. " + finalTotal);
        flipzon.placeOrder(this, finalTotal);
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
        this.discount = 0.0f;
        this.extraDeliveryCharge = 5.0f;
        this.balance = customer.balance;
        this.cart = customer.cart;
        this.coupons = customer.coupons;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public float getDiscount(Product product) {
        return product.getDiscounts()[2];
    }

    @Override
    public int getDeliveryTime() {
        return ((int) (Math.random()*(11-7+1) + 7));
    }

    @Override
    public int getNumCoupons() {
        return 0;
    }
}


class Prime extends Customer {
    public Prime(Customer customer) {
        super(customer.getName(), customer.getPassword());
        this.discount = 5.0f;
        this.extraDeliveryCharge = 2.0f;
        this.balance = customer.balance;
        this.cart = customer.cart;
        this.coupons = customer.coupons;
    }

    @Override
    public int getStatus() {
        return 1;
    }

    @Override
    public float getDiscount(Product product) {
        return product.getDiscounts()[1];
    }

    @Override
    public int getDeliveryTime() {
        return ((int) (Math.random()*(7-3+1) + 3));
    }

    @Override
    public int getNumCoupons() {
        return ((int) (Math.random()*(3-1+1) + 1));
    }
}


class Elite extends Customer {
    public Elite(Customer customer) {
        super(customer.getName(), customer.getPassword());
        this.discount = 10.0f;
        this.extraDeliveryCharge = 0.0f;
        this.balance = customer.balance;
        this.cart = customer.cart;
        this.coupons = customer.coupons;
    }

    @Override
    public int getStatus() {
        return 2;
    }

    @Override
    public float getDiscount(Product product) {
        return product.getDiscounts()[0];
    }

    @Override
    public int getDeliveryTime() {
        return 2;
    }

    @Override
    public int getNumCoupons() {
        return ((int) (Math.random()*(5-3+1) + 1));
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