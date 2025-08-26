package org.javaguru.travel.insurance.core.domain.export;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agreement_uuids")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgreementUuidEntity {

    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "time_date")
    private LocalDateTime timeDate;
}
