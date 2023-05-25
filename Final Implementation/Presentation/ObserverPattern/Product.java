import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private double price;
    private List<ProductObserver> observers = new ArrayList<>();
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }
    
    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    } 
    private void notifyObservers() {
        for (ProductObserver observer : observers) {
            observer.update(name, price);
        }
    }
}

