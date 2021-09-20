package com.cjrequena.sample.domain.event;

import com.cjrequena.sample.event.EEventType;
import com.cjrequena.sample.event.ESchemaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public abstract class Event<T> {

  // Unique id for the specific message. This id is globally unique
  @NotBlank
  @Builder.Default
  protected String id = UUID.randomUUID().toString();

  // Identifies the context in which an event happened.
  @NotBlank
  protected String source;

  // Name of the component generating this message.
  @NotBlank
  protected String service;

  // The version of the CloudEvents specification which the event uses.
  @NotBlank
  @Builder.Default
  protected String specVersion = "1.0";

  // Type of message
  @NotNull
  protected EEventType type;

  // Content type of the data value. Must adhere to RFC 2046 format.
  @Builder.Default
  protected String dataContentType = "application/json";

  // Describes the subject of the event in the context of the event producer (identified by source).
  protected String subject;

  // Date and time for when the message was published
  @NotNull
  @Builder.Default
  protected OffsetDateTime time = OffsetDateTime.now();

  // Payload
  @NotNull
  protected T data;

  // Base64 encoded event payload. Must adhere to RFC4648.
  @JsonProperty(value = "data_base64")
  protected String dataBase64;

  // Identifies the schema that data adheres to.
  protected ESchemaType dataSchema;

  // Unique id for the specific message. This id is globally unique
  @NotBlank
  protected UUID aggregateId;

  /**
   * Get Headers from event
   * @return
   */
  @JsonIgnore
  public Map<String, Object> getHeaders() {
    return new HashMap<>(Map.of("event-id", id,
      "event-timestamp", time.toString(),
      "event-type", type.getValue(),
      "event-version", type.getSchema().getValue()));
  }
}