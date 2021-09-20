package com.cjrequena.sample.mapper;

import com.cjrequena.sample.domain.BankAccountEntity;
import com.cjrequena.sample.dto.BankAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BankAccountDtoEntityMapper extends DTOEntityMapper<BankAccountDTO, BankAccountEntity> {
}
