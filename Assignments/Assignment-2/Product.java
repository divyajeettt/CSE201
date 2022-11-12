public class Product implements Item {
    private final String id;
    private final String name;
    private final float price;
    private final String details;
    private int quantity;
    private float[] discounts = new float[] {0.0f, 0.0f, 0.0f};

    public Product(String id, String name, float price, int quantity, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Product product, int newQuantity) {
        this.id = product.id;
        this.name = product.name;
        this.details = product.details;
        this.price = product.price;
        this.quantity = newQuantity;
        this.discounts = product.discounts;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String print(int status) {
        return (
            this.id + ": " + this.name
            + "\n\t" + "Price (for 1 piece): Rs. " + this.price + "/-"
            + "\n\t" + "Quantity: " + this.quantity
            + "\n\t" + "Other details: " + this.details
        );
    }

    public float getPrice(int status) {
        return this.price;
    }

    public float getDiscount(int status) {
        return this.discounts[status];
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void setDiscounts(float[] discounts) {
        System.arraycopy(discounts, 0, this.discounts, 0, 3);
    }

    public boolean isDeal() {
        return false;
    }
}