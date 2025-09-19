package bes25.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	// CommandLineRunner insertoi demo dataa H2:seen tai tietokantaan käynnistettäessä aplikaatio
    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.save(new Book("Eragon", "Christopher Paolini", 2005, "978-952-459-525-2", 19.90));
        	repository.save(new Book("Perillinen", "Christopher Paolini", 2006, "978-952-459-773-7", 21.90));
        	repository.save(new Book("Brisingr", "Christopher Paolini", 2008, "978-952-459-924-3", 24.90));
        	repository.save(new Book("Perintö", "Christopher Paolini", 2012, "978-952-459-995-3", 27.90));
        };
    }

}
