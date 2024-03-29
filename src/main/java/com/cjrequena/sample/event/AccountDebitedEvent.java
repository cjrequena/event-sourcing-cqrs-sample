package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.DebitBankAccountDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class AccountDebitedEvent extends Event<DebitBankAccountDTO> implements Serializable {

  public AccountDebitedEvent() {
  }

  @Builder
  public AccountDebitedEvent(
    @NotBlank UUID id,
    @NotBlank String source,
    @NotBlank String specVersion,
    @NotNull EEventType type,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull DebitBankAccountDTO data,
    String dataBase64,
    ESchemaType dataSchema,
    @NotBlank UUID aggregateId,
    @NotBlank int version) {
    super(id, source, specVersion, type, dataContentType, subject, time, data, dataBase64, dataSchema, aggregateId, version);
  }
}
