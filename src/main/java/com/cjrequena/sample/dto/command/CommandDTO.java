package com.cjrequena.sample.dto.command;

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
  "aggregateId",
  "eventType",
  "eventVersion",
  "eventTimeStamp",
  "producedBy",
  "producedByVersion"
})
@JsonTypeName("eventDTO")
public class CommandDTO<T> implements DTO, Serializable {
  /**
   * eventId
   */
  @JsonProperty(value = "event_id", required = true)
  @NotNull(message = "Event ID is mandatory")
  @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", message = "Format is not valid")
  @Getter(onMethod = @__({@JsonProperty("event_id")}))
  private UUID commandId;

  /**
   * aggregateId
   */
  @JsonProperty(value = "aggregate_id", required = true)
  @NotNull(message = "Aggregate ID is mandatory")
  @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", message = "Format is not valid")
  @Getter(onMethod = @__({@JsonProperty("aggregate_id")}))
  private UUID aggregateId;

  /**
   * eventType
   */
  @JsonProperty(value = "event_type", required = true)
  @NotNull(message = "Event Type is mandatory")
  //@Pattern(regexp = "CreateAccountCommand|CreditAccountCommand|DebitAccountCommand", message = "Value not accepted")
  @Getter(onMethod = @__({@JsonProperty("event_type")}))
  private String commandType;

  /**
   * eventVersion
   */
  @JsonProperty(value = "event_version", required = true)
  @NotNull(message = "Event Version is mandatory")
  @Getter(onMethod = @__({@JsonProperty("event_version")}))
  private String eventVersion;

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
   * producedBy
   */
  @JsonProperty(value = "produced_by", required = true)
  @NotNull(message = "Produced by is mandatory")
  @Getter(onMethod = @__({@JsonProperty(value = "produced_by", required = true)}))
  private String producedBy;

  /**
   * producedByVersion
   */
  @JsonProperty(value = "produced_by_version", required = true)
  @NotNull(message = "Produced by version is mandatory")
  @Getter(onMethod = @__({@JsonProperty(value = "produced_by_version", required = true)}))
  private String producedByVersion;

  /**
   * producedByVersion
   */
  @JsonProperty(value = "payload", required = true)
  @NotNull(message = "Payload is mandatory")
  @Getter(onMethod = @__({@JsonProperty(value = "payload", required = true)}))
  private T payload;
}
