import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

interface Checkout {
    double calculateTotal();
}

interface Payment {
    boolean processPayment(double amount);
}

abstract class Product {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract void display();
}

class Electronics extends Product {
    private String brand;

    public Electronics(String name, double price, String brand) {
        super(name, price);
        this.brand = brand;
    }

    @Override
    public void display() {
        System.out.println(" " + name + " | Brand: " + brand + " | Price: ₹" + price);
    }
}

class Clothing extends Product {
    public Clothing(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.println( name + " | Price: ₹" + price);
    }
}

class Beauty extends Product {
    private String brand;

    public Beauty(String name, double price, String brand) {
        super(name, price);
        this.brand = brand;
    }

    @Override
    public void display() {
        System.out.println( name + " | Brand: " + brand + " | Price: ₹" + price);
    }
}

class Books extends Product {
    private String author;

    public Books(String name, double price, String author) {
        super(name, price);
        this.author = author;
    }

    @Override
    public void display() {
        System.out.println( name + " | Author: " + author + " | Price: ₹" + price);
    }
}

class ShoppingCart implements Checkout{
    private List<Product> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public synchronized void addItem(Product item) {
        items.add(item);
    }

    public void removeItem(String itemName) {
        boolean itemRemoved = items.removeIf(item -> item.name.equalsIgnoreCase(itemName));
        if (!itemRemoved) {
            System.out.println("Warning: Item '" + itemName + "' not found in the cart.");
        }
    }

    public void displayCart() {
        for (Product item : items) {
            item.display();
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Product item : items) {
            total += item.price;
        }
        return total;
    }

    public boolean processPayment(Payment payment) {
        double total = calculateTotal();
        return payment.processPayment(total);
    }
}

class PaymentProcessor implements Payment {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of ₹" + amount);
        System.out.println("Pay the total amount during the delivery time");
        return true; // Simulated payment success
    }
}

class UPIPayment implements Payment {
    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your UPI ID: ");
        String upiId = scanner.nextLine();
        System.out.println("Payment of ₹" + amount + " made successfully via UPI to " + upiId);
        return true;
    }
}

