package com.cjrequena.sample.service;

import com.cjrequena.sample.MainApplication;
import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountCommandServiceTest {

  @Autowired
  private AccountCommandService accountCommandService;

  @Test
  public void test() throws Exception {
    BankAccountDTO dto = new BankAccountDTO();
    dto.setOwner("cjrequena");
    dto.setBalance(BigDecimal.TEN);
    accountCommandService.createAccount(dto);
  }
}
