package bes25.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;
import bes25.bookstore.domain.Category;
import bes25.bookstore.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // for real DB later
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateSearchDeleteBook() {
        // luo kategoria (luodaan kategoria testin sisällä, koska Book-entiteetti tarvitsee viittauksen Category-entiteettiin eli category on pakollinen kenttä kirja-entiteetissä ja testi ei voi olettaa, että tietokannassa on jo olemassa sopiva kategoria.)
        Category category = new Category("TestCategory");
        categoryRepository.save(category);

        // luo kirja
        Book book = new Book("Test Title", "Test Author", 2025, "ISBN123456", 10.0, category);
        bookRepository.save(book);
        assertThat(book.getId()).isNotNull();

        // hae kirja
        List<Book> foundBooks = bookRepository.findByTitle("Test Title");
        assertThat(foundBooks).hasSize(1);
        assertThat(foundBooks.get(0).getAuthor()).isEqualTo("Test Author");

        // poista kirja
        bookRepository.delete(book);
        foundBooks = bookRepository.findByTitle("Test Title");
        assertThat(foundBooks).isEmpty();
    }
}
