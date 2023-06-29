package ch.hftm.blog.dtos;

import ch.hftm.blog.entity.Entry;
import io.smallrye.common.constraint.NotNull;

public class CommentDto {

    private Long id;
    private String content;

    // private Entry entry;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    // public Entry getEntry() {
    //     return entry;
    // }
    // public void setEntry(Entry entry) {
    //     this.entry = entry;
    // }


}
