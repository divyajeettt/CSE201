public class Product {
    private final String id;
    private final String name;
    private final String details;
    private final float price;
    private int quantity;
    private float[] discounts;        // discounts in Order: {Elite, Prime, Normal}

    public Product(String id, String name, String details, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
        this.discounts = new float[] {0.0f, 0.0f, 0.0f};
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDetails() {
        return this.details;
    }

    public float getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.getQuantity();
    }

    public void setDiscounts(float[] discounts) {
        for (int i=0; i < 3; i++)
            this.discounts[i] = discounts[i];
    }
}
