import java.util.*;


public class Customer {
    private final String name;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private int age;
    protected float balance = 1000.0f;
    protected ArrayList<Product> cart = new ArrayList<>();
    protected ArrayList<Float> coupons = new ArrayList<>();
    protected float discount;
    protected int deliveryTime;
    protected float extraDeliveryCharge;

    public Customer(String name, String password, String email, String phoneNumber, int age) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getDeliveryTime() {
        return this.deliveryTime;
    }

    public float getBalance() {
        return this.balance;
    }

    public int getCartSize() {
        return this.cart.size();
    }

    public boolean matchCredentials(String name, String email, String password) {
        return (this.name.equals(name) && this.email.equals(email) && this.password.equals(password));
    }

    public void addBalance(float amount) {
        this.balance += amount;
    }

    public void addToCart(Product product) {
        this.cart.add(product);
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
        if (this.getCartSize() == 0)
            System.out.println("Your cart is empty!");
        else {
            int products = 0;
            int deals = 0;
            for (Product product: this.cart) {
                String type = (product.getId().contains("D-") ? "Deal" : "Product");
                if (type.equals("Deal"))
                    deals++;
                else
                    products++;
                int num = (product.getId().contains("D-") ? deals : products);
                System.out.println(type + num + ": ");
                System.out.println(product);
            }
        }
    }

    public void emptyCart() {
        this.cart = new ArrayList<>();
    }
}


class Normal extends Customer {
    public Normal(String name, String password, String email, String phoneNumber, int age) {
        super(name, password, email, phoneNumber, age);
        this.discount = 0.0f;
        this.deliveryTime = (int) (Math.random()*(11-7+1) + 7);
        this.extraDeliveryCharge = 5;
//        this.coupons = 0;
    }

    public void setDeliveryTime() {
        this.deliveryTime = (int) (Math.random() * (11 - 7 + 1) + 7);
    }

//    public void setCoupons() {
//        this.coupons = 0;
//    }
}


class Prime extends Customer {
    public Prime(String name, String password, String email, String phoneNumber, int age) {
        super(name, password, email, phoneNumber, age);
        this.discount = 5.0f;
        this.deliveryTime = (int) (Math.random()*(7-3+1) + 3);
        this.extraDeliveryCharge = 2;
//        this.coupons = (int) (Math.random()*(3-1+1) + 1);
    }

    public void setDeliveryTime() {
        this.deliveryTime = (int) (Math.random()*(7-3+1) + 3);
    }

//    public void setCoupons() {
//        this.coupons = (int) (Math.random()*(3-1+1) + 1);
//    }
}

class Elite extends Customer {
    public Elite(String name, String password, String email, String phoneNumber, int age) {
        super(name, password, email, phoneNumber, age);
        this.discount = 10.0f;
        this.deliveryTime = 2;
        this.extraDeliveryCharge = 0;
//        this.coupons = (int) (Math.random()*(5-3+1) + 3);
    }

    public void setDeliveryTime() {
        this.deliveryTime = (int) (Math.random()*(7-3+1) + 3);
    }

//    public void setCoupons() {
//        this.coupons = (int) (Math.random()*(5-3+1) + 3);
//    }
}