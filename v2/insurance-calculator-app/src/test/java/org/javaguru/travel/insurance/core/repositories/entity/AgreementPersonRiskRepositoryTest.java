package org.javaguru.travel.insurance.core.repositories.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AgreementPersonRiskRepositoryTest {

    @Autowired
    private AgreementPersonRiskRepository repository;

    @Test
    @DisplayName("Test: Table AgreementPersonRiskRepository is present")
    void tableAgreementPersonRepositoryIsPresent() {
        assertNotNull(repository);
    }
}