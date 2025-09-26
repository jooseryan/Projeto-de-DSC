package br.ufpb.ecosystem.mappers;

import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.entities.BibliographicSource;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        AuthorMapper.class,
        KeywordMapper.class
})
public interface BibliographicSourceMapper {


    BibliographicSourceDTO toDto(BibliographicSource entity);

    BibliographicSource toEntity(BibliographicSourceDTO dto);

    List<BibliographicSourceDTO> toDtoList(List<BibliographicSource> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget BibliographicSource entity, BibliographicSourceDTO dto);
}
