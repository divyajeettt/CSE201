public interface Item {
    String getId();
    String getName();
    String print(int status);
    float getPrice(int status);
    float getDiscount(int status);
    int getQuantity();
    void setQuantity(int quantity);
    boolean isDeal();
}