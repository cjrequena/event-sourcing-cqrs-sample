package com.cjrequena.sample.command;

import com.cjrequena.sample.configuration.StreamChannelConfiguration;
import com.cjrequena.sample.dto.event.AccountCreatedEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandHandler {


  @Autowired
  public CommandHandler() {
  }

  @StreamListener(value = StreamChannelConfiguration.COMMAND_INPUT_CHANNEL)
  public synchronized void listener(AccountCreatedEventDTO dto) throws InterruptedException {
    log.debug("command::received -> {}", dto);
  }


}
