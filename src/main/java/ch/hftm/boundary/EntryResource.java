package ch.hftm.boundary;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import java.util.List;

import ch.hftm.control.EntryService;
import ch.hftm.entity.Entry;

@Path("/entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntryResource {

    @Inject
    EntryService entryService;

    @GET
    public Response getAllEntries() {
        List<Entry> entries = entryService.getEntrys();
        if (entries.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No entries found.").build();
        }
        return Response.status(Response.Status.OK).entity(entries).build();
    }

    @GET
    @Path("/{id}")
    public Response getEntry(@PathParam("id") Long id) {
        Entry entry = entryService.getEntry(id);
        if (entry == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entry with ID " + id + " not found.").build();
        }
        return Response.status(Response.Status.OK).entity(entry).build();
    }

    @POST
    public Response addEntry(Entry entry) {
        try {
            entryService.addEntry(entry);
            return Response.status(Response.Status.CREATED).entity("Entry created successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create entry.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateEntry(@PathParam("id") Long id, Entry entry) {
        try {
            entryService.updateEntry(id, entry);
            return Response.status(Response.Status.OK).entity("Entry updated successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entry with ID " + id + " not found. Failed to update entry.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEntry(@PathParam("id") Long id) {
        try {
            entryService.deleteEntry(id);
            return Response.status(Response.Status.NO_CONTENT).entity("Entry deleted successfully.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entry with ID " + id + " not found. Failed to delete entry.").build();
        }
    }
}
