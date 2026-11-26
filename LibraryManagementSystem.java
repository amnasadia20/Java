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
 