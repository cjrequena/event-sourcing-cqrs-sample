package com.cjrequena.sample.dto.event;

import com.cjrequena.sample.dto.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
  "metadata",
  "payload"
})
@JsonTypeName("eventDTO")
public class EventDTO<T> implements DTO, Serializable {

  /**
   *
   */
  @JsonProperty(value = "metadata", required = true)
  @NotNull(message = "Metadata is mandatory")
  @Getter(onMethod = @__({@JsonProperty(value = "metadata", required = true)}))
  private EventMetaDataDTO metadata;

  /**
   *
   */
  @JsonProperty(value = "payload", required = true)
  @NotNull(message = "Payload is mandatory")
  @Getter(onMethod = @__({@JsonProperty(value = "payload", required = true)}))
  private T payload;
}
