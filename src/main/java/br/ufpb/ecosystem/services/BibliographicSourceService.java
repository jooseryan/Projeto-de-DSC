package br.ufpb.ecosystem.services;

import br.ufpb.ecosystem.dtos.AuthorDTO;
import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.dtos.KeywordDTO;
import br.ufpb.ecosystem.entities.*;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum;
import br.ufpb.ecosystem.exception.ResourceNotFoundException;
import br.ufpb.ecosystem.mappers.AuthorMapper;
import br.ufpb.ecosystem.mappers.BibliographicSourceMapper;
import br.ufpb.ecosystem.mappers.KeywordMapper;
import br.ufpb.ecosystem.repositories.*;
import br.ufpb.ecosystem.specification.BibliographicSourceSpecs;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BibliographicSourceService {

    private final AuthorRepository authorRepository;
    private final KeywordRepository keywordRepository;
    private final AuthorMapper authorMapper;
    private final KeywordMapper keywordMapper;
    private final BibliographicSourceRepository repository;
    private final BibliographicSourceMapper mapper;

    public BibliographicSourceService(AuthorRepository authorRepository,
                                      KeywordRepository keywordRepository,
                                      AuthorMapper authorMapper,
                                      KeywordMapper keywordMapper,
                                      BibliographicSourceRepository repository,
                                      BibliographicSourceMapper mapper) {
        this.authorRepository = authorRepository;
        this.keywordRepository = keywordRepository;
        this.authorMapper = authorMapper;
        this.keywordMapper = keywordMapper;
        this.repository = repository;
        this.mapper = mapper;
    }

    // ====================== INSERT ======================
    public BibliographicSourceDTO insert(BibliographicSourceDTO dto) {
        BibliographicSource source = mapper.toEntity(dto);
        source.setAuthors(dto.getAuthors() != null
                ? dto.getAuthors().stream().map(this::findOrCreateAuthor).collect(Collectors.toList())
                : new ArrayList<>());
        source.setKeywords(dto.getKeywords() != null
                ? dto.getKeywords().stream().map(this::findOrCreateKeyword).collect(Collectors.toList())
                : new ArrayList<>());

        return mapper.toDto(repository.save(source));
    }

    // ====================== SAVE ======================
    public BibliographicSourceDTO save(BibliographicSourceDTO dto) {
        return insert(dto); // reutiliza insert para consistência
    }

    // ====================== FINDERS ======================
    public Page<BibliographicSourceDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public BibliographicSourceDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fonte bibliográfica com ID " + id + " não encontrada."));
    }

    // ====================== UPDATE ======================
    public BibliographicSourceDTO update(Long id, BibliographicSourceDTO dto) {
        return applyUpdate(id, dto, false);
    }

    public BibliographicSourceDTO partialUpdate(Long id, BibliographicSourceDTO dto) {
        return applyUpdate(id, dto, true);
    }

    private BibliographicSourceDTO applyUpdate(Long id, BibliographicSourceDTO dto, boolean partial) {
        BibliographicSource entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fonte bibliográfica com ID " + id + " não encontrada."));

        if (!partial || dto.getReviewerCode() != null) entity.setReviewerCode(dto.getReviewerCode());
        if (!partial || dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (!partial || dto.getYear() != 0) entity.setYear(dto.getYear());
        if (!partial || dto.getReference() != null) entity.setReference(dto.getReference());
        if (!partial || dto.getUrl() != null) entity.setUrl(dto.getUrl());
        if (!partial || dto.getType() != null) entity.setType(dto.getType());
        if (!partial || dto.getMedia() != null) entity.setMedia(dto.getMedia());
        if (!partial || dto.getDriveUrl() != null) entity.setDriveUrl(dto.getDriveUrl());
        if (!partial || dto.getImageUrl() != null) entity.setImageUrl(dto.getImageUrl());
        if (!partial || dto.getNotes() != null) entity.setNotes(dto.getNotes());
        if (!partial || dto.getAbstractText() != null) entity.setAbstractText(dto.getAbstractText());

        if (dto.getAuthors() != null) entity.setAuthors(dto.getAuthors().stream().map(this::findOrCreateAuthor).collect(Collectors.toList()));
        if (dto.getKeywords() != null) entity.setKeywords(dto.getKeywords().stream().map(this::findOrCreateKeyword).collect(Collectors.toList()));

        return mapper.toDto(repository.save(entity));
    }

    // ====================== DELETE ======================
    @Transactional
    public void delete(Long id) {
        BibliographicSource source = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bibliographic source not found"));

        // Remove relacionamento
        source.getAuthors().clear();
        source.getKeywords().clear();
        repository.save(source);

        repository.deleteById(id);
    }

    // ====================== SEARCH ======================
    public Page<BibliographicSource> search(String title, String authorName, Integer yearStart, Integer yearEnd,
                                            BibliographicSourceEnum.Type type, BibliographicSourceEnum.Media media,
                                            int page, int size) {
        Specification<BibliographicSource> spec = Specification
                .where(BibliographicSourceSpecs.titleContains(title))
                .and(BibliographicSourceSpecs.authorNameContains(authorName))
                .and(BibliographicSourceSpecs.yearBetween(yearStart, yearEnd))
                .and(BibliographicSourceSpecs.typeEquals(type))
                .and(BibliographicSourceSpecs.mediaEquals(media));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "year"));

        return repository.findAll(spec, pageable);
    }


    // ====================== HELPERS ======================
    private Author findOrCreateAuthor(AuthorDTO dto) {
        if (dto.getOrcid() != null && !dto.getOrcid().isBlank()) {
            return authorRepository.findByOrcid(dto.getOrcid())
                    .orElseGet(() -> authorRepository.save(authorMapper.toEntity(dto)));
        }
        if (dto.getName() != null && dto.getEmail() != null) {
            return authorRepository.findByNameIgnoreCaseAndEmailIgnoreCase(dto.getName(), dto.getEmail())
                    .orElseGet(() -> authorRepository.save(authorMapper.toEntity(dto)));
        }
        throw new IllegalArgumentException("Autor inválido: ORCID ou (nome + email) são obrigatórios.");
    }

    private Keyword findOrCreateKeyword(KeywordDTO dto) {
        return keywordRepository.findByValueIgnoreCase(dto.getValue())
                .orElseGet(() -> keywordRepository.save(keywordMapper.toEntity(dto)));
    }
}
