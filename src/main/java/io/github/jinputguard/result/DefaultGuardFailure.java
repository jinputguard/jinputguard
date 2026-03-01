package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.result.errors.ErrorDetails;
import io.github.jinputguard.result.errors.WithCause;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public class DefaultGuardFailure implements GuardFailure {

	private final Path path;
	private final ErrorDetails message;
	private final Throwable cause;

	public DefaultGuardFailure(@Nonnull Path path, @Nonnull ErrorDetails message) {
		this(path, message, null);
	}

	public DefaultGuardFailure(@Nonnull Path path, @Nonnull ErrorDetails details, @Nullable Throwable cause) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.message = Objects.requireNonNull(details, "details cannot be null");
		this.cause = determineCause(details, cause);
	}

	@Nullable
	private static Throwable determineCause(@Nonnull ErrorDetails details, @Nullable Throwable cause) {
		if (details instanceof WithCause wCause) {
			if (cause == null) {
				return wCause.cause();
			}
			cause.addSuppressed(wCause.cause());
		}
		return cause;
	}

	@Override
	public String getPath() {
		return path.format();
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
		return "GuardFailure:\n"
			+ "  - Path: " + getPath() + "\n"
			+ "  - Message: " + getMessage() + "\n"
			+ "  - Cause: " + (cause == null ? "" : cause.getMessage());
	}

}
