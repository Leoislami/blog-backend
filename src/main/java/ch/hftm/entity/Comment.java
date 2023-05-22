package ch.hftm.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String content;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entryId")
    private Entry entry;

    public Comment(String content) {
        this.content = content;
    }
}
