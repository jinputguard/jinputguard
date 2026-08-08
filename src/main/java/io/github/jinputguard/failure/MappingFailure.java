package io.github.jinputguard.failure;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public class MappingFailure implements GuardFailure {

	private static final String MESSAGE_TEMPLATE = "Invalid %s";
	private static final String MESSAGE_WITH_CAUSE_TEMPLATE = "Invalid %s: %s";

	private final String path;
	private final String message;
	private final Throwable cause;

	public MappingFailure(@Nonnull String path) {
		this(path, null);
	}

	public MappingFailure(@Nonnull String path, @Nullable Throwable cause) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.message = cause == null
			? MESSAGE_TEMPLATE.formatted(path)
			: MESSAGE_WITH_CAUSE_TEMPLATE.formatted(path, cause.getMessage());
		this.cause = cause;
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
		return message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cause, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MappingFailure other) {
			return Objects.equals(path, other.path) && Objects.equals(message, other.message) && Objects.equals(cause, other.cause);
		}
		return false;
	}

}
