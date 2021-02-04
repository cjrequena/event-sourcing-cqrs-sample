package com.cjrequena.sample.exception.controller;

import org.springframework.http.HttpStatus;

/**
 * Bad Filters Api Exception
 * @author manuel.flores
 * @created 2020-12-15
 */
public class BadFiltersControllerException extends ControllerException {
  public BadFiltersControllerException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
