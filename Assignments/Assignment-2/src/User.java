public interface User {
    String getName();
    boolean matchCredentials(String name, String password);
}