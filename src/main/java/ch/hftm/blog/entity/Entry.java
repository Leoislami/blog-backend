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
    private String title;
    private String content;
    private int likes;


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



}
