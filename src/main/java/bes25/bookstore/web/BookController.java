package bes25.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import bes25.bookstore.domain.BookRepository;

@Controller

public class BookController {
    private BookRepository repository;
	// constructor injection. Can only be one constructor then.
	public BookController(BookRepository repository) {
		this.repository = repository;
	}

    @RequestMapping(value= {"/", "/booklist"})
	public String bookList(Model model) {
		model.addAttribute("books", repository.findAll());
		return "booklist";
	}

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "Welcome to the Bookstore!";
    }

}
