package com.cjrequena.sample.command;

import com.cjrequena.sample.dto.command.CommandDTO;
import com.cjrequena.sample.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.KafkaHeaders;
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
public class CommandEmitter {

  private static final String LISTENER_CONDITION = "#command.source.payload instanceof T(com.cjrequena.sample.dto.DTO)";

  @TransactionalEventListener(condition = LISTENER_CONDITION, phase = TransactionPhase.BEFORE_COMMIT, fallbackExecution = true)
  public void emmitEvent(Command command) throws Exception {
    MessageChannel messageChannel = command.getMessageChannel();
    CommandDTO commandDTO = (CommandDTO) command.getSource();

    // Headers
    final Map headers = command.getHeaders();
    // By using the identity of an aggregate as the partition key, all commands for the same aggregate will end up in the same partition in the commands topic and will
    // be processed in order, in a single thread. This way no command will be handled before the previous one has produced all downstream events, and Horwitz notes that
    // this will create a strong consistency guarantee.
    headers.put(KafkaHeaders.MESSAGE_KEY, commandDTO.getAggregateId().toString().getBytes());
    messageChannel.send(MessageBuilder.withPayload((commandDTO)).copyHeaders(headers).build());
    log.debug("command::sourced {}", commandDTO.toString());
  }

}
