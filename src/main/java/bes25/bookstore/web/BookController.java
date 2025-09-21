package bes25.bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;
import bes25.bookstore.domain.CategoryRepository;
import jakarta.validation.Valid;

//import org.springframework.web.bind.annotation.RequestBody;


@Controller

public class BookController {
    
    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository crepository;

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
        model.addAttribute("categories", crepository.findAll());
        return "addbook";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", crepository.findAll());
            return "addbook";
        }
        repository.save(book);
        return "redirect:/booklist";
    }

    @GetMapping(value = "/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }

    @GetMapping(value = "/editBook/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        Book book = repository.findById(bookId).get(); // haetaan kirja reposta
        model.addAttribute("editBook", book); // lisätään malliobjekti "book", jota lomake käyttää
        model.addAttribute("categories", crepository.findAll());
        return "editbookwithvalidation"; // näkymä editbook.html
    }

    @PostMapping("/saveEditedBook")
    public String saveEditedBook(@Valid @ModelAttribute("editBook") Book book,
            BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", crepository.findAll());
            model.addAttribute ("editBook", book);
            return "editbookwithvalidation";
        }
        repository.save(book);
        return "redirect:/booklist";
    }
    

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "Welcome to the Bookstore!";
    }

}
