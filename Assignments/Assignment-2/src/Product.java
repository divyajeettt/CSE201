public class Product {
    private String id;
    private String name;
    private final float price;
    private String details;
    private int quantity = 0;
    private float[] discounts;        // discounts in Order: {Elite, Prime, Normal}

    public Product(String id, String name, float price, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.discounts = new float[] {0.0f, 0.0f, 0.0f};
    }

    public String toString() {
        return (
            this.id + ": " + this.name
            + "\n" + "Price (for 1 piece): " + this.price
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
        System.arraycopy(discounts, 0, this.discounts, 0, 3);
    }
}