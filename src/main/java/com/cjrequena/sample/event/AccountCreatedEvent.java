package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class AccountCreatedEvent extends Event<BankAccountDTO> implements Serializable {

}
