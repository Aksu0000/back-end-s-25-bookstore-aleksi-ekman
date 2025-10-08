package bes25.bookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import bes25.bookstore.domain.Book;
import bes25.bookstore.domain.BookRepository;
import bes25.bookstore.domain.Category;
import bes25.bookstore.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Book testBook;       // Muuttuja testikirjalle
    private Category testCategory; // Muuttuja testikategorialle


    // Tehdään testidata enne jokaista testauskertaa
    @BeforeEach
    public void setupTestData() {
        bookRepository.deleteAll();     // Tyhjennetään aiempi data varmuuden vuoksi
        categoryRepository.deleteAll();

        // Luodaan testikategoria
        testCategory = new Category();
        testCategory.setName("TestCategory");
        categoryRepository.save(testCategory);      // Tallennetaan H2-tietokantaan

        // Luodaan testikirja
        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setPublicationYear(2025);
        testBook.setIsbn("1234567890013");
        testBook.setPrice(10.0);
        testBook.setCategory(testCategory);    // Liitetään kategoria
        bookRepository.save(testBook);        // Tallennetaan H2-tietokantaan
    }

    // BookRestController testit
    @Test
    public void testGetAllBooks() throws Exception {     // Simuloidaan GET-pyyntö /api/books endpointtiin
        this.mockMvc.perform(get("/api/books"))        // Tarkistetaan, että vastaus sisältää juuri luodun testikirjan
            .andExpect(status().isOk())              // Odotetaan HTTP 200 OK
            .andExpect(content().string(containsString("Test Book"))); // Sisältö tarkistetaan
    }

    @Test
    @WithMockUser(username="admin", authorities={"ADMIN"})
    public void testAdminCanDeleteBook() throws Exception {     // Simuloidaan DELETE-pyyntö /api/books/{id} ADMIN-roolilla
        this.mockMvc.perform(delete("/api/books/{id}", testBook.getId()))       // ADMINin pitäisi pystyä poistamaan kirja
            .andExpect(status().isNoContent());        // 204 No Content
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    public void testUserCannotDeleteBook() throws Exception {       // Simuloidaan DELETE-pyyntö /api/books/{id} tavallisella USER-roolilla
        this.mockMvc.perform(delete("/api/books/{id}", testBook.getId()))   // USERin ei pitäisi saada poisto-oikeutta
            .andExpect(status().isForbidden());     // Odotetaan HTTP 403 Forbidden
    }

    // CategoryRestController testit
    @Test
    public void testGetAllCategories() throws Exception {       // Simuloidaan GET-pyyntö /api/categories endpointtiin
        this.mockMvc.perform(get("/api/categories"))        // Tarkistetaan, että vastaus sisältää testikategorian
            .andExpect(status().isOk())     // Odotetaan HTTP 200 OK
            .andExpect(content().string(containsString("TestCategory")));    // Sisältö tarkistetaan
    }
}
