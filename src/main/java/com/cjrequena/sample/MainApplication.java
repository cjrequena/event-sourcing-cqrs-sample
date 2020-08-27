package com.cjrequena.sample;

import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.MoneyAmountDTO;
import com.cjrequena.sample.service.AccountCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@SpringBootApplication
@EnableAutoConfiguration
public class MainApplication implements CommandLineRunner {

  @Autowired
  private AccountCommandService accountCommandService;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override public void run(String... args) throws Exception {
    BankAccountDTO bankAccountDTO = new BankAccountDTO();
    MoneyAmountDTO moneyAmountDTO = new MoneyAmountDTO();

    bankAccountDTO.setAccountId(UUID.randomUUID());
    bankAccountDTO.setOwner("John Lennon");
    bankAccountDTO.setBalance(BigDecimal.valueOf(100));
    accountCommandService.createAccount(bankAccountDTO); // Account created with balance 100
    accountCommandService.createAccount(bankAccountDTO); // Should log an error on projection layer, because the Bank account was already created.
    moneyAmountDTO.setAmount(BigDecimal.valueOf(50));
    accountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 50 to the account
    moneyAmountDTO.setAmount(BigDecimal.valueOf(100));
    accountCommandService.debitMoneyFromAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Debit 100 to the account
    moneyAmountDTO.setAmount(BigDecimal.valueOf(100));
    accountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 100 to the account
    // John lennon balance should be 150

    bankAccountDTO = new BankAccountDTO();
    moneyAmountDTO = new MoneyAmountDTO();
    bankAccountDTO.setAccountId(UUID.randomUUID());
    bankAccountDTO.setOwner("Bill Gates");
    bankAccountDTO.setBalance(BigDecimal.valueOf(250));
    accountCommandService.createAccount(bankAccountDTO); // Account created with balance 250
    moneyAmountDTO.setAmount(BigDecimal.valueOf(50));
    accountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 50 to the account
    moneyAmountDTO.setAmount(BigDecimal.valueOf(100));
    accountCommandService.debitMoneyFromAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Debit 100 to the account
    moneyAmountDTO.setAmount(BigDecimal.valueOf(120));
    accountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 120 to the account
    // Bill gates balance should be 320

    bankAccountDTO = new BankAccountDTO();
    moneyAmountDTO = new MoneyAmountDTO();
    bankAccountDTO.setAccountId(UUID.randomUUID());
    bankAccountDTO.setOwner("Steve Jobs");
    bankAccountDTO.setBalance(BigDecimal.valueOf(1000000)); // Account created with balance 1000000
    accountCommandService.createAccount(bankAccountDTO);
    moneyAmountDTO.setAmount(BigDecimal.valueOf(369000));
    accountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 3690000 to the account
    moneyAmountDTO.setAmount(BigDecimal.valueOf(69000));
    accountCommandService.debitMoneyFromAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Debit 69000 to the account
    moneyAmountDTO.setAmount(BigDecimal.valueOf(69000));
    accountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 69000 to the account
    // Steve jobs balance should be 1369000

    this.accountCommandService.printAccountsInfo();
  }
}
