package br.ufpb.ecosystem.mappers;

import br.ufpb.ecosystem.dtos.KeywordDTO;
import br.ufpb.ecosystem.entities.Keyword;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KeywordMapper {
    KeywordDTO toDto(Keyword keyword);
    Keyword toEntity(KeywordDTO dto);
    List<KeywordDTO> toDtoList(List<Keyword> keywords);
    List<Keyword> toEntityList(List<KeywordDTO> dtos);
}
