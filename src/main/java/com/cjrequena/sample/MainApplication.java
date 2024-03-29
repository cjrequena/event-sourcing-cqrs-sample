package com.cjrequena.sample;

import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.service.BankAccountCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
  private BankAccountCommandService bankAccountCommandService;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override public void run(String... args) throws Exception {
    BankAccountDTO bankAccountDTO = new BankAccountDTO();

//    bankAccountDTO.setOwner("John Lennon");
//    bankAccountDTO.setBalance(BigDecimal.valueOf(100));
//    bankAccountCommandService.createAccount(bankAccountDTO); // Account created with balance 100
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(50));
//    bankAccountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 50 to the account
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(100));
//    bankAccountCommandService.debitMoneyFromAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Debit 100 to the account
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(100));
//    bankAccountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 100 to the account
//    // John lennon balance should be 150
//
//    bankAccountDTO = new BankAccountDTO();
//    moneyAmountDTO = new MoneyAmountDTO();
//    bankAccountDTO.setOwner("Bill Gates");
//    bankAccountDTO.setBalance(BigDecimal.valueOf(250));
//    bankAccountCommandService.createAccount(bankAccountDTO); // Account created with balance 250
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(50));
//    bankAccountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 50 to the account
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(100));
//    bankAccountCommandService.debitMoneyFromAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Debit 100 to the account
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(120));
//    bankAccountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 120 to the account
//    // Bill gates balance should be 320
//
//    bankAccountDTO = new BankAccountDTO();
//    moneyAmountDTO = new MoneyAmountDTO();
//    bankAccountDTO.setOwner("Steve Jobs");
//    bankAccountDTO.setBalance(BigDecimal.valueOf(1000000)); // Account created with balance 1000000
//    bankAccountCommandService.createAccount(bankAccountDTO);
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(369000));
//    bankAccountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 3690000 to the account
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(69000));
//    bankAccountCommandService.debitMoneyFromAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Debit 69000 to the account
//    moneyAmountDTO.setAmount(BigDecimal.valueOf(69000));
//    bankAccountCommandService.creditMoneyToAccount(bankAccountDTO.getAccountId(), moneyAmountDTO); // Credit 69000 to the account
//    // Steve jobs balance should be 1369000

    this.bankAccountCommandService.printAccountsInfo();
  }
}
