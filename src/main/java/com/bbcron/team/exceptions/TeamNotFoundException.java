package com.bbcron.team.exceptions;

/**
 * Exception UserNotFoundException
 */
public class TeamNotFoundException extends RuntimeException {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * UserNotFoundException
   */
  public TeamNotFoundException() {
    super();
  }

  /**
   * UserNotFoundException
   *
   * @param message message Exception
   */
  public TeamNotFoundException(String message) {
    super(message);
  }

  /**
   * UserNotFoundException
   *
   * @param message the message
   * @param cause the cause
   */
  public TeamNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * UserNotFoundException
   *
   * @param cause the cause
   */
  public TeamNotFoundException(Throwable cause) {
    super(cause);
  }

}
