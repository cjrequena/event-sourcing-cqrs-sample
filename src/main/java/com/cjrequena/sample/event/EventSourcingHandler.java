package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Slf4j
@Component
@EnableBinding
public class EventSourcingHandler {

  private static final String LISTENER_CONDITION = "#event.source.payload instanceof T(com.cjrequena.sample.dto.DTO)";

  @TransactionalEventListener(condition = LISTENER_CONDITION, phase = TransactionPhase.BEFORE_COMMIT, fallbackExecution = true)
  public void publishEvent(Event event) throws Exception {
    MessageChannel messageChannel = event.getMessageChannel();
    EventDTO eventDTO = (EventDTO) event.getSource();
    final Map headers = event.getHeaders();
    messageChannel.send(MessageBuilder.withPayload((eventDTO)).copyHeaders(headers).build());
    log.debug("Event sourced {}", eventDTO.toString());
  }

}
