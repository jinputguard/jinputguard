package io.github.jinputguard;

import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.GuardFailure;
import io.github.jinputguard.result.GuardFailureMessageFactory;
import jakarta.annotation.Nonnull;
import java.util.Objects;

/**
 * Exception thrown when an input guard fails to validate an input.
 * 
 * @see GuardFailure
 * @see Guardresult
 */
public class InputGuardFailureException extends IllegalArgumentException {

	@Nonnull
	private final GuardFailure failure;

	/**
	 * Constructs a new InputGuardFailureException with the specified GuardFailure.
	 *
	 * @param failure the GuardFailure that caused this exception
	 */
	public InputGuardFailureException(@Nonnull GuardFailure failure) {
		super(buildExceptionMessage(failure), buildCause(failure));
		this.failure = failure;
	}

	/**
	 * Returns the GuardFailure associated with this exception.
	 *
	 * @return the GuardFailure that caused this exception
	 */
	public GuardFailure getFailure() {
		return failure;
	}

	private static String buildExceptionMessage(@Nonnull GuardFailure failure) {
		Objects.requireNonNull(failure, "failure cannot be null");
		return GuardFailureMessageFactory.getInstance().buildMessage(failure);
	}

	private static Throwable buildCause(@Nonnull GuardFailure failure) {
		return failure instanceof DefaultGuardFailure defaultGuardFailure ? defaultGuardFailure.getCause() : null;
	}

}
