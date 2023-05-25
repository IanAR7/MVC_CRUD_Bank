public class User extends ProductObserver {
    private String name;
    
    public User(String name) {
        this.name = name;
    }
    
    public void update(String name, double price) {
        System.out.println(name + " ha cambiado su precio a " + price + ". El usuario " + this.name + " ha sido notificado.");
    }
}

