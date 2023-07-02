package ch.hftm.blog.boundary;

import ch.hftm.blog.control.AuthorService;
import ch.hftm.blog.dtos.AuthorDto;
import ch.hftm.blog.dtos.DtoMapper;
import ch.hftm.blog.entity.Author;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
@Path("/authors")
public class AuthorResource {

    @Inject
    AuthorService authorService;

    @Inject
    Logger logger;

    @Inject
    DtoMapper mapper;

    @Operation(description = "Get list of Authors", summary ="Get all or specific author with optional query parameter")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Successful"),
        @APIResponse(responseCode = "400", description = "Unsuccessful")})
    @GET
    public Response getAllAuthors(@QueryParam("search") String search,  @QueryParam("page") int page) {
        logger.info("Getting all authors...");
        List<Author> authors = authorService.getAuthors();
        List<AuthorDto> authorDtos = mapper.toAllAuthorDto(authors);
        if (authors.isEmpty()) {
            logger.warn("No authors found.");
            return Response.status(Response.Status.NOT_FOUND).entity("No authors found.").build();
        } else if (search == null || search.isBlank()) {
            logger.debug("Found " + authorDtos.size() + " authors.");
            return Response.status(Response.Status.OK).entity(authorDtos).build();
        } else {
            logger.debug("Searching for authors with query: " + search);
            return Response.status(Response.Status.OK).entity(authorService.findAuthors(search, page)).build();
        }
    }

    @Operation(description = "Get specific author", summary ="Get specific author with /id")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Successful"),
        @APIResponse(responseCode = "400", description = "Unsuccessful")})
    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") Long id) {
        logger.info("Getting author with ID: " + id);
        Author author = authorService.getAuthor(id);
        if (author == null) {
            logger.warn("Author with ID " + id + " not found.");
            return Response.status(Response.Status.NOT_FOUND).entity("Author with ID " + id + " not found.").build();
        }
        AuthorDto authorDto = mapper.toOneAuthorDto(author);
        logger.debug("Found author with ID: " + id);
        return Response.status(Response.Status.OK).entity(authorDto).build();
    }

    
    @Operation(description = "Add Author", summary ="Add a new author record")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Successful"),
        @APIResponse(responseCode = "400", description = "Unsuccessful")})
    @POST
    public Response addAuthor(@Valid AuthorDto authorDto) {
        logger.info("Try to add author with name: " + authorDto.getName());
        Author author = mapper.toOneAuthor(authorDto);
        logger.debug("The new author received the id: " + author.getId());
            if (authorDto == null || authorDto.getName() == null || authorDto.getName().isBlank()) {
        return Response.status(Response.Status.BAD_REQUEST).entity("Invalid author data.").build();
        }
        try {
            authorService.addAuthor(author);
            logger.debug("Author " + authorDto.getName() + " successfully created.");
            return Response.status(Response.Status.CREATED).entity("Author created successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to create author.", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create author.").build();
        }
    }

    @Operation(description = "Update an author", summary = "Update a specific author's record")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful update"),
        @APIResponse(responseCode = "404", description = "Author not found"),
        @APIResponse(responseCode = "400", description = "Unsuccessful update")
    })
    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") Long id, @Valid AuthorDto authorDto) {
        logger.info("Attempting to update author with ID " + id);
        Author author = mapper.toOneAuthor(authorDto);
        try {
            authorService.updateAuthor(id, author);
            logger.debug("Author with ID " + id + " successfully updated.");
            return Response.status(Response.Status.OK).entity("Author updated successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to update author with ID " + id, e);
            return Response.status(Response.Status.NOT_FOUND).entity("Author with ID " + id + " not found. Failed to update author.").build();
        }
    }

    @Operation(description = "Delete an author", summary ="Delete a specific author")
    @APIResponse(responseCode = "200", description = "Successful")
    @APIResponse(responseCode = "500", description = "Unsuccessful")
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        logger.info("Attempting to delete author with ID " + id);
        try {
            authorService.deleteAuthor(id);
            logger.debug("Author with ID " + id + " successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT).entity("Author deleted successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to delete author with ID " + id, e);
            return Response.status(Response.Status.NOT_FOUND).entity("Author with ID " + id + " not found. Failed to delete author.").build();
        }
    }
}

