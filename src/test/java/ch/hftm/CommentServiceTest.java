package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.jboss.logging.Logger;
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
    
    @Inject
    Logger logger;

    @Test
    @Transactional
    void addCommentTest() {
        // Arrange
        Comment comment = new Comment("Test Comment");
        int commentsBefore;
        List<Comment> comments;
        logger.info("Starting addCommentTest... Comment to be added: " + comment.toString());

        // Act
        commentsBefore = commentService.getComments(0, 200).size();
        commentService.addComment(comment);
        comments = commentService.getComments(0, 200);
        logger.info("Comment added. Current comments: " + comments.get(comments.size() - 1));

        // Assert
        logger.info("Verifying results of addCommentTest...");
        assertEquals(commentsBefore + 1, comments.size());
        assertEquals(comment.getId(), comments.get(comments.size()-1).getId());
        logger.info("Finished addCommentTest.");
    }

    @Test
    void getCommentTest() {
        // Arrange
        Comment comment = new Comment("Example Comment");
        logger.info("Starting getCommentTest... Comment to be added and retrieved: " + comment.toString());
        commentService.addComment(comment);

        // Act
        Comment retrievedComment = commentService.getComment(comment.getId());
        logger.info("Comment retrieved: " + retrievedComment.toString());

        // Assert
        logger.info("Verifying results of getCommentTest...");
        assertEquals(comment.getId(), retrievedComment.getId());
        assertEquals(comment.getContent(), retrievedComment.getContent());
        logger.info("Finished getCommentTest.");
    }


    @Test
    @Transactional
    void updateCommentTest() {
        logger.info("Starting updateCommentTest");

        // Arrange
        Comment comment = new Comment("Old Comment");
        logger.infof("Created comment with id: " + comment.getId());
        commentService.addComment(comment);

        // Act
        comment.setContent("Updated Comment");
        logger.infof("Updated comment content to: " + comment.getContent());
        commentService.updateComment(comment.getId(), comment);
        Comment updatedComment = commentService.getComment(comment.getId());

        // Assert
        assertEquals("Updated Comment", updatedComment.getContent());
        logger.info("updateCommentTest completed successfully");
    }

    @Test
    @Transactional
    void deleteCommentTest() {
        logger.info("Starting deleteCommentTest");

        // Arrange
        Comment comment = new Comment("Comment to Delete");
        Long afterCommentdeleteId = comment.getId();
        logger.infof("Created comment with id: " + comment.getId());
        commentService.addComment(comment);
        int commentsBefore = commentService.getComments(0, 200).size();
        logger.infof("Comments before deletion: " +  commentsBefore);

        // Act
        Comment beforeCommentDeletId = commentService.getComment(comment.getId());
        commentService.deleteComment(comment.getId());
        List<Comment> commentsAfter = commentService.getComments(0, 200);
        logger.infof("Comments after deletion: " + commentsAfter.size());

        // Assert
        assertNull(afterCommentdeleteId);
        assertNotNull(beforeCommentDeletId);
        assertEquals(beforeCommentDeletId.getId(), comment.getId());
        assertEquals(commentsBefore - 1, commentsAfter.size());

        logger.info("deleteCommentTest completed successfully");
    }


    @Test
    void findCommentsTest() {
        logger.info("Starting findCommentsTest");

        // Arrange
        Comment comment1 = new Comment("First Comment");
        Comment comment2 = new Comment("Second Comment");
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        logger.info("Created comments with ids: {}, {}");

        // Act
        List<Comment> comments = commentService.findComments("Second", 0, 200);
        logger.infof("Found {} comments with the term 'Second' " + comments.size());

        // Assert
        assertTrue(comments.size() >= 1);
        assertEquals("Second Comment", comments.get(0).getContent());
        logger.info("findCommentsTest completed successfully");
    }

}
