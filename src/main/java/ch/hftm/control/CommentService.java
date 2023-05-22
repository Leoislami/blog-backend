package ch.hftm.control;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import ch.hftm.entity.Comment;
import ch.hftm.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Dependent
public class CommentService {

    @Inject
    CommentRepository commentRepository;

    @Inject
    Logger logger;

    public List<Comment> getComments() {
        var comments = commentRepository.listAll();
        logger.info("Returning " + comments.size() + " Comments");
        return comments;
    }

    @Transactional
    public void addComment(Comment comment) {
        logger.info("Adding Comment " + comment.getContent());
        commentRepository.persist(comment);
    }

    public Comment getComment(Long id) {
        Optional<Comment> comment = commentRepository.findByIdOptional(id);
        return comment.orElse(null);
    }

    @Transactional
    public void updateComment(Long id, Comment commentDetails) {
        Optional<Comment> optionalComment = commentRepository.findByIdOptional(id);
        if(optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentDetails.getContent());
            commentRepository.persist(comment);
            logger.info("Updated Comment with id " + id);
        } else {
            logger.info("Comment with id " + id + " not found");
        }
    }

    @Transactional
    public void deleteComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findByIdOptional(id);
        if(optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            commentRepository.delete(comment);
            logger.info("Deleted Comment with id " + id);
        } else {
            logger.info("Comment with id " + id + " not found");
        }
    }
}
