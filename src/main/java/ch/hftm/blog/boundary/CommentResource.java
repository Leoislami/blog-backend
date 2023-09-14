package ch.hftm.blog.boundary;

import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.dtos.CommentDto;
import ch.hftm.blog.dtos.CommentDtoPost;
import ch.hftm.blog.dtos.DtoMapper;
import ch.hftm.blog.entity.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    @Inject
    CommentService commentService;

    @Inject
    Logger logger;

    @Inject
    DtoMapper mapper;

@GET
public Response getComments(@QueryParam("search") String search, @QueryParam("offset") @DefaultValue("0") int offset, @QueryParam("limit") @DefaultValue("10") int limit) {
    logger.info("Getting all comments...");
    List<Comment> comments = commentService.getComments(offset, limit);
    List<CommentDto> commentDtos = mapper.toAllCommentDto(comments);
    if (comments.isEmpty()) {
        logger.warn("No comments found.");
        return Response.status(Response.Status.NOT_FOUND).entity("No comments found.").build();
    } else if (search == null || search.isBlank()) {
        logger.debug("Found " + commentDtos.size() + " comments.");
        return Response.status(Response.Status.OK).entity(commentDtos).build();
    } else {
        logger.debug("Searching for comments with query: " + search);
        return Response.status(Response.Status.OK).entity(commentService.findComments(search, offset, limit)).build();
    }
}


    @GET
    @Path("/{id}")
    public Response getComment(@PathParam("id") Long id) {
        logger.info("Getting comment with ID: " + id);
        Comment comment = commentService.getComment(id);
        if (comment == null) {
            logger.warn("Comment with ID " + id + " not found.");
            return Response.status(Response.Status.NOT_FOUND).entity("Comment with ID " + id + " not found.").build();
        }
        CommentDto commentDto = mapper.toOneCommentDto(comment);
        logger.debug("Found comment with ID: " + id);
        return Response.status(Response.Status.OK).entity(commentDto).build();
    }

    @POST
    public Response addComment(@Valid CommentDtoPost commentDto) {
        logger.info("Try to add comment: " + commentDto.getContent());
        Comment comment = mapper.toOneCommentPost(commentDto);
        try {
            commentService.addComment(comment);
            logger.debug("Comment " + commentDto.getContent() + " successfully created with id: " + comment.getId());
            return Response.status(Response.Status.CREATED).entity("Comment created successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to create comment.", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create comment.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateComment(@PathParam("id") Long id, @Valid CommentDtoPost commentDto) {
        logger.info("Attempting to update comment with ID " + id);
        Comment comment = mapper.toOneCommentPost(commentDto);
        try {
            commentService.updateComment(id, comment);
            logger.debug("Comment with ID " + id + " successfully updated.");
            return Response.status(Response.Status.OK).entity("Comment updated successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to update comment with ID " + id, e);
            return Response.status(Response.Status.NOT_FOUND).entity("Comment with ID " + id + " not found. Failed to update comment.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") Long id) {
        logger.info("Attempting to delete comment with ID " + id);
        try {
            commentService.deleteComment(id);
            logger.debug("Comment with ID " + id + " successfully deleted.");
            return Response.status(Response.Status.NO_CONTENT).entity("Comment deleted successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to delete comment with ID " + id, e);
            return Response.status(Response.Status.NOT_FOUND).entity("Comment with ID " + id + " not found. Failed to delete comment.").build();
        }
    }
}
