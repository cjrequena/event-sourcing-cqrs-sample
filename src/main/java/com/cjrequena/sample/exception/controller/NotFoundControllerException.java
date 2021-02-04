package com.cjrequena.sample.exception.controller;

import org.springframework.http.HttpStatus;

public class NotFoundControllerException extends ControllerException {
  public NotFoundControllerException() {
    super(HttpStatus.NOT_FOUND);
  }
}
