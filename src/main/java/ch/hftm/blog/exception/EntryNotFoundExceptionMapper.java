package ch.hftm.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntryNotFoundExceptionMapper implements ExceptionMapper<EntryNotFoundException> {

    @Override
    public Response toResponse(EntryNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}
