package com.cjrequena.sample.service;

import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.domain.BankAccountEntity;
import com.cjrequena.sample.domain.event.AccountCreatedEvent;
import com.cjrequena.sample.domain.event.AccountCreditedEvent;
import com.cjrequena.sample.domain.event.AccountDebitedEvent;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.CreditBankAccountDTO;
import com.cjrequena.sample.dto.DebitBankAccountDTO;
import com.cjrequena.sample.event.EEventType;
import com.cjrequena.sample.event.KafkaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    UUID aggregateId = UUID.randomUUID();
    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
    accountCreatedEvent.setType(EEventType.ACCOUNT_CREATED_EVENT);
    accountCreatedEvent.setAggregateId(aggregateId);
    bankAccountDTO.setAccountId(aggregateId);
    accountCreatedEvent.setData(bankAccountDTO);
    KafkaEvent<AccountCreatedEvent> kafkaEvent = new KafkaEvent(accountCreatedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREATED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
    return bankAccountDTO;
  }

  @Transactional
  public void creditMoneyToAccount(UUID accountId, CreditBankAccountDTO dto) {
    AccountCreditedEvent creditedEvent = new AccountCreditedEvent();
    creditedEvent.setType(EEventType.ACCOUNT_CREDITED_EVENT);
    creditedEvent.setAggregateId(accountId);
    creditedEvent.setData(dto);
    KafkaEvent<AccountCreditedEvent> kafkaEvent = new KafkaEvent(creditedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREDITED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void debitMoneyFromAccount(UUID accountId, DebitBankAccountDTO dto) {
    AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent();
    accountDebitedEvent.setType(EEventType.ACCOUNT_DEBITED_EVENT);
    accountDebitedEvent.setAggregateId(accountId);
    accountDebitedEvent.setData(dto);
    KafkaEvent<AccountDebitedEvent> kafkaEvent = new KafkaEvent(accountDebitedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_DEBITED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  public void printAccountsInfo() {
    final List<BankAccountEntity> all = this.bankAccountRepository.findAll();
    for (BankAccountEntity bankAccountEntity : all) {
      log.debug("accountId: {} owner: {} balance {}", bankAccountEntity.getAccountId(), bankAccountEntity.getOwner(), bankAccountEntity.getBalance());
    }
  }
}
