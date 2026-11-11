package io.github.jinputguard;

import jakarta.annotation.Nonnull;

/**
 * Exception thrown when an input guard fails to validate an input.
 * 
 * @see GuardFailure
 * @see Guardresult
 */
public class InputGuardException extends IllegalArgumentException {

	@Nonnull
	private final GuardFailure failure;

	/**
	 * Constructs a new {@link InputGuardException} with the specified GuardFailure.
	 *
	 * @param failure the {@link GuardFailure} that caused this exception
	 */
	public InputGuardException(@Nonnull GuardFailure failure) {
		super(failure.getMessage(), failure.getCause());
		this.failure = failure;
	}

	/**
	 * Returns the {@link GuardFailure} associated with this exception.
	 *
	 * @return the {@link GuardFailure} that caused this exception
	 */
	public GuardFailure getFailure() {
		return failure;
	}

}
