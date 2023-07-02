package ch.hftm.blog.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
// @Data
@Entity
public class Entry {

    @Id @GeneratedValue
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;
    private Integer likes;


    // Constructor

    public Entry(String title, String content) {
        this.title = title;
        this.content = content;
    }


    @ManyToOne
    private Author author;

    @OneToMany(fetch = FetchType.EAGER)
    // @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "entryId")
    public List<Comment> comments = new ArrayList<>();


    @Override
    public String toString() {
        return "Entry [id=" + id + ", title=" + title + ", content=" + content + ", likes=" + likes + ", author="
                + author + ", comments=" + comments + "]";
    }


    

}
