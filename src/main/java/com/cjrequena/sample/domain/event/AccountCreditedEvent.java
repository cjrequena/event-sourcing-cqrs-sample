package com.cjrequena.sample.domain.event;

import com.cjrequena.sample.dto.CreditBankAccountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class AccountCreditedEvent extends Event<CreditBankAccountDTO> implements Serializable {

}
