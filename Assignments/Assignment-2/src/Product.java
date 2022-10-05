public class Product {
    private String id;
    private String name;
    private String details;
    private final float price;
    private int quantity;
    private float[] discounts;        // discounts in Order: {Elite, Prime, Normal}
    private Product p1;
    private Product p2;

    public Product(String id, String name, String details, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
        this.discounts = new float[] {0.0f, 0.0f, 0.0f};
    }

    public Product(String id, String name, String details, float price, int quantity, Product p1, Product p2) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
        this.discounts = new float[] {0.0f, 0.0f, 0.0f};
    }

    public String toString() {
        return (
            this.id + ": " + this.name
            + "\n" + "Price: " + this.price
            + "\n" + "Quantity: " + this.quantity
            + "\n" + "Other details: " + this.details
        );
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void resetQuantity() {
        this.quantity = 0;
    }

    public void incQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void setDiscounts(float[] discounts) {
        for (int i=0; i < 3; i++)
            this.discounts[i] = discounts[i];
    }
}