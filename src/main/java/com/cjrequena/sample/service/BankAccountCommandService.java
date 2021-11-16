package com.cjrequena.sample.service;

import com.cjrequena.sample.command.Command;
import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.CreditBankAccountCommand;
import com.cjrequena.sample.command.DebitBankAccountCommand;
import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.entity.BankAccountEntity;
import com.cjrequena.sample.event.AccountCreatedEvent;
import com.cjrequena.sample.event.AccountCreditedEvent;
import com.cjrequena.sample.event.AccountDebitedEvent;
import com.cjrequena.sample.event.EEventType;
import com.cjrequena.sample.event.KafkaEvent;
import com.cjrequena.sample.exception.service.AggregateVersionServiceException;
import com.cjrequena.sample.exception.service.BankAccountNotFoundServiceException;
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
  public void handler(Command command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    log.debug("Command type: {} Command aggregate_id: {}",command.getCommandType(), command.getAggregateId());
    switch (command.getCommandType()) {
      case "CreateBankAccountCommand":
        this.process((CreateBankAccountCommand) command);
        break;
      case "DebitBankAccountCommand":
        this.process((DebitBankAccountCommand) command);
        break;
      case "CreditBankAccountCommand":
        this.process((CreditBankAccountCommand) command);
        break;
    }
  }

  @Transactional
  public void process(CreateBankAccountCommand createBankAccountCommand) {
    AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
      .type(EEventType.ACCOUNT_CREATED_EVENT)
      .data(createBankAccountCommand.getData())
      .aggregateId(createBankAccountCommand.getAggregateId())
      .version(createBankAccountCommand.getVersion())
      .build();

    KafkaEvent<AccountCreatedEvent> kafkaEvent = new KafkaEvent(accountCreatedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREATED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(CreditBankAccountCommand creditBankAccountCommand) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    this.validateAggregateState(creditBankAccountCommand.getAggregateId(), creditBankAccountCommand.getVersion());

    AccountCreditedEvent accountCreditedEvent = AccountCreditedEvent.builder()
      .type(EEventType.ACCOUNT_CREDITED_EVENT)
      .data(creditBankAccountCommand.getData())
      .aggregateId(creditBankAccountCommand.getAggregateId())
      .version(creditBankAccountCommand.getVersion() + 1) // TODO
      .build();

    KafkaEvent<AccountCreditedEvent> kafkaEvent = new KafkaEvent(accountCreditedEvent, eventOutputChannel);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREDITED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(DebitBankAccountCommand debitBankAccountCommand) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    this.validateAggregateState(debitBankAccountCommand.getAggregateId(), debitBankAccountCommand.getVersion());

    AccountDebitedEvent accountDebitedEvent = AccountDebitedEvent.builder()
      .type(EEventType.ACCOUNT_DEBITED_EVENT)
      .data(debitBankAccountCommand.getData())
      .aggregateId(debitBankAccountCommand.getAggregateId())
      .version(debitBankAccountCommand.getVersion() + 1) // TODO
      .build();

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

  private BankAccountEntity validateAggregateState(UUID aggregateId, int version) throws AggregateVersionServiceException, BankAccountNotFoundServiceException {
    final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(aggregateId);
    if (bankAccountEntityOptional.isPresent()) {
      BankAccountEntity bankAccountEntity = bankAccountEntityOptional.get();
      if (bankAccountEntity.getVersion() != version) {
        throw new AggregateVersionServiceException("Current aggregate version do not match with command aggregate version");
      }
      return bankAccountEntity;
    } else {
      throw new BankAccountNotFoundServiceException("Bank account " + aggregateId + " not found");
    }
  }
}
