package com.cjrequena.sample.domain.event;

import com.cjrequena.sample.dto.DebitBankAccountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class AccountDebitedEvent extends Event<DebitBankAccountDTO> implements Serializable {

}
