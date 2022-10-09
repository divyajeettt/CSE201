public class Deal implements Item {
    private final int num;
    private final Product[] products;
    private int quantity;
    private final float[] prices;
    private final float[] discounts = new float[]{0.0f, 0.0f, 0.0f};

    public Deal(int num, Product p1, Product p2, float[] prices) {
        this.num = num;
        this.products = new Product[] {p1, p2};
        this.prices = prices;
    }

    public Deal(Deal product, int newQuantity) {
        this.num = product.num;
        this.quantity = newQuantity;
        this.products = product.products;
        this.prices = product.prices;
    }

    public String print(int status) {
        if (status == 3)
            return (
                "D-" + this.num + ": " + "Deal-" + this.num
                + "\n" + "Giveaway Prices:"
                + "\n\t Elite: Rs. " + this.prices[0] + "/-"
                + "\n\t Prime: Rs. " + this.prices[1] + "/-"
                + "\n\t Normal: Rs. " + this.prices[2] + "/-"
                + "\n" + "Details: This Deal is an offer on the Products "
                + this.products[0].getName() + " and " + this.products[1].getName()
            );
        else
            return (
                "D-" + this.num + ": " + "Deal-" + this.num
                + "\n\t" + "Giveaway Price: Rs. " + this.prices[status] + "/-"
                + "\n\t" + "Details: This Deal is an offer on the Products "
                + this.products[0].getName() + " and " + this.products[1].getName()
            );
    }

    public String getId() {
        return ("D-" + this.num);
    }

    public String getName() {
        return ("Deal-" + this.num);
    }

    public float getPrice(int status) {
        return this.prices[status];
    }

    public float getDiscount(int status) {
        return this.discounts[status];
    }

    public Product[] getProducts() {
        return this.products;
    }

    public int getQuantity() {
        return Math.min(this.products[0].getQuantity(), this.products[1].getQuantity());
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDeal() {
        return true;
    }
}