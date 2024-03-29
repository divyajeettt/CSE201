import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Customer implements User {
    private final String name;
    private final String password;
    protected float balance = 1000.0f;
    protected HashMap<String, Item> cart = new HashMap<>();
    protected ArrayList<Float> coupons = new ArrayList<>();
    protected float discount;
    protected float extraDeliveryCharge;

    public abstract int getDeliveryTime();
    public abstract int getStatus();
    public abstract float getDiscount(Item item);
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

    public HashMap<String, Item> getCart() {
        return this.cart;
    }

    public boolean isEmptyCart() {
        return (this.cart.size() == 0);
    }

    public boolean hasInCart(String pId) {
        return this.cart.containsKey(pId);
    }

    public void incQuantity(String pId, int quantity) {
        Product product = (Product) this.cart.get(pId);
        product.incQuantity(quantity);
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

    public void addToCart(Item item) {
        this.cart.put(item.getId(), item);
    }

    public void viewCoupons() {
        if (this.coupons.size() == 0)
            System.out.println("You do not have any Coupons!");
        else {
            for (int i=0; i < this.coupons.size(); i++)
                System.out.println("Coupon-" + (i+1) + ": " + this.coupons.get(i) + "%");
        }
    }

    public void manageCart(Flipzon flipzon) {
        boolean isUpdated = false;
        for (String pId: this.cart.keySet()) {
            Item item = this.cart.get(pId);
            if (flipzon.getProduct(pId) == null) {
                System.out.println("Sorry, we are currently short on Stock of " + item.getName() + "(s)!");
                this.cart.remove(pId);
                isUpdated = true;
                continue;
            }
            int available = flipzon.getProduct(pId).getQuantity();
            int inCart = item.getQuantity();
            if (available < inCart) {
                System.out.println("Sorry, we are currently short on Stock of " + item.getName() + "(s)!");
                this.cart.get(pId).setQuantity(inCart - available);
                isUpdated = true;
            }
        }
        if (isUpdated) {
            System.out.println("Your Cart has been modified! Here is your updated Cart!");
            this.viewCart();
        }
    }

    public void viewCart() {
        if (this.isEmptyCart())
            System.out.println("Your Cart is empty!");
        else {
            int numProducts = 0;
            for (Item product: this.cart.values()) {
                if (product.isDeal())
                    continue;
                numProducts++;
                System.out.println("Product " + numProducts + ":");
                System.out.println(product.print(3));
            }
            int numDeals = 0;
            for (Item deal: this.cart.values()) {
                if (!deal.isDeal())
                    continue;
                numDeals++;
                System.out.println("Deal " + numDeals + ":");
                System.out.println(deal.print(this.getStatus()));
            }
        }
    }

    public void emptyCart() {
        this.cart.clear();
    }

    public void removeFromCart(String pId, String pName, int quantity) {
        if (!this.hasInCart(pId))
            System.out.println("Item " + pName + " does not exist in your Cart!");
        else {
            int inCart = this.cart.get(pId).getQuantity();
            if (quantity > inCart)
                System.out.println("There are only " + inCart + " " + pName + "(s) in your Cart!");
            else {
                this.cart.get(pId).setQuantity(inCart - quantity);
                if (this.cart.get(pId).getQuantity() == 0)
                    this.cart.remove(pId);
                System.out.println(quantity + " " + pName + "(s) removed from your Cart successfully!");
            }
        }
    }

    public void addCoupons(int numCoupons) {
        for (int i=0; i < numCoupons; i++) {
            float newCoupon = ThreadLocalRandom.current().nextInt(5, 15+1);
            if (Math.random() >= 0.5f)
                newCoupon += 0.5f;
            this.coupons.add(newCoupon);
            System.out.println("You have won a Coupon of " + newCoupon + "%!");
        }
    }

    public float getCartPrice(float coupon) {
        float total = 0.0f;
        boolean usedCoupon = false;
        for (Item item: this.cart.values()) {
            if (item.isDeal()) {
                total += item.getPrice(this.getStatus());
                continue;
            }
            float price = item.getPrice(3) * item.getQuantity();
            float maxDiscount = Math.max(this.discount, Math.max(coupon, this.getDiscount(item)));
            total += price - maxDiscount / 100.0f * price;
            if (!usedCoupon && maxDiscount == coupon && !item.isDeal())
                usedCoupon = true;
        }
        if (coupon != 0 && usedCoupon) {
            System.out.println("Used a Coupon of " + coupon + "%!");
            this.coupons.remove(coupon);
        }
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
            System.out.println("Delivery Charges: Rs. 100.0/-");
        System.out.println("Final Amount: Rs. " + finalTotal + "/-");
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
        return 2;
    }

    @Override
    public float getDiscount(Item item) {
        return item.getDiscount(2);
    }

    @Override
    public int getDeliveryTime() {
        return ThreadLocalRandom.current().nextInt(7, 10+1);
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
    public float getDiscount(Item item) {
        return item.getDiscount(1);
    }

    @Override
    public int getDeliveryTime() {
        return ThreadLocalRandom.current().nextInt(3, 6+1);
    }

    @Override
    public int getNumCoupons() {
        return ThreadLocalRandom.current().nextInt(1, 2+1);
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
        return 0;
    }

    @Override
    public float getDiscount(Item item) {
        return item.getDiscount(0);
    }

    @Override
    public int getDeliveryTime() {
        return 2;
    }

    @Override
    public int getNumCoupons() {
        return ThreadLocalRandom.current().nextInt(3, 4+1);
    }

    @Override
    public void getFreeProduct(Flipzon flipzon) {
        if (Math.random() >= 0.5f) {
            List<Category> categoryList = new ArrayList<>(flipzon.getCategoryList());
            int luckyIndex = new Random().nextInt(categoryList.size());
            Category category = categoryList.get(luckyIndex);
            if (category.getId().equals("Dx0"))
                return;

            List<Item> productList = new ArrayList<>(category.getProductList());
            luckyIndex = new Random().nextInt(productList.size());
            Product product = (Product) productList.get(luckyIndex);

            System.out.println("You have won ONE piece of the following free Product from " + flipzon.getName() + "!");
            System.out.println(product.print(3));
            flipzon.getProduct(product.getId()).setQuantity(product.getQuantity() - 1);
            if (flipzon.getProduct(product.getId()).getQuantity() == 0)
                flipzon.deleteProduct(product.getId());
        }
    }
}