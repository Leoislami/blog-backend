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

@QuarkusTest
public class BlogInteractionsTest {

    @Inject
    AuthorService authorService;
    
    @Inject
    CommentService commentService;
    
    @Inject
    EntryService entryService;

    @Test
    void addingEntryAndComment(){
        // Create Author
        Author author = new Author("Max", "Mustermann", "Testaccount");

        // Create Entry
        Entry entry = new Entry("First Blog", "The content of my first blog");

        // Create Comment
        Comment comment = new Comment("My first comment");

        // Add Author
        authorService.addAuthor(author);

        // Set Author for Entry
        entry.setAuthor(author);
        entryService.addEntry(entry);

        // Set Entry for Comment
        comment.setEntry(entry);
        commentService.addComment(comment);

        // Get Updated Entry and Comment
        List<Entry> entries = entryService.getEntrys();
        Entry addedEntry = entries.get(entries.size() - 1);

        List<Comment> comments = commentService.getComments(0, 200);
        Comment addedComment = comments.get(comments.size() - 1);

        // Assert
        assertEquals(author.getId(), addedEntry.getAuthor().getId());
        assertEquals(entry.getId(), addedComment.getEntry().getId());
    }

        @Test
        void updateEntry(){
        // Create Entry
        Entry entry = new Entry("First Blog", "Initial content");
        entryService.addEntry(entry);

        // Update Entry
        Entry updatedEntry = new Entry("Updated Blog", "Updated content");
        entryService.updateEntry(entry.getId(), updatedEntry);

        // Get Updated Entry
        Entry fetchedEntry = entryService.getEntry(entry.getId());

        // Assert
        assertEquals(updatedEntry.getTitle(), fetchedEntry.getTitle());
        assertEquals(updatedEntry.getContent(), fetchedEntry.getContent());
    }
            
        @Test
        void deleteComment(){
        // Create Comment
        Comment comment = new Comment("Test Comment");
        Long deletedComment = comment.getId();
        commentService.addComment(comment);

        // Delete Comment
        commentService.deleteComment(comment.getId());

        // Assert
        assertNull(deletedComment);
    }

        @Test
        void searchEntries(){
        // Create Entries
        Entry entry1 = new Entry("First Blog", "Content about technology");
        Entry entry2 = new Entry("Second Blog", "Content about sports");
        entryService.addEntry(entry1);
        entryService.addEntry(entry2);

        // Search Entries
        String searchQuery = "technology";
        List<Entry> searchResults = entryService.findEntries(searchQuery, 0);

        // Assert
        assertTrue(searchResults.size() >= 1);
        assertEquals(entry1.getTitle(), searchResults.get(0).getTitle());
        assertTrue(searchResults.get(0).getContent().contains(searchQuery));
    }




}
