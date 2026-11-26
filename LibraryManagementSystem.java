import java.util.ArrayList;
 
/**
 * Main class: creates Book objects, stores them in an ArrayList
 * and demonstrates searching, borrowing, returning and price analysis.
 */
public class LibraryManagementSystem {
 
    // Searches the list for a book by ISBN.
    // Displays the details if found, otherwise displays a "not found" message.
    public static Book searchByISBN(ArrayList<Book> books, String isbn) {
        for (Book b : books) {
            if (b.getISBN().equals(isbn)) {
                System.out.println("Book found:");
                b.displayDetails();
                return b;
            }
        }
        System.out.println("Book with ISBN " + isbn + " was not found.");
        return null;
    }
    // Calculates and returns the average price of all books in the list
    public static double calculateAveragePrice(ArrayList<Book> books) {
        if (books.isEmpty()) {
            return 0.0;   // avoid division by zero
        }
        double total = 0;
        for (Book b : books) {
            total += b.getPrice();
        }
        return total / books.size();
    }
 
    public static void main(String[] args) {
 
        // Create the collection that will hold all books
        ArrayList<Book> library = new ArrayList<>();
 
        // Create five Book objects using the parameterized constructor
        library.add(new Book("Java: The Complete Reference", "Herbert Schildt", "9781260440232", 45.99, "Programming", true));
        library.add(new Book("Clean Code", "Robert C. Martin", "9780132350884", 55.50, "Software Engineering", true));
        library.add(new Book("The Hobbit, or There and back again", "J.R.R Tolkein", "9780062315007", 14.99, "Fiction", true));
        library.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", "9780262046305", 89.00, "Computer Science", false));
        library.add(new Book("The Seerah of the Prophet", "Safiur Rahman Mubarakpuri", "9789960899558", 20.00, "Islamic Studies", true));
 
        // ---------- 1. Display all books and their price categories ----------
        System.out.println("========== ALL BOOKS IN THE LIBRARY ==========");
        int availableCount = 0;
        int number = 1;
        for (Book b : library) {
            System.out.println("\nBook " + number++);
            b.displayDetails();
            System.out.println("Price Category : " + b.getPriceCategory());
            if (b.isAvailable()) {
                availableCount++;      // count the available books
            }
        }
        System.out.println("\nNumber of books currently available: " + availableCount
                + " out of " + library.size());
        // ---------- 2. Searching by ISBN ----------
        System.out.println("\n========== SEARCHING BY ISBN ==========");
        System.out.println("Searching for ISBN 9780132350884...");
        searchByISBN(library, "9780132350884");          // existing book
        System.out.println("\nSearching for ISBN 1111111111111...");
        searchByISBN(library, "1111111111111");          // non-existing book
 
        // ---------- 3. Borrowing and returning ----------
        System.out.println("\n========== BORROWING AND RETURNING ==========");
        Book selected = library.get(2);                  // " The Hobbit, or There and back again "
        System.out.println("Borrowing the book:");
        selected.borrowBook();                           // borrow it
        System.out.println("\nAttempting to borrow the same book again:");
        selected.borrowBook();                           // fails: already borrowed
        System.out.println("\nReturning the book:");
        selected.returnBook();                           // return it
        System.out.println("\nAttempting to return it again:");
        selected.returnBook();                           // fails: not borrowed
        System.out.println("\nUpdated availability status of \"" + selected.getTitle() + "\": "
                + (selected.isAvailable() ? "Available" : "Not Available (Borrowed)"));
 
        // ---------- 4. Price validation (encapsulation demo) ----------
        System.out.println("\n========== PRICE VALIDATION ==========");
        selected.setPrice(-10);                          // rejected
        System.out.printf("Price after invalid update: $%.2f%n", selected.getPrice());
 
        // ---------- 5. Price analysis ----------
        System.out.println("\n========== PRICE ANALYSIS ==========");
        double average = calculateAveragePrice(library);
        System.out.printf("Average book price: $%.2f%n", average);
    }
}
 
/**
 * Book class: models a single book in the library.
 * All fields are private (encapsulation) and are accessed through getters/setters.
 */
class Book {
 
    // ---------- Private instance variables ----------
    private String title;
    private String author;
    private String ISBN;
    private double price;
    private String category;
    private boolean available;   // true = on the shelf, false = borrowed
     // ---------- Constructors ----------
 
    // Default constructor: assigns sensible default values
    public Book() {
        this.title = "Unknown Title";
        this.author = "Unknown Author";
        this.ISBN = "0000000000";
        this.price = 0.0;
        this.category = "General";
        this.available = true;   // a new book starts as available
    }
 
    // Parameterized constructor: initializes all book information
    public Book(String title, String author, String ISBN,
                double price, String category, boolean available) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        setPrice(price);          // reuse the setter so validation is applied
        this.category = category;
        this.available = available;
    }
 
    // ---------- Getters ----------
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public String getISBN()     { return ISBN; }
    public double getPrice()    { return price; }
    public String getCategory() { return category; }
    public boolean isAvailable(){ return available; }
 
    // ---------- Setters ----------
    public void setTitle(String title)       { this.title = title; }
    public void setAuthor(String author)     { this.author = author; }
    public void setISBN(String ISBN)         { this.ISBN = ISBN; }
    public void setCategory(String category) { this.category = category; }
    public void setAvailable(boolean available) { this.available = available; }
 
    // Price setter with validation: price must not be negative
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            // Invalid value: reject it and keep the previous price
            System.out.println("Invalid price (" + price + ") for \"" + title
                    + "\". Price cannot be negative. Price not changed.");
        }
    }
// ---------- Instance methods ----------
 
    // Displays all details of the book in a clear format
    public void displayDetails() {
        System.out.println("ISBN     : " + ISBN);
        System.out.println("Title    : " + title);
        System.out.println("Author   : " + author);
        System.out.println("Category : " + category);
        System.out.printf("Price    : $%.2f%n", price);
        System.out.println("Status   : " + (available ? "Available" : "Not Available (Borrowed)"));
    }
 
    // Borrows the book only if it is currently available
    public void borrowBook() {
        if (available) {
            available = false;
            System.out.println("\"" + title + "\" has been borrowed successfully.");
        } else {
            System.out.println("Sorry, \"" + title + "\" cannot be borrowed. It is already borrowed.");
        }
    }
 