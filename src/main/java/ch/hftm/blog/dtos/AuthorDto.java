package ch.hftm.blog.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class AuthorDto {
    
    private Long id;
    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    @NotBlank(message = "Vorname darf nicht leer sein")
    private String vorname;

    @NotBlank(message = "AccountName darf nicht leer sein")
    private String accountName;
    // private List<EntryDto> entrys;

    // public List<EntryDto> getEntrys() {
    //     return entrys;
    // }
    // public void setEntrys(List<EntryDto> entrys) {
    //     this.entrys = entrys;
    // }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
}


