package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

import ch.hftm.control.AuthorService;
import ch.hftm.entity.Author;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class AuthorServiceTest {
    
    @Inject
    AuthorService authorService;

    @Test
    void listingAndAddingAuthors() {
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
}
