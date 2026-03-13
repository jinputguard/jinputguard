package io.github.jinputguard;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * A guard failure.
 * 
 *
 */
public interface GuardFailure {

	/**
	 * The path where the failure happened.
	 * @return
	 */
	@Nonnull
	String getPath();

	/**
	 * The failure message.
	 * @return
	 */
	@Nonnull
	String getMessage();

	/**
	 * The cause of the failure, <code>null</code> if none.
	 * @return
	 */
	@Nullable
	Throwable getCause();

}
