package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public final class MappingFailure extends GuardFailure {

	public MappingFailure(@Nullable Throwable cause) {
		this(Path.root(), cause);
	}

	protected MappingFailure(@Nonnull Path path, @Nullable Throwable cause) {
		super(path, cause);
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
		return new MappingFailure(path.atPath(superPath), cause);
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