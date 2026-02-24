package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public class DefaultGuardFailure implements GuardFailure {

	private final Path path;
	private final ErrorMessage message;
	private final Throwable cause;

	protected DefaultGuardFailure(@Nonnull Path path, @Nonnull ErrorMessage message, @Nullable Throwable cause) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.message = Objects.requireNonNull(message, "message cannot be null");
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
	public String getMessage() {
		return message.getMessage();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":\n"
			+ "  - Path: " + path + "\n"
			+ "  - Message: " + message + "\n"
			+ "  - Cause: " + (cause == null ? "" : cause.getMessage());
	}

}
