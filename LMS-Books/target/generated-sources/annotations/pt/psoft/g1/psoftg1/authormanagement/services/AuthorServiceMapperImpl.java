package pt.psoft.g1.psoftg1.authormanagement.services;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.dto.request.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T19:40:59+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class AuthorServiceMapperImpl extends AuthorServiceMapper {

    @Override
    public void update(UpdateAuthorRequest request, Author author) {
        if ( request == null ) {
            return;
        }

        author.setName( map( request.getName() ) );
        author.setBio( map( request.getBio() ) );
        author.setPhoto( map( request.getPhoto() ) );
    }
}
