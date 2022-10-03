import java.util.*;


public class Customer {
    private String name;
    private String password;
    private float wallet;
    private HashMap<String, Product> cart;
}


class Normal extends Customer {}
class Prime extends Customer {}
class Elite extends Customer {}