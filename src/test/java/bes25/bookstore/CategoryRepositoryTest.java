package bes25.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import bes25.bookstore.domain.Category;
import bes25.bookstore.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // for real DB later
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateSearchDeleteCategory() {
        // luo kategoria
        Category category = new Category("TestCategory2");
        categoryRepository.save(category);
        assertThat(category.getId()).isNotNull();

        // hae kategoria
        List<Category> foundCategories = categoryRepository.findByName("TestCategory2");
        assertThat(foundCategories).hasSize(1);
        assertThat(foundCategories.get(0).getName()).isEqualTo("TestCategory2");

        // poista kategoria
        categoryRepository.delete(category);
        foundCategories = categoryRepository.findByName("TestCategory2");
        assertThat(foundCategories).isEmpty();
    }
    
}
