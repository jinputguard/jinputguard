package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public sealed abstract class GuardFailure implements Serializable
	permits ValidationFailure, MultiFailure, MappingFailure {

	protected final Object value;
	protected final Path path;
	protected final Throwable cause;

	protected GuardFailure(@Nullable Object value, @Nonnull Path path, @Nullable Throwable cause) {
		this.value = value;
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.cause = cause;
	}

	public Object getValue() {
		return value;
	}

	public Path getPath() {
		return path;
	}

	public InputGuardFailureException toException() {
		return new InputGuardFailureException(this, cause);
	}

	public abstract String getMessage();

	public abstract GuardFailure atPath(Path superPath);

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":\n"
			+ "  - Path: " + path + "\n"
			+ "  - Message: " + getMessage();
	}

}
