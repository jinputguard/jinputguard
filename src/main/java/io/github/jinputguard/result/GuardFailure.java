package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public abstract class GuardFailure implements Serializable {

	protected final Object value;
	protected final Path path;

	protected GuardFailure(@Nullable Object value, @Nonnull Path path) {
		this.value = value;
		this.path = Objects.requireNonNull(path, "path cannot be null");
	}

	public Object getValue() {
		return value;
	}

	public Path getPath() {
		return path;
	}

	public InputGuardFailureException toException() {
		return new InputGuardFailureException(this);
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
