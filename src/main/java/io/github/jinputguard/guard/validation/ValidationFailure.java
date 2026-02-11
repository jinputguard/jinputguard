package io.github.jinputguard.guard.validation;

import io.github.jinputguard.result.AbstractGuardFailure;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import java.util.Objects;

public final class ValidationFailure extends AbstractGuardFailure {

	private final @Nonnull ValidationError error;

	public ValidationFailure(@Nonnull ValidationError error, Path path) {
		this(error, path, null);
	}

	public ValidationFailure(@Nonnull ValidationError error, Path path, Throwable cause) {
		super(path, cause);
		this.error = Objects.requireNonNull(error, "Validation error cannot be null");
	}

	public ValidationError getError() {
		return error;
	}

	@Override
	public String getMessage() {
		return "Invalid " + path.format() + ": " + error.getConstraintMessage();
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, error);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ValidationFailure other) {
			return Objects.equals(this.path, other.path)
				&& Objects.equals(this.cause, other.cause)
				&& Objects.equals(this.error, other.error);
		}
		return false;
	}

}