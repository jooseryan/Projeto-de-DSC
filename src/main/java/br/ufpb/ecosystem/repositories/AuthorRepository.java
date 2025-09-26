package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByOrcid(String orcid);

    Optional<Author> findByNameIgnoreCaseAndEmailIgnoreCase(String name, String email);

}
