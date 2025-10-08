package bes25.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import bes25.bookstore.web.BookController;
import bes25.bookstore.web.HomeController;
import bes25.bookstore.web.LoginController;
import bes25.bookstore.web.BookRestController;
import bes25.bookstore.web.CategoryRestController;

import bes25.bookstore.domain.BookRepository;
import bes25.bookstore.domain.CategoryRepository;
import bes25.bookstore.domain.AppUserRepository;

@SpringBootTest
class BookstoreApplicationTests {

	// Controllerit
	@Autowired
	private BookController bookController;

	@Autowired
	private HomeController homeController;

	@Autowired
	private LoginController loginController;

	@Autowired
	private BookRestController bookRestController;

	@Autowired
	private CategoryRestController categoryRestController;

	// Repositoryt
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

	@Test
	void contextLoads() throws Exception {
		// Controllerit
		assertThat(bookController).isNotNull();
		assertThat(homeController).isNotNull();
		assertThat(loginController).isNotNull();
		assertThat(bookRestController).isNotNull();
		assertThat(categoryRestController).isNotNull();
		
		// Repositoryt
		assertThat(bookRepository).isNotNull();
        assertThat(categoryRepository).isNotNull();
        assertThat(appUserRepository).isNotNull();
	}

}
