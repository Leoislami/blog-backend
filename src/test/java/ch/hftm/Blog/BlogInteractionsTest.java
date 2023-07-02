package ch.hftm.Blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import ch.hftm.blog.control.AuthorService;
import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.control.EntryService;
import ch.hftm.blog.entity.Author;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.Entry;
import org.jboss.logging.Logger;

@QuarkusTest
public class BlogInteractionsTest {

@Inject
AuthorService authorService;

@Inject
CommentService commentService;

@Inject
EntryService entryService;

@Inject
Logger logger;

@Test
void addingEntryAndComment(){
    // Create Author
    Author author = new Author("Max", "Mustermann", "Testaccount");
    logger.info("Created author: " + author.getAccountName());

    // Create Entry
    Entry entry = new Entry("First Blog", "The content of my first blog");
    logger.info("Created entry: " + entry.getTitle());

    // Create Comment
    Comment comment = new Comment("My first comment");
    logger.info("Created comment: " + comment.getContent());

    // Add Author
    authorService.addAuthor(author);
    logger.info("Added author: " + author.getAccountName());

    // Set Author for Entry
    entry.setAuthor(author);
    entryService.addEntry(entry);
    logger.info("Added entry: " + entry.getTitle() + " by author: " + author.getAccountName());

    // Set Entry for Comment
    comment.setEntry(entry);
    commentService.addComment(comment);
    logger.info("Added comment: " + comment.getContent() + " to entry: " + entry.getTitle());

    // Get Updated Entry and Comment
    List<Entry> entries = entryService.getEntrys();
    Entry addedEntry = entries.get(entries.size() - 1);
    logger.info("Retrieved added entry: " + addedEntry.getTitle());

    List<Comment> comments = commentService.getComments(0, 200);
    Comment addedComment = comments.get(comments.size() - 1);
    logger.info("Retrieved added comment: " + addedComment.getContent());

    // Assert
    assertEquals(author.getId(), addedEntry.getAuthor().getId());
    logger.info("Author ID of added entry matches the expected value.");

    assertEquals(entry.getId(), addedComment.getEntry().getId());
    logger.info("Entry ID of added comment matches the expected value.");
}

@Test
void updateEntry(){
    // Create Entry
    Entry entry = new Entry("First Blog", "Initial content");
    entryService.addEntry(entry);
    logger.info("Added entry: " + entry.getTitle());

    // Update Entry
    Entry updatedEntry = new Entry("Updated Blog", "Updated content");
    entryService.updateEntry(entry.getId(), updatedEntry);
    logger.info("Updated entry with ID: " + entry.getId());

    // Get Updated Entry
    Entry fetchedEntry = entryService.getEntry(entry.getId());
    logger.info("Fetched entry with ID: " + entry.getId());

    // Assert
    assertEquals(updatedEntry.getTitle(), fetchedEntry.getTitle());
    logger.info("Title of fetched entry matches the updated value.");

    assertEquals(updatedEntry.getContent(), fetchedEntry.getContent());
    logger.info("Content of fetched entry matches the updated value.");
}

@Test
void deleteComment(){
    // Create Comment
    Comment comment = new Comment("Test Comment");
    Long deletedComment = comment.getId();
    commentService.addComment(comment);
    logger.info("Added comment: " + comment.getContent());

    // Delete Comment
    commentService.deleteComment(comment.getId());
    logger.info("Deleted comment with ID: " + comment.getId());

    // Assert
    assertNull(deletedComment);
    logger.info("Comment ID is null, indicating successful deletion.");
}

@Test
void searchEntries(){
    // Create Entries
    Entry entry1 = new Entry("First Blog", "Content about technology");
    Entry entry2 = new Entry("Second Blog", "Content about sports");
    entryService.addEntry(entry1);
    entryService.addEntry(entry2);
    logger.info("Added entries: " + entry1.getTitle() + ", " + entry2.getTitle());

    // Search Entries
    String searchQuery = "technology";
    List<Entry> searchResults = entryService.findEntries(searchQuery, 0);
    logger.info("Performed search for entries with query: " + searchQuery);

    // Assert
    assertTrue(searchResults.size() >= 1);
    logger.info("Search results contain at least one entry.");

    assertEquals(entry1.getTitle(), searchResults.get(0).getTitle());
    logger.info("Title of the first search result matches the expected value.");

    assertTrue(searchResults.get(0).getContent().contains(searchQuery));
    logger.info("Content of the first search result contains the search query.");
}

}
