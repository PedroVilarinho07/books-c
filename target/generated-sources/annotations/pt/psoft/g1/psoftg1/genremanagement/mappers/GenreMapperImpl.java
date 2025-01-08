package pt.psoft.g1.psoftg1.genremanagement.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsPerMonthDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreBookCountResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreLendingsAvgPerMonthResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreLendingsCountPerMonthResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreLendingsResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T21:32:02+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class GenreMapperImpl extends GenreMapper {

    @Override
    public GenreDTO toGenreView(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setGenre( map( genre.getGenre() ) );

        return genreDTO;
    }

    @Override
    public GenreDTO mapStringToGenreView(String genre) {
        if ( genre == null ) {
            return null;
        }

        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setGenre( map( genre ) );

        return genreDTO;
    }

    @Override
    public GenreBookCountResponseDTO toGenreBookCountResponseDTO(GenreBookCountDTO genreBookCountView) {
        if ( genreBookCountView == null ) {
            return null;
        }

        GenreBookCountResponseDTO genreBookCountResponseDTO = new GenreBookCountResponseDTO();

        genreBookCountResponseDTO.setGenreDTO( mapStringToGenreView( genreBookCountView.getGenre() ) );
        genreBookCountResponseDTO.setBookCount( genreBookCountView.getBookCount() );

        return genreBookCountResponseDTO;
    }

    @Override
    public List<GenreBookCountResponseDTO> toGenreBookCountResponseDTO(List<GenreBookCountDTO> genreBookCountView) {
        if ( genreBookCountView == null ) {
            return null;
        }

        List<GenreBookCountResponseDTO> list = new ArrayList<GenreBookCountResponseDTO>( genreBookCountView.size() );
        for ( GenreBookCountDTO genreBookCountDTO : genreBookCountView ) {
            list.add( toGenreBookCountResponseDTO( genreBookCountDTO ) );
        }

        return list;
    }

    @Override
    public GenreLendingsResponseDTO toGenreAvgLendingsResponseDTO(GenreLendingsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GenreLendingsResponseDTO genreLendingsResponseDTO = new GenreLendingsResponseDTO();

        genreLendingsResponseDTO.setGenre( map( dto.getGenre() ) );
        genreLendingsResponseDTO.setValue( map( dto.getValue() ) );

        return genreLendingsResponseDTO;
    }

    @Override
    public List<GenreLendingsResponseDTO> toGenreAvgLendingsResponseDTO(List<GenreLendingsDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<GenreLendingsResponseDTO> list = new ArrayList<GenreLendingsResponseDTO>( dtos.size() );
        for ( GenreLendingsDTO genreLendingsDTO : dtos ) {
            list.add( toGenreAvgLendingsResponseDTO( genreLendingsDTO ) );
        }

        return list;
    }

    @Override
    public GenreLendingsCountPerMonthResponseDTO toGenreLendingsCountPerMonthView(GenreLendingsPerMonthDTO dto) {
        if ( dto == null ) {
            return null;
        }

        List<GenreLendingsResponseDTO> lendingsCount = null;
        Integer year = null;
        Integer month = null;

        lendingsCount = toGenreAvgLendingsResponseDTO( dto.getValues() );
        year = dto.getYear();
        month = dto.getMonth();

        GenreLendingsCountPerMonthResponseDTO genreLendingsCountPerMonthResponseDTO = new GenreLendingsCountPerMonthResponseDTO( year, month, lendingsCount );

        return genreLendingsCountPerMonthResponseDTO;
    }

    @Override
    public List<GenreLendingsCountPerMonthResponseDTO> toGenreLendingsCountPerMonthView(List<GenreLendingsPerMonthDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<GenreLendingsCountPerMonthResponseDTO> list = new ArrayList<GenreLendingsCountPerMonthResponseDTO>( dtos.size() );
        for ( GenreLendingsPerMonthDTO genreLendingsPerMonthDTO : dtos ) {
            list.add( toGenreLendingsCountPerMonthView( genreLendingsPerMonthDTO ) );
        }

        return list;
    }

    @Override
    public GenreLendingsAvgPerMonthResponseDTO toGenreLendingsAveragePerMonthResponseDTO(GenreLendingsPerMonthDTO dto) {
        if ( dto == null ) {
            return null;
        }

        List<GenreLendingsResponseDTO> durationAverages = null;
        Integer year = null;
        Integer month = null;

        durationAverages = toGenreAvgLendingsResponseDTO( dto.getValues() );
        year = dto.getYear();
        month = dto.getMonth();

        GenreLendingsAvgPerMonthResponseDTO genreLendingsAvgPerMonthResponseDTO = new GenreLendingsAvgPerMonthResponseDTO( year, month, durationAverages );

        return genreLendingsAvgPerMonthResponseDTO;
    }

    @Override
    public List<GenreLendingsAvgPerMonthResponseDTO> toGenreLendingsAveragePerMonthResponseDTO(List<GenreLendingsPerMonthDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<GenreLendingsAvgPerMonthResponseDTO> list = new ArrayList<GenreLendingsAvgPerMonthResponseDTO>( dtos.size() );
        for ( GenreLendingsPerMonthDTO genreLendingsPerMonthDTO : dtos ) {
            list.add( toGenreLendingsAveragePerMonthResponseDTO( genreLendingsPerMonthDTO ) );
        }

        return list;
    }
}
