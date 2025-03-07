package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public final class MappingFailure extends GuardFailure {

	private final Throwable cause;

	public MappingFailure(@Nullable Object value, @Nullable Throwable cause) {
		this(value, Path.root(), cause);
	}

	protected MappingFailure(@Nullable Object value, @Nonnull Path path, @Nullable Throwable cause) {
		super(value, path);
		this.cause = cause;
	}

	@Override
	public InputGuardFailureException toException() {
		return new InputGuardFailureException(this, cause);
	}

	@Override
	public String getMessage() {
		return "Mapping failed for " + path.format();
	}

	public Throwable getCause() {
		return cause;
	}

	@Override
	public MappingFailure atPath(Path superPath) {
		return new MappingFailure(value, path.atPath(superPath), cause);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, path, cause);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MappingFailure other) {
			return Objects.equals(this.value, other.value)
				&& Objects.equals(this.path, other.path)
				&& Objects.equals(this.cause, other.cause);
		}
		return false;
	}

}