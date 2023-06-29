package ch.hftm.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;


public class CommentNotUpdatedExceptionMapper implements ExceptionMapper<CommentNotUpdatedException> {

    @Override
    public Response toResponse(CommentNotUpdatedException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}