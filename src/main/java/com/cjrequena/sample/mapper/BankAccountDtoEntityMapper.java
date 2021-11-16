package com.cjrequena.sample.mapper;

import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.entity.BankAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 */
@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BankAccountDtoEntityMapper extends DTOEntityMapper<BankAccountDTO, BankAccountEntity> {
}
