package com.cjrequena.sample.projection;

import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.MoneyAmountDTO;
import com.cjrequena.sample.dto.event.AccountCreatedEventDTO;
import com.cjrequena.sample.dto.event.AccountCreditedEventDTO;
import com.cjrequena.sample.dto.event.AccountDebitedEventDTO;
import com.cjrequena.sample.event.EEvent;
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

  @StreamListener(value = StreamChannelConfiguration.EVENT_INPUT_CHANNEL, condition = "headers['operation']=='AccountCreatedEvent'")
  public synchronized void listener(AccountCreatedEventDTO dto) throws InterruptedException {
    log.debug("event::sourced::received -> {}", dto);
    if (dto.getEventType().equals(EEvent.ACCOUNT_CREATED_EVENT.getCode())) {
      BankAccountDTO bankAccountDTO = dto.getPayload();
      BankAccountEntity entity = new BankAccountEntity();
      entity.setAccountId(bankAccountDTO.getAccountId());
      entity.setOwner(bankAccountDTO.getOwner());
      entity.setBalance(bankAccountDTO.getBalance());
      final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(dto.getAggregateId());
      if (bankAccountEntityOptional.isEmpty()) {
        this.bankAccountRepository.save(entity);
      } else {
        log.error("Bank account {} already exists", dto.getAggregateId());
      }
    }
  }

  @StreamListener(value = StreamChannelConfiguration.EVENT_INPUT_CHANNEL, condition = "headers['operation']=='AccountCreditedEvent'")
  public synchronized void listener2(AccountCreditedEventDTO dto) {
    log.debug("event::sourced::received -> {}", dto);
    if (dto.getEventType().equals(EEvent.ACCOUNT_CREDITED_EVENT.getCode())) {
      MoneyAmountDTO moneyAmountDTO = dto.getPayload();
      final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(dto.getAggregateId());
      if (bankAccountEntityOptional.isPresent()) {
        BankAccountEntity entity = bankAccountEntityOptional.get();
        entity.setBalance(entity.getBalance().add(moneyAmountDTO.getAmount()));
        this.bankAccountRepository.save(entity);
      } else {
        log.error("Bank account {} does not exist", dto.getAggregateId());
      }
    }
  }

  @StreamListener(value = StreamChannelConfiguration.EVENT_INPUT_CHANNEL, condition = "headers['operation']=='AccountDebitedEvent'")
  public synchronized void listener3(AccountDebitedEventDTO dto) throws InterruptedException {
    log.debug("event::sourced::received -> {}", dto);
    if (dto.getEventType().equals(EEvent.ACCOUNT_DEBITED_EVENT.getCode())) {
      MoneyAmountDTO moneyAmountDTO = dto.getPayload();
      final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(dto.getAggregateId());
      if (bankAccountEntityOptional.isPresent()) {
        BankAccountEntity entity = bankAccountEntityOptional.get();
        entity.setBalance(entity.getBalance().subtract(moneyAmountDTO.getAmount()));
        this.bankAccountRepository.save(entity);
      } else {
        log.error("Bank account {} does not exist", dto.getAggregateId());
      }
    }
  }
}
