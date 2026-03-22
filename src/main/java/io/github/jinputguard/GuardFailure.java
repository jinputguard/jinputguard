package io.github.jinputguard;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Represents a failure that occurred during the process of an input by an {@link InputGuard}.
 * It contains information about the path where the failure happened, the failure message, and an optional cause.
 * 
 * @see InputGuard
 * @see InputGuardFailureException
 */
public interface GuardFailure {

	/**
	 * The path where the failure occurred, represented as a string.
	 * This can be used to identify the specific part of the input that caused the failure.
	 * @return
	 */
	@Nonnull
	String getPath();

	/**
	 * The failure message, describing the reason for the failure.
	 * @return
	 */
	@Nonnull
	String getMessage();

	/**
	 * The cause of the failure, if any.
	 * @return
	 */
	@Nullable
	Throwable getCause();

}
