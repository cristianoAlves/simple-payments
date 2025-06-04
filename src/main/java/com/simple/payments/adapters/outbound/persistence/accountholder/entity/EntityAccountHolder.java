package com.simple.payments.adapters.outbound.persistence.accountholder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SP_ACCOUNT_HOLDER")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class EntityAccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_holder_seq")
    @SequenceGenerator(name = "account_holder_seq", sequenceName = "account_holder_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Column(unique = true)
    @ToString.Exclude
    private String document;

    @Column(unique = true)
    private String email;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountHolderType type;

}
