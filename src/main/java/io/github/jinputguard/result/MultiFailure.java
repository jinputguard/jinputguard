package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A failure that regroups multiple other failures (collection, etc.).
 */
public final class MultiFailure extends DefaultGuardFailure {

	private final @Nonnull List<GuardFailure> failures;

	public MultiFailure(List<GuardFailure> failures) {
		this(failures, Path.root(), null);
	}

	protected MultiFailure(List<GuardFailure> failures, Path path, Throwable cause) {
		super(path, cause);
		this.failures = Objects.requireNonNull(failures, "failures cannot be null");
	}

	public List<GuardFailure> getFailures() {
		return failures;
	}

	@Override
	public String getMessage() {
		return "Multiple failures while processing " + path.format() + ":\n"
			+ failures.stream().map(GuardFailure::getMessage).map(msg -> ("- " + msg).indent(2)).collect(Collectors.joining());
	}

	@Override
	public MultiFailure atPath(Path superPath) {
		var newFailures = failures.stream().map(failure -> failure.atPath(superPath)).toList();
		return new MultiFailure(newFailures, path.atPath(superPath), cause);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, failures);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MultiFailure other) {
			return Objects.equals(this.path, other.path)
				&& Objects.equals(this.cause, other.cause)
				&& Objects.equals(this.failures, other.failures);
		}
		return false;
	}

}