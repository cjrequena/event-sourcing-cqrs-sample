package com.cjrequena.sample.projection;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BankAccountProjection {

  private final BankAccountRepository bankAccountRepository;

  @Autowired
  public BankAccountProjection(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
  }

  @StreamListener(value = StreamChannelConfiguration.EVENT_INPUT_CHANNEL, condition = "headers['operation']=='com.cjrequena.samample.accountcreatedevent.v1'")
  public synchronized void listener(AccountCreatedEvent event) throws InterruptedException {
    log.debug("event::sourced::received -> {}", event);
    if (event.getType().equals(EEventType.ACCOUNT_CREATED_EVENT)) {
      BankAccountDTO dto = event.getData();
      BankAccountEntity entity = new BankAccountEntity();
      entity.setAccountId(dto.getAccountId());
      entity.setOwner(dto.getOwner());
      entity.setBalance(dto.getBalance());
      final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(event.getAggregateId());
      if (bankAccountEntityOptional.isEmpty()) {
        this.bankAccountRepository.save(entity);
      } else {
        log.error("Bank account {} already exists", event.getAggregateId());
      }
    }
  }

  @StreamListener(value = StreamChannelConfiguration.EVENT_INPUT_CHANNEL, condition = "headers['operation']=='com.cjrequena.samample.accountcreditedevent.v1'")
  public synchronized void listener2(AccountCreditedEvent event) {
    log.debug("event::sourced::received -> {}", event);
    if (event.getType().equals(EEventType.ACCOUNT_CREDITED_EVENT)) {
      CreditBankAccountDTO dto = event.getData();
      final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(event.getAggregateId());
      if (bankAccountEntityOptional.isPresent()) {
        BankAccountEntity entity = bankAccountEntityOptional.get();
        entity.setBalance(entity.getBalance().add(dto.getAmount()));
        this.bankAccountRepository.save(entity);
      } else {
        log.error("Bank account {} does not exist", event.getAggregateId());
      }
    }
  }

  @StreamListener(value = StreamChannelConfiguration.EVENT_INPUT_CHANNEL, condition = "headers['operation']=='com.cjrequena.samample.accountdebitedevent.v1'")
  public synchronized void listener3(AccountDebitedEvent event) throws InterruptedException {
    log.debug("event::sourced::received -> {}", event);
    if (event.getType().equals(EEventType.ACCOUNT_DEBITED_EVENT)) {
      DebitBankAccountDTO dto = event.getData();
      final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(event.getAggregateId());
      if (bankAccountEntityOptional.isPresent()) {
        BankAccountEntity entity = bankAccountEntityOptional.get();
        entity.setBalance(entity.getBalance().subtract(dto.getAmount()));
        this.bankAccountRepository.save(entity);
      } else {
        log.error("Bank account {} does not exist", event.getAggregateId());
      }
    }
  }
}
