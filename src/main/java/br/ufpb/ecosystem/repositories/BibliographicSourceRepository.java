package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.entities.BibliographicSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliographicSourceRepository extends JpaRepository<BibliographicSource, Long>, JpaSpecificationExecutor<BibliographicSource> {

}
