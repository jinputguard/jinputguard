package io.github.jinputguard;

import jakarta.annotation.Nonnull;
import java.util.Objects;

public class InputGuardFailureException extends IllegalArgumentException {

	@Nonnull
	private final GuardFailure failure;

	public InputGuardFailureException(@Nonnull GuardFailure failure) {
		super(buildExceptionMessage(failure), failure.getCause());
		this.failure = failure;
	}

	public GuardFailure getFailure() {
		return failure;
	}

	private static String buildExceptionMessage(@Nonnull GuardFailure failure) {
		Objects.requireNonNull(failure, "failure cannot be null");
		return failure.getPath().toString() + ": " + failure.getMessage();
	}

}
