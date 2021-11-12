package com.cjrequena.sample.service;

import com.cjrequena.sample.MainApplication;
import lombok.extern.log4j.Log4j2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankAccountCommandServiceTest {

  @Autowired
  private BankAccountCommandService bankAccountCommandService;

  @Test
  public void test() throws Exception {
  }
}
