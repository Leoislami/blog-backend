package ch.hftm.blog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;
    
    @NotBlank(message = "Content is required")
    @Size(min = 1, max = 255, message = "Content must be between 1 and 255 characters")
    private String content;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entryId")
    private Entry entry;

    public Comment(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", content=" + content + ", entry=" + entry + "]";
    }

}
