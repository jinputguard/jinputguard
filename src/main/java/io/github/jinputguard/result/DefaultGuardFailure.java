package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.result.errors.ErrorDetails;
import io.github.jinputguard.result.errors.WithEmbeddedCause;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public class DefaultGuardFailure implements GuardFailure {

	private final String path;
	private final ErrorDetails message;
	private final Throwable cause;

	public DefaultGuardFailure(@Nonnull String path, @Nonnull ErrorDetails message) {
		this(path, message, null);
	}

	public DefaultGuardFailure(@Nonnull String path, @Nonnull ErrorDetails details, @Nullable Throwable cause) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.message = Objects.requireNonNull(details, "details cannot be null");
		this.cause = determineCause(details, cause);
	}

	@Nullable
	private static Throwable determineCause(@Nonnull ErrorDetails details, @Nullable Throwable cause) {
		if (details instanceof WithEmbeddedCause wCause) {
			if (cause == null) {
				return wCause.cause();
			}
			cause.addSuppressed(wCause.cause());
		}
		return cause;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public String getMessage() {
		return message.getMessage(path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cause, message, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof DefaultGuardFailure other) {
			return Objects.equals(cause, other.cause) && Objects.equals(message, other.message) && Objects.equals(path, other.path);
		}
		return false;
	}

}
