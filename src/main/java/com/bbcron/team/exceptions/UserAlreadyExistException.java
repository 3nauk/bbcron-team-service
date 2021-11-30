package com.bbcron.team.exceptions;

public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException() {
    super();
  }

  public UserAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public UserAlreadyExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyExistException(String message) {
    super(message);
  }

  public UserAlreadyExistException(Throwable cause) {
    super(cause);
  }

  /**
   *
   */
  private static final long serialVersionUID = 1L;

}
