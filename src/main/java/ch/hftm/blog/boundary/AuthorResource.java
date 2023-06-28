package ch.hftm.blog.boundary;

import ch.hftm.blog.control.AuthorService;
import ch.hftm.blog.entity.Author;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @Inject
    AuthorService authorService;

    @GET
    public Response getAllAuthors() {
        List<Author> authors = authorService.getAuthors();
        return Response.ok(authors).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") Long id) {
        Author author = authorService.getAuthor(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(author).build();
    }

    @POST
    public Response addAuthor(Author author) {
        authorService.addAuthor(author);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") Long id, Author author) {
        Author existingAuthor = authorService.getAuthor(id);
        if (existingAuthor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        authorService.updateAuthor(id, author);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        Author existingAuthor = authorService.getAuthor(id);
        if (existingAuthor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        authorService.deleteAuthor(id);
        return Response.ok().build();
    }
}
