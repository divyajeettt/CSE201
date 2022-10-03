import java.util.*;


public class Customer {
    private final String name;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private int age;
    protected float wallet;
    protected HashMap<String, Product> cart;
    protected float discount;
    protected int deliveryTime;
    protected float extraDeliveryCharge;

    public Customer(String name, String password, String email, String phoneNumber, int age) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.wallet = 1000.0f;
        this.cart = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public float getBalance() {
        return this.wallet;
    }

    public boolean matchCredentials(String name, String email, String password) {
        return (this.name.equals(name) && this.email.equals(email) && this.password.equals(password));
    }

    public void addBalance(float amount) {
        this.wallet += amount;
    }
}


class Normal extends Customer {
    public Normal(String name, String password, String email, String phoneNumber, int age) {
        super(name, password, email, phoneNumber, age);
        this.discount = 0.0f;
        this.deliveryTime = (int) (Math.random()*(10-7+1) + 7);
        this.extraDeliveryCharge = 5;
    }
}


class Prime extends Customer {
    public Prime(String name, String password, String email, String phoneNumber, int age) {
        super(name, password, email, phoneNumber, age);
        this.discount = 5.0f;
        this.deliveryTime = (int) (Math.random()*(6-3+1) + 3);
        this.extraDeliveryCharge = 2;
    }
}

class Elite extends Customer {
    public Elite(String name, String password, String email, String phoneNumber, int age) {
        super(name, password, email, phoneNumber, age);
        this.discount = 10.0f;
        this.deliveryTime = 2;
        this.extraDeliveryCharge = 0;
    }
}