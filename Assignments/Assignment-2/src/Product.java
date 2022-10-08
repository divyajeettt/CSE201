public class Product {
    private String id;
    private String name;
    private final float price;
    private String details;
    private int quantity;
    private Product[] dealProducts = new Product[] {null, null};
    private float[] dealPrices = new float[] {0.0f, 0.0f, 0.0f};
    private float[] discounts = new float[] {0.0f, 0.0f, 0.0f};

    public Product(String id, String name, float price, int quantity, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int dealNum, Product p1, Product p2, float[] dealPrices) {
        this.id = "D-" + dealNum;
        this.name = "Deal-" + dealNum;
        this.details = "This Deal is an offer on the Products: " + p1.name + " and " + p2.name;
        this.price = p1.price + p2.price;
        this.dealPrices = dealPrices;
        this.quantity = 1;
        this.dealProducts = new Product[] {p1, p2};
    }

    public Product(Product product, int newQuantity) {
        this.id = product.id;
        this.name = product.name;
        this.details = product.details;
        this.price = product.price;
        this.quantity = newQuantity;
        this.discounts = product.discounts;
        this.dealProducts = product.dealProducts;
    }

    public String toString() {
        return (
            this.id + ": " + this.name
            + "\n" + "Price (for 1 piece): Rs. " + this.price + "/-"
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

    public float[] getDiscounts() {
        return this.discounts;
    }

    public Product[] getDealProducts() {
        return this.dealProducts;
    }

    public int getQuantity() {
        return this.quantity;
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
}