package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.hftm.blog.control.AuthorService;
import ch.hftm.blog.entity.Author;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class AuthorServiceTest {
    
    @Inject
    AuthorService authorService;

    @Test
    @Transactional
    void addAuthorTest() {
        // Arrange
        Author author = new Author("Hans","Ueli","Testaccount");
        int authorsBefore;
        List<Author> authors;

        // Act
        authorsBefore = authorService.getAuthors().size();
        authorService.addAuthor(author);
        authors = authorService.getAuthors();

        // Assert
        assertEquals(authorsBefore + 1, authors.size());
        assertEquals(author.getId(), authors.get(authors.size()-1).getId());
    }

    @Test
    void getAuthorTest() {
        // Arrange
        Author author = new Author("Max", "Mustermann", "maxm");
        authorService.addAuthor(author);

        // Act
        Author retrievedAuthor = authorService.getAuthor(author.getId());

        // Assert
        assertEquals(author.getId(), retrievedAuthor.getId());
        assertEquals(author.getName(), retrievedAuthor.getName());
        assertEquals(author.getVorname(), retrievedAuthor.getVorname());
        assertEquals(author.getAccountName(), retrievedAuthor.getAccountName());
    }

    @Test
    @Transactional
    void updateAuthorTest() {
        // Arrange
        Author author = new Author("John", "Doe", "johnd");
        authorService.addAuthor(author);

        // Act
        author.setName("Jane");
        authorService.updateAuthor(author.getId(), author);
        Author updatedAuthor = authorService.getAuthor(author.getId());

        // Assert
        assertEquals("Jane", updatedAuthor.getName());
    }

    @Test
    @Transactional
    void deleteAuthorTest() {
        // Arrange
        Author author = new Author("Peter", "Parker", "spiderman");
        Long afterAuthordeleteId = author.getId();
        authorService.addAuthor(author);
        int authorsBefore = authorService.getAuthors().size();

        // Act
        Author beforeAuthordeletedId = authorService.getAuthor(author.getId());
        authorService.deleteAuthor(author.getId());
        List<Author> authorsAfter = authorService.getAuthors();

        // Assert
        assertNull(afterAuthordeleteId);
        assertNotNull(beforeAuthordeletedId);
        assertEquals(beforeAuthordeletedId.getId(), author.getId());
        assertEquals(authorsBefore - 1, authorsAfter.size());
    }

    @Test
    void findAuthorsTest() {
        // Arrange
        Author author1 = new Author("John", "Doe", "johnd");
        Author author2 = new Author("Jane", "Smith", "janes");
        authorService.addAuthor(author1);
        authorService.addAuthor(author2);

        // Act
        List<Author> authors = authorService.findAuthors("John", 0);

        // Assert
        assertTrue(authors.size() >= 1);
        assertEquals("John", authors.get(0).getName());
    }
}
