package ch.hftm.blog.dtos;

import java.util.List;

import org.mapstruct.Mapper;

import ch.hftm.blog.entity.Author;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.Entry;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface DtoMapper {
    
    // Entry Methoden
    List<EntryDto> toAllEntryDto(List<Entry> entry);

    EntryDto toOneEntryDto(Entry entry);

    Entry toOneEntry(EntryDto entryDto);

    Entry toOneEntryPost(EntryDtoPost entryDto);

    // Author Methoden
    List<AuthorDto> toAllAuthorDto(List<Author> authors);

    AuthorDto toOneAuthorDto(Author author);

    Author toOneAuthor(AuthorDto authorDto);

    Author toOneAuthorPost(AuthorDtoPost authorDto);

    // Comment Methoden
    List<CommentDto> toAllCommentDto(List<Comment> comments);

    CommentDto toOneCommentDto(Comment comment);

    Comment toOneComment(CommentDto commentDto);

    Comment toOneCommentPost(CommentDtoPost commentDto);

}
