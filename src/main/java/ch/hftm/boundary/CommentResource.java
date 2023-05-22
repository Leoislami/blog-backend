package ch.hftm.boundary;

import ch.hftm.entity.Comment;
import ch.hftm.control.CommentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    @Inject
    CommentService commentService;

    @GET
    public Response getComments() {
        List<Comment> comments = commentService.getComments();
        return Response.ok(comments).build();
    }

    @GET
    @Path("/{id}")
    public Response getComment(@PathParam("id") Long id) {
        Comment comment = commentService.getComment(id);
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(comment).build();
    }

    @POST
    public Response addComment(Comment comment) {
        commentService.addComment(comment);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateComment(@PathParam("id") Long id, Comment comment) {
        Comment existingComment = commentService.getComment(id);
        if (existingComment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        commentService.updateComment(id, comment);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") Long id) {
        Comment existingComment = commentService.getComment(id);
        if (existingComment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        commentService.deleteComment(id);
        return Response.ok().build();
    }
}
