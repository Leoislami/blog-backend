package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.entity.Comment;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class CommentServiceTest {
    
    @Inject
    CommentService commentService;

    @Test
    @Transactional
    void addCommentTest() {
        // Arrange
        Comment comment = new Comment("Test Comment");
        int commentsBefore;
        List<Comment> comments;

        // Act
        commentsBefore = commentService.getComments(0, 200).size();
        commentService.addComment(comment);
        comments = commentService.getComments(0, 200);

        // Assert
        assertEquals(commentsBefore + 1, comments.size());
        assertEquals(comment.getId(), comments.get(comments.size()-1).getId());
    }

    @Test
    void getCommentTest() {
        // Arrange
        Comment comment = new Comment("Example Comment");
        commentService.addComment(comment);

        // Act
        Comment retrievedComment = commentService.getComment(comment.getId());

        // Assert
        assertEquals(comment.getId(), retrievedComment.getId());
        assertEquals(comment.getContent(), retrievedComment.getContent());
    }

    @Test
    @Transactional
    void updateCommentTest() {
        // Arrange
        Comment comment = new Comment("Old Comment");
        commentService.addComment(comment);

        // Act
        comment.setContent("Updated Comment");
        commentService.updateComment(comment.getId(), comment);
        Comment updatedComment = commentService.getComment(comment.getId());

        // Assert
        assertEquals("Updated Comment", updatedComment.getContent());
    }

    @Test
    @Transactional
    void deleteCommentTest() {
        // Arrange
        Comment comment = new Comment("Comment to Delete");
        Long afterCommentdeleteId = comment.getId();
        commentService.addComment(comment);
        int commentsBefore = commentService.getComments(0, 200).size();

        // Act
        Comment beforeCommentDeletId = commentService.getComment(comment.getId());
        commentService.deleteComment(comment.getId());
        List<Comment> commentsAfter = commentService.getComments(0, 200);

        // Assert
        assertNull(afterCommentdeleteId);
        assertNotNull(beforeCommentDeletId);
        assertEquals(beforeCommentDeletId.getId(), comment.getId());
        assertEquals(commentsBefore - 1, commentsAfter.size());
    }

    @Test
    void findCommentsTest() {
        // Arrange
        Comment comment1 = new Comment("First Comment");
        Comment comment2 = new Comment("Second Comment");
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        // Act
        List<Comment> comments = commentService.findComments("Second", 0, 200);

        // Assert
        assertTrue(comments.size() >= 1);
        assertEquals("Second Comment", comments.get(0).getContent());
    }
}
