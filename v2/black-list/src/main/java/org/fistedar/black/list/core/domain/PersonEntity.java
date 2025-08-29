package org.fistedar.black.list.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonEntity {

    @Id
    @Column(name = "personCode", nullable = false)
    private String personCode;

    @Column(name = "person_firstName",nullable = false)
    private String personFirstName;

    @Column(name = "person_lastName", nullable = false)
    private String personLastName;

    @Column(name = "blackListed", nullable = false)
    private Boolean blackListed;
}
