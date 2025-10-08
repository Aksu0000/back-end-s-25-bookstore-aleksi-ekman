package bes25.bookstore.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;
import org.springframework.web.bind.annotation.RequestBody;

//import org.springframework.web.bind.annotation.RequestBody;
@RestController
public class BookRestController {

    private final BookRepository bookRepository;

	public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    //hae kaikki kirjat
    @GetMapping("/books")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    //hae kirja id:n perusteella
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }
    
    // päivitä olemassa oleva kirja JSON:illa
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> editBook(@RequestBody Book editedBook, @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    editedBook.setId(id);
                    bookRepository.save(editedBook);
                    return ResponseEntity.ok(editedBook);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Poista kirja ID:n perusteella
    @PreAuthorize("hasAuthorities('ADMIN')")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    
}
