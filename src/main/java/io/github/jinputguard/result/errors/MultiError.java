package io.github.jinputguard.result.errors;

import io.github.jinputguard.GuardFailure;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultiError implements ErrorDetails {

	private final @Nonnull List<GuardFailure> failures;

	public MultiError(List<GuardFailure> failures) {
		Objects.requireNonNull(failures, "failures cannot be null");
		if (failures.isEmpty()) {
			throw new IllegalArgumentException("Failure list is empty");
		}
		this.failures = failures;
	}

	@Override
	public String getMessage() {
		return "multiple errors:\n"
			+ failures.stream().map(this::getSubMessage).collect(Collectors.joining());
	}

	private String getSubMessage(GuardFailure fail) {
		var line = "- " + fail.getPath() + " -> " + fail.getMessage();
		return line.indent(2);
	}

}
