package com.cjrequena.sample.command;

import org.springframework.context.ApplicationEvent;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.MessageChannel;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
public class Command<T> extends ApplicationEvent {

  private final Map<String, Object> headers;
  private MessageChannel messageChannel;

  public Command(T source, MessageChannel messageChannel) {
    super(source != null ? source : KafkaNull.INSTANCE);
    this.headers = new HashMap<>();
    this.messageChannel = messageChannel;
  }

  public void addHeader(String key, Object value) {
    headers.put(key, value);
  }

  public Map<String, Object> getHeaders() {
    return new HashMap<>(headers);
  }

  public MessageChannel getMessageChannel() {
    return this.messageChannel;
  }
}
