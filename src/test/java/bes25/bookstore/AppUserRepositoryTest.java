package bes25.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import bes25.bookstore.domain.AppUser;
import bes25.bookstore.domain.AppUserRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void testCreateSearchDeleteUser() {
        // luo käyttäjä
        AppUser user = new AppUser("test", "user", "testuser", "$2a$10$hashexample",  "USER");
        appUserRepository.save(user);
        assertThat(user.getId()).isNotNull();

        // hae käyttäjä
        AppUser foundUser = appUserRepository.findByUsername("testuser");
        assertThat(foundUser).isNotNull();

        // poista käyttäjä
        appUserRepository.delete(user);
        foundUser = appUserRepository.findByUsername("testuser");
        assertThat(foundUser).isNull();
    }
    
}
