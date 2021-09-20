package com.cjrequena.sample.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(value = {
  "amount"
})
@JsonTypeName("payload")
public class MoneyAmountDTO implements Serializable {
  private BigDecimal amount;
}
