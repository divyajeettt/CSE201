import java.util.*;


public class Category {
    private final String id;
    private final String name;
    private HashMap<String, Product> products;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
        this.products = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<String, Product> getProducts() {
        return this.products;
    }

    public Collection<Product> getProductList() {
        return this.products.values();
    }

    public boolean hasProduct(String pId) {
        return this.products.containsKey(pId);
    }

    public void addProduct(Product product) {
        this.products.put(product.getId(), product);
    }

    public void deleteProduct(String pId) {
        this.products.remove(pId);
    }
}