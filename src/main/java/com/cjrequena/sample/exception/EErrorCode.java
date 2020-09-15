package com.cjrequena.sample.exception;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
public enum EErrorCode {

  NOT_CONTENT_ERROR("ERROR-000204"),
  NOT_MODIFIED_ERROR("ERROR-000304"),
  BAD_REQUEST_ERROR("ERROR-000400"),
  NOT_FOUND_ERROR("ERROR-000404"),
  CONFLICT_ERROR("ERROR-000409"),
  INTERNAL_SERVER_ERROR("ERROR-000500");

  private String errorCode;

  /**
   *
   * @param errorCode
   */
  EErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   *
   * @return
   */
  public String getErrorCode() {
    return this.errorCode;
  }

}


