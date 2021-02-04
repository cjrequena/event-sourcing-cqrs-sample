package com.cjrequena.sample.exception.controller;

import org.springframework.http.HttpStatus;

public class NotAcceptableControllerException extends ControllerException {
  public NotAcceptableControllerException() {
    super(HttpStatus.NOT_ACCEPTABLE);
  }
}
