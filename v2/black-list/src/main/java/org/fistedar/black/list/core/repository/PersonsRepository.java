package org.fistedar.black.list.core.repository;

import org.fistedar.black.list.core.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonsRepository extends JpaRepository<PersonEntity, String> {
    Optional<PersonEntity> findByPersonCode(String personCode);
}
