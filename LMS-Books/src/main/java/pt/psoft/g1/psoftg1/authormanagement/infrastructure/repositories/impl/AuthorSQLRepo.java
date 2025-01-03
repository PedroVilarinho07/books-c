package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorLendingResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.factories.AuthorFactory;
import pt.psoft.g1.psoftg1.authormanagement.mappers.AuthorSQLMapper;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.dataModel.sql.AuthorDataModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("sql")
@RequiredArgsConstructor
public class AuthorSQLRepo implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final AuthorFactory authorFactory;

    @Override
    public Optional<Author> findByAuthorNumber(String authorNumber) {
        TypedQuery<AuthorDataModel> query = entityManager.createQuery(
                "SELECT a FROM AuthorDataModel a WHERE a.authorNumber = :authorNumber", AuthorDataModel.class);
        query.setParameter("authorNumber", authorNumber);
        if (query.getResultList().stream().findFirst().isPresent()) {
            return Optional.of(AuthorSQLMapper.convertToDomainAuthor(authorFactory, query.getResultList().stream().findFirst().get()));
        }
        return Optional.empty();
    }



    @Override
    public List<Author> searchByNameName(String name) {
        TypedQuery<AuthorDataModel> query = entityManager.createQuery(
                "SELECT a FROM AuthorDataModel a WHERE a.name = :name", AuthorDataModel.class);
        query.setParameter("name", name);

        return query.getResultList().stream()
                .map(dataModel -> AuthorSQLMapper.convertToDomainAuthor(authorFactory, dataModel))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Author save(Author author) {
        AuthorDataModel dataModel = AuthorSQLMapper.convertToDataModelAuthor(author);
        entityManager.persist(dataModel);
        return author;

    }

    @Override
    public Iterable<Author> findAll() {
        TypedQuery<AuthorDataModel> query = entityManager.createQuery("SELECT a FROM AuthorDataModel a", AuthorDataModel.class);

        return query.getResultList().stream()
                .map(dataModel -> AuthorSQLMapper.convertToDomainAuthor(authorFactory, dataModel))
                .collect(Collectors.toList());
    }



    @Override
    public void delete(Author author) {
        AuthorDataModel dataModel = entityManager.find(AuthorDataModel.class, author.getAuthorNumber());
        if (dataModel != null) {
            entityManager.remove(dataModel);
        }
    }




}
