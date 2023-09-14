package ch.hftm.blog.entity;

import java.util.List;


import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {

    @Id @GeneratedValue
    private Long id;
    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    @NotBlank(message = "Vorname darf nicht leer sein")
    private String vorname;

    @NotBlank(message = "AccountName darf nicht leer sein")
    private String accountName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId")
    public List<Entry> entrys = new ArrayList<>();

    public Author(String name, String vorname, String accountName) {
        this.name = name;
        this.vorname = vorname;
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + ", vorname=" + vorname + ", accountName=" + accountName
                + ", entrys=" + entrys + "]";
    }


}
