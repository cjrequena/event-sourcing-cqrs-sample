package com.cjrequena.sample.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
public interface StreamChannelConfiguration {

  String EVENT_OUTPUT_CHANNEL = "event_output_channel";

  @Output(EVENT_OUTPUT_CHANNEL)
  MessageChannel eventOutputChannel();

  String EVENT_INPUT_CHANNEL = "event_input_channel";

  @Input(EVENT_INPUT_CHANNEL)
  MessageChannel eventInputChannel();

}
