package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public final class NonIndexedElementGuardFailure implements SubElementGuardFailure {

	private final String containerPath;
	private final GuardFailure elementFailure;

	NonIndexedElementGuardFailure(@Nullable String containerPath, @Nonnull GuardFailure elementFailure) {
		this.containerPath = containerPath;
		this.elementFailure = Objects.requireNonNull(elementFailure, "elementFailure cannot be null");
	}

	@Override
	public String getContainerPath() {
		return containerPath;
	}

	@Override
	public GuardFailure getElementFailure() {
		return elementFailure;
	}

}
