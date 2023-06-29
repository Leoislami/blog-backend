package ch.hftm.blog.exception;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(Long id) {
        super("Entry with ID " + id + " not found.");
    }
}
