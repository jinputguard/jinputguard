package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public abstract class AbstractGuardFailure implements GuardFailure {

	protected final Path path;
	protected final Throwable cause;

	protected AbstractGuardFailure(@Nonnull Path path, @Nullable Throwable cause) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.cause = cause;
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public abstract String getMessage();

	@Override
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
