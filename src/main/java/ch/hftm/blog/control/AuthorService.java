package ch.hftm.blog.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

import ch.hftm.blog.entity.Author;
import ch.hftm.blog.exception.AuthorNotFoundException;
import ch.hftm.blog.repository.AuthorRepository;

import java.util.List;

@ApplicationScoped
public class AuthorService {

    @Inject
    AuthorRepository authorRepository;

    @Inject
    Logger logger;

    public List<Author> getAuthors() {
        var authors = authorRepository.listAll();
        logger.info("Returning " + authors.size() + " authors.");
        return authors;
    }

    @Transactional
    public void addAuthor(Author author) {
        logger.info("Adding author " + author.getAccountName() + ".");
        authorRepository.persist(author);
        logger.info("Author " + author.getAccountName() + " added successfully.");
    }

    public Author getAuthor(Long id) {
        logger.info("Getting author with ID " + id + ".");
        Author author = authorRepository.findById(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        logger.info("Author with ID " + id + " retrieved successfully.");
        return author;
    }


    @Transactional
    public void updateAuthor(Long id, Author author) {
        logger.info("Updating author with ID " + id + ".");
        Author authorToUpdate = getAuthor(id);
        if (authorToUpdate != null) {
            authorToUpdate.setName(author.getName());
            authorToUpdate.setVorname(author.getVorname());
            authorToUpdate.setAccountName(author.getAccountName());
            authorToUpdate.setEntrys(author.getEntrys());
            logger.info("Author with ID " + id + " has been updated successfully.");
        } else {
            logger.warn("No author found with ID " + id + " to update.");
        }
    }

    @Transactional
    public void deleteAuthor(Long id) {
        logger.info("Deleting author with ID " + id + ".");
        Author author = getAuthor(id);
        if (author != null) {
            authorRepository.delete(author);
            logger.info("Author with ID " + id + " has been deleted successfully.");
        } else {
            logger.warn("No author found with ID " + id + " to delete.");
        }
    }

public List<Author> findAuthors(String searchString, int page) {
    logger.info("Searching for authors with query: " + searchString + " on page " + page + ".");
    
    var authorsQuery = authorRepository
        .find("name like ?1 or vorname like ?1", "%" + searchString + "%")
        .page(page, 20); // Setzen der gewünschte Anzahl von Autoren pro Seite

    var authors = authorsQuery.list();
    
    if (authors.isEmpty()) {
        logger.warn("No authors found with query: " + searchString + ".");
    } else {
        logger.info("Found " + authors.size() + " authors with query: " + searchString + ".");
    }
    return authors;
}

}
