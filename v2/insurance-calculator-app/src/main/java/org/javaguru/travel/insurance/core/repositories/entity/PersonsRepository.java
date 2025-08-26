package org.javaguru.travel.insurance.core.repositories.entity;

import org.javaguru.travel.insurance.core.domain.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<PersonEntity, Long> {

    @Query(""" 
            select person from PersonEntity person
            where person.firstName = :firstName
            and person.lastName = :lastName
            and person.personCode = :personCode
            """)
    Optional<PersonEntity> findByFirstNameAndLastNameAndPersonCode(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("personCode") String personCode);
}
