package ch.hftm.blog.repository;

import jakarta.enterprise.context.ApplicationScoped;
import ch.hftm.blog.entity.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {
}
