package ch.hftm.blog.exception;

public class CommentNotUpdatedException extends RuntimeException {
    public CommentNotUpdatedException(Long id) {
        super("Comment with id " + id + " not found, failed to update comment");
    }
}