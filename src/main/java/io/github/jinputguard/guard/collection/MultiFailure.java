package io.github.jinputguard.guard.collection;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.result.AbstractGuardFailure;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A failure that regroups multiple other failures (collection, etc.).
 */
public final class MultiFailure extends AbstractGuardFailure {

	private final @Nonnull List<GuardFailure> failures;

	public MultiFailure(List<GuardFailure> failures, Path path) {
		this(failures, path, null);
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
		return "multiple failures:\n"
			+ failures.stream().map(fail -> fail.getPath() + " -> " + fail.getMessage()).map(msg -> ("- " + msg).indent(2)).collect(Collectors.joining());
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