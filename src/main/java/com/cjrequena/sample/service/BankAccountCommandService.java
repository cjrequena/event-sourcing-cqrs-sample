package com.cjrequena.sample.service;

import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.domain.BankAccountEntity;
import com.cjrequena.sample.domain.event.AccountCreatedEvent;
import com.cjrequena.sample.domain.event.AccountCreditedEvent;
import com.cjrequena.sample.domain.event.AccountDebitedEvent;
import com.cjrequena.sample.domain.event.EventMetaData;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.MoneyAmountDTO;
import com.cjrequena.sample.event.EEvent;
import com.cjrequena.sample.event.KafkaEvent;
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
    EventMetaData eventMetaData = new EventMetaData();
    eventMetaData.setEventId(UUID.randomUUID());
    eventMetaData.setEventTimeStamp(LocalDateTime.now());
    eventMetaData.setEventType(EEvent.ACCOUNT_CREATED_EVENT.getCode());
    eventMetaData.setAggregateId(bankAccountDTO.getAccountId());
    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
    accountCreatedEvent.setMetadata(eventMetaData);
    accountCreatedEvent.setPayload(bankAccountDTO);
    KafkaEvent<AccountCreatedEvent> kafkaEvent = new KafkaEvent(accountCreatedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEvent.ACCOUNT_CREATED_EVENT.getCode());
    applicationEventPublisher.publishEvent(kafkaEvent);
    return bankAccountDTO;
  }

  @Transactional
  public void creditMoneyToAccount(UUID accountId, MoneyAmountDTO moneyCreditDTO) {
    EventMetaData eventMetaData = new EventMetaData();
    eventMetaData.setEventId(UUID.randomUUID());
    eventMetaData.setEventTimeStamp(LocalDateTime.now());
    eventMetaData.setEventType(EEvent.ACCOUNT_CREDITED_EVENT.getCode());
    eventMetaData.setAggregateId(accountId);
    AccountCreditedEvent creditedEvent = new AccountCreditedEvent();
    creditedEvent.setMetadata(eventMetaData);
    creditedEvent.setPayload(moneyCreditDTO);
    KafkaEvent<AccountCreditedEvent> kafkaEvent = new KafkaEvent(creditedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEvent.ACCOUNT_CREDITED_EVENT.getCode());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void debitMoneyFromAccount(UUID accountId, MoneyAmountDTO moneyCreditDTO) {
    EventMetaData eventMetaData = new EventMetaData();
    eventMetaData.setEventId(UUID.randomUUID());
    eventMetaData.setEventTimeStamp(LocalDateTime.now());
    eventMetaData.setEventType(EEvent.ACCOUNT_DEBITED_EVENT.getCode());
    eventMetaData.setAggregateId(accountId);
    AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent();
    accountDebitedEvent.setMetadata(eventMetaData);
    accountDebitedEvent.setPayload(moneyCreditDTO);
    KafkaEvent<AccountDebitedEvent> kafkaEvent = new KafkaEvent(accountDebitedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEvent.ACCOUNT_DEBITED_EVENT.getCode());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  public void printAccountsInfo() {
    final List<BankAccountEntity> all = this.bankAccountRepository.findAll();
    for (BankAccountEntity bankAccountEntity : all) {
      log.debug("accountId: {} owner: {} balance {}", bankAccountEntity.getAccountId(), bankAccountEntity.getOwner(), bankAccountEntity.getBalance());
    }
  }
}
