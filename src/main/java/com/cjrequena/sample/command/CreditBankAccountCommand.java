package com.cjrequena.sample.command;

import com.cjrequena.sample.dto.CreditBankAccountDTO;
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
public class CreditBankAccountCommand extends Command<CreditBankAccountDTO> implements Serializable {

  @Builder
  public CreditBankAccountCommand(CreditBankAccountDTO creditBankAccountDTO) {
    super(creditBankAccountDTO.getAccountId(), creditBankAccountDTO.getVersion(), creditBankAccountDTO);
  }
}
