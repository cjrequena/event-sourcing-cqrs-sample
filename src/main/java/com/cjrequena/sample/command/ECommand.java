package com.cjrequena.sample.command;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
public enum ECommand {

  CREATE_ACCOUNT_COMMAND("CreateAccountCommand"),
  CREDIT_ACCOUNT_COMMAND("CreditAccountCommand"),
  DEBIT_ACCOUNT_COMMAND("DebitAccountCommand");

  /** The code. */
  private final String code;

  /**
   * Instantiates a new board base segmentType.
   *
   * @param code the code
   */
  ECommand(String code) {
    this.code = code;
  }

  /**
   * Gets the value.
   *
   * @param code the code
   * @return the value
   */
  public static ECommand getValue(String code) {
    for (ECommand v : values()) {
      if (code.equals(v.getCode())) {
        return v;
      }
    }
    return null;
  }

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }
}
