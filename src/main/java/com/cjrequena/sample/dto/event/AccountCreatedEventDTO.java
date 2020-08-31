package com.cjrequena.sample.dto.event;

import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class AccountCreatedEventDTO extends EventDTO<BankAccountDTO> implements Serializable {

}
