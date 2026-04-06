package io.github.jinputguard.result;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

public final class IndexedElementGuardFailure implements SubElementGuardFailure {

	private final String containerPath;
	private final int index;
	private final GuardFailure elementFailure;

	IndexedElementGuardFailure(@Nullable String containerPath, int index, @Nonnull GuardFailure elementFailure) {
		this.containerPath = containerPath;
		this.index = index;
		if (index < 0) {
			throw new IllegalArgumentException("index cannot be negative");
		}
		this.elementFailure = Objects.requireNonNull(elementFailure, "elementFailure cannot be null");
	}

	@Override
	public String getContainerPath() {
		return containerPath;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public GuardFailure getElementFailure() {
		return elementFailure;
	}

}
