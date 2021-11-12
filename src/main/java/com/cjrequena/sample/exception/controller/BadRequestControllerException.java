package com.cjrequena.sample.exception.controller;

import org.springframework.http.HttpStatus;

public class BadRequestControllerException extends ControllerException {
  public BadRequestControllerException() {
    super(HttpStatus.BAD_REQUEST);
  }

  public BadRequestControllerException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
