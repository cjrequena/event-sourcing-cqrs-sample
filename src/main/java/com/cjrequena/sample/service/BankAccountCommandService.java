package com.cjrequena.sample.service;

import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.CreditBankAccountCommand;
import com.cjrequena.sample.command.DebitBankAccountCommand;
import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.CreditBankAccountDTO;
import com.cjrequena.sample.dto.DebitBankAccountDTO;
import com.cjrequena.sample.entity.BankAccountEntity;
import com.cjrequena.sample.event.AccountCreatedEvent;
import com.cjrequena.sample.event.AccountCreditedEvent;
import com.cjrequena.sample.event.AccountDebitedEvent;
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

  @Transactional
  public void process(CreateBankAccountCommand createBankAccountCommand) {
    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
    accountCreatedEvent.setType(EEventType.ACCOUNT_CREATED_EVENT);
    accountCreatedEvent.setAggregateId(createBankAccountCommand.getAggregateId());
    accountCreatedEvent.setData(createBankAccountCommand.getData());
    accountCreatedEvent.setVersion(createBankAccountCommand.getVersion());
    KafkaEvent<AccountCreatedEvent> kafkaEvent = new KafkaEvent(accountCreatedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREATED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(CreditBankAccountCommand creditBankAccountCommand) {

    final BankAccountEntity bankAccountEntity;
    final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(creditBankAccountCommand.getAggregateId());
    if(bankAccountEntityOptional.isPresent()){
      bankAccountEntity = bankAccountEntityOptional.get();
      if(bankAccountEntity.getVersion()!= creditBankAccountCommand.getVersion()){
        throw new RuntimeException("Not matching version"); // TODO
      }
    }else{
      throw new RuntimeException("Not found"); // TODO
    }

    AccountCreditedEvent accountCreditedEvent = new AccountCreditedEvent();
    accountCreditedEvent.setType(EEventType.ACCOUNT_CREDITED_EVENT);
    accountCreditedEvent.setAggregateId(creditBankAccountCommand.getAggregateId());
    accountCreditedEvent.setData(creditBankAccountCommand.getData());
    accountCreditedEvent.setVersion(bankAccountEntity.getVersion() + 1 );
    KafkaEvent<AccountCreditedEvent> kafkaEvent = new KafkaEvent(accountCreditedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREDITED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(DebitBankAccountCommand debitBankAccountCommand) {
    final BankAccountEntity bankAccountEntity;
    final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(debitBankAccountCommand.getAggregateId());
    if(bankAccountEntityOptional.isPresent()){
      bankAccountEntity = bankAccountEntityOptional.get();
      if(bankAccountEntity.getVersion()!= debitBankAccountCommand.getVersion()){
        throw new RuntimeException("Not matching version"); // TODO
      }
    }else{
      throw new RuntimeException("Not found"); // TODO
    }

    AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent();
    accountDebitedEvent.setType(EEventType.ACCOUNT_DEBITED_EVENT);
    accountDebitedEvent.setAggregateId(debitBankAccountCommand.getAggregateId());
    accountDebitedEvent.setData(debitBankAccountCommand.getData());
    accountDebitedEvent.setVersion(bankAccountEntity.getVersion() + 1 );
    KafkaEvent<AccountCreditedEvent> kafkaEvent = new KafkaEvent(accountDebitedEvent, eventOutputChannel);
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
