package com.cjrequena.sample.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
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
public class BankAccountEntity {
  @Id
  private UUID accountId;
  private String owner;
  private BigDecimal balance;
}
