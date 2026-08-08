package io.github.jinputguard.failure;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public class SimpleFailure implements GuardFailure {

	private final String path;
	private final String message;
	private final Throwable cause;

	public SimpleFailure(@Nonnull String path, @Nonnull String message) {
		this(path, message, null);
	}

	public SimpleFailure(@Nonnull String path, @Nonnull String message, @Nullable Throwable cause) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.message = Objects.requireNonNull(message, "message cannot be null");
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
		return Objects.hash(cause, message, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof SimpleFailure other) {
			return Objects.equals(cause, other.cause) && Objects.equals(message, other.message) && Objects.equals(path, other.path);
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + SimpleFailure.class.getSimpleName() + "] path: " + path + ", message: " + getMessage() + (getCause() != null ? ", caused by: " + getCause() : "");
	}

}
