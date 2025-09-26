package br.ufpb.ecosystem.mappers;

import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.entities.Author;
import br.ufpb.ecosystem.entities.BibliographicSource;
import br.ufpb.ecosystem.entities.Keyword;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T14:57:16-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class BibliographicSourceMapperImpl implements BibliographicSourceMapper {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private KeywordMapper keywordMapper;

    @Override
    public BibliographicSourceDTO toDto(BibliographicSource entity) {
        if ( entity == null ) {
            return null;
        }

        BibliographicSourceDTO bibliographicSourceDTO = new BibliographicSourceDTO();

        bibliographicSourceDTO.setId( entity.getId() );
        bibliographicSourceDTO.setReviewerCode( entity.getReviewerCode() );
        bibliographicSourceDTO.setTitle( entity.getTitle() );
        bibliographicSourceDTO.setAuthors( authorMapper.toDtoList( entity.getAuthors() ) );
        bibliographicSourceDTO.setYear( entity.getYear() );
        bibliographicSourceDTO.setReference( entity.getReference() );
        bibliographicSourceDTO.setUrl( entity.getUrl() );
        bibliographicSourceDTO.setType( entity.getType() );
        bibliographicSourceDTO.setMedia( entity.getMedia() );
        bibliographicSourceDTO.setDriveUrl( entity.getDriveUrl() );
        bibliographicSourceDTO.setImageUrl( entity.getImageUrl() );
        bibliographicSourceDTO.setNotes( entity.getNotes() );
        bibliographicSourceDTO.setKeywords( keywordMapper.toDtoList( entity.getKeywords() ) );
        bibliographicSourceDTO.setAbstractText( entity.getAbstractText() );

        return bibliographicSourceDTO;
    }

    @Override
    public BibliographicSource toEntity(BibliographicSourceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BibliographicSource bibliographicSource = new BibliographicSource();

        bibliographicSource.setId( dto.getId() );
        bibliographicSource.setReviewerCode( dto.getReviewerCode() );
        bibliographicSource.setTitle( dto.getTitle() );
        bibliographicSource.setAuthors( authorMapper.toEntityList( dto.getAuthors() ) );
        bibliographicSource.setYear( dto.getYear() );
        bibliographicSource.setReference( dto.getReference() );
        bibliographicSource.setUrl( dto.getUrl() );
        bibliographicSource.setType( dto.getType() );
        bibliographicSource.setMedia( dto.getMedia() );
        bibliographicSource.setDriveUrl( dto.getDriveUrl() );
        bibliographicSource.setImageUrl( dto.getImageUrl() );
        bibliographicSource.setNotes( dto.getNotes() );
        bibliographicSource.setKeywords( keywordMapper.toEntityList( dto.getKeywords() ) );
        bibliographicSource.setAbstractText( dto.getAbstractText() );

        return bibliographicSource;
    }

    @Override
    public List<BibliographicSourceDTO> toDtoList(List<BibliographicSource> entities) {
        if ( entities == null ) {
            return null;
        }

        List<BibliographicSourceDTO> list = new ArrayList<BibliographicSourceDTO>( entities.size() );
        for ( BibliographicSource bibliographicSource : entities ) {
            list.add( toDto( bibliographicSource ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(BibliographicSource entity, BibliographicSourceDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getReviewerCode() != null ) {
            entity.setReviewerCode( dto.getReviewerCode() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( entity.getAuthors() != null ) {
            List<Author> list = authorMapper.toEntityList( dto.getAuthors() );
            if ( list != null ) {
                entity.getAuthors().clear();
                entity.getAuthors().addAll( list );
            }
        }
        else {
            List<Author> list = authorMapper.toEntityList( dto.getAuthors() );
            if ( list != null ) {
                entity.setAuthors( list );
            }
        }
        entity.setYear( dto.getYear() );
        if ( dto.getReference() != null ) {
            entity.setReference( dto.getReference() );
        }
        if ( dto.getUrl() != null ) {
            entity.setUrl( dto.getUrl() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
        if ( dto.getMedia() != null ) {
            entity.setMedia( dto.getMedia() );
        }
        if ( dto.getDriveUrl() != null ) {
            entity.setDriveUrl( dto.getDriveUrl() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getNotes() != null ) {
            entity.setNotes( dto.getNotes() );
        }
        if ( entity.getKeywords() != null ) {
            List<Keyword> list1 = keywordMapper.toEntityList( dto.getKeywords() );
            if ( list1 != null ) {
                entity.getKeywords().clear();
                entity.getKeywords().addAll( list1 );
            }
        }
        else {
            List<Keyword> list1 = keywordMapper.toEntityList( dto.getKeywords() );
            if ( list1 != null ) {
                entity.setKeywords( list1 );
            }
        }
        if ( dto.getAbstractText() != null ) {
            entity.setAbstractText( dto.getAbstractText() );
        }
    }
}
