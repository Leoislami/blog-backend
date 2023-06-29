package ch.hftm.blog.repository;

import jakarta.enterprise.context.ApplicationScoped;
import ch.hftm.blog.entity.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment>{
    
}
