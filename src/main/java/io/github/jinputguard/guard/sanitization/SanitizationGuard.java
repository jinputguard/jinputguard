package io.github.jinputguard.guard.sanitization;

import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public class SanitizationGuard<T> implements InputGuard<T, T> {

	private final @Nonnull Function<T, T> sanitizationFunction;

	public SanitizationGuard(@Nonnull Function<T, T> sanitizationFunction) {
		this.sanitizationFunction = Objects.requireNonNull(sanitizationFunction, "Sanitization function cannot be null");
	}

	@Override
	public GuardResult<T> process(T value) {
		T newValue = sanitizationFunction.apply(value);
		return GuardResult.success(newValue);
	}

	@Override
	public String toString() {
		return "SanitizationGuard -> " + sanitizationFunction.toString();
	}

}
