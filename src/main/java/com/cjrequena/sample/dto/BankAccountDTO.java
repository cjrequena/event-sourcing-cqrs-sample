package com.cjrequena.sample.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(value = {
  "accountId",
  "owner",
  "balance"
})
@JsonTypeName("payload")
public class BankAccountDTO implements DTO, Serializable {
    private UUID accountId;
    private String owner;
    private BigDecimal balance;
}
