package ch.hftm.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {
    
    private Long id;
    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    @NotBlank(message = "Vorname darf nicht leer sein")
    private String vorname;

    @NotBlank(message = "AccountName darf nicht leer sein")
    private String accountName;
    // private List<EntryDto> entrys;

}


