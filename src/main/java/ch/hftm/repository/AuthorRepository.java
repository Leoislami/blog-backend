package ch.hftm.repository;

import jakarta.enterprise.context.ApplicationScoped;
import ch.hftm.entity.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {
}
