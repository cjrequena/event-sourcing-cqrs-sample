package com.cjrequena.sample.service;

import com.cjrequena.sample.db.OffsetLimitRequestBuilder;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.db.rsql.CustomRsqlVisitor;
import com.cjrequena.sample.db.rsql.RsqlSearchOperation;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.mapper.BankAccountDtoEntityMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

  public BankAccountDTO retrieveById(UUID accountId) {
    //--
    Optional<BankAccountEntity> entity = this.bankAccountRepository.findById(accountId);
    if (!entity.isPresent()) {
      log.error("Bank account {} does not exist", accountId);
      return null;
    }
    return bankAccountDtoEntityMapper.toDTO(entity.get());
    //--
  }

  public Page retrieve(String search, String sort, Integer offset, Integer limit) throws ExceptionInInitializerError {
    //--
    try {
      Page<BankAccountEntity> page;
      Specification<BankAccountEntity> specification;
      Pageable pageable = OffsetLimitRequestBuilder.create(offset, limit, sort);

      if (search != null) {
        Node rootNode = new RSQLParser(RsqlSearchOperation.defaultOperators()).parse(search);
        specification = rootNode.accept(new CustomRsqlVisitor<>());
        page = this.bankAccountRepository.findAll(specification, pageable);
      } else {
        page = this.bankAccountRepository.findAll(pageable);
      }

      return page.map(entity -> bankAccountDtoEntityMapper.toDTO(entity));
    } catch (PropertyReferenceException ex) {
      log.error("{}", ex.getMessage());
      return null;
    }
    //--
  }

}
