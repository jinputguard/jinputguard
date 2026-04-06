package io.github.jinputguard.result;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public final class MultiGuardFailure implements GuardFailure {

	private final String path;
	private final List<GuardFailure> failures;

	public MultiGuardFailure(@Nullable String path, List<GuardFailure> failures) {
		this.path = path;
		this.failures = Objects.requireNonNull(failures, "failures cannot be null");
	}

	public String getPath() {
		return path;
	}

	public List<GuardFailure> getFailures() {
		return failures;
	}

}
