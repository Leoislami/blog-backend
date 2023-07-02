package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.jboss.logging.Logger;
import ch.hftm.blog.control.AuthorService;
import ch.hftm.blog.entity.Author;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class AuthorServiceTest {
    
    @Inject
    AuthorService authorService;
    
    @Inject
    Logger logger;

    @Test
    @Transactional
    void addAuthorTest() {
        // Arrange
        Author author = new Author("Hans","Ueli","Testaccount");
        int authorsBefore;
        List<Author> authors;

        // Act
        authorsBefore = authorService.getAuthors().size();
        logger.infof("Before adding, there are " + authorsBefore + " authors.");
        authorService.addAuthor(author);
        authors = authorService.getAuthors();

        // Assert
        assertEquals(authorsBefore + 1, authors.size());
        assertEquals(author.getId(), authors.get(authors.size()-1).getId());
        logger.infof("After adding, there are" + authors.size() + " authors.");
    }

    @Test
    void getAuthorTest() {
        // Arrange
        Author author = new Author("Max", "Mustermann", "maxm");
        authorService.addAuthor(author);
        logger.infof("Added author with id {}.", author.getId());

        // Act
        Author retrievedAuthor = authorService.getAuthor(author.getId());

        // Assert
        assertEquals(author.getId(), retrievedAuthor.getId());
        assertEquals(author.getName(), retrievedAuthor.getName());
        assertEquals(author.getVorname(), retrievedAuthor.getVorname());
        assertEquals(author.getAccountName(), retrievedAuthor.getAccountName());
        logger.infof("Retrieved author with id." + retrievedAuthor.getId());
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
        logger.infof("Updated author with id. " + updatedAuthor.getId() + " New name is " + updatedAuthor.getName());
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
        logger.infof("Deleted author with id." + beforeAuthordeletedId.getId() + "Before deletion, there were " + authorsBefore + "authors. Now, there are " + authorsAfter.size() + " authors.");
    }
    @Test
    void findAuthorsTest() {
        // Arrange
        Author author1 = new Author("John", "Doe", "johnd");
        Author author2 = new Author("Jane", "Smith", "janes");
        authorService.addAuthor(author1);
        authorService.addAuthor(author2);
        logger.info("Created authors with ids: {}, {}");

        // Act
        List<Author> authors = authorService.findAuthors("John", 0);
        logger.infof("Found " + authors.size() + " authors with the term 'Second'");

        // Assert
        assertTrue(authors.size() >= 1);
        assertEquals("John", authors.get(0).getName());
    }
}
