package bes25.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import bes25.bookstore.domain.AppUser;
import bes25.bookstore.domain.AppUserRepository;
import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;
import bes25.bookstore.domain.Category;
import bes25.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	// CommandLineRunner insertoi demo dataa H2:seen tai tietokantaan käynnistettäessä aplikaatio
    @Bean
    public CommandLineRunner demo(BookRepository repository, CategoryRepository crepository, AppUserRepository aurepository) {
        return (args) -> {
			Category fantasy = new Category("Fantasy");
        	Category scifi = new Category("Sci-Fi");
			Category thriller = new Category("Thriller");
			crepository.save(fantasy);
        	crepository.save(scifi);
			crepository.save(thriller);

            repository.save(new Book("Eragon", "Christopher Paolini", 2005, "978-952-459-525-2", 19.90, fantasy));
        	repository.save(new Book("Perillinen", "Christopher Paolini", 2006, "978-952-459-773-7", 21.90, fantasy));
        	repository.save(new Book("Brisingr", "Christopher Paolini", 2008, "978-952-459-924-3", 24.90, fantasy));
        	repository.save(new Book("Perintö", "Christopher Paolini", 2012, "978-952-459-995-3", 27.90, fantasy));

			// Create users: admin/admin user/user
			AppUser user1 = new AppUser("user", "$2a$10$G5mujdmwWh.jil9X.sqV3uBGJJ9Lz/vn7Emyv16KnpykYBjASGzFu", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$FWjcgEeZdWUGgjyJZmxQNurBxhED3WfIdem7MPqxnvbxEJibSYFdu", "ADMIN");
			AppUser user3 = new AppUser("aksu", "$2a$10$tJa62m.vEuDntkh10I50le3FPogit6Xx7CAqT6oh062K2LU3IwZja", "ADMIN");
			aurepository.save(user1);
			aurepository.save(user2);
			aurepository.save(user3);
        };
    }

}
