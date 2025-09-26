package br.ufpb.ecosystem.mappers;

import br.ufpb.ecosystem.dtos.AuthorDTO;
import br.ufpb.ecosystem.entities.Author;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T14:57:16-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDTO toDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDTO authorDTO = new AuthorDTO();

        authorDTO.setId( author.getId() );
        authorDTO.setName( author.getName() );
        authorDTO.setEmail( author.getEmail() );
        authorDTO.setOrcid( author.getOrcid() );
        authorDTO.setAffiliation( author.getAffiliation() );

        return authorDTO;
    }

    @Override
    public Author toEntity(AuthorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Author author = new Author();

        author.setId( dto.getId() );
        author.setName( dto.getName() );
        author.setEmail( dto.getEmail() );
        author.setOrcid( dto.getOrcid() );
        author.setAffiliation( dto.getAffiliation() );

        return author;
    }

    @Override
    public List<AuthorDTO> toDtoList(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorDTO> list = new ArrayList<AuthorDTO>( authors.size() );
        for ( Author author : authors ) {
            list.add( toDto( author ) );
        }

        return list;
    }

    @Override
    public List<Author> toEntityList(List<AuthorDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Author> list = new ArrayList<Author>( dtos.size() );
        for ( AuthorDTO authorDTO : dtos ) {
            list.add( toEntity( authorDTO ) );
        }

        return list;
    }
}
