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
  "aggregateId",
  "eventType",
  "eventVersion",
  "eventTimeStamp",
  "producedBy",
  "producedByVersion"
})
@JsonTypeName("eventDTO")
public class EventDTO<T> implements DTO, Serializable {
  /**
   * eventId
   */
  @JsonProperty(value = "eventId", required = true)
  @NotNull(message = "Event ID is mandatory")
  @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", message = "Format is not valid")
  @Getter(onMethod = @__({@JsonProperty("eventId")}))
  private UUID eventId;

  /**
   * aggregateId
   */
  @JsonProperty(value = "aggregateId", required = true)
  @NotNull(message = "Aggregate ID is mandatory")
  @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", message = "Format is not valid")
  @Getter(onMethod = @__({@JsonProperty("aggregateId")}))
  private UUID aggregateId;

  /**
   * eventType
   */
  @JsonProperty(value = "eventType", required = true)
  @NotNull(message = "Event Type is mandatory")
  @Pattern(regexp = "SourcedTransferUnitCreated|SourcedTransferUnitChanged|SourcedTransferUnitRetired", message = "Value not accepted")
  @Getter(onMethod = @__({@JsonProperty("eventType")}))
  private String eventType;

  /**
   * eventVersion
   */
  @JsonProperty(value = "eventVersion", required = true)
  @NotNull(message = "Event Version is mandatory")
  @Getter(onMethod = @__({@JsonProperty("eventVersion")}))
  private String eventVersion;

  /**
   * eventTimeStamp
   */
  @JsonProperty(value = "eventTimeStamp", required = true)
  @NotNull(message = "Event Timestamp is mandatory")
  @Pattern(regexp = "[0-9]{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-1]))T(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])Z", message = "Format date time is not valid")
  @Getter(onMethod = @__({@JsonProperty("eventTimeStamp")}))
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime eventTimeStamp;

  /**
   * producedBy
   */
  @JsonProperty(value = "producedBy", required = true)
  @NotNull(message = "Produced by is mandatory")
  @Getter(onMethod = @__({@JsonProperty("producedBy")}))
  private String producedBy;

  /**
   * producedByVersion
   */
  @JsonProperty(value = "producedByVersion", required = true)
  @NotNull(message = "Produced by version is mandatory")
  @Getter(onMethod = @__({@JsonProperty("producedByVersion")}))
  private String producedByVersion;

  /**
   * producedByVersion
   */
  @JsonProperty(value = "payload", required = true)
  @NotNull(message = "Payload is mandatory")
  @Getter(onMethod = @__({@JsonProperty("payload")}))
  private T payload;
}
