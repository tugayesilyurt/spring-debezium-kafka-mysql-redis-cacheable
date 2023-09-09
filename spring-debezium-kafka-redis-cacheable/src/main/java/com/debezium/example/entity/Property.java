package com.debezium.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "debezium_property",
        indexes = {@Index(name = "propertykeyidx",columnList = "property_key",unique = true)})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;

    @PrePersist
    private void prePersist() {
        createdDate = LocalDateTime.now();
    }
}
