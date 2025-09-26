package br.ufpb.ecosystem.mappers;

import br.ufpb.ecosystem.dtos.KeywordDTO;
import br.ufpb.ecosystem.entities.Keyword;
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
public class KeywordMapperImpl implements KeywordMapper {

    @Override
    public KeywordDTO toDto(Keyword keyword) {
        if ( keyword == null ) {
            return null;
        }

        KeywordDTO keywordDTO = new KeywordDTO();

        keywordDTO.setId( keyword.getId() );
        keywordDTO.setValue( keyword.getValue() );

        return keywordDTO;
    }

    @Override
    public Keyword toEntity(KeywordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Keyword keyword = new Keyword();

        keyword.setId( dto.getId() );
        keyword.setValue( dto.getValue() );

        return keyword;
    }

    @Override
    public List<KeywordDTO> toDtoList(List<Keyword> keywords) {
        if ( keywords == null ) {
            return null;
        }

        List<KeywordDTO> list = new ArrayList<KeywordDTO>( keywords.size() );
        for ( Keyword keyword : keywords ) {
            list.add( toDto( keyword ) );
        }

        return list;
    }

    @Override
    public List<Keyword> toEntityList(List<KeywordDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Keyword> list = new ArrayList<Keyword>( dtos.size() );
        for ( KeywordDTO keywordDTO : dtos ) {
            list.add( toEntity( keywordDTO ) );
        }

        return list;
    }
}
