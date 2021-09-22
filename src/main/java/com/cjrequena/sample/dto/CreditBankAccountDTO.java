package com.cjrequena.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@JsonPropertyOrder(value = {
  "accountId",
  "version",
  "amount"

})
@Schema
@Data
public class CreditBankAccountDTO {

  @JsonProperty(value = "account_id")
  private UUID accountId;

  @NotNull(message = "version is a required field")
  @JsonProperty(value = "version", required = true)
  private int version;

  @JsonProperty(value = "amount")
  private BigDecimal amount;

}
