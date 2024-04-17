package com.fuenteadictos.fuenteadictos.domain;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseObject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private String uuid; // REVISAR, NO SE GENERA AUTOMÁTICAMENTE FIXME
                         // CUANDO SEPA COMO GENERARLO PONER nullable = false

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDate creatDate;

    @Column(name = "voided", nullable = false)
    private boolean voided = false;

}
