package ch.hftm.blog.boundary;

import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.hftm.blog.control.EntryService;
import ch.hftm.blog.dtos.DtoMapper;
import ch.hftm.blog.dtos.EntryDto;
import ch.hftm.blog.dtos.EntryDtoPost;
import ch.hftm.blog.entity.Entry;


@ApplicationScoped
@Path("/entries")
@Tag(name = "Entry Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntryResource {

    @Inject
    EntryService entryService;

    @Inject
    Logger logger;

    @Inject
    DtoMapper mapper;


    @Operation(description = "Get specific entry", summary = "Get specific entry with /id")
    @APIResponses(value = { @APIResponse(responseCode = "204", description = "Successful") })
    @GET
    public Response getAllEntries(@QueryParam("search") String search, @QueryParam("page") int page) {
        List<Entry> entries = entryService.getEntrys();
        List<EntryDto> entryDtos = mapper.toAllEntryDto(entries);
        logger.info("Getting all entries");
        logger.debug("Get All entries with searchString: " + search + "and pagination: " + page);
        if (entries.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No entries found.").build();
        } else if (search == null || search.isBlank()) {
            return Response.status(Response.Status.OK).entity(entryDtos).build();
        } else {
            return Response.status(Response.Status.OK).entity(entryService.findEntries(search, page)).build();
        }
    }


  
    @Operation(description = "Get specific entry", summary = "Get specific entry with /id")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Successful"),
        @APIResponse(responseCode = "400", description = "Unsuccessful") })
    @GET
    @Path("/{id}")
    public Response getEntry(@PathParam("id") Long id) {
        Entry entry = entryService.getEntry(id);
        EntryDto entryDto = mapper.toOneEntryDto(entry);
        if (entry == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entry with ID " + id + " not found.").build();
        }
        return Response.status(Response.Status.OK).entity(entryDto).build();
    }


    @Operation(description = "Add Entry", summary = "Add a new entry record")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Successful Created"),
            @APIResponse(responseCode = "400", description = "Unsuccessful") })
    @POST
    public Response addEntry(@Valid EntryDtoPost entryDto) {
        logger.info("Try to add entry with title: " + entryDto.getTitle());
        Entry entry = mapper.toOneEntryPost(entryDto);
        logger.debug("The new entry received the id: " + entry.getId());
        try {
            entryService.addEntry(entry);
            return Response.status(Response.Status.CREATED).entity("Entry created successfully.").build();
        } catch (Exception e) {
            logger.error("Failed to create entry.", e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }



    @Operation(description = "Put a Entry", summary = "Add or edit one specific entry")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Successful"),
    @APIResponse(responseCode = "400", description = "Unsuccessful") })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEntry(@PathParam("id") Long id, @Valid EntryDtoPost entryDto) {
        Entry entry = mapper.toOneEntryPost(entryDto);
        try {
            entryService.updateEntry(id, entry);
            return Response.status(Response.Status.OK).entity("Entry updated successfully.").build();
    } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entry with ID " + id + " not found. Failed to update entry.").build();
    }
}


//     @Operation(description = "Patch a Entry", summary = "Update specific fields of an entry")
//     @APIResponses(value = { 
//     @APIResponse(responseCode = "200", description = "Successful"),
//     @APIResponse(responseCode = "400", description = "Unsuccessful") 
// })
//     @PATCH
//     @Path("/{id}")
//     public Response patchEntry(@PathParam("id") Long id, EntryDtoPost entryDto) {
//     Entry existingEntry = entryService.getEntry(id);
//         if (existingEntry == null) {
//             return Response.status(Response.Status.NOT_FOUND).entity("Entry with ID " + id + " not found. Failed to update entry.").build();
//     }
    
//         if (entryDto.getTitle() != null) {
//             if (entryDto.getTitle().length() < 3 || entryDto.getTitle().length() > 50) {
//                 return Response.status(Response.Status.BAD_REQUEST).entity("Title must be between 3 and 50 characters").build();
//             } else {
//                 existingEntry.setTitle(entryDto.getTitle());
//         }
//     }

//         if (entryDto.getContent() != null) {
//             if (entryDto.getContent().isBlank()) {
//                 return Response.status(Response.Status.BAD_REQUEST).entity("Content cannot be blank").build();
//             } else {
//                 existingEntry.setContent(entryDto.getContent());
//         }
//     }
    
//         if (entryDto.getLikes() != 0) {
//             existingEntry.setLikes(entryDto.getLikes());
//         }


//         try {
//             entryService.updateEntry(id, existingEntry);
//                 return Response.status(Response.Status.OK).entity("Entry updated successfully.").build();
//             } catch (Exception e) {
//                 return Response.status(Response.Status.NOT_FOUND).entity("Failed to update entry.").build();
//     }
// }




    @Operation(description = "Delete a entry", summary = "Delete a specific entry")
    @APIResponse(responseCode = "200", description = "Successful")
    @APIResponse(responseCode = "500", description = "Unsuccessful")
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
