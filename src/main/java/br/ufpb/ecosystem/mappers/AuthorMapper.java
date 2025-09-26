package br.ufpb.ecosystem.mappers;

import br.ufpb.ecosystem.dtos.AuthorDTO;
import br.ufpb.ecosystem.entities.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDto(Author author);
    Author toEntity(AuthorDTO dto);
    List<AuthorDTO> toDtoList(List<Author> authors);
    List<Author> toEntityList(List<AuthorDTO> dtos);
}

