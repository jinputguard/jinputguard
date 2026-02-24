package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import java.util.Objects;

public class InputGuardFailureException extends IllegalArgumentException {

	@Nonnull
	private final GuardFailure failure;

	public InputGuardFailureException(@Nonnull GuardFailure failure) {
		super(Objects.requireNonNull(failure, "failure cannot be null").getMessage(), failure.getCause());
		this.failure = failure;
	}

	public GuardFailure getFailure() {
		return failure;
	}

}