class CardPayment implements Payment {
    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Payment of ₹" + amount + " made successfully via card ending with " + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the shopping cart!");

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your mobile number: ");
            String mobileNumber = scanner.nextLine();
            if (!Pattern.matches("\\d{10}", mobileNumber)) {
                throw new IllegalArgumentException("Invalid mobile number format. Please enter a 10-digit number.");
            }
            System.out.print("Enter your email address: ");
            String email = scanner.nextLine();
            if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
                throw new IllegalArgumentException("Invalid email address format. Please enter a valid email address.");
            }
            System.out.print("Enter your City: ");
            String address = scanner.nextLine();

            Electronics laptop = new Electronics("Laptop", 99999, "Dell");
            Electronics smartphone = new Electronics("Smartphone", 69999, "Samsung");
            Electronics printer = new Electronics("Printer", 14999, "HP");
            Electronics watch = new Electronics("SmartWatch", 4999, "Boat");
            Clothing shirt = new Clothing("T Shirt", 1999);
            Clothing jeans = new Clothing("Jeans", 3999);
            Clothing skirt = new Clothing("Skirt", 1499);
            Clothing saree = new Clothing("Saree", 5999);
            Clothing kurta = new Clothing("Kurta", 1999);
            Beauty lipstick = new Beauty("Lipstick", 999, "Maybelline");
            Beauty shampoo = new Beauty("Shampoo", 1299, "Head & Shoulders");
            Beauty sunscreen = new Beauty("Sunscreen", 799, "Lotus");
            Beauty moisturizer = new Beauty("Moisturizer", 1999, "Lakme");
            Books novel = new Books("Novel", 1599, "John Doe");
            Books cookbook = new Books("Cookbook", 2499, "Jane Smith");
            Books fiction = new Books("Fiction", 1999, "J K Rowling");

            ShoppingCart cart = new ShoppingCart();

            boolean continueShopping = true;
            while (continueShopping) {
                System.out.println("Choose a category to shop:");
                System.out.println("1-Electronics");
                System.out.println("2-Clothing");
                System.out.println("3-Beauty");
                System.out.println("4-Books");
                System.out.println("5-(Continue to next step)");
                System.out.print("Enter category number (1-5): ");
                int categoryChoice = scanner.nextInt();

                switch (categoryChoice) {
                    case 1:
                        System.out.println("Electronics:");
                        laptop.display();
                        smartphone.display();
                        printer.display();
                        watch.display();
                        System.out.print("Enter item name to add to cart: ");
                        String electronicsItem = scanner.next();
                        if (electronicsItem.equalsIgnoreCase("Laptop")) {
                            cart.addItem(laptop);
                        } else if (electronicsItem.equalsIgnoreCase("Smartphone")) {
                            cart.addItem(smartphone);
                        }
                        else if (electronicsItem.equalsIgnoreCase("Printer")) {
                            cart.addItem(printer);
                        }
                        else if (electronicsItem.equalsIgnoreCase("SmartWatch")) {
                            cart.addItem(watch);
                        }
                        else {
                            System.out.println("Invalid item!");
                        }
                        break;
                    case 2:
                        System.out.println("Clothing:");
                        shirt.display();
                        jeans.display();
                        skirt.display();
                        saree.display();
                        kurta.display();
                        System.out.print("Enter item name to add to cart: ");
                        String clothingItem = scanner.next();
                        if (clothingItem.equalsIgnoreCase("T Shirt")) {
                            cart.addItem(shirt);
                        } else if (clothingItem.equalsIgnoreCase("Jeans")) {
                            cart.addItem(jeans);
                        }
                        else if (clothingItem.equalsIgnoreCase("Skirt")) {
                            cart.addItem(skirt);
                        }
                        else if (clothingItem.equalsIgnoreCase("Saree")) {
                            cart.addItem(saree);
                        }
                        else if (clothingItem.equalsIgnoreCase("Kurta")) {
                            cart.addItem(kurta);
                        }else {
                            System.out.println("Invalid item!");
                        }
                        break;
                    case 3:
                        System.out.println("Beauty:");
                        lipstick.display();
                        shampoo.display();
                        sunscreen.display();
                        moisturizer.display();
                        System.out.print("Enter item name to add to cart: ");
                        String beautyItem = scanner.next();
                        if (beautyItem.equalsIgnoreCase("Lipstick")) {
                            cart.addItem(lipstick);
                        } else if (beautyItem.equalsIgnoreCase("Shampoo")) {
                            cart.addItem(shampoo);
                        }
                        else if (beautyItem.equalsIgnoreCase("Sunscreen")) {
                            cart.addItem(sunscreen);
                        }
                        else if (beautyItem.equalsIgnoreCase("Moisturizer")) {
                            cart.addItem(moisturizer);
                        }else {
                            System.out.println("Invalid item!");
                        }
                        break;
                    case 4:
                        System.out.println("Books:");
                        novel.display();
                        cookbook.display();
                        fiction.display();
                        System.out.print("Enter item name to add to cart: ");
                        String bookItem = scanner.next();
                        if (bookItem.equalsIgnoreCase("Novel")) {
                            cart.addItem(novel);
                        } else if (bookItem.equalsIgnoreCase("Cookbook")) {
                            cart.addItem(cookbook);
                        }
                        else if (bookItem.equalsIgnoreCase("Fiction")) {
                            cart.addItem(fiction);
                        }else {
                            System.out.println("Invalid item!");
                        }
                        break;
                    case 5:
                        if(cart.calculateTotal() == 0) {
                            System.out.println("No items in the cart. Exiting...");
                            continueShopping = false;
                        } else {
                            continueShopping = false;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
            if (cart.calculateTotal() == 0) {
                scanner.close();
                return;
            }
            cart.displayCart();

            double totalAmount = cart.calculateTotal();
            System.out.println("Total amount: ₹" + totalAmount);

            boolean continueRemoval = true;
            while (continueRemoval) {
                System.out.println("1-Remove item from cart");
                System.out.println("2-Continue to payment");
                System.out.print("Enter choice (1-2): ");
                int cartOption = scanner.nextInt();

                switch (cartOption) {
                    case 1:
                        System.out.println("Enter item name to remove from cart: ");
                        String itemToRemove = scanner.next();
                        cart.removeItem(itemToRemove);
                        break;
                    case 2:
                        continueRemoval = false;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            }

            System.out.println("Choose payment method:");
            System.out.println("1-UPI");
            System.out.println("2-Card");
            System.out.println("3-Cash on Delivery");
            System.out.print("Enter payment method number (1-3): ");
            int paymentChoice = scanner.nextInt();
            Payment paymentMethod;
            switch (paymentChoice) {
                case 1:
                    paymentMethod = new UPIPayment();
                    break;
                case 2:
                    paymentMethod = new CardPayment();
                    break;
                case 3:
                    paymentMethod = new PaymentProcessor();
                    break;
                default:
                    System.out.println("Invalid payment choice. Proceeding with Cash on Delivery.");
                    paymentMethod = new PaymentProcessor();
                    break;
            }
            boolean paymentResult = cart.processPayment(paymentMethod);
            if (paymentResult) {
                System.out.println("Order successful. Thank you for your purchase!");
            } else {
                System.out.println("Payment failed. Please try again.");
            }

            scanner.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
}
}
}