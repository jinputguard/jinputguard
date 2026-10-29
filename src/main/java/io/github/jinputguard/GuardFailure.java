package io.github.jinputguard;

import io.github.jinputguard.failure.SimpleFailure;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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
	@Nullable
	Throwable getCause();

	/**
	 * Creates a simple guard failure with the specified path and message.
	 *
	 * @param path    the path where the failure occurred
	 * @param message the failure message
	 * @return a new instance of {@link GuardFailure}
	 */
	static GuardFailure from(@Nonnull String path, @Nonnull String message) {
		return new SimpleFailure(path, message);
	}

	/**
	 * Creates a simple guard failure with the specified path, message, and cause.
	 *
	 * @param path    the path where the failure occurred
	 * @param message the failure message
	 * @param cause   the cause of the failure
	 * @return a new instance of {@link GuardFailure}
	 */
	static GuardFailure from(@Nonnull String path, @Nonnull String message, @Nullable Throwable cause) {
		return new SimpleFailure(path, message, cause);
	}

}
