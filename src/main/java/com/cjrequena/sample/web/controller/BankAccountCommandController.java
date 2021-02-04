package com.cjrequena.sample.web.controller;

import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.MoneyAmountDTO;
import com.cjrequena.sample.service.BankAccountCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

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
@RequestMapping(value = "/event-sourcing-cqrs-sample", headers = {"Accept-Version=" + VND_SAMPLE_SERVICE_V1})
@Tag(name = "Bank Account Commands", description = "Bank Account Commands API")
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
      @Parameter(name = "accept-version", required = true, in = ParameterIn.HEADER, schema = @Schema(name = "accept-version", type = "string", allowableValues = {
        VND_SAMPLE_SERVICE_V1})),
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BankAccountDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "201", description = "Created - The request was successful, we created a new resource and the response body contains the representation."),
      @ApiResponse(responseCode = "204", description = "No Content - The request was successful, we created a new resource and the response body does not contains the representation."),
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
  public ResponseEntity<Void> create(@Parameter @Valid @RequestBody BankAccountDTO dto, BindingResult bindingResult, HttpServletRequest request, UriComponentsBuilder ucBuilder)  {
    //--
    //
    dto = bankAccountCommandService.createAccount(dto);
    // Headers
    HttpHeaders headers = new HttpHeaders();
    headers.set(CACHE_CONTROL, "no store, private, max-age=0");
    headers.setLocation(ucBuilder.path(new StringBuilder().append(request.getServletPath()).append("/{account_id}").toString()).buildAndExpand(dto.getAccountId()).toUri());
    //
    return new ResponseEntity<>(headers, HttpStatus.CREATED);
    //---
  }

  @Operation(
    summary = "Command for bank account credit operation ",
    description = "Command for bank account credit operation ",
    parameters = {
      @Parameter(name = "accept-version", required = true, in = ParameterIn.HEADER, schema = @Schema(name = "accept-version", type = "string", allowableValues = {
        VND_SAMPLE_SERVICE_V1})),
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MoneyAmountDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "204", description = "No Content - The request was successful, we created a new resource and the response body does not contains the representation."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PutMapping(path = "/{account_id}/credit", produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> creditMoneyToAccount(@PathVariable(value = "account_id") UUID accountId, @RequestBody MoneyAmountDTO dto, BindingResult bindingResult,
    HttpServletRequest request, UriComponentsBuilder ucBuilder){
    this.bankAccountCommandService.creditMoneyToAccount(accountId, dto);
    // Headers
    HttpHeaders headers = new HttpHeaders();
    headers.set(CACHE_CONTROL, "no store, private, max-age=0");
    headers.setLocation(ucBuilder.path(new StringBuilder().append(request.getServletPath()).append("/{account_id}").toString()).buildAndExpand(accountId).toUri());
    //
    return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
  }

  @Operation(
    summary = "Command for bank account debit operation ",
    description = "Command for bank account debit operation ",
    parameters = {
      @Parameter(name = "accept-version", required = true, in = ParameterIn.HEADER, schema = @Schema(name = "accept-version", type = "string", allowableValues = {
        VND_SAMPLE_SERVICE_V1})),
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MoneyAmountDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "204", description = "No Content - The request was successful, we created a new resource and the response body does not contains the representation."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PutMapping(path = "/{account_id}/debit", produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> debitMoneyFromAccount(@PathVariable(value = "account_id") UUID accountId, @RequestBody MoneyAmountDTO dto, BindingResult bindingResult,
    HttpServletRequest request, UriComponentsBuilder ucBuilder) {
    this.bankAccountCommandService.debitMoneyFromAccount(accountId, dto);
    // Headers
    HttpHeaders headers = new HttpHeaders();
    headers.set(CACHE_CONTROL, "no store, private, max-age=0");
    headers.setLocation(ucBuilder.path(new StringBuilder().append(request.getServletPath()).append("/{account_id}").toString()).buildAndExpand(accountId).toUri());
    //
    return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
  }
}
