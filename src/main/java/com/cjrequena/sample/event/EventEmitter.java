package com.cjrequena.sample.event;

import com.cjrequena.sample.domain.event.Event;
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
public class EventEmitter {

  private static final String LISTENER_CONDITION = "#kafkaEvent.source instanceof T(com.cjrequena.sample.domain.event.Event)";

  @TransactionalEventListener(condition = LISTENER_CONDITION, phase = TransactionPhase.BEFORE_COMMIT, fallbackExecution = true)
  public void emmitEvent(KafkaEvent kafkaEvent) throws Exception {
    MessageChannel messageChannel = kafkaEvent.getMessageChannel();
    Event event = (Event) kafkaEvent.getSource();

    // Headers
    final Map headers = kafkaEvent.getHeaders();
    // By using the identity of an aggregate as the partition key, all commands for the same aggregate will end up in the same partition in the commands topic and will
    // be processed in order, in a single thread. This way no command will be handled before the previous one has produced all downstream events, and Horwitz notes that
    // this will create a strong consistency guarantee.
    headers.put(KafkaHeaders.MESSAGE_KEY, event.getAggregateId().toString().getBytes());
    messageChannel.send(MessageBuilder.withPayload((event)).copyHeaders(headers).build());
    log.debug("event::sourced {}", event.toString());
  }

}
