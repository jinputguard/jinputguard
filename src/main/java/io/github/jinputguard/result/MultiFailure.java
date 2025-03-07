package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A failure that regroups multiple other failures (collection, etc.).
 */
public final class MultiFailure extends GuardFailure {

	private final @Nonnull List<GuardFailure> failures;

	public MultiFailure(@Nullable Object value, List<GuardFailure> failures) {
		this(value, failures, Path.root());
	}

	protected MultiFailure(@Nullable Object value, List<GuardFailure> failures, Path path) {
		super(value, path);
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
		return new MultiFailure(value, newFailures, path.atPath(superPath));
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, path, failures);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MultiFailure other) {
			return Objects.equals(this.value, other.value)
				&& Objects.equals(this.path, other.path)
				&& Objects.equals(this.failures, other.failures);
		}
		return false;
	}

}