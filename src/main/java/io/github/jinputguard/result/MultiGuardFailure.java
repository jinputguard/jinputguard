package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import java.util.List;
import java.util.Objects;

/**
 * A class representing the failure of a guard.
 */
public class MultiGuardFailure implements GuardFailure {

	private final String path;
	private final List<GuardFailure> failures;

	public MultiGuardFailure(String path, List<GuardFailure> failures) {
		this.path = Objects.requireNonNull(path, "path cannot be null");
		this.failures = List.copyOf(failures);
	}

	@Override
	public String getPath() {
		return path;
	}

	public List<GuardFailure> getFailures() {
		return failures;
	}

	@Override
	public String getMessage() {
		var buffer = new StringBuilder();
		buffer.append(path).append(" contains ").append(failures.size()).append(" illegal element").append(failures.size() == 1 ? "" : "s").append(":\n");
		var it = failures.iterator();
		while (it.hasNext()) {
			buffer.append("  - ").append(it.next().getMessage());
			if (it.hasNext()) {
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}

	@Override
	public Throwable getCause() {
		return null;
	}

}
