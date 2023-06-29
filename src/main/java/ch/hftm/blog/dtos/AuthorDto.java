package ch.hftm.blog.dtos;

import java.util.List;

public class AuthorDto {
    
    private Long id;
    private String name;
    private String vorname;
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


