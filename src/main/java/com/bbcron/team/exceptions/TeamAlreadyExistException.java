package com.bbcron.team.exceptions;

public class TeamAlreadyExistException extends RuntimeException {

  /**
	 *
	 */
	private static final long serialVersionUID = 1L;

public TeamAlreadyExistException() {
    super();
  }

  public TeamAlreadyExistException(String message) {
    super(message);
  }

  public TeamAlreadyExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public TeamAlreadyExistException(Throwable cause) {
    super(cause);
  }

  protected TeamAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
