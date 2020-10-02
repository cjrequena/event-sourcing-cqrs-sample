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
  String EVENT_INPUT_CHANNEL = "event_input_channel";

  String COMMAND_OUTPUT_CHANNEL = "command_output_channel";
  String COMMAND_INPUT_CHANNEL = "command_input_channel";

  @Output(EVENT_OUTPUT_CHANNEL)
  MessageChannel eventOutputChannel();

  @Input(EVENT_INPUT_CHANNEL)
  MessageChannel eventInputChannel();

  @Output(COMMAND_OUTPUT_CHANNEL)
  MessageChannel commandOutputChannel();

  @Input(COMMAND_INPUT_CHANNEL)
  MessageChannel commandInputChannel();
}
