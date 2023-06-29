package ch.hftm.blog.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.exception.CommentNotFoundException;
import ch.hftm.blog.exception.CommentNotUpdatedException;
import ch.hftm.blog.repository.CommentRepository;
import io.quarkus.panache.common.Page;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommentService {

    @Inject
    CommentRepository commentRepository;

    @Inject
    Logger logger;

    public List<Comment> getComments(int offset, int limit) {
        logger.info("Fetching comments with offset: " + offset + ", limit: " + limit);
        var comments = commentRepository.findAll().page(Page.of(offset, limit)).list();
        logger.info("Returning " + comments.size() + " comments");
        return comments;
    }

    @Transactional
    public void addComment(Comment comment) {
        logger.info("Adding Comment " + comment.getContent());
        commentRepository.persist(comment);
    }

    public Comment getComment(Long id) {
        return commentRepository.findByIdOptional(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    public void updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findByIdOptional(id)
                .orElseThrow(() -> new CommentNotUpdatedException(id));
        comment.setContent(commentDetails.getContent());
        commentRepository.persist(comment);
        logger.info("Updated Comment with id " + id);
    }

     @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findByIdOptional(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        commentRepository.delete(comment);
        logger.info("Deleted Comment with id " + id);
    }
    

    public List<Comment> findComments(String search, int offset, int limit) {
        logger.info("Searching comments with query: " + search + ", offset: " + offset + ", limit: " + limit);
        var comments = commentRepository.find("content like ?1", "%" + search + "%").page(Page.of(offset, limit)).list();
        logger.info("Found " + comments.size() + " comments");
        return comments;
    }
}
