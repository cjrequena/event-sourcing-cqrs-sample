package com.cjrequena.sample.exception.service;

public class BankAccountNotFoundServiceException extends ServiceException {
  public BankAccountNotFoundServiceException(String message) {
    super(message);
  }
}
