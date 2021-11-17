package com.cjrequena.sample.service;

import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.entity.BankAccountEntity;
import com.cjrequena.sample.exception.service.BankAccountNotFoundServiceException;
import com.cjrequena.sample.exception.service.RSQLParserServiceException;
import com.cjrequena.sample.mapper.BankAccountDtoEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 *
 */
@Slf4j
@Service
public class BankAccountQueryService {

  private final BankAccountRepository bankAccountRepository;
  private final BankAccountDtoEntityMapper bankAccountDtoEntityMapper;

  @Autowired
  public BankAccountQueryService(BankAccountRepository bankAccountRepository, BankAccountDtoEntityMapper bankAccountDtoEntityMapper) {
    this.bankAccountRepository = bankAccountRepository;
    this.bankAccountDtoEntityMapper = bankAccountDtoEntityMapper;
  }

  public BankAccountDTO retrieveById(UUID accountId) throws BankAccountNotFoundServiceException {
    //--
    Optional<BankAccountEntity> entity = this.bankAccountRepository.findById(accountId);
    if (!entity.isPresent()) {
      log.error("Bank account {} does not exist", accountId);
      throw new BankAccountNotFoundServiceException("Bank account {} does not exist");
    }
    return bankAccountDtoEntityMapper.toDTO(entity.get());
    //--
  }

  public List<BankAccountDTO> retrieve() throws RSQLParserServiceException {
    //--
    try {
      final List<BankAccountEntity> bankAccountEntities = this.bankAccountRepository.findAll();
      return bankAccountEntities.stream().map(entity -> bankAccountDtoEntityMapper.toDTO(entity)).collect(Collectors.toList());
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      throw ex;
    }
    //--
  }

}
