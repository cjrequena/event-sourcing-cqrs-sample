package com.cjrequena.sample.dto.command;

import com.cjrequena.sample.dto.MoneyAmountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class DebitAccountCommandDTO extends CommandDTO<MoneyAmountDTO> implements Serializable {

}
