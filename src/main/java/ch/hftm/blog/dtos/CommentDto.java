package ch.hftm.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Long id;

    @NotBlank(message = "Content is required")
    @Size(min = 1, max = 255, message = "Content must be between 1 and 255 characters")
    private String content;

    // private Entry entry;

}