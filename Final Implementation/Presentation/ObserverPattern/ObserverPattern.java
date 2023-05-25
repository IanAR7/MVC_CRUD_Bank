public class ObserverPattern {
    public static void main(String[] args) {
        Product product = new Product("Smartphone", 800); 
        User user1 = new User("Juan");
        User user2 = new User("Ana");

        product.addObserver(user1);
        product.addObserver(user2);
        product.setPrice(700); 
        // Notifica a usuarios interesados en el producto sobre el cambio de precio.
    }
}


