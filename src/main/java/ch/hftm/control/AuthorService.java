package ch.hftm.control;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

import ch.hftm.entity.Author;
import ch.hftm.repository.AuthorRepository;

import java.util.List;

@Dependent
public class AuthorService {

    @Inject
    AuthorRepository authorRepository;

    @Inject
    Logger logger;

    public List<Author> getAuthors() {
        var authors = authorRepository.listAll();
        logger.info("returning " + authors.size() + " Authors");
        return authors;
    }

    @Transactional
    public void addAuthor(Author author) {
        logger.info("Adding Author " + author.getAccountName());
        authorRepository.persist(author);
    }

    public Author getAuthor(Long id) {
        Author author = authorRepository.findById(id);
        if (author == null) {
            logger.error("No author found with id " + id);
            return null;
        }
        return author;
    }

    @Transactional
    public void updateAuthor(Long id, Author author) {
        Author authorToUpdate = getAuthor(id);
        if (authorToUpdate != null) {
            authorToUpdate.setName(author.getName());
            authorToUpdate.setVorname(author.getVorname());
            authorToUpdate.setAccountName(author.getAccountName());
            authorToUpdate.setEntrys(author.getEntrys());
            logger.info("Author with id " + id + " has been updated.");
        }
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = getAuthor(id);
        if (author != null) {
            authorRepository.delete(author);
            logger.info("Author with id " + id + " has been deleted.");
        }
    }
}
