package io.github.jinputguard;

import java.io.Serializable;

/**
 * Represents a failure that occurred during the process of an input by an {@link InputGuard}.
 * It contains information about the path where the failure happened, the failure message, and an optional cause.
 * 
 * @see InputGuard
 * @see InputGuardFailureException
 */
public interface GuardFailure extends Serializable {

	/**
	 * Gets the path where the failure occurred.
	 *
	 * @return the path of the failure
	 */
	String getPath();

	/**
	 * Gets the failure message describing what went wrong.
	 *
	 * @return the failure message
	 */
	String getMessage();

	/**
	 * Gets the cause of the failure, if any.
	 *
	 * @return the cause of the failure, or null if there is no cause
	 */
	Throwable getCause();

}
