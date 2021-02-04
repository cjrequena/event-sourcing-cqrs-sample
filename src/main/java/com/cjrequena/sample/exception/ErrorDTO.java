package com.cjrequena.sample.exception;

import com.cjrequena.sample.dto.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

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
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {
  "date",
  "errorCode",
  "message"
})
@JsonTypeName("error")
@XmlRootElement
public class ErrorDTO implements DTO {

  @JsonProperty(value = "date")
  @Getter(onMethod = @__({@JsonProperty("date")}))
  private String date;

  @JsonProperty(value = "error_code")
  @Getter(onMethod = @__({@JsonProperty("error_code")}))
  private String errorCode;

  @JsonProperty(value = "message")
  @Getter(onMethod = @__({@JsonProperty("message")}))
  private String message;

}
