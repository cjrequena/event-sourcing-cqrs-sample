package com.cjrequena.sample.event;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
public enum EEvent {

  ACCOUNT_CREATED_EVENT("AccountCreatedEvent"),
  ACCOUNT_CREDITED_EVENT("AccountCreditedEvent"),
  ACCOUNT_DEBITED_EVENT("AccountDebitedEvent");


  /** The code. */
  private final String code;

  /**
   * Instantiates a new board base segmentType.
   *
   * @param code the code
   */
  EEvent(String code) {
    this.code = code;
  }

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Gets the value.
   *
   * @param code the code
   * @return the value
   */
  public static EEvent getValue(String code) {
    for (EEvent v : values()) {
      if (code.equals(v.getCode())) {
        return v;
      }
    }
    return null;
  }
}
