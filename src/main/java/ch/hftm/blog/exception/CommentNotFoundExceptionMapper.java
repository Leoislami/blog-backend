package ch.hftm.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class CommentNotFoundExceptionMapper implements ExceptionMapper<CommentNotFoundException> {

    @Override
    public Response toResponse(CommentNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}