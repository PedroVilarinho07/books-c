package pt.psoft.g1.psoftg1.lendingmanagement.api;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.lendingmanagement.dto.response.LendingResponseDTO;
import pt.psoft.g1.psoftg1.lendingmanagement.dto.response.LendingsAverageDurationResponseDTO;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T21:35:25+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class LendingViewMapperImpl extends LendingViewMapper {

    @Override
    public LendingResponseDTO toLendingView(Lending lending) {
        if ( lending == null ) {
            return null;
        }

        LendingResponseDTO lendingResponseDTO = new LendingResponseDTO();

        lendingResponseDTO.setLendingNumber( map( lending.getLendingNumber() ) );
        lendingResponseDTO.setIsbn( map( lending.getIsbn() ) );
        lendingResponseDTO.setReturnedDate( lending.getReturnedDate() );
        lendingResponseDTO.setStartDate( lending.getStartDate() );
        lendingResponseDTO.setLimitDate( lending.getLimitDate() );
        lendingResponseDTO.setDaysUntilReturn( mapOpt( lending.getDaysUntilReturn() ) );
        lendingResponseDTO.setDaysOverdue( mapOpt( lending.getDaysOverdue() ) );

        lendingResponseDTO.setFineValueInCents( lending.getFineValueInCents().orElse(null) );

        return lendingResponseDTO;
    }

    @Override
    public List<LendingResponseDTO> toLendingView(List<Lending> lendings) {
        if ( lendings == null ) {
            return null;
        }

        List<LendingResponseDTO> list = new ArrayList<LendingResponseDTO>( lendings.size() );
        for ( Lending lending : lendings ) {
            list.add( toLendingView( lending ) );
        }

        return list;
    }

    @Override
    public LendingsAverageDurationResponseDTO toLendingsAverageDurationView(Double lendingsAverageDuration) {
        if ( lendingsAverageDuration == null ) {
            return null;
        }

        LendingsAverageDurationResponseDTO lendingsAverageDurationResponseDTO = new LendingsAverageDurationResponseDTO();

        lendingsAverageDurationResponseDTO.setLendingsAverageDuration( lendingsAverageDuration );

        return lendingsAverageDurationResponseDTO;
    }
}
