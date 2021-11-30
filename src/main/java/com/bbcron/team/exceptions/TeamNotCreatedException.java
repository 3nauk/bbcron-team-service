package com.bbcron.team.exceptions;

import javax.ws.rs.core.Response;

/**
 * Exception UserNotFoundException
 */
public class TeamNotCreatedException extends RuntimeException {

  /**
	 *
	 */
	private static final long serialVersionUID = 1L;

/**
   * UserNotFoundException
   */
  public TeamNotCreatedException() {
    super();
  }

  /**
   * UserNotFoundException
   *
   * @param message message Exception
   */
  public TeamNotCreatedException(String message) {
    super(message);
  }

  /**
   * UserNotFoundException
   *
   * @param message the message
   * @param cause   the cause
   */
  public TeamNotCreatedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * UserNotFoundException
   *
   * @param cause the cause
   */
  public TeamNotCreatedException(Throwable cause) {
    super(cause);
  }

  public TeamNotCreatedException(Response response) {
    super("Error Created User " + response.getStatus() + ":" + response.toString());
  }

}
