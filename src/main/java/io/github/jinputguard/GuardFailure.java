package io.github.jinputguard;

import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface GuardFailure {

	@Nonnull
	Path getPath();

	@Nonnull
	String getMessage();

	@Nullable
	Throwable getCause();

}
