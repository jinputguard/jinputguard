package io.github.jinputguard.guard.mapping;

import io.github.jinputguard.result.AbstractGuardFailure;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public final class MappingFailure extends AbstractGuardFailure {

	public MappingFailure(@Nonnull Path path, @Nullable Throwable cause) {
		super(path, cause);
	}

	@Override
	public String getMessage() {
		return "Mapping failed for " + path.format();
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, cause);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MappingFailure other) {
			return Objects.equals(this.path, other.path)
				&& Objects.equals(this.cause, other.cause);
		}
		return false;
	}

}