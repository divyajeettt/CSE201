import java.util.*;


public class Category {
    private final String id;
    private final String name;
    private HashMap<String, Item> items;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
        this.items = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<String, Item> getProducts() {
        return this.items;
    }

    public Collection<Item> getProductList() {
        return this.items.values();
    }

    public boolean hasProduct(String pId) {
        return this.items.containsKey(pId);
    }

    public void addProduct(Item product) {
        this.items.put(product.getId(), product);
    }

    public void deleteProduct(String pId) {
        this.items.remove(pId);
    }
}