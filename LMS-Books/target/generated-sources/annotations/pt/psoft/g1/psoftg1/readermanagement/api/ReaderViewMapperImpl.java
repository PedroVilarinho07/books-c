package pt.psoft.g1.psoftg1.readermanagement.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.readermanagement.dto.request.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.dto.response.ReaderCountResponseDTO;
import pt.psoft.g1.psoftg1.readermanagement.dto.response.ReaderResponseDTO;
import pt.psoft.g1.psoftg1.readermanagement.model.BirthDate;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T21:35:25+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ReaderViewMapperImpl extends ReaderViewMapper {

    @Override
    public ReaderResponseDTO toReaderView(ReaderDetails readerDetails) {
        if ( readerDetails == null ) {
            return null;
        }

        ReaderResponseDTO readerResponseDTO = new ReaderResponseDTO();

        readerResponseDTO.setFullName( map( readerDetailsReaderNameName( readerDetails ) ) );
        readerResponseDTO.setEmail( map( readerDetailsReaderUsername( readerDetails ) ) );
        readerResponseDTO.setBirthDate( map( readerDetailsBirthDateBirthDate( readerDetails ) ) );
        readerResponseDTO.setPhoneNumber( map( readerDetails.getPhoneNumber() ) );
        readerResponseDTO.setGdprConsent( readerDetails.isGdprConsent() );
        readerResponseDTO.setReaderNumber( map( readerDetails.getReaderNumber() ) );
        readerResponseDTO.setMarketingConsent( readerDetails.isMarketingConsent() );
        readerResponseDTO.setThirdPartySharingConsent( readerDetails.isThirdPartySharingConsent() );

        readerResponseDTO.setPhoto( generatePhotoUrl(readerDetails) );
        readerResponseDTO.setInterestList( mapInterestList(readerDetails.getInterestList()) );

        return readerResponseDTO;
    }

    @Override
    public List<ReaderResponseDTO> toReaderView(Iterable<ReaderDetails> readerList) {
        if ( readerList == null ) {
            return null;
        }

        List<ReaderResponseDTO> list = new ArrayList<ReaderResponseDTO>();
        for ( ReaderDetails readerDetails : readerList ) {
            list.add( readerDetailsToReaderResponseDTO( readerDetails ) );
        }

        return list;
    }

    @Override
    public List<ReaderCountResponseDTO> toReaderCountViewList(List<ReaderBookCountDTO> readerBookCountDTOList) {
        if ( readerBookCountDTOList == null ) {
            return null;
        }

        List<ReaderCountResponseDTO> list = new ArrayList<ReaderCountResponseDTO>( readerBookCountDTOList.size() );
        for ( ReaderBookCountDTO readerBookCountDTO : readerBookCountDTOList ) {
            list.add( readerBookCountDTOToReaderCountResponseDTO( readerBookCountDTO ) );
        }

        return list;
    }

    private String readerDetailsReaderNameName(ReaderDetails readerDetails) {
        if ( readerDetails == null ) {
            return null;
        }
        Reader reader = readerDetails.getReader();
        if ( reader == null ) {
            return null;
        }
        Name name = reader.getName();
        if ( name == null ) {
            return null;
        }
        String name1 = name.getName();
        if ( name1 == null ) {
            return null;
        }
        return name1;
    }

    private String readerDetailsReaderUsername(ReaderDetails readerDetails) {
        if ( readerDetails == null ) {
            return null;
        }
        Reader reader = readerDetails.getReader();
        if ( reader == null ) {
            return null;
        }
        String username = reader.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private LocalDate readerDetailsBirthDateBirthDate(ReaderDetails readerDetails) {
        if ( readerDetails == null ) {
            return null;
        }
        BirthDate birthDate = readerDetails.getBirthDate();
        if ( birthDate == null ) {
            return null;
        }
        LocalDate birthDate1 = birthDate.getBirthDate();
        if ( birthDate1 == null ) {
            return null;
        }
        return birthDate1;
    }

    protected ReaderResponseDTO readerDetailsToReaderResponseDTO(ReaderDetails readerDetails) {
        if ( readerDetails == null ) {
            return null;
        }

        ReaderResponseDTO readerResponseDTO = new ReaderResponseDTO();

        readerResponseDTO.setReaderNumber( map( readerDetails.getReaderNumber() ) );
        readerResponseDTO.setBirthDate( map( readerDetails.getBirthDate() ) );
        readerResponseDTO.setPhoneNumber( map( readerDetails.getPhoneNumber() ) );
        readerResponseDTO.setPhoto( map( readerDetails.getPhoto() ) );
        readerResponseDTO.setGdprConsent( readerDetails.isGdprConsent() );
        readerResponseDTO.setMarketingConsent( readerDetails.isMarketingConsent() );
        readerResponseDTO.setThirdPartySharingConsent( readerDetails.isThirdPartySharingConsent() );
        readerResponseDTO.setInterestList( mapInterestList( readerDetails.getInterestList() ) );

        return readerResponseDTO;
    }

    protected ReaderCountResponseDTO readerBookCountDTOToReaderCountResponseDTO(ReaderBookCountDTO readerBookCountDTO) {
        if ( readerBookCountDTO == null ) {
            return null;
        }

        ReaderCountResponseDTO readerCountResponseDTO = new ReaderCountResponseDTO();

        readerCountResponseDTO.setLendingCount( readerBookCountDTO.getLendingCount() );

        return readerCountResponseDTO;
    }
}
