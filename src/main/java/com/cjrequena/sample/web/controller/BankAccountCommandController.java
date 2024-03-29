package com.cjrequena.sample.web.controller;

import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.CreditBankAccountCommand;
import com.cjrequena.sample.command.DebitBankAccountCommand;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.CreditBankAccountDTO;
import com.cjrequena.sample.dto.DebitBankAccountDTO;
import com.cjrequena.sample.exception.controller.BadRequestControllerException;
import com.cjrequena.sample.exception.controller.NotFoundControllerException;
import com.cjrequena.sample.exception.service.AggregateVersionServiceException;
import com.cjrequena.sample.exception.service.BankAccountNotFoundServiceException;
import com.cjrequena.sample.service.BankAccountCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.cjrequena.sample.common.Constant.VND_SAMPLE_SERVICE_V1;
import static org.apache.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
@SuppressWarnings("unchecked")
@Slf4j
@RestController
@RequestMapping(value = "/event-sourcing-cqrs-sample")
public class BankAccountCommandController {

  private BankAccountCommandService bankAccountCommandService;

  @Autowired
  public BankAccountCommandController(BankAccountCommandService bankAccountCommandService) {
    this.bankAccountCommandService = bankAccountCommandService;
  }

  @Operation(
    summary = "Command for new bank account creation ",
    description = "Command for new bank account creation ",
    parameters = {
      @Parameter(
        name = "accept-version",
        required = true,
        in = ParameterIn.HEADER,
        schema = @Schema(
          name = "accept-version",
          type = "string",
          allowableValues = {VND_SAMPLE_SERVICE_V1}
        )
      ),
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BankAccountDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "202", description = "Accepted - The request has been accepted for processing, but the processing has not been completed. The request might or might not eventually be acted upon, as it might be disallowed when processing actually takes place.."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PostMapping(
    path = "/bank-accounts",
    produces = {APPLICATION_JSON_VALUE}
  )
  public ResponseEntity<Void> createAccount(@Parameter @Valid @RequestBody BankAccountDTO dto, BindingResult bindingResult, HttpServletRequest request, UriComponentsBuilder ucBuilder) throws NotFoundControllerException, BadRequestControllerException {
    try {
      CreateBankAccountCommand createBankAccountCommand = CreateBankAccountCommand.builder().bankAccountDTO(dto).build();
      this.bankAccountCommandService.handler(createBankAccountCommand);
      // Headers
      HttpHeaders headers = new HttpHeaders();
      headers.set(CACHE_CONTROL, "no store, private, max-age=0");
      headers.set("account-id", createBankAccountCommand.getData().getAccountId().toString());
      return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    } catch (BankAccountNotFoundServiceException ex) {
      throw new NotFoundControllerException();
    } catch (AggregateVersionServiceException ex) {
      throw new BadRequestControllerException(ex.getMessage());
    }
  }

  @Operation(
    summary = "Command for bank account credit operation ",
    description = "Command for bank account credit operation ",
    parameters = {
      @Parameter(
        name = "accept-version",
        required = true,
        in = ParameterIn.HEADER,
        schema = @Schema(
          name = "accept-version",
          type = "string", allowableValues = {VND_SAMPLE_SERVICE_V1}
        )
      ),
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditBankAccountDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "202", description = "Accepted - The request has been accepted for processing, but the processing has not been completed. The request might or might not eventually be acted upon, as it might be disallowed when processing actually takes place.."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PostMapping(path = "/bank-accounts/credit", produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> creaditAccount(@RequestBody CreditBankAccountDTO dto, BindingResult bindingResult,
    HttpServletRequest request, UriComponentsBuilder ucBuilder) throws NotFoundControllerException, BadRequestControllerException {
    try {
      CreditBankAccountCommand creditBankAccountCommand = CreditBankAccountCommand.builder().creditBankAccountDTO(dto).build();
      this.bankAccountCommandService.handler(creditBankAccountCommand);
      // Headers
      HttpHeaders headers = new HttpHeaders();
      headers.set(CACHE_CONTROL, "no store, private, max-age=0");
      return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    } catch (BankAccountNotFoundServiceException ex) {
      throw new NotFoundControllerException();
    } catch (AggregateVersionServiceException ex) {
      throw new BadRequestControllerException(ex.getMessage());
    }
  }

  @Operation(
    summary = "Command for bank account debit operation ",
    description = "Command for bank account debit operation ",
    parameters = {
      @Parameter(name = "accept-version", required = true, in = ParameterIn.HEADER, schema = @Schema(name = "accept-version", type = "string", allowableValues = {
        VND_SAMPLE_SERVICE_V1})),
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DebitBankAccountDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "202", description = "Accepted - The request has been accepted for processing, but the processing has not been completed. The request might or might not eventually be acted upon, as it might be disallowed when processing actually takes place.."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PostMapping(path = "bank-accounts/debit", produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> debitAccount(@RequestBody DebitBankAccountDTO dto, BindingResult bindingResult,
    HttpServletRequest request, UriComponentsBuilder ucBuilder) throws NotFoundControllerException, BadRequestControllerException {
    try {
      DebitBankAccountCommand debitBankAccountCommand = DebitBankAccountCommand.builder().debitBankAccountDTO(dto).build();
      this.bankAccountCommandService.handler(debitBankAccountCommand);
      // Headers
      HttpHeaders headers = new HttpHeaders();
      headers.set(CACHE_CONTROL, "no store, private, max-age=0");
      return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    } catch (BankAccountNotFoundServiceException ex) {
      throw new NotFoundControllerException();
    } catch (AggregateVersionServiceException ex) {
      throw new BadRequestControllerException(ex.getMessage());
    }
  }
}
