package com.cjrequena.sample.service;

import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.MoneyAmountDTO;
import com.cjrequena.sample.dto.event.AccountCreatedEventDTO;
import com.cjrequena.sample.dto.event.AccountCreditedEventDTO;
import com.cjrequena.sample.dto.event.AccountDebitedEventDTO;
import com.cjrequena.sample.event.EEvent;
import com.cjrequena.sample.event.Event;
import com.cjrequena.sample.exception.EErrorCode;
import com.cjrequena.sample.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
public class BankAccountCommandService {

  private ApplicationEventPublisher eventPublisher;
  private MessageChannel eventOutputChannel;
  private BankAccountRepository bankAccountRepository;

  @Autowired
  public BankAccountCommandService(ApplicationEventPublisher commandPublisher, BankAccountRepository bankAccountRepository,
    @Qualifier(StreamChannelConfiguration.EVENT_OUTPUT_CHANNEL) MessageChannel eventOutputChannel) {
    this.eventPublisher = commandPublisher;
    this.eventOutputChannel = eventOutputChannel;
    this.bankAccountRepository = bankAccountRepository;
  }

  /**
   *
   * @param bankAccountDTO
   */
  @Transactional
  public BankAccountDTO createAccount(BankAccountDTO bankAccountDTO) throws ServiceException {
    try {
      bankAccountDTO.setAccountId(UUID.randomUUID());
      AccountCreatedEventDTO accountCreatedEventDTO = new AccountCreatedEventDTO();
      accountCreatedEventDTO.setAggregateId(bankAccountDTO.getAccountId());
      accountCreatedEventDTO.setEventId(UUID.randomUUID());
      accountCreatedEventDTO.setEventTimeStamp(LocalDateTime.now());
      accountCreatedEventDTO.setEventVersion("v1");
      accountCreatedEventDTO.setEventType(EEvent.ACCOUNT_CREATED_EVENT.getCode());
      accountCreatedEventDTO.setPayload(bankAccountDTO);
      Event<AccountCreatedEventDTO> event = new Event(accountCreatedEventDTO, eventOutputChannel);
      event.addHeader("operation", EEvent.ACCOUNT_CREATED_EVENT.getCode());
      eventPublisher.publishEvent(event);
      return bankAccountDTO;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), ex);
    }
  }

  @Transactional
  public void creditMoneyToAccount(UUID accountId, MoneyAmountDTO moneyCreditDTO) throws ServiceException {
    try {
      AccountCreditedEventDTO accountCreditedEventDTO = new AccountCreditedEventDTO();
      accountCreditedEventDTO.setAggregateId(accountId);
      accountCreditedEventDTO.setEventId(UUID.randomUUID());
      accountCreditedEventDTO.setEventTimeStamp(LocalDateTime.now());
      accountCreditedEventDTO.setEventVersion("v1");
      accountCreditedEventDTO.setEventType(EEvent.ACCOUNT_CREDITED_EVENT.getCode());
      accountCreditedEventDTO.setPayload(moneyCreditDTO);
      Event<AccountCreditedEventDTO> event = new Event(accountCreditedEventDTO, eventOutputChannel);
      event.addHeader("operation", EEvent.ACCOUNT_CREDITED_EVENT.getCode());
      eventPublisher.publishEvent(event);
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), ex);
    }
  }

  @Transactional
  public void debitMoneyFromAccount(UUID accountId, MoneyAmountDTO moneyCreditDTO) throws ServiceException {
    try {
      AccountDebitedEventDTO accountDebitedEventDTO = new AccountDebitedEventDTO();
      accountDebitedEventDTO.setAggregateId(accountId);
      accountDebitedEventDTO.setEventId(UUID.randomUUID());
      accountDebitedEventDTO.setEventTimeStamp(LocalDateTime.now());
      accountDebitedEventDTO.setEventVersion("v1");
      accountDebitedEventDTO.setEventType(EEvent.ACCOUNT_DEBITED_EVENT.getCode());
      accountDebitedEventDTO.setPayload(moneyCreditDTO);
      Event<AccountDebitedEventDTO> event = new Event(accountDebitedEventDTO, eventOutputChannel);
      event.addHeader("operation", EEvent.ACCOUNT_DEBITED_EVENT.getCode());
      eventPublisher.publishEvent(event);
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), ex);
    }
  }

  public void printAccountsInfo() {
    final List<BankAccountEntity> all = this.bankAccountRepository.findAll();
    for (BankAccountEntity bankAccountEntity : all) {
      log.debug("accountId: {} owner: {} balance {}", bankAccountEntity.getAccountId(), bankAccountEntity.getOwner(), bankAccountEntity.getBalance());
    }
  }
}
