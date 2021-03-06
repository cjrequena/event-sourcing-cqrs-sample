package com.cjrequena.sample.service;

import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.MoneyAmountDTO;
import com.cjrequena.sample.dto.event.AccountCreatedEventDTO;
import com.cjrequena.sample.dto.event.AccountCreditedEventDTO;
import com.cjrequena.sample.dto.event.AccountDebitedEventDTO;
import com.cjrequena.sample.dto.event.EventMetaDataDTO;
import com.cjrequena.sample.event.EEvent;
import com.cjrequena.sample.event.Event;
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

  private ApplicationEventPublisher applicationEventPublisher;
  private MessageChannel eventOutputChannel;
  private BankAccountRepository bankAccountRepository;

  @Autowired
  public BankAccountCommandService(ApplicationEventPublisher applicationEventPublisher, BankAccountRepository bankAccountRepository,
    @Qualifier(StreamChannelConfiguration.EVENT_OUTPUT_CHANNEL) MessageChannel eventOutputChannel) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.eventOutputChannel = eventOutputChannel;
    this.bankAccountRepository = bankAccountRepository;
  }

  /**
   *
   * @param bankAccountDTO
   */
  @Transactional
  public BankAccountDTO createAccount(BankAccountDTO bankAccountDTO) {
    bankAccountDTO.setAccountId(UUID.randomUUID());
    EventMetaDataDTO metaDataDTO = new EventMetaDataDTO();
    metaDataDTO.setEventId(UUID.randomUUID());
    metaDataDTO.setEventTimeStamp(LocalDateTime.now());
    metaDataDTO.setEventType(EEvent.ACCOUNT_CREATED_EVENT.getCode());
    metaDataDTO.setAggregateId(bankAccountDTO.getAccountId());
    AccountCreatedEventDTO accountCreatedEventDTO = new AccountCreatedEventDTO();
    accountCreatedEventDTO.setMetadata(metaDataDTO);
    accountCreatedEventDTO.setPayload(bankAccountDTO);
    Event<AccountCreatedEventDTO> event = new Event(accountCreatedEventDTO, eventOutputChannel);
    event.addHeader("operation", EEvent.ACCOUNT_CREATED_EVENT.getCode());
    applicationEventPublisher.publishEvent(event);
    return bankAccountDTO;
  }

  @Transactional
  public void creditMoneyToAccount(UUID accountId, MoneyAmountDTO moneyCreditDTO) {
    EventMetaDataDTO metaDataDTO = new EventMetaDataDTO();
    metaDataDTO.setEventId(UUID.randomUUID());
    metaDataDTO.setEventTimeStamp(LocalDateTime.now());
    metaDataDTO.setEventType(EEvent.ACCOUNT_CREDITED_EVENT.getCode());
    metaDataDTO.setAggregateId(accountId);
    AccountCreditedEventDTO accountCreditedEventDTO = new AccountCreditedEventDTO();
    accountCreditedEventDTO.setMetadata(metaDataDTO);
    accountCreditedEventDTO.setPayload(moneyCreditDTO);
    Event<AccountCreditedEventDTO> event = new Event(accountCreditedEventDTO, eventOutputChannel);
    event.addHeader("operation", EEvent.ACCOUNT_CREDITED_EVENT.getCode());
    applicationEventPublisher.publishEvent(event);
  }

  @Transactional
  public void debitMoneyFromAccount(UUID accountId, MoneyAmountDTO moneyCreditDTO) {
    EventMetaDataDTO metaDataDTO = new EventMetaDataDTO();
    metaDataDTO.setEventId(UUID.randomUUID());
    metaDataDTO.setEventTimeStamp(LocalDateTime.now());
    metaDataDTO.setEventType(EEvent.ACCOUNT_DEBITED_EVENT.getCode());
    metaDataDTO.setAggregateId(accountId);
    AccountDebitedEventDTO accountDebitedEventDTO = new AccountDebitedEventDTO();
    accountDebitedEventDTO.setMetadata(metaDataDTO);
    accountDebitedEventDTO.setPayload(moneyCreditDTO);
    Event<AccountDebitedEventDTO> event = new Event(accountDebitedEventDTO, eventOutputChannel);
    event.addHeader("operation", EEvent.ACCOUNT_DEBITED_EVENT.getCode());
    applicationEventPublisher.publishEvent(event);
  }

  public void printAccountsInfo() {
    final List<BankAccountEntity> all = this.bankAccountRepository.findAll();
    for (BankAccountEntity bankAccountEntity : all) {
      log.debug("accountId: {} owner: {} balance {}", bankAccountEntity.getAccountId(), bankAccountEntity.getOwner(), bankAccountEntity.getBalance());
    }
  }
}
