package bes25.bookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    // Testataan, että home-sivu on julkinen
    @Test
    public void testHomePageLoads() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome"))); // Tarkista, että home.html sisältää esim. "Welcome"
    }

    // Testataan, että kirjaamattomat ohjataan home-sivulle, jos yrittävät /booklist-sivulle
    @Test
    public void testBookListRedirectsToLoginWhenNotAuthenticated() throws Exception {
        this.mockMvc.perform(get("/booklist"))
                .andExpect(status().is3xxRedirection());
    }

    // Testataan, että kirjautunut käyttäjä pääsee booklist-sivulle
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAuthenticatedUserCanAccessBookList() throws Exception {
        this.mockMvc.perform(get("/booklist"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Books")));
    }

    // Testataan, että ADMIN voi poistaa kirjan
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testAdminCanDeleteBook() throws Exception {
        this.mockMvc.perform(get("/deleteBook/1"))              // Oletetaan, että kirjan poisto tehdään URL:illa /delete/{id}, esim. /delete/1
                .andExpect(status().is3xxRedirection());    // yleensä poiston jälkeen ohjataan /booklist-sivulle
    }

    // Testataan, että USER-roolilla ei ole lupaa poistaa kirjaa
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUserCannotDeleteBook() throws Exception {
        this.mockMvc.perform(get("/deleteBook/1"))
                .andExpect(status().isForbidden());
    }
    
}
