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

@Entity
public class Entry {

    @Id @GeneratedValue
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;
    private int likes;


    // Constructor

    public Entry() {
    }


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


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public int getLikes() {
        return likes;
    }


    public void setLikes(int likes) {
        this.likes = likes;
    }


    public Author getAuthor() {
        return author;
    }


    public void setAuthor(Author author) {
        this.author = author;
    }


    public List<Comment> getComments() {
        return comments;
    }


    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    

}
