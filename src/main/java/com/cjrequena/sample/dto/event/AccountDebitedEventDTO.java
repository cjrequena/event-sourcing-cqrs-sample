package com.cjrequena.sample.dto.event;

import com.cjrequena.sample.dto.MoneyAmountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper=true, includeFieldNames=true)
@EqualsAndHashCode(callSuper = false)
public class AccountDebitedEventDTO extends EventDTO<MoneyAmountDTO> implements Serializable {

}
