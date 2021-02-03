package com.cjrequena.sample.dto.event;

import com.cjrequena.sample.dto.DTO;
import com.cjrequena.sample.dto.serializer.LocalDateTimeDeserializer;
import com.cjrequena.sample.dto.serializer.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(value = {
  "eventId",
  "eventType",
  "eventTimeStamp",
  "aggregateId",
  "traceId",
  "spamId"

})
@JsonTypeName("eventDTO")
public class EventMetaDataDTO implements DTO, Serializable {
  /**
   * eventId
   */
  @JsonProperty(value = "event_id", required = true)
  @NotNull(message = "Event ID is mandatory")
  @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", message = "Format is not valid")
  @Getter(onMethod = @__({@JsonProperty("event_id")}))
  private UUID eventId;

  /**
   * eventType
   */
  @JsonProperty(value = "event_type", required = true)
  @NotNull(message = "Event Type is mandatory")
  //@Pattern(regexp = "AccountCreatedEvent|AccountCreditedEvent|AccountDebitedEvent", message = "Value not accepted")
  @Getter(onMethod = @__({@JsonProperty("event_type")}))
  private String eventType;

  /**
   * eventTimeStamp
   */
  @JsonProperty(value = "event_time_stamp", required = true)
  @NotNull(message = "Event Timestamp is mandatory")
  @Pattern(regexp = "[0-9]{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))T(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])Z", message = "Format date time is not valid")
  @Getter(onMethod = @__({@JsonProperty(value = "event_time_stamp", required = true)}))
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime eventTimeStamp;

  /**
   * aggregateId
   */
  @JsonProperty(value = "aggregate_id", required = true)
  @NotNull(message = "Aggregate ID is mandatory")
  @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", message = "Format is not valid")
  @Getter(onMethod = @__({@JsonProperty("aggregate_id")}))
  private UUID aggregateId;

}
