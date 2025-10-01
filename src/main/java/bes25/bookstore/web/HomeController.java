package bes25.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("message", "Welcome to the Bookstore!");
        model.addAttribute("description", "Here you can browse and manage books in various categories.");
        return "home";
    }
}