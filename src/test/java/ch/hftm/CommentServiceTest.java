package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

import ch.hftm.control.CommentService;
import ch.hftm.entity.Comment;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CommentServiceTest {
    
    @Inject
    CommentService commentService;

    @Test
    void listingAndAddingComments() {
        // Arrange
        Comment comment = new Comment("Test Comment");
        int commentsBefore;
        List<Comment> comments;

        // Act
        commentsBefore = commentService.getComments().size();
        commentService.addComment(comment);
        comments = commentService.getComments();

        // Assert
        assertEquals(commentsBefore + 1, comments.size());
        assertEquals(comment.getId(), comments.get(comments.size()-1).getId());
    }
}
