package ch.hftm.blog.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDtoPost {
    
    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    @NotBlank(message = "Vorname darf nicht leer sein")
    private String vorname;

    @NotBlank(message = "AccountName darf nicht leer sein")
    private String accountName;


    
}


