package io.github.jinputguard;

import jakarta.annotation.Nonnull;

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
		super(failure.getMessage(), failure.getCause());
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

}
