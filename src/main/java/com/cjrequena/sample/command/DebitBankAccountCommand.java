package com.cjrequena.sample.command;

import com.cjrequena.sample.dto.DebitBankAccountDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class DebitBankAccountCommand extends Command<DebitBankAccountDTO> implements Serializable {

  @Builder
  public DebitBankAccountCommand(DebitBankAccountDTO debitBankAccountDTO) {
    super(debitBankAccountDTO.getAccountId(), debitBankAccountDTO.getVersion(), debitBankAccountDTO);
  }
}
