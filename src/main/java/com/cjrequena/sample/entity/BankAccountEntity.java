package com.cjrequena.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {
  @Id
  @Column(name = "account_id")
  private UUID accountId;

  @Column(name = "owner")
  private String owner;

  @Column(name = "balance")
  private BigDecimal balance;

  @Column(name = "creation_date")
  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate creationDate;

  @Column(name = "version")
  private int version;
}
