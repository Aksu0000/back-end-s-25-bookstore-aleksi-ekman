package bes25.bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;

@Controller

public class BookController {
    
    @Autowired
    private BookRepository repository;

	public BookController(BookRepository repository) {
		this.repository = repository;
	}

    @GetMapping(value= {"/", "/booklist"})
	public String bookList(Model model) {
		model.addAttribute("books", repository.findAll());
		return "booklist";
	}

    @GetMapping(value = "/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping(value = "/save")
    public String saveEdit( Book book) {
        repository.save(book);
        return "redirect:/booklist";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }

    @GetMapping(value = "/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        Book book = repository.findById(bookId).get(); // haetaan kirja reposta
        model.addAttribute("book", book); // lisätään malliobjekti "book", jota lomake käyttää
        return "editbook"; // näkymä editbook.html
    }

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "Welcome to the Bookstore!";
    }

}
