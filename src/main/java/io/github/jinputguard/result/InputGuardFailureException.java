package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public class InputGuardFailureException extends IllegalArgumentException {

	@Nonnull
	private final GuardFailure failure;

	public InputGuardFailureException(@Nonnull GuardFailure failure, @Nullable Throwable cause) {
		super(Objects.requireNonNull(failure, "failure cannot be null").getMessage(), cause);
		this.failure = Objects.requireNonNull(failure, "failure cannot be null");
	}

	public GuardFailure getFailure() {
		return failure;
	}

}
