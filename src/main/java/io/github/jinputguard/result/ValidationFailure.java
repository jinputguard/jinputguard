package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public final class ValidationFailure extends GuardFailure {

	private final @Nonnull ValidationError error;

	public ValidationFailure(@Nullable Object value, @Nonnull ValidationError error) {
		this(value, error, Path.root(), null);
	}

	protected ValidationFailure(@Nullable Object value, @Nonnull ValidationError error, Path path, Throwable cause) {
		super(value, path, cause);
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
	public ValidationFailure atPath(Path superPath) {
		return new ValidationFailure(value, error, path.atPath(superPath), cause);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, path, error);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ValidationFailure other) {
			return Objects.equals(this.value, other.value)
				&& Objects.equals(this.path, other.path)
				&& Objects.equals(this.cause, other.cause)
				&& Objects.equals(this.error, other.error);
		}
		return false;
	}

}