package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import ch.hftm.control.AuthorService;
import ch.hftm.control.CommentService;
import ch.hftm.control.EntryService;
import ch.hftm.entity.Author;
import ch.hftm.entity.Comment;
import ch.hftm.entity.Entry;


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

        // Average
        Author author = new Author("Max", "Mustermann", "Testaccount");
        Comment comment = new Comment("Mein erster Kommentar");
        Entry entry = new Entry("FirsBlog", "Der Inhalt von meinem Ersten Blog");

        // Act
        authorService.addAuthor(author);
        entry.setAuthor(author);
        entryService.addEntry(entry);
        comment.setEntry(entry);
        commentService.addComment(comment);

        // Get Updated Entities
        List<Entry> entries = entryService.getEntrys();
        Entry addEntry = entries.get(entries.size() - 1 );
        List<Comment> comments = commentService.getComments();
        Comment addComment = comments.get(comments.size() - 1 );

        // Assert
        assertEquals(author.getId(), addEntry.getAuthor().getId());
        assertEquals(entry.getId(), addComment.getEntry().getId());

    }
}
