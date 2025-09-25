package bes25.bookstore.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


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
    public @ResponseBody Book findBookRest(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }
    
    
}
