package ch.hftm.repository;

import jakarta.enterprise.context.ApplicationScoped;
import ch.hftm.entity.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment>{
    
}
