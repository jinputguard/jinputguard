package io.github.jinputguard;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface GuardFailure {

	@Nonnull
	String getPath();

	@Nonnull
	String getMessage();

	@Nullable
	Throwable getCause();

}
