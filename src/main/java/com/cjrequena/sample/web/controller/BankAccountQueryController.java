package com.cjrequena.sample.web.controller;

import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.service.BankAccountQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.cjrequena.sample.common.Constant.VND_SAMPLE_SERVICE_V1;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Slf4j
@RestController
@RequestMapping(value = "/event-sourcing-cqrs-sample", headers = {"Accept-Version=" + VND_SAMPLE_SERVICE_V1})
@Tag(name = "Bank Account Queries", description = "Bank Account Query Events API")
@AllArgsConstructor
public class BankAccountQueryController {

  private final BankAccountQueryService bankAccountQueryService;

  @Operation(
    summary = "Retrieve bank account by account_id ",
    description = "Retrieve bank account by account_id ",
    parameters = {
      @Parameter(name = "accept-version", required = true, in = ParameterIn.HEADER, schema = @Schema(name = "accept-version", type = "string", allowableValues = {
        VND_SAMPLE_SERVICE_V1}))
    }
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "Created - The request was successful, we created a new resource and the response body contains the representation."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @GetMapping(path = "/bank-accounts/{accountId}", produces = {APPLICATION_JSON_VALUE})
  public BankAccountDTO retrieveById(@PathVariable("accountId") UUID accountId) {
    return this.bankAccountQueryService.retrieveById(accountId);
  }

}
