package com.danram.danram.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "authority")
public class Authority {
    @Id
    @Column(name = "authority_name", length = 12, columnDefinition = "varchar")
    private String authorityName;

}